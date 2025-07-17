package tw.com.dtcss.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.dtcss.config.ProjectPropertiesConfig;
import tw.com.dtcss.convert.OrdersConvert;
import tw.com.dtcss.enums.OrderStatusEnum;
import tw.com.dtcss.exception.OrderPaymentException;
import tw.com.dtcss.exception.RegistrationInfoException;
import tw.com.dtcss.mapper.MemberMapper;
import tw.com.dtcss.mapper.OrdersMapper;
import tw.com.dtcss.pojo.DTO.addEntityDTO.AddOrdersDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutOrdersDTO;
import tw.com.dtcss.pojo.entity.Member;
import tw.com.dtcss.pojo.entity.Orders;
import tw.com.dtcss.service.OrdersItemService;
import tw.com.dtcss.service.OrdersService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

	private static final AtomicInteger counter = new AtomicInteger(0);

	private final ProjectPropertiesConfig projectPropertiesConfig;
	private final OrdersConvert ordersConvert;
	private final OrdersItemService ordersItemService;
	private final MemberMapper memberMapper;

	@Override
	public Orders getOrders(Long ordersId) {
		Orders orders = baseMapper.selectById(ordersId);
		return orders;
	}

	@Override
	public Orders getOrders(Long memberId, Long ordersId) {
		LambdaQueryWrapper<Orders> ordersQueryWrapper = new LambdaQueryWrapper<>();
		ordersQueryWrapper.eq(Orders::getMemberId, memberId).eq(Orders::getOrdersId, ordersId);

		Orders orders = baseMapper.selectOne(ordersQueryWrapper);

		return orders;
	}

	@Override
	public List<Orders> getOrdersList() {
		List<Orders> ordersList = baseMapper.selectList(null);
		return ordersList;
	}

	@Override
	public List<Orders> getOrdersList(Long memberId) {
		LambdaQueryWrapper<Orders> ordersQueryWrapper = new LambdaQueryWrapper<>();
		ordersQueryWrapper.eq(Orders::getMemberId, memberId);
		List<Orders> ordersList = baseMapper.selectList(ordersQueryWrapper);
		return ordersList;
	}

	@Override
	public IPage<Orders> getOrdersPage(Page<Orders> page) {
		Page<Orders> ordersPage = baseMapper.selectPage(page, null);
		return ordersPage;
	}

	@Override
	@Transactional
	public Long addOrders(AddOrdersDTO addOrdersDTO) {
		// 新增訂單本身
		Orders orders = ordersConvert.addDTOToEntity(addOrdersDTO);
		baseMapper.insert(orders);

		return orders.getOrdersId();
	}

	@Override
	public void updateOrders(PutOrdersDTO putOrdersDTO) {
		Orders orders = ordersConvert.putDTOToEntity(putOrdersDTO);
		baseMapper.updateById(orders);
	}

	@Override
	public void updateOrders(Long memberId, PutOrdersDTO putOrdersDTO) {
		Orders orders = ordersConvert.putDTOToEntity(putOrdersDTO);

		LambdaQueryWrapper<Orders> ordersQueryWrapper = new LambdaQueryWrapper<>();
		ordersQueryWrapper.eq(Orders::getMemberId, memberId).eq(Orders::getOrdersId, orders.getOrdersId());
		baseMapper.update(orders, ordersQueryWrapper);
	}

	@Override
	public void deleteOrders(Long ordersId) {
		baseMapper.deleteById(ordersId);
	}

	@Override
	public void deleteOrders(Long memberId, Long ordersId) {
		LambdaQueryWrapper<Orders> ordersQueryWrapper = new LambdaQueryWrapper<>();
		ordersQueryWrapper.eq(Orders::getMemberId, memberId).eq(Orders::getOrdersId, ordersId);
		baseMapper.delete(ordersQueryWrapper);
	}

	@Override
	public void deleteOrdersList(List<Long> ordersIds) {
		baseMapper.deleteBatchIds(ordersIds);
	}

	@Override
	public String payment(Long id) {

		// 創建全方位金流對象
		AllInOne allInOne = new AllInOne("");

		// 創建信用卡一次付清模式
		AioCheckOutOneTime aioCheckOutOneTime = new AioCheckOutOneTime();

		// 根據前端傳來的資料,獲取訂單
		Orders orders = this.getOrders(id);

		if (orders == null) {
			log.warn("Log: 查無此訂單, id=", id);
			throw new RegistrationInfoException("查無此訂單");
		}

		// 判斷是否已繳費
		if (OrderStatusEnum.PAYMENT_SUCCESS.getValue().equals(orders.getStatus())) {
			log.info("Log: 訂單已繳費, id=", id);
			throw new RegistrationInfoException("訂單已繳費");
		}

		/* 對應DTCSS 進行修改，產生付款表單時就判斷訂單使否過期 */

		// 計算是否超過 24 小時
		LocalDateTime createTime = orders.getCreateDate();
		LocalDateTime now = LocalDateTime.now();
		//now.minusSeconds(300) 測試用
		if (createTime.isBefore(now.minusHours(24))) {
			log.info("Log: 訂單已過期, id= " + id + "刪除會員 和 訂單");

			// 付款鏈結過期，刪除訂單
			baseMapper.deleteById(orders);

			// 付款鏈結過期，刪除會員
			memberMapper.deleteById(orders.getMemberId());

			throw new RegistrationInfoException("此付款連結已失效，請重新註冊並於獲得付款連結24小時內完成付款");
		}

		// 根據訂單ID,獲取這個訂單的持有者Member，如果訂單為子報名者要求產生，則直接拋出錯誤
		Member member = memberMapper.selectById(orders.getMemberId());
		if ("slave".equals(member.getGroupRole())) {
			throw new OrderPaymentException("Group registration must be paid by the primary registrant");
		}

		// 獲取當前時間並格式化，為了填充交易時間
		String nowFormat = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

		// 訂單交易編號,僅接受20位長度，編號不可重複，使用自定義生成function 處理
		aioCheckOutOneTime.setMerchantTradeNo(this.generateTradeNo());

		// 設定交易日期
		aioCheckOutOneTime.setMerchantTradeDate(nowFormat);

		// 綠界金流 僅接受新台幣 以及整數的金額，所以BigDecimal 要進行去掉無意義的0以及轉換成String
		aioCheckOutOneTime.setTotalAmount(orders.getTotalAmount().stripTrailingZeros().toPlainString());

		// 設定交易描述
		aioCheckOutOneTime.setTradeDesc(
				"This payment page only displays the total order amount. For details, please see the TOPBS2025 official website membership page, TOPPS 2025 registration fee");
		// 設定交易產品名稱概要,他沒有辦法一個item對應一個amount , 但可以透過#將item分段顯示
		// 例如: item01#item02#item03
		aioCheckOutOneTime.setItemName(orders.getItemsSummary());

		// 設定付款完成後，返回的前端網址，這邊讓他回到官網
		aioCheckOutOneTime.setClientBackURL("https://" + projectPropertiesConfig.getDomain());
		// 設定付款完成通知的網址,應該可以直接設定成後端API，實證有效
		aioCheckOutOneTime.setReturnURL("https://" + projectPropertiesConfig.getDomain() + "/prod-api/payment");
		// 這邊不需要他回傳額外付款資料
		aioCheckOutOneTime.setNeedExtraPaidInfo("N");
		// 設定英文介面
//		aioCheckOutOneTime.setLanguage("CHI");

		// 這邊使用他預留的客製化欄位,填入我們的訂單ID,當他透過return URL 觸發我們API時會回傳
		// 這邊因為還是只能String , 所以要將Long 類型做轉換
		aioCheckOutOneTime.setCustomField1(String.valueOf(orders.getOrdersId()));

		String form = allInOne.aioCheckOut(aioCheckOutOneTime, null);
		System.out.println("產生的form " + form);
		return form;

	}

	private String generateTradeNo() {
		// 獲取UTC當前時間戳
		long timestamp = System.currentTimeMillis();
		// 每次請求自增，並限制在 0~99 之間
		int count = counter.getAndIncrement() % 100;
		// 最後開頭用dtcss + 時間戳 + 自增數
		return "dtcss" + timestamp + String.format("%02d", count); // 生成交易编号
	}

}
