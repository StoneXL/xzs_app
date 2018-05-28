package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 作者：Android on 2017/9/11
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class Host extends BaseBack {

    /**
     * data : [{"isOnline":1,"zhujiBeizhu":"","zhujiDanyuan":"1","zhujiFanghao":"12","zhujiGongsiId":1,"zhujiId":1,"zhujiJiaruAdmin":"肖楠","zhujiJiaruShijian":"2017-09-08 00:18:47.0","zhujiLoudong":"1","zhujiMac":"139A31373138","zhujiMima":"","zhujiShebeiName":"测试报警器","zhujiXiangmuId":"321","zhujiXiangmuName":"凯姆国际"},{"isOnline":1,"zhujiBeizhu":"测试","zhujiDanyuan":"2","zhujiFanghao":"908","zhujiGongsiId":1,"zhujiId":2,"zhujiJiaruAdmin":"肖楠","zhujiJiaruShijian":"2017-09-08 09:42:06.0","zhujiLoudong":"2","zhujiMac":"13A531373138","zhujiMima":"","zhujiShebeiName":"凯姆908","zhujiXiangmuId":"321","zhujiXiangmuName":"凯姆国际"},{"isOnline":1,"zhujiBeizhu":"","zhujiDanyuan":"1","zhujiFanghao":"1","zhujiGongsiId":1,"zhujiId":3,"zhujiJiaruAdmin":"庾亮","zhujiJiaruShijian":"2017-09-08 11:04:15.0","zhujiLoudong":"1","zhujiMac":"139A31373138","zhujiMima":"","zhujiShebeiName":"中远51区测试","zhujiXiangmuId":"346","zhujiXiangmuName":"中远公馆"}]
     * status : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable{
        /**
         * isOnline : 1
         * zhujiBeizhu :
         * zhujiDanyuan : 1
         * zhujiFanghao : 12
         * zhujiGongsiId : 1
         * zhujiId : 1
         * zhujiJiaruAdmin : 肖楠
         * zhujiJiaruShijian : 2017-09-08 00:18:47.0
         * zhujiLoudong : 1
         * zhujiMac : 139A31373138
         * zhujiMima :
         * zhujiShebeiName : 测试报警器
         * zhujiXiangmuId : 321
         * zhujiXiangmuName : 凯姆国际
         * zhujizhujiguanliyuan : 1503121321
         */

        private int isOnline;
        private String zhujiBeizhu;
        private String zhujiDanyuan;
        private String zhujiFanghao;
        private int zhujiGongsiId;
        private int zhujiId;
        private String zhujiJiaruAdmin;
        private String zhujiJiaruShijian;
        private String zhujiLoudong;
        private String zhujiMac;
        private String zhujiMima;
        private String zhujiShebeiName;
        private String zhujiXiangmuId;
        private String zhujiXiangmuName;
        private String zhujiHaoma;

        protected DataBean(Parcel in) {
            isOnline = in.readInt();
            zhujiBeizhu = in.readString();
            zhujiDanyuan = in.readString();
            zhujiFanghao = in.readString();
            zhujiGongsiId = in.readInt();
            zhujiId = in.readInt();
            zhujiJiaruAdmin = in.readString();
            zhujiJiaruShijian = in.readString();
            zhujiLoudong = in.readString();
            zhujiMac = in.readString();
            zhujiMima = in.readString();
            zhujiShebeiName = in.readString();
            zhujiXiangmuId = in.readString();
            zhujiXiangmuName = in.readString();
            zhujiHaoma=in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public String getZhujiBeizhu() {
            return zhujiBeizhu;
        }

        public void setZhujiBeizhu(String zhujiBeizhu) {
            this.zhujiBeizhu = zhujiBeizhu;
        }

        public String getZhujiDanyuan() {
            return zhujiDanyuan;
        }

        public void setZhujiDanyuan(String zhujiDanyuan) {
            this.zhujiDanyuan = zhujiDanyuan;
        }

        public String getZhujiFanghao() {
            return zhujiFanghao;
        }

        public void setZhujiFanghao(String zhujiFanghao) {
            this.zhujiFanghao = zhujiFanghao;
        }

        public int getZhujiGongsiId() {
            return zhujiGongsiId;
        }

        public void setZhujiGongsiId(int zhujiGongsiId) {
            this.zhujiGongsiId = zhujiGongsiId;
        }

        public int getZhujiId() {
            return zhujiId;
        }

        public void setZhujiId(int zhujiId) {
            this.zhujiId = zhujiId;
        }

        public String getZhujiJiaruAdmin() {
            return zhujiJiaruAdmin;
        }

        public void setZhujiJiaruAdmin(String zhujiJiaruAdmin) {
            this.zhujiJiaruAdmin = zhujiJiaruAdmin;
        }

        public String getZhujiJiaruShijian() {
            return zhujiJiaruShijian;
        }

        public void setZhujiJiaruShijian(String zhujiJiaruShijian) {
            this.zhujiJiaruShijian = zhujiJiaruShijian;
        }

        public String getZhujiLoudong() {
            return zhujiLoudong;
        }

        public void setZhujiLoudong(String zhujiLoudong) {
            this.zhujiLoudong = zhujiLoudong;
        }

        public String getZhujiMac() {
            return zhujiMac;
        }

        public void setZhujiMac(String zhujiMac) {
            this.zhujiMac = zhujiMac;
        }

        public String getZhujiMima() {
            return zhujiMima;
        }

        public void setZhujiMima(String zhujiMima) {
            this.zhujiMima = zhujiMima;
        }

        public String getZhujiShebeiName() {
            return zhujiShebeiName;
        }

        public void setZhujiShebeiName(String zhujiShebeiName) {
            this.zhujiShebeiName = zhujiShebeiName;
        }

        public String getZhujiXiangmuId() {
            return zhujiXiangmuId;
        }

        public void setZhujiXiangmuId(String zhujiXiangmuId) {
            this.zhujiXiangmuId = zhujiXiangmuId;
        }

        public String getZhujiXiangmuName() {
            return zhujiXiangmuName;
        }

        public void setZhujiXiangmuName(String zhujiXiangmuName) {
            this.zhujiXiangmuName = zhujiXiangmuName;
        }

        public String getZhujiHaoma() {
            return zhujiHaoma;
        }

        public void setZhujiHaoma(String zhujiHaoma) {
            this.zhujiHaoma = zhujiHaoma;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(isOnline);
            dest.writeString(zhujiBeizhu);
            dest.writeString(zhujiDanyuan);
            dest.writeString(zhujiFanghao);
            dest.writeInt(zhujiGongsiId);
            dest.writeInt(zhujiId);
            dest.writeString(zhujiJiaruAdmin);
            dest.writeString(zhujiJiaruShijian);
            dest.writeString(zhujiLoudong);
            dest.writeString(zhujiMac);
            dest.writeString(zhujiMima);
            dest.writeString(zhujiShebeiName);
            dest.writeString(zhujiXiangmuId);
            dest.writeString(zhujiXiangmuName);
            dest.writeString(zhujiHaoma);
        }
    }
}
