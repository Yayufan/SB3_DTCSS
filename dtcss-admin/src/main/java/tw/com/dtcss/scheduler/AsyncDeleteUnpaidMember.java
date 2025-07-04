package tw.com.dtcss.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.dtcss.enums.OrderStatusEnum;
import tw.com.dtcss.mapper.MemberMapper;
import tw.com.dtcss.mapper.OrdersMapper;
import tw.com.dtcss.pojo.entity.Member;
import tw.com.dtcss.pojo.entity.Orders;

@RequiredArgsConstructor
@Component
@Slf4j
public class AsyncDeleteUnpaidMember {

	private final MemberMapper memberMapper;
	private final OrdersMapper ordersMapper;

	// 使用 Cron 表達式設置定時任務 (每分鐘第零秒執行此任務，測試時使用)
//		@Scheduled(cron = "0 * * * * ?")
	// 使用 Cron 表達式設置定時任務 (每天凌晨2點執行 cron = "0 0 2 * * ?" )
	@Scheduled(cron = "0 0 2 * * ?")
	public void deleteUnpaidMember() {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime deadline = now.minusHours(24);
		
		// 1.先查詢訂單中尚未付款的訂單，並刪除沒繳費的訂單
		LambdaQueryWrapper<Orders> ordersWrapper = new LambdaQueryWrapper<>();
		ordersWrapper.eq(Orders::getStatus, OrderStatusEnum.UNPAID.getValue())
		// 創建時間 小於 當前時間減去 24小時 ， 代表過期
		.lt(Orders::getCreateDate,deadline);
		List<Orders> orderList = ordersMapper.selectList(ordersWrapper);

		ordersMapper.delete(ordersWrapper);
		
		// 2.提取尚未付款訂單 的 會員
		List<Long> memberIds = orderList.stream().map(Orders::getMemberId).collect(Collectors.toList());

		// 3.如果會員ID列表為空,直接返回不用往下執行
		if (memberIds.isEmpty()) {
			System.out.println("沒有需要，刪除的會員");
			log.info("Log:沒有需要，刪除的會員");
			return;
		}

		// 4.刪除尚未付款的會員，讓他們能重新註冊
		LambdaQueryWrapper<Member> memberWrapper = new LambdaQueryWrapper<>();
		memberWrapper.in(Member::getMemberId, memberIds);
		memberMapper.delete(memberWrapper);

		System.out.println("刪除未繳費的會員");
		log.info("Log:刪除未繳費的會員");
	}
}
