package com.scrm.vo.system;

import lombok.Builder;
import lombok.Data;

/**
 * @author liuKevin
 * @date 2021年10月08日 17:43
 */
@Data
@Builder
public class CaptchaVO {

    /**
     * 图片流
     */
    private String img;
    /**
     * 图片的唯一标示
     */
    private String uuid;

}
