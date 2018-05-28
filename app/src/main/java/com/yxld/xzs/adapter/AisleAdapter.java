package com.yxld.xzs.adapter;
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

import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.CxwyCommon;

import java.util.List;

/**
 * Created by 89876 on 2017/5/6 0006.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * <p>
 */
public class AisleAdapter extends BaseQuickAdapter<CxwyCommon.CvoListBean,BaseViewHolder> {
    public AisleAdapter(@Nullable List<CxwyCommon.CvoListBean> data) {
        super(R.layout.activity_aisle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CxwyCommon.CvoListBean item) {
        helper.setText(R.id.aisle_item_name,item.getTongdaoname());
        String url = Environment.getExternalStorageDirectory().getPath() + "/xzs/CapturePicture/" + item.getShebeixuliehao()  + "" + item.getTongdaohao() +".jpg";
        ImageView img = helper.getView(R.id.aisle_item_icon);
        helper.addOnClickListener(R.id.record);
        Glide.with(mContext)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.camera_aisle_item)
                .into(img);
    }

}

