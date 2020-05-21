package com.dreams.security;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dreams.sys.bo.UserBo;
import com.dreams.sys.po.UserPo;
import com.dreams.sys.service.UserService;

/**
 * UserDetails 用户对象
 * 
 * @author Ashe
 */

@Service
public class LoadUserService implements UserDetailsService 
{
	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPo userPo = this.userService.getUserByUserId(username);
		if(userPo != null)
		{
			return new UserBo(userPo.getUserId(),userPo.getUserName(),userPo.getPassword(),null);
		}
		throw new UsernameNotFoundException("没有用户");
	}

}
