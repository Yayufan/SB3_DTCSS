package tw.com.dtcss.pojo.DTO.addEntityDTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddQuestionnaireDTO {

	@NotNull
	@Schema(description = "與會者ID")
	private Long attendeesId;

	@NotBlank
	@Schema(description = "職稱")
	private String jobTitle;

	@NotBlank
	@Schema(description = "任職單位")
	private String affiliation;

	@NotBlank
	@Schema(description = "課程內容")
	private String courseContent;

	@NotBlank
	@Schema(description = "教學方式")
	private String teachingMethod;

	@NotBlank
	@Schema(description = "講者知識和專業")
	private String speakerKnowledge;

	@NotBlank
	@Schema(description = "課程時間")
	private String courseTime;

	@NotBlank
	@Schema(description = "場地")
	private String site;

	@NotBlank
	@Schema(description = "活動流程")
	private String activityProcess;

	@NotBlank
	@Schema(description = "活動整體滿意度")
	private String entireEvent;

	@NotEmpty
	@Schema(description = "主要期待")
	private List<String> expect;

	@NotBlank
	@Schema(description = "最滿意的部分")
	private String mostSatisfied;

	@NotBlank
	@Schema(description = "可以改進的部分")
	private String improve;

	@NotBlank
	@Schema(description = "未來希望加入的課程或主題")
	private String future;

}
