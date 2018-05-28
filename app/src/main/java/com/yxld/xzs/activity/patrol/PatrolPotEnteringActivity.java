package com.yxld.xzs.activity.patrol;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
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
import io.reactivex.functions.Consumer;

import static com.p2p.core.utils.DES.bytesToHexString;

/**
 * 作者：Android on 2017/9/15
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class PatrolPotEnteringActivity extends BaseActivity {

    @BindView(R.id.tv_bianma)
    TextView tvBianma;
    @BindView(R.id.tv_jingweidu)
    TextView tvJingweidu;
    @BindView(R.id.tv_dizhi)
    EditText tvDizhi;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter mIntentFilter;
    private boolean mIsNfcEnabled;

    private static final String[][] TECH_LIST;

    private WheelView xiangmu;
    private ArrayList<String> xiangmuList;
    private NumericWheelAdapter xiangmuAdapter;
    private int[] ids;
    private int id = -1;

    static {
        TECH_LIST = new String[][]{
                //与@xml/nfc_filter_protocol.xml中的一样
//                {MifareClassic.class.getName(), NfcA.class.getName(), Ndef.class.getName()},
//                {MifareClassic.class.getName(), NfcA.class.getName(), NdefFormatable.class.getName()}
                {MifareClassic.class.getName()},
                {NfcA.class.getName()},
                {Ndef.class.getName()},
                {NdefFormatable.class.getName()},
                {IsoDep.class.getName()},
                {MifareUltralight.class.getName()}
        };
    }

    private void unregisterNfcForegroundListener() {
        if (mIsNfcEnabled) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNfcForegroundListener();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        boolean isJustLook = false;
//        if(bundle!=null && bundle.containsKey(PatrolRecordActivity.KEY_IN_TYPE)){
//            int inType = bundle.getInt(PatrolRecordActivity.KEY_IN_TYPE,PatrolRecordActivity.IN_TYPE_CHECK);
//            isJustLook = inType == PatrolRecordActivity.IN_TYPE_LOOK;
//        }
//        if(!isJustLook){
        initNfcEvent();
//        }
        setContentView(R.layout.activity_patrol_pot_entering);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNfcForegroundListener();
    }

    private void registerNfcForegroundListener() {
        if (mIsNfcEnabled) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, new IntentFilter[]{mIntentFilter}, TECH_LIST);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        KLog.i("nfc卡靠近");
        if (mIsNfcEnabled && NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            handlerIntent(intent);
        }
    }

    private void initNfcEvent() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (mNfcAdapter == null) {
            Toast.makeText(this, "该设备不支持NFC，请更换支持NFC的设备", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "请在设置中打开NFC", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mIsNfcEnabled = true;
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        mIntentFilter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        mIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
    }

    private void handlerIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String nfcDeviceId = bytesToHexString(tag.getId());
        KLog.i("Nfc", "deviceId:" + nfcDeviceId);
        nfcDeviceId = nfcDeviceId.replace("[", "").replace("]", "").replace(", ", "");
        nfcDeviceId = nfcDeviceId.toUpperCase();
        nfcDeviceId = "0x" + nfcDeviceId;
        tvBianma.setText(nfcDeviceId);
    }

    private void findXm() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        if (xiangMu.status == 0) {
                            ids = new int[xiangMu.getData().size()];
                            xiangmuList = new ArrayList<String>();
                            for (int i = 0; i < xiangMu.getData().size(); i++) {
                                if (xiangMu.getData().get(i) != null) {
                                    ids[i] = xiangMu.getData().get(i).getXiangmuId();
                                    xiangmuList.add(xiangMu.getData().get(i).getXiangmuName());
                                }
                            }
                            xiangmuAdapter = new NumericWheelAdapter(PatrolPotEnteringActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
                            xiangmuAdapter.setTextSize(15);
                            xiangmu.setViewAdapter(xiangmuAdapter);
                        } else {
                            onError(xiangMu.status, xiangMu.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    private void showWheelView(View showView) {
        if (xiangmuList == null) {
            findXm();
        }
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                tvJingweidu.setText(xiangmuList.get(xiangmu.getCurrentItem()));
                id = ids[xiangmu.getCurrentItem()];
            }
        });
        xiangmu = (WheelView) ll_content.findViewById(R.id.xiangmu);
        if (xiangmuList != null) {
            xiangmu.setViewAdapter(xiangmuAdapter);
        }
        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.tv_jingweidu, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jingweidu:
                showWheelView(tvJingweidu);
                break;
            case R.id.tv_confirm:
                if (id == -1) {
                    Toast.makeText(PatrolPotEnteringActivity.this, "请选择项目", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvBianma.getText().toString().trim().equals("")) {
                    Toast.makeText(PatrolPotEnteringActivity.this, "没有nfc编码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvDizhi.getText().toString().trim().equals("")) {
                    Toast.makeText(PatrolPotEnteringActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("dianNfcBianma", tvBianma.getText().toString().trim());
                map.put("dianXiangmuId", id + "");
                map.put("dianDizhi", tvDizhi.getText().toString().trim());
                map.put("uuid", Contains.uuid);
                KLog.i(map);
                addXunJianDian(map);
                break;
        }
    }

    private void addXunJianDian(Map map) {
        progressDialog.show();
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).addDian(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        progressDialog.hide();
                        if (baseBack.getStatus() == 0) {
                            Toast.makeText(PatrolPotEnteringActivity.this, baseBack.getMsg(), Toast.LENGTH_SHORT).show();
                            tvBianma.setText("");
                            tvDizhi.setText("");
//                            tvJingweidu.setText("");
//                            id = -1;
                        } else {
                            onError(baseBack.status, baseBack.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        progressDialog.hide();
                    }
                });
        disposables.add(disposable);
    }
}
