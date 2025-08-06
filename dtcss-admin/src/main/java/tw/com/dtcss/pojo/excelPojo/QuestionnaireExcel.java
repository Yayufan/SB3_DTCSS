package tw.com.dtcss.pojo.excelPojo;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class QuestionnaireExcel {
	
	@ExcelProperty("會員姓名")
	private String chineseName;
	
	@ExcelProperty("會員信箱")
	private String email;

	@ExcelProperty("職稱")
	private String jobTitle;

	@ExcelProperty("任職單位類型")
	private String affiliation;

	@ExcelProperty("課程內容")
	private String courseContent;

	@ExcelProperty("教學方式")
	private String teachingMethod;

	@ExcelProperty("講者知識和專業")
	private String speakerKnowledge;

	@ExcelProperty("課程時間")
	private String courseTime;

	@ExcelProperty("場地")
	private String site;

	@ExcelProperty("活動流程")
	private String activityProcess;

	@ExcelProperty("活動整體滿意度")
	private String entireEvent;

	@ExcelProperty("主要期待")
	private String expect;

	@ExcelProperty("最滿意的部分")
	private String mostSatisfied;

	@ExcelProperty("可以改進的部分")
	private String improve;

	@ExcelProperty("未來希望加入的課程或主題")
	private String future;

	@ExcelProperty("創建時間")
	private LocalDateTime createDate;

}
