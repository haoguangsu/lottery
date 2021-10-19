package com.hgs.caipiao.dao;

import com.hgs.caipiao.entity.CaipiaoPrizeLevelListEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CaipiaoPrizeLevelListDao {
    int deleteByPrimaryKey(@Param("lotteryGameName") String lotteryGameName, @Param("lotteryDrawNum") String lotteryDrawNum, @Param("prizelevel") String prizelevel);

    int insert(CaipiaoPrizeLevelListEntity record);

    int insertSelective(CaipiaoPrizeLevelListEntity record);

    CaipiaoPrizeLevelListEntity selectByPrimaryKey(@Param("lotteryGameName") String lotteryGameName, @Param("lotteryDrawNum") String lotteryDrawNum, @Param("prizelevel") String prizelevel);

    int updateByPrimaryKeySelective(CaipiaoPrizeLevelListEntity record);

    int updateByPrimaryKey(CaipiaoPrizeLevelListEntity record);

    String getByNameAndDrawNumAndPrizeLevel(@Param("lotteryGameName")String lotteryGameName,
                                         @Param("lotteryDrawNum")String lotteryDrawNum,
                                         @Param("prizeLevel")String prizeLevel);
}