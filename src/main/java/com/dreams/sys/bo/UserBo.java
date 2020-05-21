package com.dreams.sys.bo;

import java.util.Set;

import com.dreams.sys.po.UserPo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserBo extends UserPo implements UserDetails
{
	private String userId;
	private String password;
	private String username;
	private String phone;
	private String openId;

	private String keyWord;
	private String createStartTime;
	private String createEndTime;

	private Integer page;
	private Integer limit;

	private Set<GrantedAuthority> authorities = null;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true ;
	private boolean enabled = true;
	
	public UserBo() { }

	public UserBo(String userId,String username,String password,String phone)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

	public UserBo(String userId,String username,String password,String phone,String openId)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.openId = openId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public String getOpenId() {
		return openId;
	}

	@Override
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
