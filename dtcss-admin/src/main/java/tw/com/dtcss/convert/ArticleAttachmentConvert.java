package tw.com.dtcss.convert;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddArticleAttachmentDTO;
import tw.com.dtcss.pojo.entity.ArticleAttachment;

@Mapper(componentModel = "spring")
public interface ArticleAttachmentConvert {
	ArticleAttachment insertDTOToEntity(AddArticleAttachmentDTO insertArticleAttachmentDTO);
}
