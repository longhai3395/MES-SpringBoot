package com.songpeng.sparchetype.system.config.shiro;

import com.songpeng.sparchetype.system.dto.SysMenuDto;
import com.songpeng.sparchetype.system.dto.SysRoleDto;
import com.songpeng.sparchetype.system.dto.SysUserDto;
import com.songpeng.sparchetype.system.entity.SysMenu;
import com.songpeng.sparchetype.system.entity.SysRole;
import com.songpeng.sparchetype.system.enums.ESysUser;
import com.songpeng.sparchetype.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Shiro 安全管理器领域模型
 * <p>
 * 如下可以使用 @Autowired，是因为 ShiroRealm 在 ShiroConfig 中已经配置
 *
 * @author SongPeng
 * @date 2019/10/17 8:08
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private ISysUserService sysUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SysUserDto user = (SysUserDto) principalCollection.getPrimaryPrincipal();
		Set<String> perms = new HashSet<>();
		if (CollectionUtils.isNotEmpty(user.getSysRoleDtos())) {
			for (SysRoleDto sr : user.getSysRoleDtos()) {
				if (CollectionUtils.isNotEmpty(sr.getSysMenuDtos())) {
					for (SysMenuDto sm : sr.getSysMenuDtos()) {
						if (StringUtils.isNotEmpty(sm.getPermission())) {
							perms.addAll(Arrays.asList(sm.getPermission().trim().split(",")));
						}
					}
				}
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		SysUserDto user = null;
		try {
			user = sysUserService.getUserAndRoleAndMenuByUsername(username);
		} catch (Exception e) {
			log.error("账号数据查询异常,请联系管理员", e);
			throw new UnknownAccountException("账号数据查询异常,请联系管理员");
		}

		// 账号不存在
		if (null == user) {
			log.error("账号不存在");
			throw new UnknownAccountException("账号不存在");
		}

		// 账号锁定
		if (!user.getStatus().equals(ESysUser.STATUS_NORMAL.getCode())) {
			log.error("账号已被锁定,请联系管理员");
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}
}
