package com.scrm.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuKevin
 * @date 2021年09月26日 16:46
 */
@Data
public class BaseModel implements Serializable {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 企业Id
     */
    private String corpId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT)
    private String updateBy;

    /**
     * 修改时间
     */
    private String updateTIme;

    /**
     * 删除标示
     * 0: 正常, 1: 删除
     * @see com.scrm.entity.enums.BaseModelDelFlagEnum
     */
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

}
