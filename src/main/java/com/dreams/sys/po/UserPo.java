package com.dreams.sys.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 账户实体类
 * @author dreams-linxi
 * @date 2020/5/9 12:24
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPo
{
	private String userId;
	private String userName;
	private String password;
	private String sex;
	private String phone;
	private String qqCode;
	private String isAdmin;
	private String isDel;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	private String createBy;
	private Date modifyTime;
	private String  modifyBy;
	private String comment;
	private String openId;
}
