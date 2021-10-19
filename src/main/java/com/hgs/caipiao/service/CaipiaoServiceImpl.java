package com.hgs.caipiao.service;

import com.hgs.caipiao.dao.CaipiaoNumberDao;
import com.hgs.caipiao.dao.CaipiaoUserDao;
import com.hgs.caipiao.entity.CaipiaoHistoryItemEntity;
import com.hgs.caipiao.common.CommonUtil;
import com.hgs.caipiao.dao.CaipiaoHistoryDao;
import com.hgs.caipiao.dao.CaipiaoPrizeLevelListDao;
import com.hgs.caipiao.entity.CaipiaoHistoryEntity;
import com.hgs.caipiao.entity.CaipiaoNumberEntity;
import com.hgs.caipiao.entity.CaipiaoPrizeLevelListEntity;
import com.hgs.caipiao.entity.CaipiaoUserEntity;
import com.hgs.caipiao.entity.CaipiaoWinSituationEntity;
import com.hgs.caipiao.entity.ConstantInterface;
import com.alibaba.fastjson.JSONObject;
import com.hgs.caipiao.entity.ErrorCodeList;
import com.hgs.caipiao.entity.WinItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author ：206612
 * @date ：Created in 2021/10/13 16:40
 * @description：QueryServiceImpl
 */
@Slf4j
@Service
@Component
public class CaipiaoServiceImpl {
    @Autowired
    private CaipiaoHistoryDao historyDao;
    @Autowired
    private CaipiaoPrizeLevelListDao prizeLevelListDao;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private CaipiaoUserDao userDao;
    @Autowired
    private CaipiaoNumberDao numberDao;

    public String addUser(CaipiaoUserEntity entity)
    {
        CaipiaoUserEntity byPhone = userDao.getByPhone(entity.getPhoneNumber());
        if (Objects.nonNull(byPhone)) {
            return ErrorCodeList.INSERT_ERROR;
        }
        entity.setId(commonUtil.getId(entity.getId()));
        userDao.insert(entity);
        return "OK";
    }

    public String refreshHistory()
    {
        log.info("-----refreshHistory------");
        insertData(ConstantInterface.DLT_HISTORY_URL);
        insertData(ConstantInterface.PLS_HISTORY_URL);
        insertData(ConstantInterface.PLW_HISTORY_URL);
        insertData(ConstantInterface.QXC_HISTORY_URL);
        return "OK";
    }

    private CaipiaoHistoryEntity convert2Dlt(JSONObject jsonObject)
    {
        CaipiaoHistoryEntity entity = JSONObject.toJavaObject(jsonObject, CaipiaoHistoryEntity.class);
        return entity;
    }

    private int getTotal(String url)
    {
        try {
            url += "&pageSize=1&pageNo=1";
            Map<String, Object> obj = commonUtil.get(url);
            CaipiaoHistoryEntity entity = convert2Dlt((JSONObject) (obj.get("JSONObject")));
            return entity.getValue().getTotal();
        } catch (Exception e) {
            return 0;
        }
    }

    private void insertData(String url)
    {
        int total = getTotal(url);
        if (total == 0) {
            return;
        }
        if (total > 500) {
            total = 500;
        }
        boolean shouldStop = false;
        url += "&pageSize=30&pageNo=";
        try {
            for (int i = 1; i <= (total / 30) + 1; i++) {
                String nUrl = url + i;
                Map<String, Object> dltObj = commonUtil.get(nUrl);
                CaipiaoHistoryEntity entity = convert2Dlt((JSONObject) (dltObj.get("JSONObject")));
                List<CaipiaoHistoryItemEntity> items = entity.getValue().getList();
                for (CaipiaoHistoryItemEntity item : items) {
                    List<CaipiaoPrizeLevelListEntity> prizeLevelList = item.getPrizeLevelList();
                    for (CaipiaoPrizeLevelListEntity levelListEntity : prizeLevelList) {
                        levelListEntity.setLotteryDrawNum(item.getLotteryDrawNum());
                        levelListEntity.setLotteryGameName(item.getLotteryGameName());
                        try {
                            prizeLevelListDao.insert(levelListEntity);
                        } catch (Exception e) {
                            log.error("prizeLevelListDao insert error");
                            shouldStop = true;
                            break;
                        }
                    }
                    try {
                        historyDao.insert(item);
                    } catch (Exception e) {
                        log.error("historyDao insert error");
                        shouldStop = true;
                    }
                    if (shouldStop) {
                        break;
                    }
                }
                if (shouldStop) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("insertData  error");
        }

    }

    public String addNumber(CaipiaoNumberEntity entity)
    {
        CaipiaoUserEntity caipiaoUserEntity = userDao.getByUserId(entity.getUserId());
        if (Objects.isNull(caipiaoUserEntity)) {
            return ErrorCodeList.OBJECT_NOT_EXIST;
        }
        entity.setId(commonUtil.getId(entity.getId()));
        numberDao.insert(entity);
        return "OK";
    }

    public CaipiaoWinSituationEntity checkWinSituation(String userId)
    {

        CaipiaoWinSituationEntity result = new CaipiaoWinSituationEntity();
        CaipiaoUserEntity caipiaoUserEntity = userDao.getByUserId(userId);
        if (Objects.isNull(caipiaoUserEntity)) {
            result.setErrorInfo(ConstantInterface.USER_NOT_EXIST);
            return result;
        }
        List<CaipiaoNumberEntity> numberEntityList = numberDao.getByUserId(userId);
        if (CollectionUtils.isEmpty(numberEntityList)) {
            result.setErrorInfo(ConstantInterface.USER_NOT_EXIST);
            return result;
        }
        boolean hasWin = false;
        List<WinItemEntity> winItemEntities = new ArrayList<>();
        List<WinItemEntity> noWinItemEntities = new ArrayList<>();
        double winNum = 0;
        for (CaipiaoNumberEntity entity : numberEntityList) {
            String lotteryGameName = entity.getLotteryGameName();  //超级大乐透
            String lotteryDrawNum = entity.getLotteryDrawNum(); // 21117 期数
            Integer termNum = entity.getTermNum(); // 15期
            Integer multiple = entity.getMultiple(); // 1倍
            String privateLotteryNumber = entity.getLotteryNumber(); //02 05 20 21 35 02 03 号码
            for (int i = 0; i < termNum; i++) {
                String tmpDraw = String.valueOf(Integer.parseInt(lotteryDrawNum) + i);
                List<CaipiaoHistoryItemEntity> historyItemEntities = historyDao.getByNameAndDrawNum(lotteryGameName, tmpDraw);
                WinItemEntity winItem = new WinItemEntity();
                if (!CollectionUtils.isEmpty(historyItemEntities)) {
                    CaipiaoHistoryItemEntity caipiaoHistoryItemEntity = historyItemEntities.get(0);
                    winItem = commonUtil.judgeLotteryLevel(lotteryGameName, privateLotteryNumber, caipiaoHistoryItemEntity);
                    winItem.setLotteryGameName(lotteryGameName);
                    winItem.setLotteryRrawNum(tmpDraw);
                    winItem.setLotteryRrawPublicResult(caipiaoHistoryItemEntity.getLotteryDrawResult());
                    winItem.setLotteryRrawPrivateResult(privateLotteryNumber);
                    winItem.setLotteryMoneyNum(String.valueOf(Double.valueOf(winItem.getLotteryMoneyNum()) * multiple));
                    winNum += Double.valueOf(winItem.getLotteryMoneyNum()) * multiple;
                    if (Double.valueOf(winItem.getLotteryMoneyNum()) != 0) {
                        hasWin = true;
                        winItemEntities.add(winItem);
                    }
                    else {
                        noWinItemEntities.add(winItem);
                    }
                }
            }
        }
        Map<String, List<WinItemEntity>> map = new HashMap<>();
        map.put(ConstantInterface.NONE_PRICE, noWinItemEntities);
        map.put(ConstantInterface.WIN_PRICE, winItemEntities);
        if (hasWin) {
            result.setWinInfo(ConstantInterface.HAS_PRICE + winNum);
        }
        else {
            result.setWinInfo(ConstantInterface.NONE_PRICE);
        }
        result.setWins(map);

        return result;
    }

    public List<CaipiaoUserEntity> getUser()
    {
        return userDao.getAll();
    }

    public List<CaipiaoNumberEntity> getNumbersByUserId(String userId)
    {
        return numberDao.getByUserId(userId);
    }

    public String deleteUserByUserId(String userId)
    {
        userDao.deleteByPrimaryKey(userId);
        return "OK";
    }

    public String updateUser(CaipiaoUserEntity entity)
    {
        userDao.updateByPrimaryKeySelective(entity);
        return "OK";
    }
}
