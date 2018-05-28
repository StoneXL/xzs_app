package com.yxld.xzs.entity;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yishangfei on 2017/3/17 0017.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 */
public class AppRepair implements Parcelable {

    private int baoxiu_id;
    private String baoxiu_status;
    private String baoxiu_danhao;
    private String baoxiu_lrtime;
    private String baoxiu_paidantime;
    private String baoxiu_img;
    private String baoxiu_chayanimg;
    private String baoxiu_name;
    private String baoxiu_zx_weixiuren;
    private String baoxiu_fuzerenyijian;
    private String baoxiu_loupan;
    private String baoxiu_loudong;
    private String baoxiu_danyuan;
    private String baoxiu_fanghao;
    private String baoxiu_didian;
    private String baoxiu_dianhua;
    private String baoxiu_xiangmu;
    private String baoxiu_zx_weixiurenphone;
    private String baoxiu_weixiudanwei;

    public AppRepair(int baoxiu_id, String baoxiu_status, String baoxiu_danhao, String baoxiu_lrtime, String
            baoxiu_paidantime, String baoxiu_img, String baoxiu_chayanimg, String baoxiu_name, String
            baoxiu_zx_weixiuren, String baoxiu_fuzerenyijian, String baoxiu_loupan, String baoxiu_loudong, String
            baoxiu_danyuan, String baoxiu_fanghao, String baoxiu_didian, String baoxiu_dianhua, String
            baoxiu_xiangmu, String baoxiu_zx_weixiurenphone, String baoxiu_weixiudanwei) {
        this.baoxiu_id = baoxiu_id;
        this.baoxiu_status = baoxiu_status;
        this.baoxiu_danhao = baoxiu_danhao;
        this.baoxiu_lrtime = baoxiu_lrtime;
        this.baoxiu_paidantime = baoxiu_paidantime;
        this.baoxiu_img = baoxiu_img;
        this.baoxiu_chayanimg = baoxiu_chayanimg;
        this.baoxiu_name = baoxiu_name;
        this.baoxiu_zx_weixiuren = baoxiu_zx_weixiuren;
        this.baoxiu_fuzerenyijian = baoxiu_fuzerenyijian;
        this.baoxiu_loupan = baoxiu_loupan;
        this.baoxiu_loudong = baoxiu_loudong;
        this.baoxiu_danyuan = baoxiu_danyuan;
        this.baoxiu_fanghao = baoxiu_fanghao;
        this.baoxiu_didian = baoxiu_didian;
        this.baoxiu_dianhua = baoxiu_dianhua;
        this.baoxiu_xiangmu = baoxiu_xiangmu;
        this.baoxiu_zx_weixiurenphone = baoxiu_zx_weixiurenphone;
        this.baoxiu_weixiudanwei = baoxiu_weixiudanwei;
    }

    protected AppRepair(Parcel in) {
        baoxiu_id = in.readInt();
        baoxiu_status = in.readString();
        baoxiu_danhao = in.readString();
        baoxiu_lrtime = in.readString();
        baoxiu_paidantime = in.readString();
        baoxiu_img = in.readString();
        baoxiu_chayanimg = in.readString();
        baoxiu_name = in.readString();
        baoxiu_zx_weixiuren = in.readString();
        baoxiu_fuzerenyijian = in.readString();
        baoxiu_loupan = in.readString();
        baoxiu_loudong = in.readString();
        baoxiu_danyuan = in.readString();
        baoxiu_fanghao = in.readString();
        baoxiu_didian = in.readString();
        baoxiu_dianhua = in.readString();
        baoxiu_xiangmu = in.readString();
        baoxiu_zx_weixiurenphone = in.readString();
        baoxiu_weixiudanwei = in.readString();
    }

    public static final Creator<AppRepair> CREATOR = new Creator<AppRepair>() {
        @Override
        public AppRepair createFromParcel(Parcel in) {
            return new AppRepair(in);
        }

        @Override
        public AppRepair[] newArray(int size) {
            return new AppRepair[size];
        }
    };

    public int getBaoxiu_id() {
        return baoxiu_id;
    }

    public void setBaoxiu_id(int baoxiu_id) {
        this.baoxiu_id = baoxiu_id;
    }

    public String getBaoxiu_status() {
        return baoxiu_status;
    }

    public void setBaoxiu_status(String baoxiu_status) {
        this.baoxiu_status = baoxiu_status;
    }

    public String getBaoxiu_img() {
        return baoxiu_img;
    }

    public void setBaoxiu_img(String baoxiu_img) {
        this.baoxiu_img = baoxiu_img;
    }

    public String getBaoxiu_zx_weixiurenphone() {
        return baoxiu_zx_weixiurenphone;
    }

    public void setBaoxiu_zx_weixiurenphone(String baoxiu_zx_weixiurenphone) {
        this.baoxiu_zx_weixiurenphone = baoxiu_zx_weixiurenphone;
    }

    public String getBaoxiu_lrtime() {

        return baoxiu_lrtime;
    }

    public void setBaoxiu_lrtime(String baoxiu_lrtime) {
        this.baoxiu_lrtime = baoxiu_lrtime;
    }

    public String getBaoxiu_name() {
        return baoxiu_name;
    }

    public void setBaoxiu_name(String baoxiu_name) {
        this.baoxiu_name = baoxiu_name;
    }

    public String getBaoxiu_loudong() {
        return baoxiu_loudong;
    }

    public void setBaoxiu_loudong(String baoxiu_loudong) {
        this.baoxiu_loudong = baoxiu_loudong;
    }

    public String getBaoxiu_loupan() {
        return baoxiu_loupan;
    }

    public void setBaoxiu_loupan(String baoxiu_loupan) {
        this.baoxiu_loupan = baoxiu_loupan;
    }

    public String getBaoxiu_danyuan() {
        return baoxiu_danyuan;
    }

    public void setBaoxiu_danyuan(String baoxiu_danyuan) {
        this.baoxiu_danyuan = baoxiu_danyuan;
    }

    public String getBaoxiu_dianhua() {
        return baoxiu_dianhua;
    }

    public void setBaoxiu_dianhua(String baoxiu_dianhua) {
        this.baoxiu_dianhua = baoxiu_dianhua;
    }

    public String getBaoxiu_didian() {
        return baoxiu_didian;
    }

    public void setBaoxiu_didian(String baoxiu_didian) {
        this.baoxiu_didian = baoxiu_didian;
    }

    public String getBaoxiu_fanghao() {
        return baoxiu_fanghao;
    }

    public void setBaoxiu_fanghao(String baoxiu_fanghao) {
        this.baoxiu_fanghao = baoxiu_fanghao;
    }

    public String getBaoxiu_xiangmu() {
        return baoxiu_xiangmu;
    }

    public void setBaoxiu_xiangmu(String baoxiu_xiangmu) {
        this.baoxiu_xiangmu = baoxiu_xiangmu;
    }

    public String getBaoxiu_danhao() {
        return baoxiu_danhao;
    }

    public void setBaoxiu_danhao(String baoxiu_danhao) {
        this.baoxiu_danhao = baoxiu_danhao;
    }

    public String getBaoxiu_zx_weixiuren() {
        return baoxiu_zx_weixiuren;
    }

    public void setBaoxiu_zx_fuzeren(String baoxiu_zx_weixiuren) {
        this.baoxiu_zx_weixiuren = baoxiu_zx_weixiuren;
    }

    public String getBaoxiu_paidantime() {
        return baoxiu_paidantime;
    }

    public void setBaoxiu_paidantime(String baoxiu_paidantime) {
        this.baoxiu_paidantime = baoxiu_paidantime;
    }

    public String getBaoxiu_chayanimg() {
        return baoxiu_chayanimg;
    }

    public void setBaoxiu_chayanimg(String baoxiu_chayanimg) {
        this.baoxiu_chayanimg = baoxiu_chayanimg;
    }

    public void setBaoxiu_zx_weixiuren(String baoxiu_zx_weixiuren) {
        this.baoxiu_zx_weixiuren = baoxiu_zx_weixiuren;
    }

    public String getBaoxiu_fuzerenyijian() {
        return baoxiu_fuzerenyijian;
    }

    public void setBaoxiu_fuzerenyijian(String baoxiu_fuzerenyijian) {
        this.baoxiu_fuzerenyijian = baoxiu_fuzerenyijian;
    }

    public String getBaoxiu_weixiudanwei() {
        return baoxiu_weixiudanwei;
    }

    public void setBaoxiu_weixiudanwei(String baoxiu_weixiudanwei) {
        this.baoxiu_weixiudanwei = baoxiu_weixiudanwei;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(baoxiu_id);
        dest.writeString(baoxiu_status);
        dest.writeString(baoxiu_danhao);
        dest.writeString(baoxiu_lrtime);
        dest.writeString(baoxiu_paidantime);
        dest.writeString(baoxiu_img);
        dest.writeString(baoxiu_chayanimg);
        dest.writeString(baoxiu_name);
        dest.writeString(baoxiu_zx_weixiuren);
        dest.writeString(baoxiu_fuzerenyijian);
        dest.writeString(baoxiu_loupan);
        dest.writeString(baoxiu_loudong);
        dest.writeString(baoxiu_danyuan);
        dest.writeString(baoxiu_fanghao);
        dest.writeString(baoxiu_didian);
        dest.writeString(baoxiu_dianhua);
        dest.writeString(baoxiu_xiangmu);
        dest.writeString(baoxiu_zx_weixiurenphone);
        dest.writeString(baoxiu_weixiudanwei);
    }
}
