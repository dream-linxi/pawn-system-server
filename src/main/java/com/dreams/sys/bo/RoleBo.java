package com.dreams.sys.bo;

import com.dreams.sys.po.RolePo;
import lombok.Data;

/**
 * @author dreams-linxi
 * @date 2020/5/11 8:32
 */
@Data
public class RoleBo extends RolePo {
    private String keyWord;
    private String createStartTime;
    private String createEndTime;
    private Integer page;
    private Integer limit;
}
