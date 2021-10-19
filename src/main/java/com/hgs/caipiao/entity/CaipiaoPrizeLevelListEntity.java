package com.hgs.caipiao.entity;

import lombok.Data;

/**
    * 彩票历史表
    */
@Data
public class CaipiaoPrizeLevelListEntity {
    /**
    * 类型
    */
    private String lotteryGameName;

    /**
    * 期数
    */
    private String lotteryDrawNum;

    /**
    * 奖项
    */
    private String prizelevel;

    /**
    * 基本奖金
    */
    private String stakeamount;

    /**
    * 基本注数
    */
    private String stakecount;

    /**
    * 总奖金数
    */
    private String totalprizeamount;

    private int awardType;
    private String group;
    private String lotteryCondition;
    private String sort;
}