package com.yxld.xzs.contain;


import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.entity.CxwyCommon;
import com.yxld.xzs.entity.IndexMessageBean;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 静态常量类
 *
 * @author yishangfei
 */
public class Contains {

    /**
     * 用户信息
     */

    public static AppLogin appLogin = new AppLogin();

    //录音文件路径管理类
    public static final String ROOT_PATH = "xzs/";
    public static final String RECORD_DIR = "record/";

    /**
     * 公共安防
     */
    public static List<CxwyCommon> cxwyCommons = new ArrayList<CxwyCommon>();
    public static List<CxwyCommon.CvoListBean> cvoListBean = new ArrayList<>();

    public static String uuid = "";

    public static XunJianJiLuEntity jilu;
    public static List<Integer> indexMessageList = new ArrayList(){{add(0); add(0);add(0);}};

}
