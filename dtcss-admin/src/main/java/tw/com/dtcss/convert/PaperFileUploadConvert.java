package tw.com.dtcss.convert;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddPaperFileUploadDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutPaperFileUploadDTO;
import tw.com.dtcss.pojo.entity.PaperFileUpload;

@Mapper(componentModel = "spring")
public interface PaperFileUploadConvert {

	PaperFileUpload addDTOToEntity(AddPaperFileUploadDTO addPaperFileUploadDTO);

	PaperFileUpload putDTOToEntity(PutPaperFileUploadDTO putPaperFileUploadDTO);
	
	
	
}
