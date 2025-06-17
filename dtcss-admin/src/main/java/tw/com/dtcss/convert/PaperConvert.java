package tw.com.dtcss.convert;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.PutPaperForAdminDTO;
import tw.com.dtcss.pojo.DTO.addEntityDTO.AddPaperDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutPaperDTO;
import tw.com.dtcss.pojo.VO.PaperVO;
import tw.com.dtcss.pojo.VO.ReviewVO;
import tw.com.dtcss.pojo.entity.Paper;

@Mapper(componentModel = "spring")
public interface PaperConvert {

	Paper addDTOToEntity(AddPaperDTO addPaperDTO);

	Paper putDTOToEntity(PutPaperDTO putPaperDTO);
	
	Paper putForAdminDTOToEntity(PutPaperForAdminDTO putPaperForAdminDTO);
	
	PaperVO entityToVO(Paper paper);
	
	ReviewVO entityToReviewVO(Paper paper);
	
	
}
