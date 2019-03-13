package com.dlong.rep.multipledatasheettest.model;

/**
 * 公司实体类
 * @author  dlong
 * created at 2019/3/13 11:37 AM
 */
public class CompanyBean {
    /** 数据库自增ID */
    public int id;
    /** 公司名称 */
    public String name;
    /** 公司CEO */
    public String ceo;
    /** 公司建立年份 */
    public int year;

    public String toString(){
        StringBuilder builder = new StringBuilder("[");
        builder.append(id).append("--");
        builder.append("公司名称：").append(name).append("--");
        builder.append("CEO：").append(ceo).append("--");
        builder.append("创立年份：").append(year).append("年]");
        return builder.toString();
    }
}
