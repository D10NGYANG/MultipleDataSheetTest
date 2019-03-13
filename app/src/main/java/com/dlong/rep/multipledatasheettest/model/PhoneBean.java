package com.dlong.rep.multipledatasheettest.model;

/**
 * 手机实体类
 * @author  dlong
 * created at 2019/3/13 11:34 AM
 */
public class PhoneBean {
    /** 数据库自增ID */
    public int id;
    /** 手机品牌 */
    public String brand;
    /** 手机型号 */
    public String model;
    /** 手机价格 */
    public int price;

    /**
     * 转化成字符串
     * @return
     */
    public String toString(){
        StringBuilder builder = new StringBuilder("[");
        builder.append(id).append("--");
        builder.append("品牌：").append(brand).append("--");
        builder.append("型号：").append(model).append("--");
        builder.append("价格：").append(price).append("]");
        return builder.toString();
    }
}
