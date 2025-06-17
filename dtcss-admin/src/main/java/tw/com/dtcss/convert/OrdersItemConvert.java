package tw.com.dtcss.convert;

import java.util.List;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddOrdersItemDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutOrdersItemDTO;
import tw.com.dtcss.pojo.VO.OrdersItemVO;
import tw.com.dtcss.pojo.entity.OrdersItem;

@Mapper(componentModel = "spring")
public interface OrdersItemConvert {

	OrdersItem addDTOToEntity(AddOrdersItemDTO addOrdersItemDTO);

	OrdersItem putDTOToEntity(PutOrdersItemDTO putOrdersItemDTO);
	
	OrdersItemVO entityToVO(OrdersItem ordersItem);
	
	List<OrdersItemVO> entityListToVOList(List<OrdersItem> ordersItemList);
	
}
