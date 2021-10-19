package com.hgs.caipiao.common;

import com.alibaba.fastjson.JSONArray;
import com.hgs.caipiao.dao.CaipiaoHistoryDao;
import com.hgs.caipiao.dao.CaipiaoPrizeLevelListDao;
import com.hgs.caipiao.entity.CaipiaoHistoryItemEntity;
import com.hgs.caipiao.entity.ConstantInterface;
import com.hgs.caipiao.entity.DataEnum;
import com.hgs.caipiao.entity.WinItemEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：206612
 * @date ：Created in 2021/9/29 16:32
 * @description：CommonUtil
 */
@Component
public class CommonUtil {
    @Autowired
    private CaipiaoHistoryDao caipiaoHistoryDao;
    @Autowired
    private CaipiaoPrizeLevelListDao prizeLevelListDao;
    @Autowired
    private HTTPUtils httpUtils;

    public String getId(String id)
    {
        return StringUtils.isNotBlank(id) ? id : UUID.randomUUID().toString();
    }

    public Map<String, Object> get(String url)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            //请求参数
            Map<String, String> params = new HashMap<String, String>();
//            params.put("tel", "13777777777");//这是该接口需要的参数
            String res = httpUtils.get(url, params);
            System.out.println(res);//打印返回参数
            try {
                JSONObject object = JSONObject.parseObject(res);//转JSON Object
                result.put("JSONObject", object);
                return result;
            } catch (Exception e) {
                try {
                    JSONArray array = JSONObject.parseArray(res);//转JSON Array
                    result.put("JSONArray", array);
                    return result;
                } catch (Exception e1) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断排列五中奖情况
     *
     * @param lotteryPrivateNumber
     * @param entity
     * @return
     */
    private WinItemEntity judgePLW(String lotteryPrivateNumber, CaipiaoHistoryItemEntity entity)
    {
        WinItemEntity result = new WinItemEntity();
        String lotteryDrawResult = entity.getLotteryDrawResult();
        if (lotteryPrivateNumber.equals(lotteryDrawResult)) {
            getResult(result, entity, ConstantInterface.FIRST_PRICE);
            return result;
        }
        return result;
    }

    /**
     * 判断排列三中奖情况
     *
     * @param lotteryPrivateNumber
     * @param entity
     * @return
     */
    private WinItemEntity judgePLS(String lotteryPrivateNumber, CaipiaoHistoryItemEntity entity)
    {
        WinItemEntity result = new WinItemEntity();
        String lotteryDrawResult = entity.getLotteryDrawResult();
        if (lotteryPrivateNumber.equals(lotteryDrawResult)) {
            getResult(result, entity, ConstantInterface.ZHIXUAN);
            return result;
        }

        String[] publicNumbers = lotteryDrawResult.split(ConstantInterface.SPACE_SEPARATOR);
        boolean isZuxuan6 = true; // 是否是组选6，默认是组选6

        lotteryDrawResult = lotteryDrawResult.replaceAll(ConstantInterface.SPACE_SEPARATOR, "");
        lotteryPrivateNumber = lotteryPrivateNumber.replaceAll(ConstantInterface.SPACE_SEPARATOR, "");
        for (String number : publicNumbers) {
            if (lotteryDrawResult.length() - (lotteryDrawResult.replaceAll(number, "").length()) == 2) {
                isZuxuan6 = false; //如果公布号码里有重复号码，那就不是组选6，是组选3
            }
        }
        char[] pubChars = lotteryDrawResult.toCharArray();
        char[] priChars = lotteryPrivateNumber.toCharArray();
        Arrays.sort(pubChars);
        Arrays.sort(priChars);
        if (Arrays.toString(pubChars).equals(Arrays.toString(priChars))) {
            if (isZuxuan6) {
                getResult(result, entity, ConstantInterface.ZUXUAN_6);
                return result;
            }
            else {
                getResult(result, entity, ConstantInterface.ZUXUAN_3);
                return result;
            }
        }
        return result;
    }

    /**
     * 判断大乐透中奖情况
     *
     * @param lotteryPrivateNumber 用户号码
     * @param entity               公布情况
     * @return
     */
    private WinItemEntity judgeDLT(String lotteryPrivateNumber, CaipiaoHistoryItemEntity entity)
    {
        WinItemEntity result = new WinItemEntity();
        if (lotteryPrivateNumber.equals(entity.getLotteryDrawResult())) {
            getResult(result, entity, ConstantInterface.FIRST_PRICE);
            return result;
        }

        String[] publicNumbers = entity.getLotteryDrawResult().split(ConstantInterface.SPACE_SEPARATOR);
        String[] privateNumbers = lotteryPrivateNumber.split(ConstantInterface.SPACE_SEPARATOR);

        String[] prefixPubNumbers = new String[5]; //公布号码的前区5个号码
        String[] suffixPubNumbers = new String[2]; //公布号码的后区2个号码
        String[] prefixPriNumbers = new String[5]; //自己号码的前区5个号码
        String[] suffixPriNumbers = new String[2]; //自己号码的后区2个号码

        for (int i = 0; i < 5; i++) {
            prefixPubNumbers[i] = publicNumbers[i];
            prefixPriNumbers[i] = privateNumbers[i];
        }

        for (int i = 5; i < 7; i++) {
            suffixPubNumbers[i - 5] = publicNumbers[i];
            suffixPriNumbers[i - 5] = privateNumbers[i];
        }
        int prefixSameNum = 0;
        int suffixSameNum = 0;
        for (int i = 0; i < prefixPubNumbers.length; i++) {
            if (Arrays.asList(prefixPubNumbers).contains(prefixPriNumbers[i])) {
                prefixSameNum++;

            }
        }
        for (int i = 0; i < suffixPubNumbers.length; i++) {
            if (Arrays.asList(suffixPubNumbers).contains(suffixPriNumbers[i])) {
                suffixSameNum++;
            }
        }
        //5+2:一等奖；5+1：二等奖；5+0：三等奖；4+2：四等奖； 4+1：五等奖； 3+2：六等奖
        //4+0：七等奖； 3+1、2+2：八等奖； 3+0、1+2、2+1、0+2：九等奖
        if (prefixSameNum == 5 && suffixSameNum == 1) {
            getResult(result, entity, ConstantInterface.SECOND_PRICE);
            return result;
        }
        else if (prefixSameNum == 5 && suffixSameNum == 0) {
            getResult(result, entity, ConstantInterface.THIRD_PRICE);
            return result;
        }
        else if (prefixSameNum == 4 && suffixSameNum == 2) {
            getResult(result, entity, ConstantInterface.FOURTH_PRICE);
            return result;
        }
        else if (prefixSameNum == 4 && suffixSameNum == 1) {
            getResult(result, entity, ConstantInterface.FIFTH_PRICE);
            return result;
        }
        else if (prefixSameNum == 3 && suffixSameNum == 2) {
            getResult(result, entity, ConstantInterface.SIXTH_PRICE);
            return result;
        }
        else if (prefixSameNum == 4 && suffixSameNum == 0) {
            getResult(result, entity, ConstantInterface.SEVENTH_PRICE);
            return result;
        }
        else if ((prefixSameNum == 3 && suffixSameNum == 1) || (prefixSameNum == 2 && suffixSameNum == 2)) {
            getResult(result, entity, ConstantInterface.EIGHTH_PRICE);
            return result;
        }
        else if ((prefixSameNum == 3 && suffixSameNum == 0) || (prefixSameNum == 1 && suffixSameNum == 2)
                || (prefixSameNum == 2 && suffixSameNum == 1) || (prefixSameNum == 0 && suffixSameNum == 2)) {
            getResult(result, entity, ConstantInterface.NINTH_PRICE);
            return result;
        }
        else {
            result.setLotteryRrawResultLevel(ConstantInterface.NONE_PRICE);
            return result;
        }
    }

    /**
     * 判断七星彩中奖情况
     *
     * @param lotteryPrivateNumber
     * @param entity
     * @return
     */
    private WinItemEntity judgeQXC(String lotteryPrivateNumber, CaipiaoHistoryItemEntity entity)
    {
        WinItemEntity result = new WinItemEntity();
        if (lotteryPrivateNumber.equals(entity.getLotteryDrawResult())) {
            getResult(result, entity, ConstantInterface.FIRST_PRICE);
            return result;
        }

        String[] publicNumbers = entity.getLotteryDrawResult().split(ConstantInterface.SPACE_SEPARATOR);
        String[] privateNumbers = lotteryPrivateNumber.split(ConstantInterface.SPACE_SEPARATOR);

        String[] prefixPubNumbers = new String[6]; //公布号码的前区6个号码
        String[] suffixPubNumbers = new String[1]; //公布号码的后区1个号码
        String[] prefixPriNumbers = new String[6]; //自己号码的前区6个号码
        String[] suffixPriNumbers = new String[1]; //自己号码的后区1个号码

        for (int i = 0; i < 6; i++) {
            prefixPubNumbers[i] = publicNumbers[i];
            prefixPriNumbers[i] = privateNumbers[i];
        }

        suffixPubNumbers[0] = publicNumbers[6];
        suffixPriNumbers[0] = privateNumbers[6];
        int prefixSameNum = 0;
        int suffixSameNum = 0;
        for (int i = 0; i < prefixPubNumbers.length; i++) {
            if (prefixPubNumbers[i].equals(prefixPriNumbers[i])) {
                prefixSameNum++;
            }
        }
        for (int i = 0; i < suffixPubNumbers.length; i++) {
            if (suffixPubNumbers[i].equals(suffixPriNumbers[i])) {
                suffixSameNum++;
            }
        }
        //6+1:一等奖；6+0：二等奖；5+1：三等奖；5+0、4+1：四等奖；
        // 4+0、3+1：五等奖； 3+0、2+1、1+1、0+1：六等奖
        if (prefixSameNum == 6 && suffixSameNum == 0) {
            getResult(result, entity, ConstantInterface.SECOND_PRICE);
            return result;
        }
        else if (prefixSameNum == 5 && suffixSameNum == 1) {
            getResult(result, entity, ConstantInterface.THIRD_PRICE);
            return result;
        }
        else if ((prefixSameNum == 5 && suffixSameNum == 0) || (prefixSameNum == 4 && suffixSameNum == 1)) {
            getResult(result, entity, ConstantInterface.FOURTH_PRICE);
            return result;
        }
        else if ((prefixSameNum == 4 && suffixSameNum == 0) || (prefixSameNum == 3 && suffixSameNum == 1)) {
            getResult(result, entity, ConstantInterface.FIFTH_PRICE);
            return result;
        }
        else if ((prefixSameNum == 3 && suffixSameNum == 0) || (prefixSameNum == 2 && suffixSameNum == 1)
                || (prefixSameNum == 1 && suffixSameNum == 1) || (prefixSameNum == 0 && suffixSameNum == 1)) {
            getResult(result, entity, ConstantInterface.SIXTH_PRICE);
            return result;
        }
        else {
            result.setLotteryRrawResultLevel(ConstantInterface.NONE_PRICE);
            return result;
        }
    }

    private void getResult(WinItemEntity result, CaipiaoHistoryItemEntity entity, String prizeLevel)
    {
        result.setLotteryRrawResultLevel(prizeLevel);
        String stakeAmount = prizeLevelListDao.getByNameAndDrawNumAndPrizeLevel(entity.getLotteryGameName(),
                entity.getLotteryDrawNum(), prizeLevel);
        stakeAmount = stakeAmount.replaceAll(ConstantInterface.COMMA_SEPARATOR, "");
        result.setLotteryMoneyNum(stakeAmount);
    }


    /**
     * 判断中奖情况
     *
     * @param lotteryGameName          号码类型
     * @param lotteryPrivateNumber     用户号码
     * @param caipiaoHistoryItemEntity 公布情况
     * @return
     */
    public WinItemEntity judgeLotteryLevel(String lotteryGameName, String lotteryPrivateNumber, CaipiaoHistoryItemEntity caipiaoHistoryItemEntity)
    {

        if (DataEnum.DLT.getName().equals(lotteryGameName)) {
            return judgeDLT(lotteryPrivateNumber, caipiaoHistoryItemEntity);
        }
        if (DataEnum.PLS.getName().equals(lotteryGameName)) {
            return judgePLS(lotteryPrivateNumber, caipiaoHistoryItemEntity);
        }
        if (DataEnum.PLW.getName().equals(lotteryGameName)) {
            return judgePLW(lotteryPrivateNumber, caipiaoHistoryItemEntity);
        }
        if (DataEnum.QXC.getName().equals(lotteryGameName)) {
            return judgeQXC(lotteryPrivateNumber, caipiaoHistoryItemEntity);
        }

        return new WinItemEntity();

    }
}
