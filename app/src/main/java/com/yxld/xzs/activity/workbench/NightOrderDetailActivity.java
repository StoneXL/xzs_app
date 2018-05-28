package com.yxld.xzs.activity.workbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.DeliverymanAdapter;
import com.yxld.xzs.adapter.NightOrderDetailAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.NightOrderDetail;
import com.yxld.xzs.entity.OrderBean;
import com.yxld.xzs.entity.OrderDetailBean;
import com.yxld.xzs.entity.SenderBean;
import com.yxld.xzs.entity.SenderListBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 夜间订单详情
 * Created by William on 2017/11/29.
 */

public class NightOrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_receipt_name)
    TextView tvReceiptName;
    @BindView(R.id.tv_receipt_phone)
    TextView tvReceiptPhone;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_delivery_name)
    TextView tvDeliveryName;
    @BindView(R.id.ib_change)
    ImageButton ibChange;
    @BindView(R.id.auto_ll)
    AutoLinearLayout autoLl;
    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    private OrderBean nightOrderBean;
    private int type;
    private String orderNum;
    private View notDataView;//无数据
    private NightOrderDetailAdapter nightOrderDetailAdapter;
    private List<OrderDetailBean> materialBeanList;
    private List<SenderBean> senderBeenList;
    private DeliverymanAdapter deliverymanAdapter;
    private SenderBean senderBean;//被选中的配送人


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightorder_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nightOrderBean = (OrderBean) getIntent().getSerializableExtra("nightOrderBean");

//        type = nightOrderBean.getZhuangtai();
        orderNum = nightOrderBean.getBianhao();

        initView();
        initAdapter();
        initData();

    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(NightOrderDetail data) {
        if (null != data.getOrderDetail() && data.getOrderDetail().size() != 0) {
            materialBeanList = data.getOrderDetail();
        } else {
            materialBeanList = new ArrayList<>();
        }
        final int size = materialBeanList == null ? 0 : materialBeanList.size();
        if (size > 0) {
            nightOrderDetailAdapter.setNewData(materialBeanList);//将首次数据塞入适配器的方法
        } else {
            nightOrderDetailAdapter.setEmptyView(notDataView);
            nightOrderDetailAdapter.setNewData(new ArrayList<OrderDetailBean>());
        }
        nightOrderDetailAdapter.loadMoreEnd(true);//不显示没有更多数据布局
    }

    /**
     * 获取数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("bianhao", orderNum);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .nightOrderDetail(map)
                .subscribe(new Consumer<NightOrderDetail>() {
                    @Override
                    public void accept(@NonNull NightOrderDetail data) throws Exception {
                        if (data.status == 0) {
                            setData(data);
                        } else {
                            nightOrderDetailAdapter.setEmptyView(notDataView);
                            nightOrderDetailAdapter.setNewData(new ArrayList<OrderDetailBean>());
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        nightOrderDetailAdapter.setEmptyView(notDataView);
                        nightOrderDetailAdapter.setNewData(new ArrayList<OrderDetailBean>());
                    }
                });
        disposables.add(disposable);
    }

    private void initAdapter() {
        nightOrderDetailAdapter = new NightOrderDetailAdapter();
        nightOrderDetailAdapter.setEmptyView(notDataView);
        rvMaterial.setAdapter(nightOrderDetailAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        if (nightOrderBean.getZhuangtai() == 2) {
            if (nightOrderBean.getPeisongrenId() == 0) {//待指派
                autoLl.setVisibility(View.GONE);
                btConfirm.setText("安排配送人");
                type = 1;
            } else {//待取货
                autoLl.setVisibility(View.VISIBLE);
                ibChange.setVisibility(View.VISIBLE);
                tvDeliveryName.setText(nightOrderBean.getPeisongrenMing());
                btConfirm.setText("确认取货");
                type = 2;
            }
        } else if (nightOrderBean.getZhuangtai() == 3) {//待送达
            autoLl.setVisibility(View.VISIBLE);
            ibChange.setVisibility(View.GONE);
            tvDeliveryName.setText(nightOrderBean.getPeisongrenMing());
            btConfirm.setText("确认送达");
            type = 3;
        }
        tvTime.setText(nightOrderBean.getXiadanShijian());
        tvOrderNum.setText(nightOrderBean.getBianhao());
        tvReceiptName.setText(nightOrderBean.getShouhuorenMing());
        tvReceiptPhone.setText(nightOrderBean.getShouhuoDianhua());
        tvLocation.setText(nightOrderBean.getShouhuoDizhi());

        rvMaterial.setLayoutManager(new LinearLayoutManager(this));
        notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data_new, (ViewGroup)
                rvMaterial
                        .getParent(), false);
        rvMaterial.setNestedScrollingEnabled(false);//设置嵌套滑动不能用
    }

    @OnClick({R.id.ib_change, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_change:
//                ToastUtil.showInfo(this, "改变配送人");
                getSendPeopleMsg();
                break;
            case R.id.bt_confirm:
                switch (type) {
                    case 1://安排配送人
                        getSendPeopleMsg();
                        break;
                    case 2://确认取货
                        new AlertView.Builder().setTitle("是否确定取货？")
                                .setStyle(AlertView.Style.Alert)
                                .setOthers(new String[]{"取消", "确定"})
                                .setContext(this)
                                .setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {
                                        if (position == 1) {
                                            confirmDelivery();
                                        }
                                    }
                                })
                                .build()
                                .show();
                        break;
                    case 3://确认送达
                        new AlertView.Builder().setTitle("是否确定送达？")
                                .setStyle(AlertView.Style.Alert)
                                .setOthers(new String[]{"取消", "确定"})
                                .setContext(this)
                                .setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {
                                        if (position == 1) {
                                            confirmSend();
                                        }
                                    }
                                })
                                .build()
                                .show();
                        break;
                }
                break;
        }
    }

    /**
     * 确认送达
     */
    private void confirmSend() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("bianhao", orderNum);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confirmSend(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack robBean) throws Exception {
                        progressDialog.hide();
                        if (robBean.status == 0) {
                            ToastUtil.showInfo(NightOrderDetailActivity.this, "已送达");
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.show(NightOrderDetailActivity.this, "加载失败");
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 确认取货
     */
    private void confirmDelivery() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("bianhao", orderNum);

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confirmDelivery(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack robBean) throws Exception {
                        progressDialog.hide();
                        if (robBean.status == 0) {
                            ToastUtil.showInfo(NightOrderDetailActivity.this, "已取货");
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.show(NightOrderDetailActivity.this, "加载失败");
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 获取配送人信息
     */
    private void getSendPeopleMsg() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .getSendPeopleMsg(map)
                .subscribe(new Consumer<SenderListBean>() {
                    @Override
                    public void accept(@NonNull SenderListBean data) throws Exception {
                        progressDialog.hide();
                        if (data.status == 0) {
                            showBottomDialog(data);
                        } else {
                            onError(data.status, data.MSG);
                        }
//                        Log.e("wh", "size " + data.getRows().size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                      /*  nightOrderDetailAdapter.setEmptyView(notDataView);
                        nightOrderDetailAdapter.setNewData(new ArrayList<OrderDetailBean>());*/
                        ToastUtil.showInfo(NightOrderDetailActivity.this, "请求失败" + throwable
                                .getMessage());
                    }
                });
        disposables.add(disposable);
    }

    private CustomPopWindow customPopWindow;
    private RecyclerView recyclerView;

    /**
     * 显示配送人信息弹窗
     *
     * @param data
     */
    private void showBottomDialog(SenderListBean data) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_deliveryman_list, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_deliveryman);
        Button confirmDeliveryman = (Button) view.findViewById(R.id.bt_confirm_deliveryman);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));

        initRecyclerView();

        if (null != data.getRows() && data.getRows().size() != 0) {
            senderBeenList = data.getRows();
            //动态设置无效
           /* if (senderBeenList.size() < 5) {
                ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT;
                layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT;
                recyclerView.setLayoutParams(layoutParams);
            }*/
            deliverymanAdapter.setNewData(senderBeenList);
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setClippingEnable(false)
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .setFocusable(true)
                    .setView(view)
                    .setContenView(ll_content)
                    .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                    .create()
                    .showAtLocation(tvTime, Gravity.CENTER, 0, 0);
        } else {
            senderBeenList = new ArrayList<>();
            ToastUtil.showInfo(this, "没有配送人信息");
        }

        confirmDeliveryman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == senderBean) {
                    ToastUtil.showInfo(NightOrderDetailActivity.this, "没有选择配送人");
                } else {
                    setDeliveryman();
                    customPopWindow.dismiss();
                }
            }
        });
    }

    /**
     * 设置配送人信息
     */
    private void setDeliveryman() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("peisongrenId", senderBean.getCxwyPeisongId() + "");
        map.put("peisongrenMing", senderBean.getCxwyPeisongName());
        map.put("bianhao", orderNum);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .setDeliveryman(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        progressDialog.hide();
                        if (data.status == 0) {
                            ToastUtil.showInfo(NightOrderDetailActivity.this, "设置成功");
                            if (type == 1) {
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
//                                getPrint();
                            } else if (type == 2) {
//                                getPrint();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvDeliveryName.setText(senderBean.getCxwyPeisongName());
                                    }
                                });
                            }
                        } else {
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.showInfo(NightOrderDetailActivity.this, "请求失败" + throwable
                                .getMessage());
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 初始化配送人列表
     */
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        deliverymanAdapter = new DeliverymanAdapter();
        recyclerView.setAdapter(deliverymanAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
        deliverymanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                for (int j = 0; j < senderBeenList.size(); j++) {
                    if (j == i) {
                        senderBeenList.get(j).setChecked(true);
                        senderBean = senderBeenList.get(j);
                    } else {
                        senderBeenList.get(j).setChecked(false);
                    }
                }
                deliverymanAdapter.notifyDataSetChanged();
            }
        });
    }

//    http://192.168.8.129:8080/wygl/xzsapp/dingdan/print.mvc?bianhao=&uuid=  小票打印接口

    /**
     * 打印小票
     */
    private void getPrint() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("bianhao", orderNum);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .getPrint(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack robBean) throws Exception {
                        progressDialog.hide();
                        if (robBean.status == 0) {
                         /*   ToastUtil.showInfo(NightOrderDetailActivity.this, "已送达");
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();*/
                            ToastUtil.showInfo(NightOrderDetailActivity.this, "小票已打印");
                            //判断是在设置配送人页面还是确认取货页面
                            if (type == 1) {
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            } else if (type == 2) {
                                //在取货页面修改配送人并打印小票后不做操作
                            }
                        } else {
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.show(NightOrderDetailActivity.this, "加载失败");
                    }
                });
        disposables.add(disposable);
    }
}
