package tw.com.dtcss.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tw.com.dtcss.convert.QuestionnaireConvert;
import tw.com.dtcss.enums.MemberCategoryEnum;
import tw.com.dtcss.exception.RegistrationClosedException;
import tw.com.dtcss.manager.MemberManager;
import tw.com.dtcss.mapper.AttendeesMapper;
import tw.com.dtcss.mapper.MemberMapper;
import tw.com.dtcss.mapper.QuestionnaireMapper;
import tw.com.dtcss.pojo.DTO.IdCardAndEmailDTO;
import tw.com.dtcss.pojo.DTO.addEntityDTO.AddQuestionnaireDTO;
import tw.com.dtcss.pojo.VO.AttendeesVO;
import tw.com.dtcss.pojo.entity.Attendees;
import tw.com.dtcss.pojo.entity.Member;
import tw.com.dtcss.pojo.entity.Questionnaire;
import tw.com.dtcss.pojo.excelPojo.QuestionnaireExcel;
import tw.com.dtcss.service.QuestionnaireService;

/**
 * <p>
 * 問卷調查 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-08-06
 */
@Service
@RequiredArgsConstructor
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire>
		implements QuestionnaireService {

	private final MemberMapper memberMapper;
	private final MemberManager memberManager;
	private final AttendeesMapper attendeesMapper;
	private final QuestionnaireConvert questionnaireConvert;

	@Override
	public AttendeesVO getAttendeesVOByIdCard(IdCardAndEmailDTO idCardAndEmailDTO) {

		// 1.透過身分證字號查找會員資料
		LambdaQueryWrapper<Member> memberWrapper = new LambdaQueryWrapper<>();
		memberWrapper.eq(Member::getIdCard, idCardAndEmailDTO.getIdCard())
				.eq(Member::getEmail, idCardAndEmailDTO.getEmail())
				;
		Member member = memberMapper.selectOne(memberWrapper);

		// 2.如果查無資料則報錯
		if (member == null) {
			throw new RegistrationClosedException("查無此用戶");
		}

		// 3.使用會員id查詢是否具備與會資格
		LambdaQueryWrapper<Attendees> attendeesWrapper = new LambdaQueryWrapper<>();
		attendeesWrapper.eq(Attendees::getMemberId, member.getMemberId());
		Attendees attendees = attendeesMapper.selectOne(attendeesWrapper);

		// 4.如果查無資料則報錯
		if (attendees == null) {
			throw new RegistrationClosedException("用戶不具備與會資格");
		}

		// 5.組裝與會者資料
		AttendeesVO attendeesVO = new AttendeesVO();
		attendeesVO.setAttendeesId(attendees.getAttendeesId());
		attendeesVO.setMemberId(member.getMemberId());
		attendeesVO.setMember(member);

		return attendeesVO;

	}

	@Override
	public Questionnaire getQuestionnaire(Long questionnaireId) {
		return baseMapper.selectById(questionnaireId);
	}

	@Override
	public void saveQuestionnaire(AddQuestionnaireDTO addQuestionnaireDTO) {
		Questionnaire questionnaire = questionnaireConvert.addDTOToEntity(addQuestionnaireDTO);

		LambdaQueryWrapper<Questionnaire> questionnaireWrapper = new LambdaQueryWrapper<>();
		questionnaireWrapper.eq(Questionnaire::getAttendeesId, questionnaire.getAttendeesId());
		Questionnaire oldData = baseMapper.selectOne(questionnaireWrapper);

		if (oldData != null) {
			throw new RegistrationClosedException("已完成問卷調查，不需重複填寫");
		}

		baseMapper.insert(questionnaire);
	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 ， 和easyexcel没有关系
		String fileName = URLEncoder.encode("問卷調查列表", "UTF-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");

		// 查詢所有會員，用來填充與會者的基本資訊
		List<Member> memberList = memberManager.getAllMembersEfficiently();

		// 訂單轉成一對一 Map，key為 memberId, value為訂單本身
		Map<Long, Member> memberMap = memberList.stream()
				.collect(Collectors.toMap(Member::getMemberId, Function.identity()));

		// 查詢所有問卷紀錄
		List<Questionnaire> questionnaireList = baseMapper.selectList(null);

		List<QuestionnaireExcel> excelData = questionnaireList.stream().map(questionnaire -> {

			Attendees attendees = attendeesMapper.selectById(questionnaire.getAttendeesId());
			Member member = memberMap.get(attendees.getMemberId());

			QuestionnaireExcel questionnaireExcel = questionnaireConvert.entityToExcel(questionnaire);
			questionnaireExcel.setChineseName(member.getChineseName());
			questionnaireExcel.setEmail(member.getEmail());

			return questionnaireExcel;

		}).collect(Collectors.toList());

		EasyExcel.write(response.getOutputStream(), QuestionnaireExcel.class).sheet("問卷調查列表").doWrite(excelData);

	}

}
