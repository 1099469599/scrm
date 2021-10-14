package com.scrm.dto.wecom;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuKevin
 * @date 2021年10月13日 15:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeAccessTokenDTO extends BaseWeComDTO{

    private String access_token;
    private Long expires_in;
    private String provider_access_token;

}
