package com.zhaojufei.practice.myfeature.repeatsubmit.dto;

import lombok.Data;

/**
 * @ClassName: CurrentUserDTO
 * @Description: 当前登录用户信息
 * @Author: RuiXin Yu
 * @Date: 2019/7/1 13:22
 */
@Data
public class CurrentUserDTO {

    private String mobile;
    private String userId;
    private String userName;
    private String tenantId;
    private String tenantName;
    private Boolean sadmin;
}
