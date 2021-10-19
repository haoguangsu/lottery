package com.hgs.caipiao.entity;


import lombok.Data;

/**
 * 后端应答的返回值
 * @author zy204
 * @param <T>
 */
@Data
public class HttpResponse<T> {
    private int code;
    private Type type;
    // 数据
    private T data;
    private String message;
    private String operationObject = "";


    public enum Type {
        OK(0), ERROR(1), UNAUTHORIZED(2), INTERNAL_ERROR(3);
        int val;
        Type(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
}
