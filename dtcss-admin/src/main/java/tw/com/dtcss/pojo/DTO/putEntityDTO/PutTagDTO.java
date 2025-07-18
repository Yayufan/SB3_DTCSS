package tw.com.dtcss.pojo.DTO.putEntityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PutTagDTO {
	
	@NotNull
	@Schema(description = "唯一主鍵")
	private Long tagId;

	@Schema(description = "tag的分類,用於之後擴充其他表有對應的標籤可用,例如paper_tag表 可以透過type 欄位先去區分這是table需要的tag")
	private String type;

	@NotBlank
	@Schema(description = "標籤名稱,用於顯示")
	private String name;

	@Schema(description = "標籤的描述")
	private String description;

	@Schema(description = "標籤的狀態, 0為啟用  1為禁用")
	private Integer status;

	@Schema(description = "標籤的顯示顏色")
	private String color;
}
