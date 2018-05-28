package com.yxld.xzs.utils;

import android.util.LruCache;

import com.yxld.xzs.entity.XunGengXiangQing;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/19.
 */

public class NfcPlanCache {
//    private LruCache<String, List<XunGengXiangQing>> mCache;
//
//    public NfcPlanCache() {
//        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取当前最大可用内存
//        int cacheSize = maxMemory / 4;
//        mCache = new LruCache<String, List<XunGengXiangQing>>(cacheSize) {
//            //必须重写这个方法去加载正确的内存大小，不然默认返回的是元素的个数
//            //用于获取每一个存入进去的对象的大小
//            //该方法在每次加入缓存的时候调用
//            @Override
//            protected int sizeOf(String key, List<XunGengXiangQing> value) {
//                return ;//将要保存的bitmap的实际大小保存进去
//            }
//        };
//    }
}
