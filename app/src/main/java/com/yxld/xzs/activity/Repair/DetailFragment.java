package com.yxld.xzs.activity.Repair;
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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.AppRepair;
import com.yxld.xzs.view.ImageShowView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yishangfei on 2017/3/23 0023.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * <p>
 * 报修详情
 */
public class DetailFragment extends Fragment {
    @BindView(R.id.detail_single)
    TextView detailSingle;
    @BindView(R.id.detail_time)
    TextView detailTime;
    @BindView(R.id.detail_address)
    TextView detailAddress;
    @BindView(R.id.detail_name)
    TextView detailName;
    @BindView(R.id.detail_tel)
    TextView detailTel;
    @BindView(R.id.detail_details)
    TextView detailDetails;
    @BindView(R.id.detail_image1)
    ImageView detailImage1;
    @BindView(R.id.detail_image2)
    ImageView detailImage2;
    @BindView(R.id.detail_image3)
    ImageView detailImage3;

    private AppRepair appRepair;
    private String[] images;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Intent intent = getActivity().getIntent();
        appRepair = intent.getParcelableExtra("Parcelable");
        detailSingle.setText(appRepair.getBaoxiu_danhao());
        detailTime.setText(appRepair.getBaoxiu_lrtime());
        //获取地址
        String address = appRepair.getBaoxiu_loupan() + appRepair.getBaoxiu_loudong() + "栋" + appRepair
                .getBaoxiu_danyuan() + "单元" + appRepair.getBaoxiu_fanghao() + "　" + appRepair.getBaoxiu_didian();
        detailAddress.setText(address);
        detailName.setText(appRepair.getBaoxiu_name());
        detailTel.setText(appRepair.getBaoxiu_dianhua());
        detailDetails.setText(appRepair.getBaoxiu_xiangmu());
        //设置图片
        Logger.d(appRepair.getBaoxiu_img());
        images = appRepair.getBaoxiu_img().split("\\;");
        for (int i = 0; i < images.length; i++) {
            switch (i) {
                case 0:
                    Glide.with(this).load("http://img0.hnchxwl.com/" + images[0]).crossFade().into(detailImage1);
                    break;
                case 1:
                    Glide.with(this).load("http://img0.hnchxwl.com/" + images[1]).crossFade().into(detailImage2);
                    break;
                case 2:
                    Glide.with(this).load("http://img0.hnchxwl.com/" + images[2]).crossFade().into(detailImage3);
                    break;
                default:
                    break;
            }

        }

    }

    @OnClick({R.id.detail_image1, R.id.detail_image2, R.id.detail_image3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_image1:
                startImageActivity(0);
                //ImageShowView.startImageActivity(getActivity(), detailImage1, "http://img0.hnchxwl.com/" + images[0]);
                break;
            case R.id.detail_image2:
                startImageActivity(1);
                //ImageShowView.startImageActivity(getActivity(), detailImage2, "http://img0.hnchxwl.com/" + images[1]);
                break;
            case R.id.detail_image3:
                startImageActivity(2);
                //ImageShowView.startImageActivity(getActivity(), detailImage3, "http://img0.hnchxwl.com/" + images[2]);
                break;
            default:
                break;
        }
    }

    private void startImageActivity(int currentPosition) {
        if (images.length == 1 && currentPosition == 0 && !images[0].toString().trim().equals("")) {
            ImageShowView.startImageActivity(getActivity(), new ImageView[]{detailImage1}, new
                    String[]{"http://img0.hnchxwl.com/" + images[0]}, currentPosition);
        }
        if (images.length == 2 && currentPosition == 1) {
            ImageShowView.startImageActivity(getActivity(), new ImageView[]{detailImage1, detailImage2}, new
                    String[]{"http://img0.hnchxwl.com/" + images[0], "http://img0.hnchxwl.com/" + images[1]},
                    currentPosition);
        }
        if (images.length == 3 && currentPosition == 2) {
            ImageShowView.startImageActivity(getActivity(), new ImageView[]{detailImage1, detailImage2,
                    detailImage3}, new String[]{"http://img0.hnchxwl.com/" + images[0], "http://img0.hnchxwl.com/" +
                    images[1], "http://img0.hnchxwl.com/" + images[2]}, currentPosition);
        }
    }
}
