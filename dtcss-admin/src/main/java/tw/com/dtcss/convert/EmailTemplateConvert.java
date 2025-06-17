package tw.com.dtcss.convert;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddEmailTemplateDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutEmailTemplateDTO;
import tw.com.dtcss.pojo.entity.EmailTemplate;

@Mapper(componentModel = "spring")
public interface EmailTemplateConvert {

	EmailTemplate insertDTOToEntity(AddEmailTemplateDTO addArticleDTO);

	EmailTemplate updateDTOToEntity(PutEmailTemplateDTO updateArticleDTO);
	
}
