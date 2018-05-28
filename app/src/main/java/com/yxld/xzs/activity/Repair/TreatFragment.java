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

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.orhanobut.logger.Logger;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.UrlSafeBase64;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.PhotoAdapter;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppRepair;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;
import com.yxld.xzs.transformer.DefaultTransformer1;
import com.yxld.xzs.utils.AudioService;
import com.yxld.xzs.utils.MediaManager;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.util.Bimp;
import com.yxld.xzs.utils.util.FileUtils;
import com.yxld.xzs.utils.util.ImageItem;
import com.yxld.xzs.view.AudioRecordButton;
import com.yxld.xzs.view.AudioViewPage;
import com.yxld.xzs.view.ImageShowView;
import com.yxld.xzs.view.ProgressDialog;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yishangfei on 2017/3/23 0023.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * <p>
 * 维修详情
 */
public class TreatFragment extends TakePhotoFragment {
    @BindView(R.id.treat_name)
    TextView treatName;
    @BindView(R.id.treat_time)
    TextView treatTime;
    @BindView(R.id.treat_tel)
    TextView treatTel;
    @BindView(R.id.treat_image1)
    ImageView treatImage1;
    @BindView(R.id.treat_image2)
    ImageView treatImage2;
    @BindView(R.id.treat_image3)
    ImageView treatImage3;
    @BindView(R.id.treat_image4)
    ImageView treatImage4;
    @BindView(R.id.treat_image5)
    ImageView treatImage5;
    @BindView(R.id.treat_image6)
    ImageView treatImage6;
    @BindView(R.id.treat_recycler1)
    RecyclerView treatRecycler1;
    @BindView(R.id.treat_recycler2)
    RecyclerView treatRecycler2;
    @BindView(R.id.treat_audio)
    AudioRecordButton treatAudio;
    @BindView(R.id.treat_details)
    EditText treatDetails;
    @BindView(R.id.treat_button)
    Button treatButton;
    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.treat_voice)
    AutoLinearLayout treatVoice;
    @BindView(R.id.treat_record)
    AutoLinearLayout treatRecord;

    private int mark, ImgIndex;
    private int click = 0;
    private String before_key = "";
    private String back_key = "";
    private String file = "";
    private String details = "";
    private AppRepair appRepair;
    private String image1[], image2[];
    private PhotoAdapter photoAdapter;
    private PhotoAdapter photoAdapter1;
    private UploadManager uploadManager;
    private volatile boolean isCancelled = false;
//    private List<TImage> selectMedia = new ArrayList<>();//维修前图片的集合
//    private List<TImage> selectMedia1 = new ArrayList<>();//维修后图片的集合

    private ArrayList<ImageItem> tempTousuSelectBitmap = new ArrayList<ImageItem>();//维修前图片的集合
    private ArrayList<ImageItem> tempTousuSelectBitmap1 = new ArrayList<ImageItem>();//维修后图片的集合
    /**
     * 进度条加载
     */
    protected ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treat, null);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this, view);
        Intent intent = getActivity().getIntent();
        appRepair = intent.getParcelableExtra("Parcelable");
        initView();
        initRadio();
        progressDialog = new ProgressDialog(getActivity());
        return view;
    }

    private void initView() {
        //主管进来 进行审批状态等于5
        if (appRepair.getBaoxiu_status().equals("5")) {
            treatImage1.setVisibility(View.VISIBLE);
            treatImage2.setVisibility(View.VISIBLE);
            treatImage3.setVisibility(View.VISIBLE);
            treatImage4.setVisibility(View.VISIBLE);
            treatImage5.setVisibility(View.VISIBLE);
            treatImage6.setVisibility(View.VISIBLE);
            treatVoice.setVisibility(View.INVISIBLE);
            treatRecycler1.setVisibility(View.GONE);
            treatRecycler2.setVisibility(View.GONE);
            treatAudio.setText("文字留言");
            treatAudio.setEnabled(false);
            treatDetails.setHint("请输入您的文字留言");
            Drawable weather1 = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_voice_write1);
            weather1.setBounds(0, 0, weather1.getMinimumWidth(), weather1.getMinimumWidth());
            treatAudio.setCompoundDrawables(null, weather1, null, null);
            treatButton.setText("验收完成");
            final String images[] = appRepair.getBaoxiu_chayanimg().split(";");
            if (images.length == 2) {
                image1 = images[0].split(",");
                image2 = images[1].split(",");
                for (int i = 0; i < image1.length; i++) {
                    switch (i) {
                        case 0:
                            Glide.with(this)
                                    .load("http://img0.hnchxwl.com/" + image1[0])
                                    .crossFade()
                                    .into(treatImage1);
                            break;
                        case 1:
                            Glide.with(this)
                                    .load("http://img0.hnchxwl.com/" + image1[1])
                                    .crossFade()
                                    .into(treatImage2);
                            break;
                        case 2:
                            Glide.with(this)
                                    .load("http://img0.hnchxwl.com/" + image1[2])
                                    .crossFade()
                                    .into(treatImage3);
                            break;
                        default:
                            break;
                    }
                }
                for (int j = 0; j < image2.length; j++) {
                    switch (j) {
                        case 0:
                            Glide.with(this)
                                    .load("http://img0.hnchxwl.com/" + image2[0])
                                    .crossFade()
                                    .into(treatImage4);
                            break;
                        case 1:
                            Glide.with(this)
                                    .load("http://img0.hnchxwl.com/" + image2[1])
                                    .crossFade()
                                    .into(treatImage5);
                            break;
                        case 2:
                            Glide.with(this)
                                    .load("http://img0.hnchxwl.com/" + image2[2])
                                    .crossFade()
                                    .into(treatImage6);
                            break;
                        default:
                            break;
                    }
                }
            }
            if (appRepair.getBaoxiu_fuzerenyijian() == null || appRepair.getBaoxiu_fuzerenyijian().equals("")) {
                treatDetails.setText("");
            } else {
                String yijian = appRepair.getBaoxiu_fuzerenyijian().replace("null", "");
                if (yijian.startsWith("upload")) {
                    treatDetails.setVisibility(View.GONE);
                    treatRecord.setVisibility(View.VISIBLE);
                    treatAudio.setText("语音留言");
                    Drawable weather = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_voice1);
                    weather.setBounds(0, 0, weather.getMinimumWidth(), weather.getMinimumWidth());
                    treatAudio.setCompoundDrawables(null, weather, null, null);
                } else {
                    treatDetails.setText(yijian);
                    treatDetails.setEnabled(false);
                }
            }
        } else {
            //维修员进来
            treatImage1.setVisibility(View.GONE);
            treatImage2.setVisibility(View.GONE);
            treatImage3.setVisibility(View.GONE);
            treatImage4.setVisibility(View.GONE);
            treatImage5.setVisibility(View.GONE);
            treatImage6.setVisibility(View.GONE);
            treatRecycler1.setVisibility(View.VISIBLE);
            treatRecycler2.setVisibility(View.VISIBLE);
            //////////////////////////////////////////////////
            //// TODO: 2017/11/24 隐藏语音留言 只显示文字留言
            radioButton2.setChecked(true);
            radioButton.setVisibility(View.GONE);
            treatAudio.setText("文字留言");
            treatAudio.setEnabled(false);
            treatDetails.setText("");
            treatDetails.setEnabled(true);
            treatDetails.setHint("请输入您的文字留言");
            Drawable weather1 = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_voice_write1);
            weather1.setBounds(0, 0, weather1.getMinimumWidth(), weather1.getMinimumWidth());
            treatAudio.setCompoundDrawables(null, weather1, null, null);
            ///////////////////////////////////////
//            radioButton.setChecked(true);
//            treatDetails.setEnabled(false);
            treatVoice.setVisibility(View.VISIBLE);
            //维修前上传图片的适配器
            treatRecycler1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            photoAdapter = new PhotoAdapter(getActivity(), onAddPicClickListener);
            photoAdapter.setSelectMax(3);
            treatRecycler1.setAdapter(photoAdapter);
            //维修后上传图片的适配器
            treatRecycler2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            photoAdapter1 = new PhotoAdapter(getActivity(), onAddPicClickListener1);
            photoAdapter1.setSelectMax(3);
            treatRecycler2.setAdapter(photoAdapter1);
            treatButton.setText("提交审核");
        }

        treatName.setText(appRepair.getBaoxiu_zx_weixiuren());
        treatTime.setText(appRepair.getBaoxiu_paidantime());
        //        treatTel.setText("");
        treatTel.setText(appRepair.getBaoxiu_zx_weixiurenphone());
    }

    //c操作的图片的位置
    private int opreratePosition1 = -1;
    private int opreratePosition2 = -1;

    //维修前加号的点击事件
    private PhotoAdapter.onAddPicClickListener onAddPicClickListener = new PhotoAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            opreratePosition1 = position;
            Log.e("wh", "1 " + opreratePosition1);
            mark = 0;
            switch (type) {
                case 0:
                    new AlertView("上传图片", null, "取消", null,
                            new String[]{"拍照", "从相册中选择"},
                            getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
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
                                    //takePhoto.onPickFromCapture(imageUri);
                                    fun1();
                                    break;
                                case 1:
                                    //设置最多几张
//                                    takePhoto.onPickMultiple(3);
                                    fun2();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }).show();
                    break;
                case 1:
                    // 删除图片
//                    selectMedia.remove(position);
                    tempTousuSelectBitmap.remove(position);
                    photoAdapter.notifyItemRemoved(position);
                    break;
                default:
                    break;
            }
        }
    };


    public static final int PHOTOZOOM = 102; // 相册/拍照
    public static final int PHOTOTAKE = 101; // 相册/拍照
    public static final int IMAGE_COMPLETE = 103; // 结果

    private void fun1() {
        //        opreratePosition = position;
        AndPermission.with(this.getActivity())
                .requestCode(PHOTOTAKE)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission
                                .rationaleDialog(getActivity(), rationale)
                                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ToastUtil.show(getActivity(), "权限申请失败,app部分功能将无法使用!!!");
                                    }
                                })
                                .show();
                    }
                })
                .callback(permissionListener)
                .start();
    }

    private void fun2() {
//        opreratePosition = position;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTOZOOM);
    }

    File whmFile;
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == PHOTOTAKE) {
                // TODO ...
                KLog.i("成功的回调");
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                whmFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/test/" + System.currentTimeMillis() + ".jpg");
                whmFile.getParentFile().mkdirs();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //加载Uri型的文件路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(whmFile));
                //加载Uri型的文件路径
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", whmFile));
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(whmFile));
                }
                startActivityForResult(intent, PHOTOTAKE);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == PHOTOTAKE) {
                // TODO ...
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTOTAKE://拍照
                if (resultCode == RESULT_OK) {
                    //                    startPhotoZoom(Uri.fromFile(mFile));
                    //系统判断
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", whmFile);
                        //裁剪照片
                        startPhotoZoom(uri);
                    } else {
                        startPhotoZoom(Uri.fromFile(whmFile));
                    }
                }
                break;
            case PHOTOZOOM:
                if (data == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case IMAGE_COMPLETE:
                if (Bimp.tempTousuSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bitmap = decodeUriAsBitmap(imageUri);
                    String path = FileUtils.saveBitmap(bitmap, fileName);
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bitmap);
                    if (path != null && !"".equals(path)) {
                        takePhoto.setImagePath(path);
                        takePhoto.setSelected(true);
                        //                        Bimp.tempTousuSelectBitmap.add(takePhoto);
                        Log.e("wh", "2 " + opreratePosition1 + " 再来size " + tempTousuSelectBitmap.size());
                        if (mark == 0) {
                            tempTousuSelectBitmap.add(takePhoto);
                            //                        tempTousuSelectBitmap.set(opreratePosition1, takePhoto);
                            photoAdapter.setList(tempTousuSelectBitmap);
                            photoAdapter.notifyDataSetChanged();
                        } else {
                            tempTousuSelectBitmap1.add(takePhoto);
                            //                        tempTousuSelectBitmap.set(opreratePosition1, takePhoto);
                            photoAdapter1.setList(tempTousuSelectBitmap1);
                            photoAdapter1.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "为空", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    //以bitmap返回格式解析uri
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);  //imageurl 文件输出的位置
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, IMAGE_COMPLETE);
    }

    private PhotoAdapter.onAddPicClickListener onAddPicClickListener1 = new PhotoAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            opreratePosition2 = position;
            mark = 1;
            switch (type) {
                case 0:
                    new AlertView("上传图片", null, "取消", null,
                            new String[]{"拍照", "从相册中选择"},
                            getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
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
//                                    takePhoto.onPickFromCapture(imageUri);
                                    fun1();
                                    break;
                                case 1:
                                    //设置最多几张
//                                    takePhoto.onPickMultiple(3);
                                    fun2();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }).show();
                    break;
                case 1:
                    // 删除图片
//                    selectMedia1.remove(position);
                    tempTousuSelectBitmap1.remove(position);
                    photoAdapter1.notifyItemRemoved(position);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void takeCancel() {
        super.takeCancel();
        Log.e("wh", "返回结果取消");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.e("wh", "返回结果失败");
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
        Log.e("wh", "返回结果成功");
    }

    //图片成功后返回执行的方法
    private void showImg(ArrayList<TImage> images) {
        Log.e("wh", "返回结果" + images.size());
        KLog.i("图片上传成功....................");
        ImageItem imageItem;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getCompressPath() != null) {
                imageItem = new ImageItem();
                imageItem.setImagePath(images.get(i).getCompressPath());
                imageItem.setThumbnailPath(images.get(i).getCompressPath());
                // TODO: 2017/11/9
                images.get(i).getOriginalPath();
                /*if (mark == 0) {
                    KLog.i("维修前图,添加集合");
                    selectMedia.add(images.get(i).getCompressPath());
                } else {
                    selectMedia1.add(images.get(i));
                }*/
                if (mark == 0) {
                    KLog.i("维修前图,添加集合");
                    tempTousuSelectBitmap.add(imageItem);
                } else {
                    tempTousuSelectBitmap1.add(imageItem);
                }
            }
        }
        if (null != tempTousuSelectBitmap) {
            if (mark == 0) {
                KLog.i("维修前图,adapter设置适配器");
                photoAdapter.setList(tempTousuSelectBitmap);
                photoAdapter.notifyDataSetChanged();
            } else {
                photoAdapter1.setList(tempTousuSelectBitmap1);
                photoAdapter1.notifyDataSetChanged();
            }
        }
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

    private void repair() {
        if (before_key.equals("") || back_key.equals("")) {
            Toast.makeText(getActivity(), "请上传好维修前或维修后的图片", Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        } else {
            String key = before_key.substring(0,before_key.length()-1) + ";" + back_key.substring(0,back_key.length()-1);
            Logger.d(key);
            Logger.d(details);
            ServiceFactory.httpService()
                    .maintain(appRepair.getBaoxiu_id(), details, key)
                    .compose(new DefaultTransformer<String>())
                    .subscribe(new RxSubscriber<String>(getActivity()) {
                        @Override
                        public void onNext(String s) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.hide();
                            }
                            Toast.makeText(getActivity(), "维修成功", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            System.gc();
                        }
                    });
        }
    }

    private void initRadio() {
        treatAudio.setCanRecord(false);
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions
                .requestEach(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            final AudioViewPage vPager = (AudioViewPage) getActivity().findViewById(R.id.viewPager);
                            vPager.setNoScroll(false);
                            treatAudio.setCanRecord(true);
                            treatAudio.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
                                @Override
                                public void onFinished(float seconds, String filePath) {
                                    if (click == 1) {
                                        Logger.d(click + "666666");
                                        Toast.makeText(getActivity(), "重新录音,会覆盖之前的语音记录。", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    click = 1;
                                    file = filePath;
                                    treatRecord.setVisibility(View.VISIBLE);
                                    treatDetails.setVisibility(View.GONE);
                                    vPager.setNoScroll(true);
                                }
                            });
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            Toast.makeText(getActivity(),
                                    "没有访问也没有拒绝",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            treatAudio.setCanRecord(false);
                            Toast.makeText(getActivity(),
                                    "没有权限,您不能录音,请进入设置打开权限。",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick({R.id.treat_button, R.id.treat_record, R.id.radioButton, R.id.radioButton2, R.id.treat_image1, R.id.treat_image2, R.id.treat_image3, R.id.treat_image4, R.id.treat_image5, R.id.treat_image6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.treat_record:
                if (appRepair.getBaoxiu_status().equals("5")) {
                    String yijian = appRepair.getBaoxiu_fuzerenyijian().replace("null", "");
                    Intent intent = new Intent(getActivity(), AudioService.class);
                    intent.putExtra("word", yijian);
                    getActivity().startService(intent);
                } else {
                    if (file.equals("")) {
                        Toast.makeText(getActivity(), "录音出现问题,请重新录音", Toast.LENGTH_SHORT).show();
                    } else {
                        //播放前重置。
                        MediaManager.release();
                        MediaManager.playSound(file);
                    }
                }
                break;
            case R.id.radioButton:
                radioButton.setChecked(true);
                radioButton2.setChecked(false);
                treatAudio.setEnabled(true);
                treatAudio.setText("语音留言");
                treatDetails.setText("");
                treatDetails.setEnabled(false);
                treatDetails.setHint("请您按住左边话筒录音");
                Drawable weather = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_voice1);
                weather.setBounds(0, 0, weather.getMinimumWidth(), weather.getMinimumWidth());
                treatAudio.setCompoundDrawables(null, weather, null, null);
                break;
            case R.id.radioButton2:
                treatRecord.setVisibility(View.GONE);
                treatDetails.setVisibility(View.VISIBLE);
                radioButton.setChecked(false);
                radioButton2.setChecked(true);
                treatAudio.setText("文字留言");
                treatAudio.setEnabled(false);
                treatDetails.setText("");
                treatDetails.setEnabled(true);
                treatDetails.setHint("请输入您的文字留言");
                Drawable weather1 = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_voice_write1);
                weather1.setBounds(0, 0, weather1.getMinimumWidth(), weather1.getMinimumWidth());
                treatAudio.setCompoundDrawables(null, weather1, null, null);
                break;
            case R.id.treat_button:
                progressDialog.show();
                progressDialog.setCancelable(false);
                if (treatButton.getText().equals("验收完成")) {
                    ServiceFactory.httpService()
                            .check(Contains.appLogin.getAdminId(), treatDetails.getText().toString(), appRepair.getBaoxiu_chayanimg(), appRepair.getBaoxiu_id())
                            .compose(new DefaultTransformer<String>())
                            .subscribe(new RxSubscriber<String>(getActivity()) {
                                @Override
                                public void onNext(String s) {
                                    Toast.makeText(getActivity(), "验收完成", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                    System.gc();
                                }
                            });
                } else {
                    if (tempTousuSelectBitmap.size()==0||tempTousuSelectBitmap1.size()==0){
                        ToastUtil.showInfo(getActivity(),"请添加维修前后图片");
                        progressDialog.hide();
                        return;
                    }
                    //维修完成请求接口
                    ServiceFactory.httpService()
                            .token()
                            .compose(new DefaultTransformer1<String>())
                            .subscribe(new RxSubscriber<String>(getActivity()) {
                                @Override
                                public void onNext(final String s) {
                                    if (radioButton.isChecked()) {
                                        //设定需要添加的自定义变量为Map<String, String>类型 并且放到UploadOptions第一个参数里面
                                        //上传策略中使用了自定义变量
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        map.put("Android", "报修");
                                        isCancelled = false;
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                                        String date = sdf.format(new Date());
                                        final String k = "upload/baoxiu/img/" + date + "/" + "android_" + System.currentTimeMillis() + "";
                                        //put第二个参数设置文件名
                                        uploadManager.put(file, k, s,
                                                new UpCompletionHandler() {
                                                    public void complete(String key,
                                                                         ResponseInfo info, JSONObject res) {
                                                        Logger.d(key + ",\r\n " + info
                                                                + ",\r\n " + res);
                                                        if (info.isOK() == true) {
                                                            Logger.d(res.toString());
                                                            details = k;
                                                            Logger.d(details + "============");
                                                        }
                                                    }
                                                }, new UploadOptions(map, null, false,
                                                        new UpProgressHandler() {
                                                            public void progress(String key, double percent) {
                                                                Log.i("qiniu", key + ": " + percent);
                                                            }

                                                        }, new UpCancellationSignal() {
                                                    @Override
                                                    public boolean isCancelled() {
                                                        return isCancelled;
                                                    }
                                                }));
                                    } else {
                                        details = treatDetails.getText().toString();
                                    }

                                    ImgIndex = 0;
                                    for (int i = 0; i < tempTousuSelectBitmap.size(); i++) {
                                        //设定需要添加的自定义变量为Map<String, String>类型 并且放到UploadOptions第一个参数里面
                                        //上传策略中使用了自定义变量
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        map.put("Android", "报修");
                                        isCancelled = false;
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                                        String date = sdf.format(new Date());
                                        Log.d("...", "文件位置：" + tempTousuSelectBitmap.get(i).getImagePath());
                                        final String k = "upload/baoxiu/img/" + date + "/" + "android_" + System.currentTimeMillis() + "";
                                        //put第二个参数设置文件名
                                        uploadManager.put(tempTousuSelectBitmap.get(i).getImagePath(), k, s,
                                                new UpCompletionHandler() {
                                                    public void complete(String key,
                                                                         ResponseInfo info, JSONObject res) {
                                                        Logger.d(key + ",\r\n " + info
                                                                + ",\r\n " + res);
                                                        ImgIndex++;
                                                        if (info.isOK() == true) {
                                                            Logger.d(res.toString());
                                                            before_key += k + ",";
                                                            Logger.d(ImgIndex + "===============================");
                                                            if (ImgIndex == tempTousuSelectBitmap.size()) {
                                                                ImgIndex = 0;
                                                                if (tempTousuSelectBitmap1.size() == 0) {
                                                                    repair();
                                                                } else {
                                                                    for (int j = 0; j < tempTousuSelectBitmap1.size(); j++) {
                                                                        //设定需要添加的自定义变量为Map<String, String>类型 并且放到UploadOptions第一个参数里面
                                                                        //上传策略中使用了自定义变量
                                                                        HashMap<String, String> map = new HashMap<String, String>();
                                                                        map.put("Android", "报修");
                                                                        isCancelled = false;
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                                                                        String date = sdf.format(new Date());
                                                                        Log.d("...", "文件位置：" + tempTousuSelectBitmap1.get(j).getImagePath());
                                                                        final String k = "upload/baoxiu/img/" + date + "/" + "android_" + System.currentTimeMillis() + "";
                                                                        //put第二个参数设置文件名
                                                                        uploadManager.put(tempTousuSelectBitmap1.get(j).getImagePath(), k, s,
                                                                                new UpCompletionHandler() {
                                                                                    public void complete(String key,
                                                                                                         ResponseInfo info, JSONObject res) {
                                                                                        Logger.d(key + ",\r\n " + info
                                                                                                + ",\r\n " + res);
                                                                                        ImgIndex++;
                                                                                        if (info.isOK() == true) {
                                                                                            Logger.d(res.toString());
                                                                                            back_key += k + ",";
                                                                                            if (ImgIndex == tempTousuSelectBitmap1.size()) {
                                                                                                repair();
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }, new UploadOptions(map, null, false,
                                                                                        new UpProgressHandler() {
                                                                                            public void progress(String key, double percent) {
                                                                                                Log.i("qiniu", key + ": " + percent);
                                                                                            }

                                                                                        }, new UpCancellationSignal() {
                                                                                    @Override
                                                                                    public boolean isCancelled() {
                                                                                        return isCancelled;
                                                                                    }
                                                                                }));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }, new UploadOptions(map, null, false,
                                                        new UpProgressHandler() {
                                                            public void progress(String key, double percent) {
                                                                Log.i("qiniu", key + ": " + percent);
                                                            }

                                                        }, new UpCancellationSignal() {
                                                    @Override
                                                    public boolean isCancelled() {
                                                        return isCancelled;
                                                    }
                                                }));
                                    }

                                }
                            });
                }
                break;
            case R.id.treat_image1:
                if (image1==null||image1.length<1||StringUitl.isEmpty(image1[0])) {
                    return;
                }
                ImageShowView.startImageActivity(getActivity(), treatImage1, "http://img0.hnchxwl.com/" + image1[0]);
                break;
            case R.id.treat_image2:
                if (image1==null||image1.length<2||StringUitl.isEmpty(image1[1])) {
                    return;
                }
                ImageShowView.startImageActivity(getActivity(), treatImage2, "http://img0.hnchxwl.com/" + image1[1]);
                break;
            case R.id.treat_image3:
                if (image1==null||image1.length<3||StringUitl.isEmpty(image1[2])) {
                    return;
                }
                ImageShowView.startImageActivity(getActivity(), treatImage3, "http://img0.hnchxwl.com/" + image1[2]);
                break;
            case R.id.treat_image4:
                if (image2==null||image2.length<1||StringUitl.isEmpty(image2[0])) {
                    return;
                }
                ImageShowView.startImageActivity(getActivity(), treatImage4, "http://img0.hnchxwl.com/" + image2[0]);
                break;
            case R.id.treat_image5:
                if (image2==null||image2.length<2||StringUitl.isEmpty(image2[1])) {
                    return;
                }
                ImageShowView.startImageActivity(getActivity(), treatImage5, "http://img0.hnchxwl.com/" + image2[1]);
                break;
            case R.id.treat_image6:
                if (image2==null||image2.length<3||StringUitl.isEmpty(image2[2])) {
                    return;
                }
                ImageShowView.startImageActivity(getActivity(), treatImage6, "http://img0.hnchxwl.com/" + image2[2]);
                break;
            default:
                break;
        }

    }

    //七牛上传配置
    public TreatFragment() {
        //断点上传
        String dirPath = "/storage/emulated/0/xzs";
        Recorder recorder = null;
        try {
            File f = File.createTempFile("qiniu_xxxx", ".tmp");
            Log.d("qiniu", f.getAbsolutePath().toString());
            dirPath = f.getParent();
            //设置记录断点的文件的路径
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String dirPath1 = dirPath;
        //默认使用 key 的url_safe_base64编码字符串作为断点记录文件的文件名。
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                String path = key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                Log.d("qiniu", path);
                File f = new File(dirPath1, UrlSafeBase64.encodeToString(path));
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(f));
                    String tempString = null;
                    int line = 1;
                    try {
                        while ((tempString = reader.readLine()) != null) {
                            //                          System.out.println("line " + line + ": " + tempString);
                            Log.d("qiniu", "line " + line + ": " + tempString);
                            line++;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return path;
            }
        };

        Configuration config = new Configuration.Builder()
                // recorder 分片上传时，已上传片记录器
                // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .recorder(recorder, keyGen)
                .zone(Zone.zone2) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 实例化一个上传的实例
        uploadManager = new UploadManager(config);
    }
}
