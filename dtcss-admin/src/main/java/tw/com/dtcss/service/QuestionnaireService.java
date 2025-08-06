package tw.com.dtcss.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.baomidou.mybatisplus.extension.service.IService;

import jakarta.servlet.http.HttpServletResponse;
import tw.com.dtcss.pojo.DTO.addEntityDTO.AddQuestionnaireDTO;
import tw.com.dtcss.pojo.VO.AttendeesVO;
import tw.com.dtcss.pojo.entity.Questionnaire;

/**
 * <p>
 * 問卷調查 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-08-06
 */
public interface QuestionnaireService extends IService<Questionnaire> {

	/**
	 * 根據idCard獲取與會者ID
	 * @param idCard
	 * @return 
	 */
	AttendeesVO getAttendeesVOByIdCard(String idCard);

	/**
	 * 根據主鍵ID獲取問卷紀錄
	 * @param questionnaireId
	 */
	Questionnaire getQuestionnaire(Long questionnaireId);

	/**
	 * 新增問卷調查紀錄
	 * @param addQuestionnaireDTO
	 */
	void saveQuestionnaire(AddQuestionnaireDTO addQuestionnaireDTO);

	/**
	 * 下載問卷紀錄Excel
	 * @param response
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 */
	void downloadExcel(HttpServletResponse response) throws UnsupportedEncodingException, IOException;

}
