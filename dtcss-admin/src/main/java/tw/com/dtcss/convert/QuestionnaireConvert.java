package tw.com.dtcss.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddQuestionnaireDTO;
import tw.com.dtcss.pojo.entity.Questionnaire;
import tw.com.dtcss.pojo.excelPojo.QuestionnaireExcel;

@Mapper(componentModel = "spring")
public interface QuestionnaireConvert {

	@Mapping(target = "expect", source = "expect", qualifiedByName = "listToString")
	Questionnaire addDTOToEntity(AddQuestionnaireDTO addQuestionnaireDTO);

	QuestionnaireExcel entityToExcel(Questionnaire questationnaire);
	
	
	@Named("listToString")
	default String listToString(List<String> strList) {
		return Joiner.on(",").skipNulls().join(strList);
	}

	@Named("stringToList")
	default List<String> stringToList(String str) {
		return Lists.newArrayList(Splitter.on(",").trimResults().omitEmptyStrings().split(str));
	}

	
}
