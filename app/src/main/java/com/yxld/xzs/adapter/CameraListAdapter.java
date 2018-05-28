package com.yxld.xzs.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.AppCameraList;

import java.io.File;
import java.util.List;

import static com.yxld.xzs.activity.camera.DeviceActivity.LoginID;


/**
 * Created by yishangfei on 2017/2/22 0022.
 * 邮箱：yishangfei@foxmail.com
 */
public class CameraListAdapter extends BaseQuickAdapter<AppCameraList.DataBean, BaseViewHolder> {

    public CameraListAdapter(List<AppCameraList.DataBean> list) {
        super(R.layout.cameralist_item_layout, list);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, AppCameraList.DataBean item) {
        Button status=viewHolder.getView(R.id.camera_status);
        status.getBackground().setAlpha(102);
        //viewHolder.setVisible(R.id.camera_status, false);
//        viewHolder.setText(R.id.camera_bufang, item.getBufangStatus() == 1? "已布防": "未布防");
        viewHolder.setText(R.id.camera_bufang, item.getEquipXiangmuName());
        viewHolder.setText(R.id.camera_name,item.getEquipXiangmuName());
        viewHolder.setText(R.id.camera_status, item.getEquipStatus() == 1? "在线":"未在线");
        String filepath= Environment.getExternalStorageDirectory() + "/screenshot/tempHead/" + getUserID() + "/" + item.getEquipSerial() + ".jpg";
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filepath);
            //将图片显示到ImageView中
            viewHolder.setImageBitmap(R.id.camera_image,bm);
        } else {
            viewHolder.setImageDrawable(R.id.camera_image,mContext.getResources().getDrawable(R.mipmap.icon_camera));
        }
        viewHolder.setText(R.id.camera_name, item.getEquipName())
                .addOnLongClickListener(R.id.camera_image)
                .addOnClickListener(R.id.camera_image)
        .addOnClickListener(R.id.camera_video);
    }

    public String getUserID() {
        String usId;
        try {
            usId = "0" + String.valueOf((Integer.parseInt(LoginID) & 0x7fffffff));
            return usId;
        } catch (NumberFormatException e) {
            return LoginID;
        }
    }
}