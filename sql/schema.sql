CREATE TABLE t_user (
    `_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(22) NOT NULL COMMENT '用户ID',
    `username` VARCHAR(32) NOT NULL COMMENT '用户名',
    `nickname` VARCHAR(32) DEFAULT NULL COMMENT '用户昵称',
    `mobile_hash` VARCHAR(64) DEFAULT NULL COMMENT '手机号hash',
    `email_hash` VARCHAR(64) DEFAULT NULL COMMENT '邮箱hash',
    `gender` varchar(10) default 'unknown' comment '性别：male/female/unknown',
    `birthday` DATETIME DEFAULT NULL COMMENT '出生日',
    `address` TEXT DEFAULT NULL COMMENT '地区',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`_id`) COMMENT '唯一主键',
    UNIQUE `uniq_open_id` (`open_id`) COMMENT 'open_id唯一',
    UNIQUE `uniq_username` (`username`) COMMENT '用户名索引唯一',
    INDEX `idx_nickname` (`nickname`) COMMENT '用户昵称索引'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='用户表';

CREATE TABLE t_user_password (
    `_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(22) NOT NULL COMMENT '用户ID',
    `verifier` VARCHAR(64) NOT NULL COMMENT '公钥',
    `salt` VARCHAR(64) NOT NULL COMMENT '密码盐值',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`_id`) COMMENT '主键ID',
    UNIQUE `uniq_open_id` (open_id) COMMENT '用户ID唯一'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='用户密码表';
