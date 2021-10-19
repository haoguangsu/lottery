package com.hgs.caipiao.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ：206612
 * @date ：Created in 2021/10/15 17:26
 * @description：CaipiaoWinSituationEntity
 */
@Data
public class CaipiaoWinSituationEntity {
    private String errorInfo;
    private String winInfo;
    private Map<String, List<WinItemEntity>> wins;


}
