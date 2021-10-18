package com.scrm.system;

import com.scrm.BaseTest;
import com.scrm.entity.pojo.company.WeCorpAccount;
import com.scrm.service.biz.company.WeCorpAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuKevin
 * @date 2021年10月18日 19:30
 */
@Slf4j
public class WeCorpAccountTest extends BaseTest {

    @Autowired
    private WeCorpAccountService weCorpAccountService;

    /**
     * `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
     * `corp_id` varchar(128) DEFAULT '' COMMENT '企业ID',
     * `company_name` varchar(128) DEFAULT '' COMMENT '企业名称',
     * `user_id` varchar(64) DEFAULT '' COMMENT '企业管理员的用户id',
     * `mobile` varchar(20) DEFAULT '' COMMENT '企业管理员的手机号',
     * `agent_id` varchar(64) DEFAULT '' COMMENT '企业自建应用id',
     * `id_secret` varchar(64) DEFAULT '' COMMENT '企微回调的自定义标示,corpId通过MD5加密',
     * `encoding_aes_key` varchar(128) DEFAULT '' COMMENT '企微回调的自定义AES-Key',
     * `token` varchar(64) DEFAULT '' COMMENT '企微回调的自定义token',
     * `corp_secret` varchar(64) DEFAULT '' COMMENT '企微通讯录密钥凭证',
     * `contact_secret` varchar(64) DEFAULT '' COMMENT '企微外部联系人密钥',
     * `agent_secret` varchar(64) DEFAULT '' COMMENT '企微自建应用密钥',
     * `public_secret` varchar(2000) DEFAULT '' COMMENT '公钥',
     * `private_secret` varchar(5000) DEFAULT '' COMMENT '私钥',
     * `account_type` varchar(64) DEFAULT '' COMMENT '企业类型：admin代表管理员企业，member代表成员企业',
     * `wx_qr_login_redirect_uri` varchar(64) DEFAULT '' COMMENT '企业微信扫码登陆系统回调地址',
     * `customer_churn_notice_switch` tinyint DEFAULT '0' COMMENT '客户流失通知开关 0:关闭 1:开启',
     * `customer_group_notice` tinyint DEFAULT '0' COMMENT '退群通知开关1：打开。0：关闭',
     * `status` tinyint DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
     * `del_flag` tinyint DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
     * `delete_timestamp` bigint DEFAULT '0' COMMENT '删除时间戳',
     * `create_by` varchar(256) DEFAULT '' COMMENT '创建者',
     * `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     * `update_by` varchar(256) DEFAULT '' COMMENT '更新者',
     * `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     */
    @Test
    public void insert() {
        WeCorpAccount account = WeCorpAccount.builder()
                .corpId("wwb7bc0ee558e60842")
                .companyName("我来了")
                .mobile("18767218370")
                .agentId("1000004")
                .idSecret("757b505cfd34c64c85ca5b5690ee5293")
                .encodingAesKey("du1fkGuXTcjPbFbwnjk1ZYM5ymiTIJ6oetTxP5jD3si")
                .token("yLUG9BOJw1BR0wihnzk77JwmAl9")
                .corpSecret("TjOdsTpxx3PyAyuNvnnhXe-CP1YgNIGWf32T6RTrt6c")
                .contactSecret("fKmYUqb1gaVbnOuCT9qSb59j-xOnMbDiThizOZ-_lwU")
                .agentSecret("QUhOY9W9W0vVOMCBQodBquZiHQoamtF6VDYZlly7nxY")
                .publicSecret("")
                .privateSecret("")
                .accountType("member")
                .wxQrLoginRedirectUri("").build();
        weCorpAccountService.save(account);
    }

}
