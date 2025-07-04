package tw.com.dtcss.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.com.dtcss.system.mapper.SysRoleMapper;
import tw.com.dtcss.system.pojo.entity.SysRole;
import tw.com.dtcss.system.service.SysRoleService;

/**
 * <p>
 * 角色表 - 透過設置角色達成較廣泛的權限管理 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
