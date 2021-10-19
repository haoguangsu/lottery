package com.hgs.caipiao.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ：206612
 * @date ：Created in 2021/10/14 9:53
 * @description：CaipiaoHistoryEntity
 */
@Data
public class CaipiaoHistoryEntity {
    private String dataFrom;
    private Boolean emptyFlag;
    private String errorCode;
    private String errorMessage;
    private Boolean success;
    private HistoryValue value;

    @Data
    public static class HistoryValue{
        private int pageNo;
        private int pageSize;
        private int pages;
        private int total;
        private List<CaipiaoHistoryItemEntity> list;

    }
}
