package com.yxld.xzs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianShiJianEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianUploadEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/6.
 */

public class NfcPatrolUtil {
    private static final String NAME = "Nfc";

    public static boolean hasRemainTask() {
        SharedPreferences sp = DemoApplicationLike.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("hasRemainTask_" + Contains.appLogin.getAdminId(), false);
    }

    public static void writeStartPatrol(String jiluId) {
        SharedPreferences sp = DemoApplicationLike.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("hasRemainTask_" + Contains.appLogin.getAdminId(), true);
        edit.putString("currentJiLuId_" + Contains.appLogin.getAdminId(), jiluId);
        edit.putString("startTime_" + Contains.appLogin.getAdminId(), System.currentTimeMillis() + "");
        edit.apply();
    }

    public static String getCurrentJiLuId() {
        SharedPreferences sp = DemoApplicationLike.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString("currentJiLuId_" + Contains.appLogin.getAdminId(), "");
    }

    public static void writeOnCompleted() {
        SharedPreferences sp = DemoApplicationLike.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("hasRemainTask_" + Contains.appLogin.getAdminId(), false);
        edit.remove("currentJiLuId_" + Contains.appLogin.getAdminId());
        edit.remove("startTime_" + Contains.appLogin.getAdminId());
        edit.apply();
    }

    public static String handlerXunJianJiLu(XunJianJiLuEntity jiLuEntity) {
        XunJianUploadEntity entity = new XunJianUploadEntity();
        entity.jiluId = jiLuEntity.jiluId + "";
        entity.jiluWenti = jiLuEntity.jiluWenti + "";
        entity.jiluJieshuShijiShijian = TimeUtil.timesTamp2Year(jiLuEntity.jiluJieshuShijiShijian);
        List<XunJianUploadEntity.DianxiangBean> dianxiangBeanList = new ArrayList<>();

        for (XunJianDianEntity dianEntity : jiLuEntity.xunJianDianDatas) {
            XunJianUploadEntity.DianxiangBean dianxiangBean = new XunJianUploadEntity.DianxiangBean();
            dianxiangBean.xiangqingBianma = dianEntity.dianNfcBianma;
            dianxiangBean.xiangqingBuchong = TextUtils.isEmpty(dianEntity.remark) ? "" : dianEntity.remark;
            dianxiangBean.xiangqingDakaChenggong = dianEntity.isException == -1 ? "2" : dianEntity.hasChecked == 1 ? "1" : "-1";

            if (TextUtils.isEmpty(dianEntity.checkTime)) {
                dianxiangBean.xiangqingDakaShijian = "未打卡";
                entity.jiluWenti = "-1";
            } else {
                dianxiangBean.xiangqingDakaShijian = TimeUtil.timesTamp2Year(Long.parseLong(dianEntity.checkTime));
            }
            dianxiangBean.xiangqingDidian = TextUtils.isEmpty(dianEntity.dianDizhi) ? "" : dianEntity.dianDizhi;
            dianxiangBean.xiangqingJiluId = dianEntity.jiluId + "";
            dianxiangBean.xiangqingShuakaName = TextUtils.isEmpty(jiLuEntity.jiluXunjianXungengrenName) ? "" : jiLuEntity.jiluXunjianXungengrenName;
            dianxiangBean.xiangqingDianId = dianEntity.dianId+"";

            dianxiangBean.xiangqingTupian = dianEntity.remarkImgsUrls;
            dianxiangBean.xiangqingYuyin = dianEntity.remarkRecoderUrl;

            List<XunJianUploadEntity.DianxiangBean.JieguoBean> jieguos = new ArrayList<>();
            //巡检项
            for (XunJianXiangEntity xiangEntity : dianEntity.xunJianXiangDatas) {
                XunJianUploadEntity.DianxiangBean.JieguoBean jieguoBean = new XunJianUploadEntity.DianxiangBean.JieguoBean();
                jieguoBean.jieguoXungenxiangName = TextUtils.isEmpty(xiangEntity.xunjianxiangName) ? "" : xiangEntity.xunjianxiangName;
                jieguoBean.jieguoXungenJieguoName = TextUtils.isEmpty(xiangEntity.xunjianxiangDaAn) ? xiangEntity.xunjianxiangZhengchangzhi : xiangEntity.xunjianxiangDaAn;
                jieguoBean.jieguoYichang = handlerYiChang(xiangEntity);
                jieguoBean.jieguoType = "1";
                jieguoBean.jieguoXiangId = xiangEntity.xunjianxiangId+"";
                if (!"-1".equals(entity.jiluWenti) && "-1".equals(jieguoBean.jieguoYichang)) {
                    entity.jiluWenti = "-1";
                }
                jieguos.add(jieguoBean);
            }
            //事件
            for (XunJianShijianClassifyEntity classifyEntity : dianEntity.xunJianShijianClassifies) {
                for (XunJianShiJianEntity shiJianEntity : classifyEntity.list) {
                    if (shiJianEntity.isAnswer == 1) {
                        XunJianUploadEntity.DianxiangBean.JieguoBean jieguoBean = new XunJianUploadEntity.DianxiangBean.JieguoBean();
                        jieguoBean.jieguoXungenxiangName = "巡检事件";
                        jieguoBean.jieguoType = "2";
                        jieguoBean.jieguoXungenJieguoName = shiJianEntity.shijianName;
                        jieguoBean.jieguoYichang = "-1";
                        jieguoBean.jieguoXiangId = shiJianEntity.shijianId+"";
                        entity.jiluWenti = "-1";
                        jieguos.add(jieguoBean);
                    }
                }
            }

            dianxiangBean.jieguo = jieguos;
            dianxiangBeanList.add(dianxiangBean);
        }
        entity.dianxiang = dianxiangBeanList;
        return GsonHelper.toString(entity);
    }

    private static Pattern sPattern;

    static {
        sPattern = Pattern.compile("\\d+");
    }

    private static String handlerYiChang(XunJianXiangEntity xiangEntity) {
        if (xiangEntity.xunjianxiangLeixin == 1) {
            return TextUtils.isEmpty(xiangEntity.xunjianxiangDaAn) ? "1" : xiangEntity.xunjianxiangDaAn.equals(xiangEntity.xunjianxiangZhengchangzhi) ? "1" : "-1";
        } else if (xiangEntity.xunjianxiangLeixin == 2) {
            if (TextUtils.isEmpty(xiangEntity.xunjianxiangDaAn) || !xiangEntity.xunjianxiangDaAn.matches("\\d+")) {
                return "-1";
            }
            int answer = Integer.parseInt(xiangEntity.xunjianxiangDaAn);
            Matcher matcher = sPattern.matcher(xiangEntity.xunjianxiangZhengchangzhi);
            List<Double> answers = new ArrayList<>();
            while (matcher.find()) {
                answers.add(Double.parseDouble(matcher.group().trim()));
            }
            if (answers.size() == 0) {
                return "1";
            }

            if (answers.size() == 1) {
                return answer == answers.get(0) ? "1" : "-1";
            }

            double a1 = answers.get(0);
            double a2 = answers.get(1);
            return (answer >= a1 && answer <= a2) ? "1" : "-1";
        }
        return "1";
    }
}
