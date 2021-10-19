package com.hgs.caipiao.dao;

import com.hgs.caipiao.entity.CaipiaoNumberEntity;

import java.util.List;

public interface CaipiaoNumberDao {
    int deleteByPrimaryKey(String id);

    int insert(CaipiaoNumberEntity record);

    int insertSelective(CaipiaoNumberEntity record);

    CaipiaoNumberEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaipiaoNumberEntity record);

    int updateByPrimaryKey(CaipiaoNumberEntity record);

    List<CaipiaoNumberEntity> getByUserId(String userId);
}