package com.scrm.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口权限的vo返回
 *
 * @author liuKevin
 * @date 2021年10月11日 14:59
 */
@Data
@NoArgsConstructor
public class AuthPermissionVO {


    private Long id;

    private String name;

    private String path;

    private Long parentId;

    private Boolean choseFlag = false;

    private String description;

}
