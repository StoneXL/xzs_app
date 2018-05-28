package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.p2p.core.P2PHandler;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.LearnAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.entity.Level0Item;
import com.yxld.xzs.entity.Level1Item;
import com.yxld.xzs.yoosee.LearnEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:22:13
 */

public class LearnActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private String deviceId,devicePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.activity_learn);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);
        //设备账号密码获取请求摄像头防区
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        deviceId =bundle.getString("deviceId");
        devicePwd  = bundle.getString("devicePwd");
        P2PHandler.getInstance().getDefenceArea(deviceId, devicePwd);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LearnEventBus(LearnEvent message) {
        switch (message.result) {
            case 0:
                Toast.makeText(this, "学习成功啦~", Toast.LENGTH_SHORT).show();
                break;
            case 24:
                Toast.makeText(this, "该通道已学", Toast.LENGTH_SHORT).show();
                break;
            case 26:
                Toast.makeText(this, "学码超时", Toast.LENGTH_SHORT).show();
                break;
            case 32:
                Toast.makeText(this, "此码已被学", Toast.LENGTH_SHORT).show();
                break;
            case 37:
                Toast.makeText(this, "无效的码值", Toast.LENGTH_SHORT).show();
                break;
        }

        Log.d("...", "helloEventBus: ");
        LearnAdapter learnAdapter = new LearnAdapter(generateData(message.data),deviceId,devicePwd);
        recyclerview.setAdapter(learnAdapter);
    }

    public ArrayList<MultiItemEntity> generateData(ArrayList<int[]> data) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int[] ww = data.get(i);
            Level0Item lv0 = new Level0Item( i);
            for (int j = 0; j < ww.length; j++) {
                Level1Item lv1 = new Level1Item(ww[j],i,j);
//                Log.d("...", "第"+i+"行，第" + j + "个=" + ww[j]);
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    };

}