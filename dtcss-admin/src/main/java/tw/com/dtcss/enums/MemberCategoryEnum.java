package tw.com.dtcss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tw.com.dtcss.exception.RegistrationInfoException;

/**
 * 對標 member table , category 屬性
 * 
 */
@Getter
@AllArgsConstructor
public enum MemberCategoryEnum {
	MEMBER(1, "Member", "Member(會員)"), OTHERS(2, "Others", "Others(非會員)"),
	NON_MEMBER(3, "Non-Member", "Non-Member(非會員醫師)"), MVP(4, "MVP", "MVP"), SPEAKER(5, "Speaker", "講者"),
	MODERATOR(6, "Moderator", "座長"), STAFF(7, "Staff", "工作人員"), DOCTOR(8, "doctor", "9/6醫師手術直播研討會"), NURSE(9, "nurse", "9/7護理人員研討會"),
	TZU_CHI_NURSE(10, "nurse(Tzu Chi)", "9/7護理人員研討會_慈濟體系專區");

	private final Integer value;
	private final String labelEn;
	private final String labelZh;

	public static MemberCategoryEnum fromValue(Integer value) {
		for (MemberCategoryEnum type : values()) {
			if (type.value.equals(value))
				return type;
		}
		throw new RegistrationInfoException("無效的會員身份值: " + value);
	}

}
