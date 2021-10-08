package com.scrm.entity.pojo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 通讯录相关用户对象 we_user
 *
 * @author S-CRM
 * @date 2021-10-08 16:46:36
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_user")
@SuperBuilder
public class WeUser extends BaseModel {


    /**
     * 用户Id
     */
    private String userId;
    /**
     * 头像地址
     */
    private String headImageUrl;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String alias;
    /**
     * 性别。1表示男性，2表示女性
     */
    private Long gender;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 个人微信号
     */
    private String wxAccount;
    /**
     * 用户所属部门,使用逗号隔开,字符串格式存储
     */
    private String department;
    /**
     * 职务
     */
    private String position;
    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinTime;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * QQ号
     */
    private String qqAccount;
    /**
     * 座机
     */
    private String telephone;
    /**
     * 地址
     */
    private String address;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    /**
     * 备注
     */
    private String remark;
    /**
     * 离职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dimissionTime;
    /**
     * 离职后资源是否被分配(1:已分配;0:未分配;)
     */
    private Long isAllocate;
    /**
     * 激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业,6=删除
     */
    private Long isActivate;
}
