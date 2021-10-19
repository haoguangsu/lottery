package com.hgs.caipiao.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * @author ：206612
 * @date ：Created in 2020/12/5 16:25
 * @description：ErrorCode 错误码
 * 10001-11000
 */
public class ErrorCodeList {
    public static final String NAME_EXIST = "名称已存在。";
    public static final String BRIEF_IS_NOT_JSON = "brief不是json格式。";
    public static final String RECOGNITION_TYPE_IS_NULL = "识别类型为空。";
    public static final String TASK_TYPE_IN_USE = "任务类型正在使用中，无法删除。";
    public static final String NO_INSPECT_POINT = "当前没有巡视点";
    public static final String INPUT_IS_NULL = "入参为空。";
    public static final String DEVICE_LEVEL_IS_ERROR = "设备层级不在合法范围内。";
    public static final String DATE_FORMAT_IS_ERROR = "日期格式不符合。";
    public static final String INSERT_ERROR = "新增失败。";
    public static final String UPDATE_ERROR = "更新失败。";
    public static final String OBJECT_NOT_EXIST = "对象不存在。";

    private final static List<String> ErrorCodeList = new ArrayList<>();

    static {
        ErrorCodeList.add(NAME_EXIST);
        ErrorCodeList.add(BRIEF_IS_NOT_JSON);
        ErrorCodeList.add(RECOGNITION_TYPE_IS_NULL);
        ErrorCodeList.add(TASK_TYPE_IN_USE);
        ErrorCodeList.add(NO_INSPECT_POINT);
        ErrorCodeList.add(INPUT_IS_NULL);
        ErrorCodeList.add(DEVICE_LEVEL_IS_ERROR);
        ErrorCodeList.add(DATE_FORMAT_IS_ERROR);
        ErrorCodeList.add(INSERT_ERROR);
        ErrorCodeList.add(UPDATE_ERROR);
        ErrorCodeList.add(OBJECT_NOT_EXIST);
    }

    private ErrorCodeList()
    {

    }

    public static List<String> getErrorCodeList()
    {
        return ErrorCodeList;
    }
}
