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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppRepair;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.BuMen;
import com.yxld.xzs.entity.FuZe;
import com.yxld.xzs.entity.ZhiPaiRen;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;
import com.yxld.xzs.utils.PopWindowUtil;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.ImageShowView;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yishangfei on 2017/3/22 0022.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * <p>
 * 主管指派
 */
public class WorkActivity extends BaseActivity {
    @BindView(R.id.work_single)
    TextView workSingle;
    @BindView(R.id.work_time)
    TextView workTime;
    @BindView(R.id.work_address)
    TextView workAddress;
    @BindView(R.id.work_name)
    TextView workName;
    @BindView(R.id.work_tel)
    TextView workTel;
    @BindView(R.id.work_details)
    TextView workDetails;
    @BindView(R.id.work_select)
    TextView workSelect;
    @BindView(R.id.work_image1)
    ImageView workImage1;
    @BindView(R.id.work_image2)
    ImageView workImage2;
    @BindView(R.id.work_image3)
    ImageView workImage3;
    @BindView(R.id.work_button)
    Button workButton;
    @BindView(R.id.work_bumen)
    TextView mWorkBumen;
    @BindView(R.id.ll_layout1)
    AutoLinearLayout mLlLayout1;
    @BindView(R.id.work_fuze)
    TextView mWorkFuze;
    @BindView(R.id.ll_layout2)
    AutoLinearLayout mLlLayout2;
    @BindView(R.id.ll_layout3)
    AutoLinearLayout mLlLayout3;

    private int adminId;
    private String images[];
    private AppRepair appRepair;

    private WheelView wheelView;
    private NumericWheelAdapter wheelAdapter;
    private ArrayList<String> wheelList;
    private ArrayList<Integer> idlList;
    private int bumenId = -1;
    private String bumenName;
    private int fuzeId = -1;
    private String fuzeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        wheelList = new ArrayList<>();
        idlList = new ArrayList<>();
        Intent intent = getIntent();
        appRepair = intent.getParcelableExtra("Parcelable");
        workSingle.setText(appRepair.getBaoxiu_danhao());
        workTime.setText(appRepair.getBaoxiu_lrtime());
        //获取地址
        String address = appRepair.getBaoxiu_loupan() + appRepair.getBaoxiu_loudong() + "栋" + appRepair
                .getBaoxiu_danyuan() + "单元" + appRepair.getBaoxiu_fanghao() + "　" + appRepair.getBaoxiu_didian();
        workAddress.setText(address);
        workName.setText(appRepair.getBaoxiu_name());
        workTel.setText(appRepair.getBaoxiu_dianhua());
        workDetails.setText(appRepair.getBaoxiu_xiangmu());
        //设置图片
        Logger.d(appRepair.getBaoxiu_img());
        images = appRepair.getBaoxiu_img().split("\\;");
        for (int i = 0; i < images.length; i++) {
            switch (i) {
                case 0:
                    Glide.with(this).load("http://img0.hnchxwl.com/" + images[0]).crossFade().into(workImage1);
                    break;
                case 1:
                    Glide.with(this).load("http://img0.hnchxwl.com/" + images[1]).crossFade().into(workImage2);
                    break;
                case 2:
                    Glide.with(this).load("http://img0.hnchxwl.com/" + images[2]).crossFade().into(workImage3);
                    break;
                default:
                    break;
            }

        }
        //根据报修单显示不同界面状态显示
        if ("1".equals(appRepair.getBaoxiu_status())) {
            mLlLayout1.setVisibility(View.VISIBLE);
            mLlLayout2.setVisibility(View.VISIBLE);
            mLlLayout3.setVisibility(View.GONE);
        } else if ("2".equals(appRepair.getBaoxiu_status())) {
            mLlLayout1.setVisibility(View.GONE);
            mLlLayout2.setVisibility(View.GONE);
            mLlLayout3.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.work_select, R.id.work_bumen, R.id.work_fuze, R.id.work_button, R.id.work_image1, R.id
            .work_image2, R.id.work_image3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.work_select:
                Assign();
                break;
            case R.id.work_bumen:
                bumen();
                break;
            case R.id.work_fuze:
                fuze();
                break;
            case R.id.work_button:
                if ("2".equals(appRepair.getBaoxiu_status())) {
                    if (workSelect.getText().equals("")) {
                        Toast.makeText(this, "请选择需要指派的维修员", Toast.LENGTH_SHORT).show();
                    } else {
                        ServiceFactory.httpService().point(Contains.appLogin.getAdminId(), adminId, appRepair
                                .getBaoxiu_id()).compose(new DefaultTransformer<String>()).subscribe(new RxSubscriber<String>(this) {
                            @Override
                            public void onNext(String s) {
                                Toast.makeText(WorkActivity.this, "指派成功", Toast.LENGTH_SHORT).show();
                                finish();
                                System.gc();
                            }
                        });
                    }
                } else if ("1".equals(appRepair.getBaoxiu_status())) {
                    confirm();
                }
                break;
            case R.id.work_image1:
                startImageActivity(0);
//                currentPosition = 0;
//                if (!images[0].toString().trim().equals("")) {
//                    ImageShowView.startImageActivity(this, workImage1, "http://img0.hnchxwl.com/" + images[0]);
//                }
                break;
            case R.id.work_image2:
                startImageActivity(1);
//                if (images.length >= 2) {
//                    ImageShowView.startImageActivity(this, workImage2, "http://img0.hnchxwl.com/" + images[1]);
//                }
                break;
            case R.id.work_image3:
                startImageActivity(2);
//                currentPosition = 2;
//                if (images.length >= 3) {
//                    ImageShowView.startImageActivity(this, workImage3, "http://img0.hnchxwl.com/" + images[2]);
//
//                }
                break;
            default:
                break;
        }
    }

    private void startImageActivity(int currentPosition) {
        if (images.length == 1 && !images[0].toString().trim().equals("") && currentPosition == 0) {
            ImageShowView.startImageActivity(this, new ImageView[]{workImage1}, new String[]{"http://img0.hnchxwl" +
                    ".com/" + images[0]}, currentPosition);
        }
        if (images.length == 2 && currentPosition == 1) {
            ImageShowView.startImageActivity(this, new ImageView[]{workImage1, workImage2}, new
                    String[]{"http://img0.hnchxwl.com/" + images[0], "http://img0.hnchxwl.com/" + images[1]},
                    currentPosition);
        }
        if (images.length == 3 && currentPosition == 2) {
            ImageShowView.startImageActivity(this, new ImageView[]{workImage1, workImage2, workImage3}, new
                    String[]{"http://img0.hnchxwl.com/" + images[0], "http://img0.hnchxwl.com/" + images[1],
                    "http://img0.hnchxwl.com/" + images[2]}, currentPosition);
        }
    }


    //指派维修人
    private void Assign() {
        Map<String, String> map = new HashMap<>();
        map.put("answer", Contains.appLogin.getAdminId() + "");
        Disposable subscribe = HttpAPIWrapper.getInstance().getFuZeRen(map).subscribe(new Consumer<ZhiPaiRen>() {
            @Override
            public void accept(@NonNull ZhiPaiRen zhiPaiRen) throws Exception {
                if (zhiPaiRen.status == 0) {
                    wheelList.clear();
                    idlList.clear();
                    if (zhiPaiRen.getRow().size() == 0) {
                        wheelList.add(Contains.appLogin.getAdminNickName());
                        idlList.add(Contains.appLogin.getAdminId());
                    } else {
                        for (int i = 0; i < zhiPaiRen.getRow().size(); i++) {
                            wheelList.add(zhiPaiRen.getRow().get(i).getAdminNickName());
                            idlList.add(zhiPaiRen.getRow().get(i).getAdminId());
                        }
                    }
                    wheelAdapter = new NumericWheelAdapter(WorkActivity.this, 0, wheelList.size() - 1, "", wheelList);
                    wheelAdapter.setTextSize(15);
                    showWheelView(mWorkBumen, 2);
                } else if (zhiPaiRen.status==1){
                    workSelect.setText(Contains.appLogin.getAdminNickName());
                    adminId = Contains.appLogin.getAdminId();
                }else {
                    onError(zhiPaiRen.status, zhiPaiRen.getMSG());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
        disposables.add(subscribe);
    }

    /**
     * 指派部门
     */
    private void bumen() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("xiangmuid", Contains.appLogin.getAdminXiangmuId() + "");
        Disposable subscribe = HttpAPIWrapper.getInstance().getBumen(map).subscribe(new Consumer<BuMen>() {
            @Override
            public void accept(@NonNull BuMen buMen) throws Exception {
                if (buMen.status == 1) {
                    if (buMen.getRows().size() > 0) {
                        wheelList.clear();
                        idlList.clear();
                        for (int i = 0; i < buMen.getRows().size(); i++) {
                            wheelList.add(buMen.getRows().get(i).getDepartName());
                            idlList.add(buMen.getRows().get(i).getDepartId());
                        }
                        wheelAdapter = new NumericWheelAdapter(WorkActivity.this, 0, wheelList.size() - 1, "",
                                wheelList);
                        wheelAdapter.setTextSize(15);
                        showWheelView(mWorkBumen, 0);
                    }
                } else {
                    onError(buMen.status, buMen.msg);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
        disposables.add(subscribe);
    }

    private void showWheelView(View showView, final int flag) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(showView.getWindowToken(), 0);
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        TextView cancel = (TextView) ll_content.findViewById(R.id.cancel);
        TextView tv_title = (TextView) ll_content.findViewById(R.id.tv_title);
        wheelView = (WheelView) ll_content.findViewById(R.id.xiangmu);
        wheelView.setViewAdapter(wheelAdapter);
        switch (flag) {
            case 0:
                tv_title.setText("请选择部门");
                break;
            case 1:
                tv_title.setText("请选择负责人");
                break;
            case 2:
                tv_title.setText("请选择维修人");
                break;
            default:
                break;
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                onWheelViewOnconfirm(flag);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();

            }
        });
        PopWindowUtil.showFullScreenPopWindow(this, showView, view, ll_content);
    }

    private void onWheelViewOnconfirm(int flag) {
        int position = wheelView.getCurrentItem();
        switch (flag) {
            case 0:
                if (StringUitl.isEmpty(bumenName)) {
                    mWorkBumen.setText(wheelList.get(position));
                    bumenId = idlList.get(position);
                    bumenName = wheelList.get(position);
                } else {
                    if (!bumenName.equals(wheelList.get(position))) {
                        mWorkFuze.setText("");
                        fuzeId = -1;
                        fuzeName = "";
                        mWorkBumen.setText(wheelList.get(position));
                        bumenId = idlList.get(position);
                        bumenName = wheelList.get(position);
                    }
                }

                break;
            case 1:
                mWorkFuze.setText(wheelList.get(position));
                fuzeId = idlList.get(position);
                fuzeName = wheelList.get(position);
                break;
            case 2:
                workSelect.setText(wheelList.get(position));
                adminId = idlList.get(position);
                break;
            default:
                break;
        }
    }

    /**
     * 指派负责
     */
    private void fuze() {
        if (bumenId == -1) {
            ToastUtil.showToast(this, "请选择部门");
            return;
        }
        ////xzsapp/repair/chargeadmin.mvc?uuid=&xiangmuid=&departId=
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("xiangmuid", Contains.appLogin.getAdminXiangmuId() + "");
        map.put("departId", bumenId + "");
        Disposable subscribe = HttpAPIWrapper.getInstance().getFuZe(map).subscribe(new Consumer<FuZe>() {
            @Override
            public void accept(@NonNull FuZe fuze) throws Exception {
                if (fuze.status == 1) {
                    if (fuze.getRows().size() > 0) {
                        wheelList.clear();
                        idlList.clear();
                        for (int i = 0; i < fuze.getRows().size(); i++) {
                            wheelList.add(fuze.getRows().get(i).getAdminNickName());
                            idlList.add(fuze.getRows().get(i).getAdminId());
                        }
                        wheelAdapter = new NumericWheelAdapter(WorkActivity.this, 0, wheelList.size() - 1, "",
                                wheelList);
                        wheelAdapter.setTextSize(15);
                        showWheelView(mWorkBumen, 1);
                    }
                } else {
                    // onError(fuze.status);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
        disposables.add(subscribe);
    }

    private void confirm() {
        if (bumenId == -1) {
            ToastUtil.showToast(this, "请选择部门");
            return;
        }
        if (fuzeId == -1) {
            ToastUtil.showToast(this, "请选择负责人");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("baoxiu_id", appRepair.getBaoxiu_id() + "");
        map.put("baoxiu_status", "2");
        map.put("baoxiu_weixiudanwei", "自修人");
        map.put("baoxiu_zx_bumenid", bumenId + "");
        map.put("baoxiu_zx_bumen", bumenName);
        map.put("baoxiu_zx_fuzerenid", fuzeId + "");
        map.put("baoxiu_zx_fuzeren", fuzeName);
        Disposable subscribe = HttpAPIWrapper.getInstance().confirmFuze(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack fuze) throws Exception {
                if (fuze.status == 1) {
                    finish();
                } else {
                    onError(fuze.status, fuze.msg);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
        disposables.add(subscribe);

    }

}
