package tw.com.dtcss.convert;

import java.util.List;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddOrdersDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutOrdersDTO;
import tw.com.dtcss.pojo.VO.OrdersVO;
import tw.com.dtcss.pojo.entity.Orders;

@Mapper(componentModel = "spring")
public interface OrdersConvert {

	Orders addDTOToEntity(AddOrdersDTO addOrdersDTO);

	Orders putDTOToEntity(PutOrdersDTO putOrdersDTO);
	
	OrdersVO entityToVO(Orders orders);
	
	List<OrdersVO> entityListToVOList(List<Orders> ordersList);
	
}
