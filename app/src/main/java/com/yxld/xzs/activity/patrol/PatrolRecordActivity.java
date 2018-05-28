package com.yxld.xzs.activity.patrol;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.PatrolRecordQuestionaireAdapter;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.ImageItem;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianShiJianEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.view.AlignLeftRightTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 巡更记录界面
 */
public class PatrolRecordActivity extends NfcBaseActivity {
    public static final String KEY_XUN_JIAN_DIAN_ID = "key_nfc_xun_jian_dian_id";
    public static final int RESULT_CODE_NOT_THIS_POINT = 0x101;
    public static final String KEY_IN_TYPE = "key_in_type";

    public static final int IN_TYPE_LOOK = 0x201;
    public static final int IN_TYPE_CHECK = 0x202;

    public static final String KEY_ENTITY = "key_entity";

    private static final int COUNT_REMARK_IMG = 3;
    private static final int CODE_RECCORD_PERMISSION = 0x101010;
    @BindView(R.id.tv_line_name)
    AlignLeftRightTextView tvLineName;
    @BindView(R.id.tv_serial_num)
    AlignLeftRightTextView tvSerialNum;
    @BindView(R.id.tv_check_point_charset)
    AlignLeftRightTextView tvCheckPointCharset;
    @BindView(R.id.tv_check_point_address)
    AlignLeftRightTextView tvCheckPointAddress;
    @BindView(R.id.tv_check_point_time)
    AlignLeftRightTextView tvCheckPointTime;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_save_data)
    TextView tvSaveData;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.recyclerView_Shijian)
    RecyclerView recyclerViewShijian;
    @BindView(R.id.recyclerView_remark)
    RecyclerView recyclerViewRemark;

    private PatrolRecordQuestionaireAdapter mAdapter;


    private XunJianDianEntity mDianEntity;
    private PatrolRecordShiJianAdapter mShijianAdapter;
    private PatrolRecordRemarkAdapter mRemarkAdapter;
    private int mCurrnentDianPos;

    private int mCurrentInType;
    private int mCurrentSelectImgPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_record);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scrollView.smoothScrollTo(0, 0);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerViewShijian.setNestedScrollingEnabled(false);
        recyclerViewRemark.setNestedScrollingEnabled(false);
        mCurrentSelectImgPosition = 0;

        Bundle bundle = getIntent().getExtras();
        mCurrentInType = bundle.getInt(KEY_IN_TYPE, IN_TYPE_CHECK);
        if (mCurrentInType == IN_TYPE_CHECK) {
            mCurrnentDianPos = bundle.getInt(KEY_XUN_JIAN_DIAN_ID);
            mDianEntity = Contains.jilu.xunJianDianDatas.get(mCurrnentDianPos);
            initData();
            initAdapter();
        } else {
            mDianEntity = bundle.getParcelable(KEY_ENTITY);
            initData();
            onLoadDetailSucceed();
        }
    }


    private void onLoadDetailSucceed() {
        PatrolRecordQuestionaireAdapter xiangAdapter = new PatrolRecordQuestionaireAdapter(mDianEntity.xunJianXiangDatas, true);
        recyclerView.setAdapter(xiangAdapter);

        List<XunJianDianEntity> dianEntities = new ArrayList<>();
        dianEntities.add(mDianEntity);
        PatrolRecordShiJianAdapter shiJianAdapter = new PatrolRecordShiJianAdapter(dianEntities, mDianEntity.xunJianXiangDatas.size() + 1);
        recyclerViewShijian.setAdapter(shiJianAdapter);
        shiJianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                showJustLookShiJianDialog();
            }
        });
    }

    private void showJustLookShiJianDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        View view = View.inflate(this, R.layout.layout_dialog_shijian, null);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        RecyclerView rvLeftDesc = (RecyclerView) view.findViewById(R.id.recyclerView);

        final MyDialogShiJianAdapter shiJianAdapter = new MyDialogShiJianAdapter(new ArrayList<XunJianShiJianEntity>());
        rvLeftDesc.setAdapter(shiJianAdapter);


        RecyclerView rvRightTitle = (RecyclerView) view.findViewById(R.id.recyclerView_Shijian_Title);
        final MyDialogShijianRightTitleAdapter titleAdapter = new MyDialogShijianRightTitleAdapter(mDianEntity.xunJianShijianClassifies);
        rvRightTitle.setAdapter(titleAdapter);
        titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int pos) {
                for (int i = 0; i < titleAdapter.getData().size(); i++) {
                    XunJianShijianClassifyEntity entity = titleAdapter.getData().get(i);
                    if (entity.isSelected == 1) {
                        entity.isSelected = 0;
                        titleAdapter.notifyItemChanged(i);
                    }
                }

                XunJianShijianClassifyEntity entity = titleAdapter.getData().get(pos);
                entity.isSelected = 1;
                titleAdapter.notifyItemChanged(pos);
                shiJianAdapter.setNewData(entity.list);
            }
        });


        if (mDianEntity.xunJianShijianClassifies.size() > 0) {
            mDianEntity.xunJianShijianClassifies.get(0).isSelected = 1;
            shiJianAdapter.setNewData(mDianEntity.xunJianShijianClassifies.get(0).list);
        }


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }


    //************************************巡检***************************************
    private void initAdapter() {
        mAdapter = new PatrolRecordQuestionaireAdapter(mDianEntity.xunJianXiangDatas, false);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                XunJianXiangEntity xiangEntity = mAdapter.getData().get(i);
                if (xiangEntity.xunjianxiangLeixin == 2) {
                    //数值型
                    showShuZhiXingDialog(xiangEntity, i);
                }
            }
        });

        tvSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存入数据库
                onSaveData();
            }
        });


        List<XunJianDianEntity> dianData = new ArrayList<>();
        boolean flag = false;
        if (mDianEntity.xunJianShijianClassifies.size() > 0) {
            flag = true;
            dianData.add(mDianEntity);
            mShijianAdapter = new PatrolRecordShiJianAdapter(dianData, mDianEntity.xunJianXiangDatas.size() + 1);
            recyclerViewShijian.setAdapter(mShijianAdapter);
            mShijianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    //事件
                    showShiJianDialog();
                }
            });
        }


        mRemarkAdapter = new PatrolRecordRemarkAdapter(dianData, flag ? mDianEntity.xunJianXiangDatas.size() + 2 : mDianEntity.xunJianXiangDatas.size() + 1, false);
        recyclerViewRemark.setAdapter(mRemarkAdapter);

        mRemarkAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                showRemarkDialog();
            }
        });

        mRemarkAdapter.setOnAddImgItemClickListener(new OnAddImgItemClickListener() {
            @Override
            public void onItemClickListener(BaseQuickAdapter adapter, View view, int position) {
                ImageItem item = (ImageItem) adapter.getData().get(position);
                if (item.isSelected) {
                    showDeleteDialog(position);
                } else {
                    mCurrentSelectImgPosition = position;
                    handlerAddImgClick();
                }
            }
        });
    }

    private void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this).setTitle("确定删除当前图片?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDianEntity.remarkImgsUrlTemp.remove(position);
                        mRemarkAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    private void handlerAddImgClick() {
        new AlertView("上传图片", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                PatrolRecordActivity.this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                TakePhoto takePhoto = getTakePhoto();
                //获取TakePhoto图片路径
                File file = new File(Environment.getExternalStorageDirectory(), "/xzs/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                switch (position) {
                    case 0:
                        takePhoto.onPickFromCapture(imageUri);
                        break;
                    case 1:
                        //设置最多几张
                        takePhoto.onPickMultiple(COUNT_REMARK_IMG - mCurrentSelectImgPosition);
                        break;
                }
            }
        }).show();
    }


    //设置Takephoto 使用TakePhoto自带的相册   照片旋转角度纠正
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    //设置takephoto的照片使用压缩
    private void configCompress(TakePhoto takePhoto) {
        CompressConfig config;
        config = new CompressConfig.Builder()
                .setMaxSize(102400)
                .setMaxPixel(800)
                .enableReserveRaw(true)
                .create();
        takePhoto.onEnableCompress(config, false);
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();
        for (TImage img : images) {
            String path = img.getCompressPath();
            if (!TextUtils.isEmpty(path)) {
                ImageItem item = mDianEntity.remarkImgsUrlTemp.get(mCurrentSelectImgPosition);
                item.path = path;
                item.isSelected = true;
                if (mCurrentSelectImgPosition < COUNT_REMARK_IMG - 1) {
                    mDianEntity.remarkImgsUrlTemp.add(new ImageItem());
                    mCurrentSelectImgPosition++;
                }
            }
        }
        mRemarkAdapter.notifyItemChanged(0);
    }

    //备注
    private void showRemarkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        View view = View.inflate(this, R.layout.layout_dialog_shuzhixing, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        final EditText edit = (EditText) view.findViewById(R.id.edit_market);
        tvTitle.setText("备注信息");
        edit.setHint("请输入备注信息");
        StringUitls.setInputName(edit);
        edit.setText(mDianEntity.remark);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit.getText().toString())) {
                    Toast.makeText(PatrolRecordActivity.this, "还未填写任何信息", Toast.LENGTH_SHORT).show();
                } else {
                    mDianEntity.remark = edit.getText().toString();
                    mRemarkAdapter.notifyItemChanged(0);
                    alertDialog.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }


    //巡检事件
    private void showShiJianDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        View view = View.inflate(this, R.layout.layout_dialog_shijian, null);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        final MyDialogShiJianAdapter shiJianAdapter = new MyDialogShiJianAdapter(new ArrayList<XunJianShiJianEntity>());
        recyclerView.setAdapter(shiJianAdapter);
        shiJianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                XunJianShiJianEntity entity = shiJianAdapter.getData().get(i);
                entity.isAnswer = entity.isAnswer == 1 ? 0 : 1;
                shiJianAdapter.notifyItemChanged(i);
            }
        });

        RecyclerView rvRightTitle = (RecyclerView) view.findViewById(R.id.recyclerView_Shijian_Title);
        final MyDialogShijianRightTitleAdapter titleAdapter = new MyDialogShijianRightTitleAdapter(mDianEntity.xunJianShijianClassifies);
        rvRightTitle.setAdapter(titleAdapter);
        titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int pos) {
                for (int i = 0; i < titleAdapter.getData().size(); i++) {
                    XunJianShijianClassifyEntity entity = titleAdapter.getData().get(i);
                    if (entity.isSelected == 1) {
                        entity.isSelected = 0;
                        titleAdapter.notifyItemChanged(i);
                    }
                }

                XunJianShijianClassifyEntity entity = titleAdapter.getData().get(pos);
                entity.isSelected = 1;
                titleAdapter.notifyItemChanged(pos);
                shiJianAdapter.setNewData(entity.list);
            }
        });


        if (mDianEntity.xunJianShijianClassifies.size() > 0) {
            mDianEntity.xunJianShijianClassifies.get(0).isSelected = 1;
            shiJianAdapter.setNewData(mDianEntity.xunJianShijianClassifies.get(0).list);
        }


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasShiJianChecked(mDianEntity.xunJianShijianClassifies)) {
                    mShijianAdapter.notifyItemChanged(0);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(PatrolRecordActivity.this, "未选择任何事件", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }


    private boolean hasShiJianChecked(List<XunJianShijianClassifyEntity> entities) {
        for (XunJianShijianClassifyEntity classifyEntity : entities) {
            for (XunJianShiJianEntity entity : classifyEntity.list) {
                if (entity.isAnswer == 1) {
                    return true;
                }
            }
        }
        return false;
    }


    //数值型 类型2
    private void showShuZhiXingDialog(final XunJianXiangEntity xiangEntity, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();

        View view = View.inflate(this, R.layout.layout_dialog_shuzhixing, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        final EditText edit = (EditText) view.findViewById(R.id.edit_market);
        edit.setInputType(InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_SIGNED|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        edit.setHint(xiangEntity.xunjianxiangZhengchangzhi);
        edit.setText(xiangEntity.xunjianxiangDaAn);
        tvTitle.setText(xiangEntity.xunjianxiangName);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit.getText().toString())) {
                    Toast.makeText(PatrolRecordActivity.this, "还未填写任何信息", Toast.LENGTH_SHORT).show();
                } else {
                    xiangEntity.xunjianxiangDaAn = edit.getText().toString();
                    xiangEntity.isReplied = 1;
                    mAdapter.notifyItemChanged(position);
                    alertDialog.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }


    private void initData() {

        tvCheckPointAddress.setRightText(mDianEntity.dianDizhi);
        tvCheckPointCharset.setRightText(mDianEntity.dianNfcBianma);
        tvLineName.setRightText(mDianEntity.jiluXianluName);
        tvSerialNum.setRightText(mDianEntity.xuliehao + "");
        if (StringUitl.isEmpty(mDianEntity.checkTime)) {
            KLog.i("打卡时间为空:" + mDianEntity.checkTime);
            tvCheckPointTime.setRightTextColor(getResources().getColor(R.color.color_ff5654));
            if (mDianEntity.hasChecked == 1) {
                tvCheckPointTime.setRightText("设备故障");
            } else {
                tvCheckPointTime.setRightText("未打卡");
            }

        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            tvCheckPointTime.setRightTextColor(getResources().getColor(R.color.color_666666));
            tvCheckPointTime.setRightText(format.format(new Date(Long.parseLong(mDianEntity.checkTime))));
        }

        if (mCurrentInType == IN_TYPE_LOOK) {
            tvSaveData.setVisibility(View.GONE);
        }


        if (TextUtils.isEmpty(mDianEntity.remarkImgsUrls)) {
            return;
        }
        String[] imgs = mDianEntity.remarkImgsUrls.split(",");
        for (String s : imgs) {
            ImageItem item = new ImageItem();
            item.isSelected = true;
            item.path = s;
            mDianEntity.remarkImgsUrlTemp.add(item);
        }
    }

    @Override
    public void onNfcTagChecked(XunJianDianEntity entity) {

        if (entity.dianNfcBianma.equals(mDianEntity.dianNfcBianma)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
            tvCheckPointTime.setRightTextColor(getResources().getColor(R.color.color_666666));
            tvCheckPointTime.setRightText(format.format(new Date(Long.parseLong(entity.checkTime))));
        } else {
            Intent intent = new Intent();
            intent.putExtra("nfc_position", Contains.jilu.xunJianDianDatas.indexOf(entity));
            setResult(RESULT_CODE_NOT_THIS_POINT, intent);
            onNotSaveData();
        }
    }

    @Override
    public void onBackClick() {
        if (mCurrentInType == IN_TYPE_LOOK) {
            finish();
        }else if(mDianEntity.hasSaveData ==1){
            onNotSaveData();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示:数据未保存，确定退出当前页");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onNotSaveData();
                }
            });
            builder.show();
        }
    }

    private void onSaveData() {
        if (mDianEntity.hasSaveData == 1) {
            Toast.makeText(this, "该巡检点已经保存过数据，无法对数据进行更改", Toast.LENGTH_SHORT).show();
            return;
        }

        if (hasMustEmpty()) {
            Toast.makeText(this, "还有必填的项未填,请填写后再保存", Toast.LENGTH_SHORT).show();
            return;
        }


        if (mDianEntity.hasChecked != 1) {
            showNotCheckedDialog();
            return;
        }


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                handlerImgs();
                mDianEntity.hasSaveData = 1;
                dbUtil.updateOneXunJianDian(mDianEntity);
                if (Contains.jilu.jiluWenti == -1 || Contains.jilu.jiluPaixu == 1) {
                    dbUtil.updateJustOneJiLu(Contains.jilu);
                }
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer aVoid) throws Exception {
                        Toast.makeText(PatrolRecordActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    private void handlerImgs() {
        StringBuilder sBuilder = new StringBuilder();
        List<ImageItem> list = mDianEntity.remarkImgsUrlTemp;
        for (ImageItem item : list) {
            if (item.isSelected) {
                sBuilder.append(item.path).append(",");
            }
        }
        if (sBuilder.length() > 0) {
            mDianEntity.remarkImgsUrls = sBuilder.toString().substring(0, sBuilder.toString().length() - 1);
        }
    }

    private void showNotCheckedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示:该巡检点尚未打卡,请打卡后再保存");
        builder.setNeutralButton("设备故障", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //添加描述
                tvCheckPointTime.setRightText("设备故障");
                mDianEntity.checkTime = System.currentTimeMillis() + "";
                mDianEntity.isException = -1;
                Contains.jilu.jiluWenti = -1;
                mDianEntity.hasChecked = 1;

                if (Contains.jilu.jiluPaixu == 1 && Contains.jilu.nextXunjianDian != mCurrnentDianPos) {
                    Toast.makeText(PatrolRecordActivity.this, "当前为顺序巡检，该巡检点与需要打卡的点不符，请重新选择打卡点", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Contains.jilu.jiluPaixu == 1) {
                    Contains.jilu.nextXunjianDian++;
                }

                onSaveData();
            }
        });
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    private boolean hasMustEmpty() {
        for (XunJianXiangEntity entity : mDianEntity.xunJianXiangDatas) {
            if (entity.xunjianxiangLeixin == 2 && TextUtils.isEmpty(entity.xunjianxiangDaAn)) {
                return true;
            }
        }
        return false;
    }

    private void onNotSaveData() {
        Observable.create(new ObservableOnSubscribe<XunJianDianEntity>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<XunJianDianEntity> e) throws Exception {
                XunJianDianEntity dianById = dbUtil.getXunJianDianById(Contains.jilu.jiluId + "", mDianEntity.dianId + "");
                e.onNext(dianById);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<XunJianDianEntity>() {
                    @Override
                    public void accept(@NonNull XunJianDianEntity entity) throws Exception {
                        Contains.jilu.xunJianDianDatas.remove(mCurrnentDianPos);
                        mDianEntity = entity;
                        Contains.jilu.xunJianDianDatas.add(mCurrnentDianPos, entity);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        mDianEntity.remarkImgsUrlTemp.clear();
        super.onDestroy();
    }


    //************************************************************************************************************************

    private static class MyDialogShiJianAdapter extends BaseQuickAdapter<XunJianShiJianEntity, BaseViewHolder> {

        public MyDialogShiJianAdapter(@Nullable List<XunJianShiJianEntity> data) {
            super(R.layout.item_shijian_dialog, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianShiJianEntity xunJianShiJianEntity) {
            baseViewHolder.setText(R.id.tv_desc, xunJianShiJianEntity.shijianName);
            if (xunJianShiJianEntity.isAnswer == 1) {
                baseViewHolder.getView(R.id.root_layout).setBackgroundColor(mContext.getResources().getColor(R.color.color_c7c7cc));
            } else {
                baseViewHolder.getView(R.id.root_layout).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }


    private static class MyDialogShijianRightTitleAdapter extends BaseQuickAdapter<XunJianShijianClassifyEntity, BaseViewHolder> {

        public MyDialogShijianRightTitleAdapter(@Nullable List<XunJianShijianClassifyEntity> data) {
            super(R.layout.item_shijian_dialog, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianShijianClassifyEntity entity) {
            baseViewHolder.setText(R.id.tv_desc, entity.clazz);
            if (entity.isSelected == 1) {
                baseViewHolder.getView(R.id.root_layout).setBackgroundColor(mContext.getResources().getColor(R.color.color_c7c7cc));
            } else {
                baseViewHolder.getView(R.id.root_layout).setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }


    public static class PatrolRecordShiJianAdapter extends BaseQuickAdapter<XunJianDianEntity, BaseViewHolder> {

        private int mStartIndex;

        public PatrolRecordShiJianAdapter(@Nullable List<XunJianDianEntity> data, int startIndex) {
            super(R.layout.item_patrol_record, data);
            mStartIndex = startIndex;
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianDianEntity entity) {
            //事件
            TextView star = baseViewHolder.getView(R.id.tv_star);
            star.setVisibility(View.INVISIBLE);

            baseViewHolder.setVisible(R.id.tv_iv_container, true)
                    .setVisible(R.id.switch_view, false)
                    .setText(R.id.tv_title, baseViewHolder.getLayoutPosition() + mStartIndex + "、巡检事件");
            StringBuilder sBuilder = new StringBuilder();
            for (XunJianShijianClassifyEntity classifyEntity : entity.xunJianShijianClassifies) {
                for (XunJianShiJianEntity shiJianEntity : classifyEntity.list) {
                    if (shiJianEntity.isAnswer == 1) {
                        sBuilder.append(shiJianEntity.shijianName).append(",");
                    }
                }
            }
            if (sBuilder.toString().length() > 0) {
                baseViewHolder.setText(R.id.tv_desc, sBuilder.toString().substring(0, sBuilder.toString().length() - 1));
            } else {
                baseViewHolder.setText(R.id.tv_desc, "无");
            }
        }
    }


    public static class PatrolRecordRemarkAdapter extends BaseQuickAdapter<XunJianDianEntity, BaseViewHolder> {

        private int mStartIndex;
        private boolean mIsJustLook;
        private Activity mActivity;

        public PatrolRecordRemarkAdapter(@Nullable List<XunJianDianEntity> data, int startIndex, boolean isJustLook) {
            super(R.layout.item_patrol_record, data);
            mStartIndex = startIndex;
            mIsJustLook = isJustLook;
        }

        public PatrolRecordRemarkAdapter(@Nullable List<XunJianDianEntity> data, int startIndex, boolean isJustLook, Activity activity) {
            super(R.layout.item_patrol_record, data);
            mStartIndex = startIndex;
            mIsJustLook = isJustLook;
            mActivity = activity;
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final XunJianDianEntity entity) {
            //备注
            TextView star = baseViewHolder.getView(R.id.tv_star);
            star.setVisibility(View.INVISIBLE);

            baseViewHolder.setVisible(R.id.tv_iv_container, true)
                    .setVisible(R.id.switch_view, false)
                    .setText(R.id.tv_title, baseViewHolder.getLayoutPosition() + mStartIndex + "、备注")
                    .addOnClickListener(R.id.root_layout);
            if (entity.remark == null || TextUtils.isEmpty(entity.remark)) {
                baseViewHolder.setText(R.id.tv_desc, "未填写").setVisible(R.id.tv_remark_desc, false);
            } else {
                baseViewHolder.setText(R.id.tv_desc, "").setText(R.id.tv_remark_desc, entity.remark).setVisible(R.id.tv_remark_desc, true);
            }


//            //添加图片占位
//            if (!mIsJustLook && entity.remarkImgsUrlTemp.size() < COUNT_REMARK_IMG) {
//                if (entity.remarkImgsUrlTemp.size() == 0) {
//                    entity.remarkImgsUrlTemp.add(new ImageItem());
//                } else {
//                    ImageItem item = entity.remarkImgsUrlTemp.get(entity.remarkImgsUrlTemp.size() - 1);
//                    if (item.isSelected) {
//                        entity.remarkImgsUrlTemp.add(new ImageItem());
//                    }
//                }
//            }
//
//            if (entity.remarkImgsUrlTemp.size() == 0) {
//                baseViewHolder.setVisible(R.id.recyclerView, false);
//            } else {
//                baseViewHolder.setVisible(R.id.recyclerView, true);
//                AutoLinearLayout rootLayout = baseViewHolder.getView(R.id.remark_root);
//                rootLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.layout_line_sides));
//                RecyclerView rv = baseViewHolder.getView(R.id.recyclerView);
//                rv.setLayoutManager(new GridLayoutManager(mContext, 3));
//                AddImgAdapter imgAdapter = new AddImgAdapter(entity.remarkImgsUrlTemp);
//                rv.setAdapter(imgAdapter);
//                imgAdapter.setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                        if (mListener != null) {
//                            mListener.onItemClickListener(baseQuickAdapter, view, i);
//                        }
//                    }
//                });
//            }
//
//
//            final AudioRecordButton recordButton = baseViewHolder.setVisible(R.id.tv_hint, false).getView(R.id.treat_audio);
//            final AutoLinearLayout recordShowLayoutRoot = baseViewHolder.getView(R.id.treat_record);
//            final TextView tvRecordHint = baseViewHolder.getView(R.id.tv_hint);
//
//            //录音
//            if (TextUtils.isEmpty(entity.remarkRecoderUrl)) {
//                if (mIsJustLook || entity.hasSaveData == 1) {
//                    return;
//                }
//                //录音
//                baseViewHolder.setVisible(R.id.root_layout_record, true);
//                AndPermission.with(mActivity)
//                        .requestCode(CODE_RECCORD_PERMISSION)
//                        .permission(
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                Manifest.permission.CAMERA
//                        )
//                        .rationale(new RationaleListener() {
//                            @Override
//                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
//                                AndPermission.rationaleDialog(mActivity, rationale).show();
//                            }
//                        })
//                        .callback(new PermissionListener() {
//                            @Override
//                            public void onSucceed(int requestCode, @android.support.annotation.NonNull List<String> grantPermissions) {
//                                recordButton.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
//                                    @Override
//                                    public void onFinished(float seconds, String filePath) {
//                                        if (entity.hasSaveData == 1) {
//                                            File file = new File(filePath);
//                                            if (file.exists()) {
//                                                file.delete();
//                                            }
//                                            Toast.makeText(mContext, "该巡检点已经保存过记录了,无法保存录音文件", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//                                        entity.remarkRecoderUrl = filePath;
//                                        recordShowLayoutRoot.setVisibility(View.VISIBLE);
//                                        tvRecordHint.setVisibility(View.GONE);
//                                        notifyDataSetChanged();
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onFailed(int requestCode, @android.support.annotation.NonNull List<String> deniedPermissions) {
//                                Toast.makeText(mContext, "录音权限获取失败，请检查欣助手的录音权限是否被禁", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .start();
//            } else {
//                //展示录音
//                recordShowLayoutRoot.setVisibility(View.VISIBLE);
//                tvRecordHint.setVisibility(View.GONE);
//                recordShowLayoutRoot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MediaManager.release();
//                        MediaManager.playSound(entity.remarkRecoderUrl);
//                    }
//                });
//
//                if (mIsJustLook || entity.hasSaveData == 1) {
//                    recordButton.setCanRecord(false);
//                } else {
//                    recordButton.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
//                        @Override
//                        public void onFinished(float seconds, String filePath) {
//                            if (entity.hasSaveData == 1) {
//                                File file = new File(filePath);
//                                if (file.exists()) {
//                                    file.delete();
//                                }
//                                Toast.makeText(mContext, "该巡检点已经保存过记录了,无法保存录音文件", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            entity.remarkImgsUrls = filePath;
//                        }
//                    });
//                }
//            }
        }


        private OnAddImgItemClickListener mListener;

        public void setOnAddImgItemClickListener(OnAddImgItemClickListener listener) {
            mListener = listener;
        }
    }


    public static class AddImgAdapter extends BaseQuickAdapter<ImageItem, BaseViewHolder> {

        public AddImgAdapter(@Nullable List<ImageItem> data) {
            super(R.layout.item_add_img, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ImageItem item) {
            if (item.isSelected) {
                ImageView iv = baseViewHolder.getView(R.id.iv_content);
                Glide.with(mContext)
                        .load(item.path)
                        .into(iv);
            }
        }
    }

    public interface OnAddImgItemClickListener {
        void onItemClickListener(BaseQuickAdapter adapter, View view, int position);
    }
}
