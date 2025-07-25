package tw.com.dtcss.system.pojo.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CheckFileVO {

	@Schema(description = "檔案是否存在，存在為true，不存在為false")
	private Boolean exist;

	@Schema(description = "檔案的瀏覽地址")
	private String path;

}
