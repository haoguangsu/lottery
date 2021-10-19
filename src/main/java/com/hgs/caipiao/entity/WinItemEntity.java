package com.hgs.caipiao.entity;

import lombok.Data;

/**
 * @author ：206612
 * @date ：Created in 2021/10/16 14:40
 * @description：WinItemEntity
 */
@Data
public class WinItemEntity {
    //中奖类型
    private String lotteryGameName;
    //中奖期数
    private String lotteryRrawNum;
    //公布的中奖号码
    private String lotteryRrawPublicResult;
    //自己的中奖号码
    private String lotteryRrawPrivateResult;
    //中奖等级
    private String lotteryRrawResultLevel;
    //中奖奖金
    private String lotteryMoneyNum = "0";
}
