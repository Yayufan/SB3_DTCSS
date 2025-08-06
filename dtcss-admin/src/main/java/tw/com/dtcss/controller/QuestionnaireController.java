package tw.com.dtcss.controller;

import java.io.IOException;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tw.com.dtcss.pojo.DTO.IdCardAndEmailDTO;
import tw.com.dtcss.pojo.DTO.addEntityDTO.AddQuestionnaireDTO;
import tw.com.dtcss.pojo.VO.AttendeesVO;
import tw.com.dtcss.pojo.entity.Questionnaire;
import tw.com.dtcss.service.QuestionnaireService;
import tw.com.dtcss.utils.R;

/**
 * <p>
 * 問卷調查 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-08-06
 */
@Tag(name = "問卷調查API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

	private final QuestionnaireService questionnaireService;
	
	@PostMapping("id_card")
	@Operation(summary = "透過身分證字號拿到與會者資料")
	public R<AttendeesVO> getAttendeesVOByIdCard(@RequestBody @Valid IdCardAndEmailDTO idCardAndEmailDTO) {
		AttendeesVO attendeesVO = questionnaireService.getAttendeesVOByIdCard(idCardAndEmailDTO);
		return R.ok(attendeesVO);
	}
	
	@GetMapping("{id}")
	@Operation(summary = "查詢單一問卷")
	public R<Questionnaire> getQuestionnaire(@PathVariable("id") Long questionnaireId ) {
		questionnaireService.getQuestionnaire(questionnaireId);
		return R.ok();
	}
	
	@PostMapping
	@Operation(summary = "新增問卷，一個與會者只能提交一份問卷表單")
	public R<Void> saveMember(@RequestBody @Valid AddQuestionnaireDTO addQuestionnaireDTO) {
		questionnaireService.saveQuestionnaire(addQuestionnaireDTO);

		return R.ok();
	}
	
	@Operation(summary = "下載問卷excel列表")
	@SaCheckRole("super-admin")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@GetMapping("/download-excel")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		questionnaireService.downloadExcel(response);
	}
	
	
}
