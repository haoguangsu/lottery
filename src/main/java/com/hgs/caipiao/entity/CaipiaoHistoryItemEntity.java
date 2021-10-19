package com.hgs.caipiao.entity;

import lombok.Data;

import java.util.List;

/**
    * 彩票历史表
    */
@Data
public class CaipiaoHistoryItemEntity {
    /**
    * 期数
    */
    private String lotteryDrawNum;

    /**
    * 类型
    */
    private String lotteryGameName;

    /**
    * 开奖日期
    */
    private String lotteryDrawTime;

    /**
    * 开奖结果
    */
    private String lotteryDrawResult;

    /**
    * 奖池奖金
    */
    private String poolBalanceAfterDraw;

    /**
    * 销售额
    */
    private String totalSaleAmount;

    /**
    * 中奖情况
    */
    private List<CaipiaoPrizeLevelListEntity> prizeLevelList;

    private String prizeLevelListStr;
}