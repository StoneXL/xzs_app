package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2018/5/28.
 */

public class PanDianDetail extends BaseBack {

    private PanDianDetail data;
    private PanDianDetail wuzi;
    private List<PanDianBean> details;

    public PanDianDetail getData() {
        return data;
    }

    public void setData(PanDianDetail data) {
        this.data = data;
    }

    public PanDianDetail getWuzi() {
        return wuzi;
    }

    public void setWuzi(PanDianDetail wuzi) {
        this.wuzi = wuzi;
    }

    public List<PanDianBean> getDetails() {
        return details;
    }

    public void setDetails(List<PanDianBean> details) {
        this.details = details;
    }

    /**
     * wuziId : 3
     * wuziBianhao : 123
     * wuziPinpai : 小米手机
     * wuziGuige : 台
     * wuziDanwei : 台
     * wuziMingcheng : 小米手机
     * wuziFenlei : 2
     * wuziGongsiId : 1
     * wuziDanjia : 2144.29
     * wuziChaifen : null
     */

    private int wuziId;
    private String wuziBianhao;
    private String wuziPinpai;
    private String wuziGuige;
    private String wuziDanwei;
    private String wuziMingcheng;
    private int wuziFenlei;
    private int wuziGongsiId;
    private double wuziDanjia;
    private Object wuziChaifen;

    public int getWuziId() {
        return wuziId;
    }

    public void setWuziId(int wuziId) {
        this.wuziId = wuziId;
    }

    public String getWuziBianhao() {
        return wuziBianhao;
    }

    public void setWuziBianhao(String wuziBianhao) {
        this.wuziBianhao = wuziBianhao;
    }

    public String getWuziPinpai() {
        return wuziPinpai;
    }

    public void setWuziPinpai(String wuziPinpai) {
        this.wuziPinpai = wuziPinpai;
    }

    public String getWuziGuige() {
        return wuziGuige;
    }

    public void setWuziGuige(String wuziGuige) {
        this.wuziGuige = wuziGuige;
    }

    public String getWuziDanwei() {
        return wuziDanwei;
    }

    public void setWuziDanwei(String wuziDanwei) {
        this.wuziDanwei = wuziDanwei;
    }

    public String getWuziMingcheng() {
        return wuziMingcheng;
    }

    public void setWuziMingcheng(String wuziMingcheng) {
        this.wuziMingcheng = wuziMingcheng;
    }

    public int getWuziFenlei() {
        return wuziFenlei;
    }

    public void setWuziFenlei(int wuziFenlei) {
        this.wuziFenlei = wuziFenlei;
    }

    public int getWuziGongsiId() {
        return wuziGongsiId;
    }

    public void setWuziGongsiId(int wuziGongsiId) {
        this.wuziGongsiId = wuziGongsiId;
    }

    public double getWuziDanjia() {
        return wuziDanjia;
    }

    public void setWuziDanjia(double wuziDanjia) {
        this.wuziDanjia = wuziDanjia;
    }

    public Object getWuziChaifen() {
        return wuziChaifen;
    }

    public void setWuziChaifen(Object wuziChaifen) {
        this.wuziChaifen = wuziChaifen;
    }
}
