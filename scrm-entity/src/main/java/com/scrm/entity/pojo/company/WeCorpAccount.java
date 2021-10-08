package com.scrm.entity.pojo.company;

import com.scrm.entity.common.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.SuperBuilder;

/**
 * 企业相关配置对象 we_corp_account
 *
 * @author S-CRM
 * @date 2021-10-08 15:58:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("we_corp_account")
@SuperBuilder
public class WeCorpAccount extends BaseModel {

    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业管理员的用户id
     */
    private String userId;
    /**
     * 企业管理员的手机号
     */
    private String mobile;
    /**
     * 企业自建应用id
     */
    private String agentId;
    /**
     * 企微回调的自定义标示,corpId通过MD5加密
     */
    private String idSecret;
    /**
     * 企微回调的自定义AES-Key
     */
    private String encodingAesKey;
    /**
     * 企微回调的自定义token
     */
    private String token;
    /**
     * 企微通讯录密钥凭证
     */
    private String corpSecret;
    /**
     * 企微外部联系人密钥
     */
    private String contactSecret;
    /**
     * 企微自建应用密钥
     */
    private String agentSecret;
    /**
     * 公钥
     */
    private String publicSecret;
    /**
     * 私钥
     */
    private String privateSecret;
    /**
     * 企业类型：admin代表管理员企业，member代表成员企业
     */
    private String accountType;
    /**
     * 企业微信扫码登陆系统回调地址
     */
    private String wxQrLoginRedirectUri;
    /**
     * 客户流失通知开关 0:关闭 1:开启
     */
    private Long customerChurnNoticeSwitch;
    /**
     * 退群通知开关1：打开。0：关闭
     */
    private Long customerGroupNotice;
    /**
     * 帐号状态（0正常 1停用）
     */
    private Long status;
}
