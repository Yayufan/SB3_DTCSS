package tw.com.dtcss.pojo.DTO.addEntityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddPublishFileDTO {

	@Schema(description = "群組類型，用於分別是屬於哪個頁面的檔案")
	private String groupType;

	@Schema(description = "二級類別,如果群組類別底下還有細分類別,可以用這個")
	private String type;

	@Schema(description = "檔名")
	private String name;

	@Schema(description = "檔案描述")
	private String description;

	@Schema(description = "排序值")
	private Integer sort;

	@Schema(description = "儲存地址")
	private String path;
}
