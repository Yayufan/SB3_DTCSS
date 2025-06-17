package tw.com.dtcss.convert;

import java.util.List;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddCheckinRecordDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutCheckinRecordDTO;
import tw.com.dtcss.pojo.VO.CheckinRecordVO;
import tw.com.dtcss.pojo.entity.CheckinRecord;
import tw.com.dtcss.pojo.excelPojo.AttendeesExcel;
import tw.com.dtcss.pojo.excelPojo.CheckinRecordExcel;

@Mapper(componentModel = "spring")
public interface CheckinRecordConvert {

	CheckinRecord addDTOToEntity(AddCheckinRecordDTO addCheckinRecordDTO);

	CheckinRecord putDTOToEntity(PutCheckinRecordDTO putCheckinRecordDTO);

	CheckinRecordVO entityToVO(CheckinRecord checkinRecord);

	List<CheckinRecordVO> entityListToVOList(List<CheckinRecord> checkinRecordList);

	CheckinRecordExcel attendeesExcelToCheckinRecordExcel(AttendeesExcel attendeesExcel);
	
}
