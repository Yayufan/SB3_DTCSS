package tw.com.dtcss.pojo.DTO.putEntityDTO;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PutPaperFileUploadDTO {
	@Schema(description = "主鍵ID")
	private Long paperFileUploadId;

	@Schema(description = "摘要ID")
	private Long paperId;

	@Schema(description = "分類成 abstructs_pdf, abstructs_docx, slide, offical_document; 用來接收 投稿PDF、投稿WORD、投稿PPT、公文檔案")
	@TableField("type")
	private String type;

	@Schema(description = "檔案名稱-可與傳送時不同")
	@TableField("file_name")
	private String fileName;

	@Schema(description = "檔案在minio儲存的路徑")
	@TableField("path")
	private String path;

}
