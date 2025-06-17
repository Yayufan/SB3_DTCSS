package tw.com.dtcss.service.calculator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import tw.com.dtcss.enums.EarlyBirdPhaseEnum;
import tw.com.dtcss.enums.MemberCategoryEnum;
import tw.com.dtcss.exception.RegistrationInfoException;
import tw.com.dtcss.pojo.DTO.addEntityDTO.AddMemberDTO;
import tw.com.dtcss.saToken.StpKit;
import tw.com.dtcss.utils.CountryUtil;

@Component(value="earlyBirdPhaseOneFeeCalculator")
public class EarlyBirdPhaseOneFeeCalculator implements RegistrationFeeCalculator {	
	
	@Override
	public BigDecimal calculateFee(AddMemberDTO addMemberDTO) {
		
		MemberCategoryEnum memberCategoryEnum = MemberCategoryEnum.fromValue(addMemberDTO.getCategory());
		
		if (CountryUtil.isNational(addMemberDTO.getCountry())) {
			return switch (memberCategoryEnum) {
			case MEMBER -> BigDecimal.valueOf(700L); // Member(會員)
			case OTHERS -> BigDecimal.valueOf(600L); // Others(學生或護士)
			case NON_MEMBER -> BigDecimal.valueOf(1000L); // Non-Member(非會員)
			default -> throw new RegistrationInfoException("category is not in system");
			};
		} else {
			return switch (memberCategoryEnum) {
			case MEMBER -> BigDecimal.valueOf(9600L); // Member
			case OTHERS -> BigDecimal.valueOf(4800L); // Others
			case NON_MEMBER -> BigDecimal.valueOf(12800L); // Non-member
			default -> throw new RegistrationInfoException("category is not in system");
			};
		}
	}
}