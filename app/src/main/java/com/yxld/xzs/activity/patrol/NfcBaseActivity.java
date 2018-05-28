package com.yxld.xzs.activity.patrol;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.db.DBUtil;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.service.MapService;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/6.
 * 基础的nfc界面
 */

public abstract class NfcBaseActivity extends BaseActivity implements TakePhoto.TakeResultListener,InvokeListener {
    private static final String TAG = TakePhotoActivity.class.getName();
    private TakePhoto takePhoto;

    private InvokeParam invokeParam;
    protected DBUtil dbUtil;
    private static final String[][] TECH_LIST;

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


    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter mIntentFilter;
    private boolean mIsNfcEnabled;

    private MapService mMapService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MapService.MapBinder binder = (MapService.MapBinder) service;
            mMapService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMapService = null;
            Log.e("Tag", "Service DisConnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        boolean isJustLook = false;
        if(bundle!=null && bundle.containsKey(PatrolRecordActivity.KEY_IN_TYPE)){
            int inType = bundle.getInt(PatrolRecordActivity.KEY_IN_TYPE,PatrolRecordActivity.IN_TYPE_CHECK);
            isJustLook = inType == PatrolRecordActivity.IN_TYPE_LOOK;
        }
        if(!isJustLook){
            initNfcEvent();
        }

        dbUtil = DBUtil.getInstance(getApplicationContext());
        Intent mServiceIntent = new Intent(this, MapService.class);
//        bindService(mServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mIsNfcEnabled
                && intent != null && Contains.jilu!=null
                && NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            handlerIntent(intent);
        }
    }

    private void handlerIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String nfcDeviceId = bytesToHexString(tag.getId());
        Log.e("Nfc","deviceId:"+nfcDeviceId);
        int pos = getXunJianDianById(nfcDeviceId);
        if(pos == -1){
            Toast.makeText(this, "该点不在本次巡检的线路上，请检查路线是否正常", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Contains.jilu.jiluPaixu ==1 && Contains.jilu.nextXunjianDian != pos){
            //顺序巡检，并且不是下个巡检点
            Toast.makeText(this, "请按照顺序巡检", Toast.LENGTH_SHORT).show();
            return;
        }


        final XunJianDianEntity entity = Contains.jilu.xunJianDianDatas.get(pos);
        if (entity.hasChecked == 1) {
            Toast.makeText(this, "您本次已经巡逻过该卡", Toast.LENGTH_SHORT).show();
            return;
        }

        Contains.jilu.nextXunjianDian++;
        entity.hasChecked = 1;
        entity.checkTime = System.currentTimeMillis()+"";
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                dbUtil.updateJustOneJiLu(Contains.jilu);
                dbUtil.updateOneXunJianDian(entity);
                e.onNext(1);
                e.onComplete();
                Log.e("Tag","更新一条Nfc");

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        onNfcTagChecked(entity);
                    }
                });
    }

    private boolean isCorrentDakaDian(int currentDakaDian) {
        if(Contains.jilu.jiluPaixu !=1 || currentDakaDian ==0){
            return false;
        }
        return false;
    }

    private int getXunJianDianById(String bianma) {
        for(int i = 0 ,size = Contains.jilu.xunJianDianDatas.size();i<size ;i++){
            XunJianDianEntity entity = Contains.jilu.xunJianDianDatas.get(i);
            if(bianma.equals(entity.dianNfcBianma)){
                return i;
            }
        }

        return -1;
    }


    // 字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        return bytesToHexString(src, true);
    }

    private String bytesToHexString(byte[] src, boolean isPrefix) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isPrefix) {
            stringBuilder.append("0x");
        }
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.toUpperCase(Character.forDigit(
                    (src[i] >>> 4) & 0x0F, 16));
            buffer[1] = Character.toUpperCase(Character.forDigit(src[i] & 0x0F,
                    16));
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapService != null) {
//            unbindService(mServiceConnection);
        }
    }

    public abstract void onNfcTagChecked(XunJianDianEntity xunJiandianEntity);
    public abstract void onBackClick();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }
    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
    }
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }
    @Override
    public void takeCancel() {
        Log.e("wh", "这里  操作取消");
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

}
