package com.hgs.caipiao.entity;

/**
 * @author ：206612
 * @date ：Created in 2021/9/29 16:30
 * @description：DataEnum
 */
public enum DataEnum {
    PLS(5, "排列3", 3), PLW(6, "排列5", 5), DLT(4, "超级大乐透", 7), QXC(8, "7星彩", 7);
    int val;
    int size;
    String name;

    DataEnum(int val, String name, int size)
    {
        this.val = val;
        this.size = size;
        this.name = name;
    }

    public int getVal()
    {
        return val;
    }

    public int getSize()
    {
        return size;
    }

    public String getName()
    {
        return name;
    }
}
