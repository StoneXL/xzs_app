package com.yxld.xzs.entity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/8.
 */

public class XunJianXianLuXunJianDianEntity extends BaseBack {
    public String dianAddress;
    public int dianId;
    public String dianName;
    public int dianPaixu;
    public int jiluId;
    public String dianZuobiao;


    public List<XunJianXiangEntity> listXunjianxiang;
    public List<XunJianShiJianEntity> listshijian;
    public List<XunJianXianLuXunJianDianEntity> data;
    public XunJianJiLuEntity total;



}
