package tw.com.dtcss.service.calculator;

import java.math.BigDecimal;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddMemberDTO;

//定義價格計算介面
public interface RegistrationFeeCalculator {
	BigDecimal calculateFee(AddMemberDTO addMemberDTO);
}
