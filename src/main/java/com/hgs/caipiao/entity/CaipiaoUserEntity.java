package com.hgs.caipiao.entity;

import lombok.Data;

/**
    * 彩票用户表
    */
@Data
public class CaipiaoUserEntity {
    /**
    * ID
    */
    private String id;

    /**
    * 用户名
    */
    private String name;

    /**
    * 手机号码
    */
    private String phoneNumber;
}