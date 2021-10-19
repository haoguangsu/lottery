package com.hgs.caipiao.entity;

import lombok.Data;

/**
    * 彩票投注表
    */
@Data
public class CaipiaoNumberEntity {
    /**
    * ID
    */
    private String id;

    /**
    * 类型
    */
    private String lotteryGameName;

    /**
     * 号码
     */
    private String lotteryNumber;

    /**
    * 开始期数
    */
    private String lotteryDrawNum;

    /**
    * 期数
    */
    private Integer termNum;

    /**
    * 倍数
    */
    private Integer multiple;

    /**
    * 用户ID
    */
    private String userId;

    private String userName;
}