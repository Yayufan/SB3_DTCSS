package tw.com.dtcss.convert;

import java.util.List;

import org.mapstruct.Mapper;

import tw.com.dtcss.pojo.DTO.addEntityDTO.AddSettingDTO;
import tw.com.dtcss.pojo.DTO.putEntityDTO.PutSettingDTO;
import tw.com.dtcss.pojo.VO.SettingVO;
import tw.com.dtcss.pojo.entity.Setting;

@Mapper(componentModel = "spring")
public interface SettingConvert {

	Setting addDTOToEntity(AddSettingDTO addSettingDTO);

	Setting putDTOToEntity(PutSettingDTO putSettingDTO);
	
	SettingVO entityToVO(Setting setting);
	
	List<SettingVO> entityListToVOList(List<Setting> settingList);
	
}
