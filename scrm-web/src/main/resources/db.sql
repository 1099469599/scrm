-- 角色相关
DROP TABLE IF EXISTS `we_role`;
CREATE TABLE `we_role`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `corp_id`          varchar(128)        DEFAULT '' COMMENT '企业ID',
    `role_name`        varchar(64)         DEFAULT '' COMMENT '角色名称',
    `role_sort`        tinyint(4)          DEFAULT '0' COMMENT '显示顺序',
    `data_scope`       tinyint(4)          DEFAULT '4' COMMENT '数据范围(1:全部数据权限 2:本部门数据权限 3:本部门及以下数据权限 4:仅个人数据权限)',
    `visible`          tinyint(4)          DEFAULT '0' COMMENT '是否可见(0=可见,1=不可见)',
    `status`           tinyint(4)          DEFAULT '0' COMMENT '是否有效(0=有效, 1=无效)',
    `remark`           varchar(512)        DEFAULT '' COMMENT '备注',
    `del_flag`         tinyint(4)          DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `delete_timestamp` bigint(20) NOT NULL DEFAULT '0' COMMENT '删除时间戳',
    `create_by`        varchar(256)        DEFAULT '' COMMENT '创建者',
    `create_time`      datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(256)        DEFAULT '' COMMENT '更新者',
    `update_time`      datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_corpid_delflag` (`corp_id`, `del_flag`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色信息表';


-- 菜单相关
DROP TABLE IF EXISTS `we_menu`;
CREATE TABLE `we_menu`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`        bigint(20)          DEFAULT '0' COMMENT '父菜单ID',
    `name`             varchar(64)         DEFAULT '' COMMENT '菜单名称',
    `type`             tinyint(4)          DEFAULT '1' COMMENT '菜单类型(菜单<10, 接口=10)',
    `order_num`        int(11)             DEFAULT '0' COMMENT '显示顺序',
    `path`             varchar(256)        DEFAULT '' COMMENT '路由地址',
    `component`        varchar(256)        DEFAULT NULL COMMENT '组件路径',
    `icon`             varchar(128)        DEFAULT '#' COMMENT '菜单图标',
    `visible`          tinyint(4)          DEFAULT '0' COMMENT '菜单状态(0:显示,1:隐藏,2:仅超管可见)',
    `status`           tinyint(4)          DEFAULT '0' COMMENT '菜单状态（0正常,1停用）',
    `remark`           varchar(512)        DEFAULT '' COMMENT '备注',
    `del_flag`         tinyint(4)          DEFAULT '0' COMMENT '删除标志（0未删除、1已删除）',
    `delete_timestamp` bigint(20) NOT NULL DEFAULT '0' COMMENT '删除时间戳',
    `create_by`        varchar(256)        DEFAULT '' COMMENT '创建者',
    `create_time`      datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(256)        DEFAULT '' COMMENT '更新者',
    `update_time`      datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单权限表';


-- 菜单和角色的关联表
DROP TABLE IF EXISTS `we_role_menu`;
CREATE TABLE `we_role_menu`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `corp_id`          varchar(128) DEFAULT '' COMMENT '企业ID',
    `role_id`          bigint(20)   DEFAULT '0' COMMENT '角色ID(=we_role.id)',
    `menu_id`          bigint(20)   DEFAULT '0' COMMENT '菜单ID(=we_menu.id)',
    `del_flag`         tinyint(4)   DEFAULT '0' COMMENT '删除标志（0未删除、1已删除）',
    `delete_timestamp` bigint(20)   DEFAULT '0' COMMENT '删除时间戳',
    `create_by`        varchar(256) DEFAULT '' COMMENT '创建人员',
    `create_time`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(256) DEFAULT '' COMMENT '修改人员',
    `update_time`      datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_corpId_roleId_menuId` (`corp_id`, `role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 153775
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色和菜单关联表';

-- 用户和角色的关联表
DROP TABLE IF EXISTS `we_user_role`;
CREATE TABLE `we_user_role`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `corp_id`          varchar(128) DEFAULT '' COMMENT '企业ID',
    `role_id`          bigint(20)   DEFAULT '0' COMMENT '角色ID(=we_role.id)',
    `user_id`          bigint(20)   DEFAULT '0' COMMENT '用户Id(=we_user.id)',
    `user_com_id`      varchar(256) DEFAULT '' COMMENT '用户ID(=we_user.user_id)',
    `del_flag`         tinyint(4)   DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `delete_timestamp` bigint(20)   DEFAULT '0' COMMENT '删除时间戳',
    `create_by`        varchar(256) DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(256) DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_corpid_userid` (`corp_id`, `user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 629
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户和角色关联表';


-- 企业相关配置
DROP TABLE if EXISTS `we_corp_account`;
CREATE TABLE `we_corp_account`
(
    `id`                           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `corp_id`                      varchar(128)  DEFAULT '' COMMENT '企业ID',
    `company_name`                 varchar(128)  DEFAULT '' COMMENT '企业名称',
    `user_id`                      varchar(64)   DEFAULT '' COMMENT '企业管理员的用户id',
    `mobile`                       varchar(20)   DEFAULT '' COMMENT '企业管理员的手机号',
    `agent_id`                     varchar(64)   DEFAULT '' COMMENT '企业自建应用id',
    `id_secret`                    varchar(64)   DEFAULT '' COMMENT '企微回调的自定义标示,corpId通过MD5加密',
    `encoding_aes_key`             varchar(128)  DEFAULT '' COMMENT '企微回调的自定义AES-Key',
    `token`                        varchar(64)   DEFAULT '' COMMENT '企微回调的自定义token',
    `corp_secret`                  varchar(64)   DEFAULT '' COMMENT '企微通讯录密钥凭证',
    `contact_secret`               varchar(64)   DEFAULT '' COMMENT '企微外部联系人密钥',
    `agent_secret`                 varchar(64)   DEFAULT '' COMMENT '企微自建应用密钥',
    `public_secret`                varchar(2000) DEFAULT '' COMMENT '公钥',
    `private_secret`               varchar(5000) DEFAULT '' COMMENT '私钥',
    `account_type`                 varchar(64)   DEFAULT '' COMMENT '企业类型：admin代表管理员企业，member代表成员企业',
    `wx_qr_login_redirect_uri`     varchar(64)   DEFAULT '' COMMENT '企业微信扫码登陆系统回调地址',
    `customer_churn_notice_switch` tinyint(4)    DEFAULT '0' COMMENT '客户流失通知开关 0:关闭 1:开启',
    `customer_group_notice`        tinyint(4)    DEFAULT '0' COMMENT '退群通知开关1：打开。0：关闭',
    `status`                       tinyint(4)    DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`                     tinyint(4)    DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `delete_timestamp`             bigint(20)    DEFAULT '0' COMMENT '删除时间戳',
    `create_by`                    varchar(256)  DEFAULT '' COMMENT '创建者',
    `create_time`                  datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`                    varchar(256)  DEFAULT '' COMMENT '更新者',
    `update_time`                  datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_corpid_deletetimestamp` (`corp_id`, `delete_timestamp`) USING BTREE,
    UNIQUE KEY `idx_idsecret` (`id_secret`) USING BTREE,
    KEY `idx_companyname` (`company_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT ='企业相关配置';

-- 初始化企业
INSERT INTO `scrm`.`we_corp_account`(`id`, `corp_id`, `company_name`, `user_id`, `mobile`, `agent_id`, `id_secret`,
                                     `encoding_aes_key`, `token`, `corp_secret`, `contact_secret`, `agent_secret`,
                                     `public_secret`, `private_secret`, `account_type`, `wx_qr_login_redirect_uri`,
                                     `customer_churn_notice_switch`, `customer_group_notice`, `status`, `del_flag`,
                                     `delete_timestamp`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (10000, 'wwb7bc0ee558e60842', '我来了', '', '18767218370', '1000004', '757b505cfd34c64c85ca5b5690ee5293',
        'du1fkGuXTcjPbFbwnjk1ZYM5ymiTIJ6oetTxP5jD3si', 'yLUG9BOJw1BR0wihnzk77JwmAl9',
        'TjOdsTpxx3PyAyuNvnnhXe-CP1YgNIGWf32T6RTrt6c', 'fKmYUqb1gaVbnOuCT9qSb59j-xOnMbDiThizOZ-_lwU',
        'QUhOY9W9W0vVOMCBQodBquZiHQoamtF6VDYZlly7nxY', '', '', 'member', '', 0, 0, 0, 0, 0, NULL, '2021-10-18 19:36:05',
        '', '2021-10-18 19:36:05');


-- 通讯录相关用户
DROP TABLE if EXISTS `we_user`;
CREATE TABLE `we_user`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `corp_id`          varchar(128)        DEFAULT '' COMMENT '企业ID',
    `user_id`          varchar(256)        DEFAULT '' COMMENT '用户Id',
    `head_image_url`   varchar(255)        DEFAULT '' COMMENT '头像地址',
    `user_name`        varchar(200)        DEFAULT '' COMMENT '用户名称',
    `alias`            varchar(100)        DEFAULT '' COMMENT '用户昵称',
    `gender`           tinyint(4)          DEFAULT '1' COMMENT '性别。1表示男性，2表示女性',
    `mobile`           varchar(20)         DEFAULT '' COMMENT '手机号',
    `email`            varchar(200)        DEFAULT '' COMMENT '邮箱',
    `wx_account`       varchar(32)         DEFAULT '' COMMENT '个人微信号',
    `department`       tinytext COMMENT '用户所属部门,使用逗号隔开,字符串格式存储',
    `position`         varchar(300)        DEFAULT '' COMMENT '职务',
    `join_time`        datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '入职时间',
    `id_card`          char(18)            DEFAULT '' COMMENT '身份证号',
    `qq_account`       varchar(20)         DEFAULT '' COMMENT 'QQ号',
    `telephone`        varchar(32)         DEFAULT '' COMMENT '座机',
    `address`          varchar(300)        DEFAULT '' COMMENT '地址',
    `birthday`         datetime            DEFAULT NULL COMMENT '生日',
    `remark`           varchar(255)        DEFAULT '' COMMENT '备注',
    `dimission_time`   datetime            DEFAULT NULL COMMENT '离职时间',
    `is_allocate`      tinyint(4)          DEFAULT '0' COMMENT '离职后资源是否被分配(1:已分配;0:未分配;)',
    `is_activate`      tinyint(4)          DEFAULT 4 COMMENT '激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业,6=删除',
    `del_flag`         tinyint(4)          DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `delete_timestamp` bigint(20) NOT NULL DEFAULT '0' COMMENT '删除时间戳',
    `create_by`        varchar(256)        DEFAULT '' COMMENT '创建者',
    `create_time`      datetime            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(256)        DEFAULT '' COMMENT '更新者',
    `update_time`      datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_corpid_userid` (`corp_id`, `user_id`),
    KEY `idx_corpid_isactivate` (`corp_id`, `is_activate`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8mb4 COMMENT ='通讯录相关用户';

-- 部门相关
DROP TABLE IF EXISTS `we_department`;
CREATE TABLE `we_department`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `corp_id`          varchar(128) DEFAULT '' COMMENT '企业ID',
    `name`             varchar(64)  DEFAULT '' COMMENT '部门名称',
    `department_id`    bigint(20)   DEFAULT 0 DEFAULT '0' COMMENT '部门id',
    `parent_id`        bigint(20)   DEFAULT 0 COMMENT '父部门id',
    `path`             varchar(256) DEFAULT '' COMMENT '当前组织全路径',
    `del_flag`         tinyint(4)   DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `delete_timestamp` bigint(20)   DEFAULT '0' COMMENT '删除时间戳',
    `create_by`        varchar(256) DEFAULT '' COMMENT '创建者',
    `create_time`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(256) DEFAULT '' COMMENT '更新者',
    `update_time`      datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_corpid_departmentid` (`corp_id`, `department_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1391
  DEFAULT CHARSET = utf8mb4 COMMENT ='企业微信组织架构';