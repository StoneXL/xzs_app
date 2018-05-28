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

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yxld.xzs.R;
import com.yxld.xzs.utils.util.ImageItem;
import com.yxld.xzs.view.ImageShowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yishangfei on 2017/3/16 0016.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 */
public class PhotoAdapter extends
        RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    public final int TYPE_CAMERA = 1;
    public final int TYPE_PICTURE = 2;
    private Context mContext;
    private int selectMax = 9;
    private String[] images;
    private ImageView[] imageViews;
    private LayoutInflater mInflater;
//    private List<TImage> list = new ArrayList<>();
    private List<ImageItem> list = new ArrayList<>();

    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick(int type, int position);
    }

    public PhotoAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<ImageItem> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        ImageView ll_del;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.photo_image);
            ll_del = (ImageView) view.findViewById(R.id.photo_del);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.activity_repair_photo,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(viewHolder.getAdapterPosition(), v);
                }
            });
        }
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
        Log.d("...", "onBindViewHolder: "+position);
        if (getItemViewType(position) == TYPE_CAMERA) {
//        if (getItemViewType(position) == TYPE_PICTURE) {
            Log.d("...", "onBindViewHolder: 1111111111111111111111111");
            viewHolder.mImg.setImageResource(R.mipmap.icon_addpic);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick(0, viewHolder.getAdapterPosition());
                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            images=new String[list.size()];
            for (int i = 0; i <list.size() ; i++) {
//                images[i]=list.get(i).getCompressPath();
                images[i]=list.get(i).getImagePath();
            }
            //if (imageViews == null) {
                imageViews = new ImageView[list.size()];
            //}
            imageViews[position] = viewHolder.mImg;
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnAddPicClickListener.onAddPicClick(1, viewHolder.getAdapterPosition());
                }
            });
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageShowView.startImageActivity((Activity) mContext, imageViews, images, position);
                }
            });
            Glide.with(mContext)
//                    .load(list.get(position).getCompressPath())
                    .load(list.get(position).getImagePath())
                    .crossFade()
                    .into(viewHolder.mImg);
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
