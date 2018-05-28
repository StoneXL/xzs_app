package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 作者：Android on 2017/9/11
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class FangQu extends BaseBack {

    /**
     * data : [{"isStudy":0,"paianShebei":{"shebeiBeizhu":"","shebeiFangquBianhao":"1","shebeiFangquLeixin":"0","shebeiId":1,"shebeiMingliKaiguan":"1","shebeiName":"门磁","shebeiXuexiAdmin":"肖楠","shebeiXuexiShijian":"2017-09-08 00:24:53.0","shebeiZhujiMac":"139A31373138"}},{"isStudy":0,"paianShebei":{"shebeiBeizhu":"","shebeiFangquBianhao":"2","shebeiFangquLeixin":"1","shebeiId":2,"shebeiMingliKaiguan":"1","shebeiName":"红外1","shebeiXuexiAdmin":"肖楠","shebeiXuexiShijian":"2017-09-08 00:25:21.0","shebeiZhujiMac":"139A31373138"}},{"isStudy":0,"paianShebei":{"shebeiBeizhu":"","shebeiFangquBianhao":"3","shebeiFangquLeixin":"0","shebeiId":3,"shebeiMingliKaiguan":"1","shebeiName":"红外2","shebeiXuexiAdmin":"肖楠","shebeiXuexiShijian":"2017-09-08 00:26:05.0","shebeiZhujiMac":"139A31373138"}},{"isStudy":1,"paianShebei":{"shebeiBeizhu":"","shebeiFangquBianhao":"128","shebeiFangquLeixin":"","shebeiId":0,"shebeiMingliKaiguan":"","shebeiName":"","shebeiXuexiAdmin":"","shebeiXuexiShijian":"","shebeiZhujiMac":""}}]
     * status : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable,MultiItemEntity{
        /**
         * isStudy : 0
         * paianShebei : {"shebeiBeizhu":"","shebeiFangquBianhao":"1","shebeiFangquLeixin":"0","shebeiId":1,"shebeiMingliKaiguan":"1","shebeiName":"门磁","shebeiXuexiAdmin":"肖楠","shebeiXuexiShijian":"2017-09-08 00:24:53.0","shebeiZhujiMac":"139A31373138"}
         */

        private int isStudy;
        private PaianShebeiBean paianShebei;

        protected DataBean(Parcel in) {
            isStudy = in.readInt();
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

        public int getIsStudy() {
            return isStudy;
        }

        public void setIsStudy(int isStudy) {
            this.isStudy = isStudy;
        }

        public PaianShebeiBean getPaianShebei() {
            return paianShebei;
        }

        public void setPaianShebei(PaianShebeiBean paianShebei) {
            this.paianShebei = paianShebei;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(isStudy);
        }

        @Override
        public int getItemType() {
            return 0;
        }

        public static class PaianShebeiBean implements Parcelable{
            /**
             * shebeiBeizhu :
             * shebeiFangquBianhao : 1
             * shebeiFangquLeixin : 0
             * shebeiId : 1
             * shebeiMingliKaiguan : 1
             * shebeiName : 门磁
             * shebeiXuexiAdmin : 肖楠
             * shebeiXuexiShijian : 2017-09-08 00:24:53.0
             * shebeiZhujiMac : 139A31373138
             */

            private String shebeiBeizhu;
            private String shebeiFangquBianhao;
            private String shebeiFangquLeixin;
            private int shebeiId;
            private String shebeiMingliKaiguan;
            private String shebeiName;
            private String shebeiXuexiAdmin;
            private String shebeiXuexiShijian;
            private String shebeiZhujiMac;

            protected PaianShebeiBean(Parcel in) {
                shebeiBeizhu = in.readString();
                shebeiFangquBianhao = in.readString();
                shebeiFangquLeixin = in.readString();
                shebeiId = in.readInt();
                shebeiMingliKaiguan = in.readString();
                shebeiName = in.readString();
                shebeiXuexiAdmin = in.readString();
                shebeiXuexiShijian = in.readString();
                shebeiZhujiMac = in.readString();
            }

            public static final Creator<PaianShebeiBean> CREATOR = new Creator<PaianShebeiBean>() {
                @Override
                public PaianShebeiBean createFromParcel(Parcel in) {
                    return new PaianShebeiBean(in);
                }

                @Override
                public PaianShebeiBean[] newArray(int size) {
                    return new PaianShebeiBean[size];
                }
            };

            public String getShebeiBeizhu() {
                return shebeiBeizhu;
            }

            public void setShebeiBeizhu(String shebeiBeizhu) {
                this.shebeiBeizhu = shebeiBeizhu;
            }

            public String getShebeiFangquBianhao() {
                return shebeiFangquBianhao;
            }

            public void setShebeiFangquBianhao(String shebeiFangquBianhao) {
                this.shebeiFangquBianhao = shebeiFangquBianhao;
            }

            public String getShebeiFangquLeixin() {
                return shebeiFangquLeixin;
            }

            public void setShebeiFangquLeixin(String shebeiFangquLeixin) {
                this.shebeiFangquLeixin = shebeiFangquLeixin;
            }

            public int getShebeiId() {
                return shebeiId;
            }

            public void setShebeiId(int shebeiId) {
                this.shebeiId = shebeiId;
            }

            public String getShebeiMingliKaiguan() {
                return shebeiMingliKaiguan;
            }

            public void setShebeiMingliKaiguan(String shebeiMingliKaiguan) {
                this.shebeiMingliKaiguan = shebeiMingliKaiguan;
            }

            public String getShebeiName() {
                return shebeiName;
            }

            public void setShebeiName(String shebeiName) {
                this.shebeiName = shebeiName;
            }

            public String getShebeiXuexiAdmin() {
                return shebeiXuexiAdmin;
            }

            public void setShebeiXuexiAdmin(String shebeiXuexiAdmin) {
                this.shebeiXuexiAdmin = shebeiXuexiAdmin;
            }

            public String getShebeiXuexiShijian() {
                return shebeiXuexiShijian;
            }

            public void setShebeiXuexiShijian(String shebeiXuexiShijian) {
                this.shebeiXuexiShijian = shebeiXuexiShijian;
            }

            public String getShebeiZhujiMac() {
                return shebeiZhujiMac;
            }

            public void setShebeiZhujiMac(String shebeiZhujiMac) {
                this.shebeiZhujiMac = shebeiZhujiMac;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(shebeiBeizhu);
                dest.writeString(shebeiFangquBianhao);
                dest.writeString(shebeiFangquLeixin);
                dest.writeInt(shebeiId);
                dest.writeString(shebeiMingliKaiguan);
                dest.writeString(shebeiName);
                dest.writeString(shebeiXuexiAdmin);
                dest.writeString(shebeiXuexiShijian);
                dest.writeString(shebeiZhujiMac);
            }
        }
    }
}
