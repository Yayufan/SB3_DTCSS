package tw.com.dtcss.pojo.DTO.putEntityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PutEmailTemplateDTO {

	@NotNull
	@Schema(description = "主鍵ID")
	private Long emailTemplateId;

	@Schema(description = "類別，用於拆分Email模板適合的人群")
	private String category;

	@NotBlank
	@Schema(description = "信件模板名稱")
	private String name;

	@Schema(description = "信件模板描述")
	private String description;

	@Schema(description = "用於儲存unlayer 的 design JSON數據")
	private String design;

	@Schema(description = "HTML 信件內容")
	private String htmlContent;

	@Schema(description = "當HTML 信件不支援時的 純文字內容")
	private String plainText;

}
