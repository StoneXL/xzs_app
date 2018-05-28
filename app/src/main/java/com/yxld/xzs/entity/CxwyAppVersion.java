package com.yxld.xzs.entity;

/**
 * Created by William on 2017/12/2.
 */

public class CxwyAppVersion {
    private CxwyAppVersion ver;

    // Fields
    /** 版本流水号*/
    private Integer versionId;
    /** 版本号*/
    private String versionUId;
    /** 版本描述*/
    private String versionExplain;
    /** 版本强制更新 1强制更新 2 不强制更新*/
    private Integer versionIsCompulsory;
    /** 下载地址*/
    private String versionDownloadUrl;
    /** 创建时间*/
    private String versionCTime;

    private int versionType;
    // Constructors

    /** default constructor */
    public CxwyAppVersion() {
    }


    /** full constructor */
    public CxwyAppVersion(String versionUId, String versionExplain, Integer versionIsCompulsory, String versionDownloadUrl, String versionCTime) {
        this.versionUId = versionUId;
        this.versionExplain = versionExplain;
        this.versionIsCompulsory = versionIsCompulsory;
        this.versionDownloadUrl = versionDownloadUrl;
        this.versionCTime = versionCTime;
    }


    // Property accessors


    public CxwyAppVersion getVer() {
        return ver;
    }

    public void setVer(CxwyAppVersion ver) {
        this.ver = ver;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getVersionUId() {
        return versionUId;
    }

    public void setVersionUId(String versionUId) {
        this.versionUId = versionUId;
    }

    public String getVersionExplain() {
        return versionExplain;
    }

    public void setVersionExplain(String versionExplain) {
        this.versionExplain = versionExplain;
    }

    public Integer getVersionIsCompulsory() {
        return versionIsCompulsory;
    }

    public void setVersionIsCompulsory(Integer versionIsCompulsory) {
        this.versionIsCompulsory = versionIsCompulsory;
    }

    public String getVersionDownloadUrl() {
        return versionDownloadUrl;
    }

    public void setVersionDownloadUrl(String versionDownloadUrl) {
        this.versionDownloadUrl = versionDownloadUrl;
    }

    public String getVersionCTime() {
        return versionCTime;
    }

    public void setVersionCTime(String versionCTime) {
        this.versionCTime = versionCTime;
    }

    public int getVersionType() {
        return versionType;
    }

    public void setVersionType(int versionType) {
        this.versionType = versionType;
    }

    @Override
    public String toString() {
        return "CxwyAppVersion{" +
                "ver=" + ver +
                ", versionId=" + versionId +
                ", versionUId='" + versionUId + '\'' +
                ", versionExplain='" + versionExplain + '\'' +
                ", versionIsCompulsory=" + versionIsCompulsory +
                ", versionDownloadUrl='" + versionDownloadUrl + '\'' +
                ", versionCTime='" + versionCTime + '\'' +
                ", versionType=" + versionType +
                '}';
    }
}
