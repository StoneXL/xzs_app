package com.yxld.xzs.activity.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.videogo.constant.IntentConsts;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.util.DateTimeUtil;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.AisleAdapter;
import com.yxld.xzs.adapter.SpacesItemDecoration;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.CxwyCommon;
import com.yxld.xzs.entity.CxwyCommonToken;
import com.yxld.xzs.http.api.support.ErrorHandlerInterceptor;
import com.yxld.xzs.list.RemoteListContant;
import com.yxld.xzs.utils.UIUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yxld.xzs.contain.Contains.cvoListBean;
import static com.yxld.xzs.http.api.API.IP_PRODUCT;

/**
 * 作者：hu on 2017/6/3
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class AisleActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private AisleAdapter aisleAdapter;
    private CxwyCommonToken cxwyCommonToken;
    private List<CxwyCommon> cxwyCommons = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cxwyCommons = Contains.cxwyCommons;
            cvoListBean = new ArrayList<>();
            cvoListBean.clear();
            for (int i = 0; i < cxwyCommons.size(); i++) {
                for (int j = 0; j < cxwyCommons.get(i).getCvoList().size(); j++) {
                    cvoListBean.add(cxwyCommons.get(i).getCvoList().get(j));
                }
            }
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(AisleActivity.this, 2));
            recyclerView.addItemDecoration(new SpacesItemDecoration(UIUtils.getDisplayWidth(AisleActivity.this) / 1080 * 20));
            aisleAdapter = new AisleAdapter(cvoListBean);
            aisleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    Intent intent = new Intent(AisleActivity.this, RecordActivity.class);
                    EZCameraInfo ezCameraInfo = new EZCameraInfo();
                    ezCameraInfo.setCameraName(aisleAdapter.getData().get(i).getTongdaoname());
                    ezCameraInfo.setDeviceSerial(aisleAdapter.getData().get(i).getShebeixuliehao());
                    ezCameraInfo.setCameraNo(aisleAdapter.getData().get(i).getTongdaohao());
//                    Bundle bundle = new Bundle();
//                    intent.putExtras(bundle);
                    intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, ezCameraInfo);
                    intent.putExtra(RemoteListContant.QUERY_DATE_INTENT_KEY, DateTimeUtil.getNow());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(aisleAdapter);
            aisleAdapter.setOnItemClickListener(AisleActivity.this);
            KLog.i("设置完adapter");
        }
    };

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        KLog.i("点击");
        //获取系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //利用SharedPreferences 存储通道号和时间
        SharedPreferences preferences = getSharedPreferences("device", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int Channel = cvoListBean.get(position).getTongdaohao();
        int id = cvoListBean.get(position).getSxtid();
        if (preferences.getString("Time" + id, df.format(new Date())).equals(df.format(new Date()))) {
            Intent intent = new Intent(this, RealPlayActivity.class);
            intent.putExtra("DeviceSerial", cvoListBean.get(position).getShebeixuliehao());
            intent.putExtra("Channel", Channel);
            startActivityForResult(intent, 0);
            editor.putString("Time" + id, df.format(new Date()));
            editor.commit();
        } else {
            int time = getDistanceTimes(preferences.getString("Time" + id, df.format(new Date())), df.format(new Date()));
//            KLog.i(time);
//           if (time<= 10){
//               ToastUtil.show(this, "请" + TimeUtil.getiDefferTime(preferences.getString("Time"+id,df.format(new Date()))) + "后进行再进行连接", Toast.LENGTH_SHORT);
//           }else {
            editor.putString("Time" + id, df.format(new Date()));
            editor.commit();
            Intent intent = new Intent(this, RealPlayActivity.class);
            intent.putExtra("DeviceSerial", cvoListBean.get(position).getShebeixuliehao());
            intent.putExtra("Channel", Channel);
            startActivity(intent);
//           }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        aisleAdapter.notifyDataSetChanged();
    }

    private CxwyCommon cxwyCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aisle);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAccessToken();
        findAllVcr();
    }

    private void getAccessToken() {
//        String url = "https://open.ys7.com/api/lapp/token/get" + "?appKey=" + DemoApplicationLike.AppKey + "&appSecret=8b2ee852dadd753b8138494800b4afe7";
        String url = "https://open.ys7.com/api/lapp/token/get";

        FormBody body = new FormBody.Builder()
                .add("appKey", DemoApplicationLike.AppKey)
                .add("appSecret", DemoApplicationLike.AppSecret)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorHandlerInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                KLog.i(result);
                Gson gson = new Gson();
                cxwyCommonToken = gson.fromJson(result, CxwyCommonToken.class);
                if (null != cxwyCommonToken && "200".equals( cxwyCommonToken.getCode())) {
                    DemoApplicationLike.getOpenSDK().setAccessToken(cxwyCommonToken.getData().getAccessToken());
                }
            }
        });
    }

    private void findAllVcr() {
//        String url = IP_PRODUCT + "/cameraAdmin/cameraAdmin_getAllSiyouTongdao?id=223";
        String url = IP_PRODUCT + "/vcr/vcr_findAllVcrXzs?uuid=" + Contains.uuid + "&xmid=" + getIntent().getStringExtra("xiangmuId");
        KLog.i("开始获取列表");
        // + Contains.appLogin.getAdminId()
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorHandlerInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                KLog.i("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                KLog.i("成功");
                String result = response.body().string();
                KLog.i(result);
                Gson gson = new Gson();
                cxwyCommon = gson.fromJson(result, CxwyCommon.class);
                Contains.cxwyCommons = cxwyCommon.getData();
                handler.sendEmptyMessage(0);
            }
        });

    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static int getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int times = (int) min;
        return times;
    }
}
