package com.hgs.caipiao.dao;

import com.hgs.caipiao.entity.CaipiaoHistoryItemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CaipiaoHistoryDao {
    int deleteByPrimaryKey(@Param("lotteryDrawNum") String lotteryDrawNum, @Param("lotteryGameName") String lotteryGameName);

    int insert(CaipiaoHistoryItemEntity record);

    int insertSelective(CaipiaoHistoryItemEntity record);

    CaipiaoHistoryItemEntity selectByPrimaryKey(@Param("lotteryDrawNum") String lotteryDrawNum, @Param("lotteryGameName") String lotteryGameName);

    int updateByPrimaryKeySelective(CaipiaoHistoryItemEntity record);

    int updateByPrimaryKey(CaipiaoHistoryItemEntity record);

    List<CaipiaoHistoryItemEntity> getByNameAndDrawNum(@Param("lotteryGameName") String lotteryGameName,@Param("lotteryDrawNum")  String lotteryDrawNum);
}