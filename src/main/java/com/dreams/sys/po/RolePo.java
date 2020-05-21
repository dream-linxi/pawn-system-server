package com.dreams.sys.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author dreams-linxi
 * @date 2020/5/11 8:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolePo {
    private String roleId;
    private String roleName;
    private String roleDesc;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String modifyBy;
    private Date modifyTime;
}
