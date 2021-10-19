package com.hgs.caipiao.dao;

import com.hgs.caipiao.entity.CaipiaoUserEntity;

import java.util.List;

public interface CaipiaoUserDao {
    int deleteByPrimaryKey(String id);

    int insert(CaipiaoUserEntity record);

    int insertSelective(CaipiaoUserEntity record);

    CaipiaoUserEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaipiaoUserEntity record);

    int updateByPrimaryKey(CaipiaoUserEntity record);

    CaipiaoUserEntity getByPhone(String phoneNumber);

    CaipiaoUserEntity getByUserId(String userId);

    List<CaipiaoUserEntity> getAll();
}