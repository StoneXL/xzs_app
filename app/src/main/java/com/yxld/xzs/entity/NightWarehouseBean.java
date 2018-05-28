package com.yxld.xzs.entity;

import java.io.Serializable;

/**
 * Created by William on 2017/11/28.
 */

public class NightWarehouseBean  implements Serializable{

    /**
     * id : 79
     * chukudanBianhao : yc20171103174400001
     * chukurenMing : admin_wqj
     * chukurenId : 221
     * tijiaoShijian : 2017-11-03 17:44:00.0
     * beihuoren : null
     * beikuorenId : null
     * beihuoShijian : null
     * linghuoren : 王煌
     * linghuorenId : 446
     * linghuoShijian : null
     * linghuoZhuangtai : 1
     * gongsiId : 1
     * xiangmuId : 346
     * chulizhuangtai : null
     * xiangmuName : null
     */

    private int id;//
    private String chukudanBianhao;//出库单编号
    private String chukurenMing;//出库人名
    private int chukurenId;//出库人id
    private String tijiaoShijian;//提交时间
    private String beihuoren;//备货人
    private String beikuorenId;//备货人id
    private String beihuoShijian;//备货时间
    private String linghuoren;//领货人
    private int linghuorenId;//领货人id
    private String linghuoShijian;//领货时间
    private int linghuoZhuangtai;//领货状态 1未备货 2 已备货
    private int gongsiId;//公司id
    private int xiangmuId;//项目id
    private String chulizhuangtai;//处理状态
    private String xiangmuName;//项目名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChukudanBianhao() {
        return chukudanBianhao;
    }

    public void setChukudanBianhao(String chukudanBianhao) {
        this.chukudanBianhao = chukudanBianhao;
    }

    public String getChukurenMing() {
        return chukurenMing;
    }

    public void setChukurenMing(String chukurenMing) {
        this.chukurenMing = chukurenMing;
    }

    public int getChukurenId() {
        return chukurenId;
    }

    public void setChukurenId(int chukurenId) {
        this.chukurenId = chukurenId;
    }

    public String getTijiaoShijian() {
        return tijiaoShijian;
    }

    public void setTijiaoShijian(String tijiaoShijian) {
        this.tijiaoShijian = tijiaoShijian;
    }

    public String getBeihuoren() {
        return beihuoren;
    }

    public void setBeihuoren(String beihuoren) {
        this.beihuoren = beihuoren;
    }

    public String getBeikuorenId() {
        return beikuorenId;
    }

    public void setBeikuorenId(String beikuorenId) {
        this.beikuorenId = beikuorenId;
    }

    public String getBeihuoShijian() {
        return beihuoShijian;
    }

    public void setBeihuoShijian(String beihuoShijian) {
        this.beihuoShijian = beihuoShijian;
    }

    public String getLinghuoren() {
        return linghuoren;
    }

    public void setLinghuoren(String linghuoren) {
        this.linghuoren = linghuoren;
    }

    public int getLinghuorenId() {
        return linghuorenId;
    }

    public void setLinghuorenId(int linghuorenId) {
        this.linghuorenId = linghuorenId;
    }

    public String getLinghuoShijian() {
        return linghuoShijian;
    }

    public void setLinghuoShijian(String linghuoShijian) {
        this.linghuoShijian = linghuoShijian;
    }

    public int getLinghuoZhuangtai() {
        return linghuoZhuangtai;
    }

    public void setLinghuoZhuangtai(int linghuoZhuangtai) {
        this.linghuoZhuangtai = linghuoZhuangtai;
    }

    public int getGongsiId() {
        return gongsiId;
    }

    public void setGongsiId(int gongsiId) {
        this.gongsiId = gongsiId;
    }

    public int getXiangmuId() {
        return xiangmuId;
    }

    public void setXiangmuId(int xiangmuId) {
        this.xiangmuId = xiangmuId;
    }

    public String getChulizhuangtai() {
        return chulizhuangtai;
    }

    public void setChulizhuangtai(String chulizhuangtai) {
        this.chulizhuangtai = chulizhuangtai;
    }

    public String getXiangmuName() {
        return xiangmuName;
    }

    public void setXiangmuName(String xiangmuName) {
        this.xiangmuName = xiangmuName;
    }

    @Override
    public String toString() {
        return "NightWarehouseBean{" +
                "id=" + id +
                ", chukudanBianhao='" + chukudanBianhao + '\'' +
                ", chukurenMing='" + chukurenMing + '\'' +
                ", chukurenId=" + chukurenId +
                ", tijiaoShijian='" + tijiaoShijian + '\'' +
                ", beihuoren='" + beihuoren + '\'' +
                ", beikuorenId='" + beikuorenId + '\'' +
                ", beihuoShijian='" + beihuoShijian + '\'' +
                ", linghuoren='" + linghuoren + '\'' +
                ", linghuorenId=" + linghuorenId +
                ", linghuoShijian='" + linghuoShijian + '\'' +
                ", linghuoZhuangtai=" + linghuoZhuangtai +
                ", gongsiId=" + gongsiId +
                ", xiangmuId=" + xiangmuId +
                ", chulizhuangtai='" + chulizhuangtai + '\'' +
                ", xiangmuName='" + xiangmuName + '\'' +
                '}';
    }
}
