package tw.com.dtcss.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 問卷調查
 * </p>
 *
 * @author Joey
 * @since 2025-08-06
 */
@Getter
@Setter
@TableName("questionnaire")
@Schema(name = "Questionnaire", description = "問卷調查")
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主鍵ID")
    @TableId("questionnaire_id")
    private Long questionnaireId;

    @Schema(description = "與會者ID")
    @TableField("attendees_id")
    private Long attendeesId;

    @Schema(description = "職稱")
    @TableField("job_title")
    private String jobTitle;

    @Schema(description = "任職單位")
    @TableField("affiliation")
    private String affiliation;

    @Schema(description = "課程內容")
    @TableField("course_content")
    private String courseContent;

    @Schema(description = "教學方式")
    @TableField("teaching_method")
    private String teachingMethod;

    @Schema(description = "講者知識和專業")
    @TableField("speaker_knowledge")
    private String speakerKnowledge;

    @Schema(description = "課程時間")
    @TableField("course_time")
    private String courseTime;

    @Schema(description = "場地")
    @TableField("site")
    private String site;

    @Schema(description = "活動流程")
    @TableField("activity_process")
    private String activityProcess;

    @Schema(description = "活動整體滿意度")
    @TableField("entire_event")
    private String entireEvent;

    @Schema(description = "主要期待")
    @TableField("expect")
    private String expect;

    @Schema(description = "最滿意的部分")
    @TableField("most_satisfied")
    private String mostSatisfied;

    @Schema(description = "可以改進的部分")
    @TableField("improve")
    private String improve;

    @Schema(description = "未來希望加入的課程或主題")
    @TableField("future")
    private String future;

    @Schema(description = "創建者")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "創建時間")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @Schema(description = "最後修改者")
    @TableField("update_by")
    private String updateBy;

    @Schema(description = "最後修改時間")
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;

    @Schema(description = "邏輯刪除,預設為0活耀,1為刪除")
    @TableField("is_deleted")
    @TableLogic
    private Byte isDeleted;
}
