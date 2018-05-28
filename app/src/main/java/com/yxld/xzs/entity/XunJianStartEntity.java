package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/8.
 */

public class XunJianStartEntity extends BaseBack {
    public String dianAddress;
    public int dianId;
    public String dianName;
    public int dianPaixu;
    public int jiluId;
    public String dianZuobiao;


    public List<XunJianXiangEntity> listXunjianxiang;
    public List<XunJianShiJianEntity> listshijian;
    public List<XunJianXianLuXunJianDianEntity> data;
    public int total;
}
