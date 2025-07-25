package tw.com.dtcss.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.com.dtcss.system.mapper.SysRoleMenuMapper;
import tw.com.dtcss.system.pojo.entity.SysRoleMenu;
import tw.com.dtcss.system.service.SysRoleMenuService;

/**
 * <p>
 * 角色與菜單 - 多對多關聯表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
