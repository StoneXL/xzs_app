package com.yxld.xzs.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yxld.xzs.R;


/**
 * 盘点异常数量修改弹框
 * Created by William on 2018/5/28.
 */

public class YiChangDialog extends BaseDialog {
    private Context mContext;
    private Button mBtDialogCancel;
    private Button mBtDialogConfirm;
    private OnConfirmListener onConfirmListener;
    private EditText editText;
    private TextView textView;

    private String num;

    public YiChangDialog(Context context, String num) {
        super(context);
        mContext = context;
        this.num = num;
    }

    @Override
    public void initView() {
        setContentView(R.layout.yichang_dialog);
        mBtDialogConfirm = (Button) findViewById(R.id.bt_confirm);
        mBtDialogCancel = (Button) findViewById(R.id.bt_cancel);
        editText = (EditText) findViewById(R.id.et_num);
        textView = (TextView) findViewById(R.id.tv_num);
    }

    @Override
    public void initListener() {
        mBtDialogConfirm.setOnClickListener(this);
        mBtDialogCancel.setOnClickListener(this);
    }

    @Override
    public void initData() {
        textView.setText(num);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            //如果取消按钮按下时，侦听存在，那么调用侦听的onCancel
            case R.id.bt_cancel:
                if(onConfirmListener != null){
                    onConfirmListener.onCancel();
                }
                break;
            case R.id.bt_confirm:
                if(onConfirmListener != null&& !TextUtils.isEmpty(editText.getText().toString().trim())){
                    onConfirmListener.onConfirm(editText.getText().toString().trim());
                }
                break;
            default:
                break;

        }
        //对话框消失
        dismiss();

    }

    public void setConfirmListener(OnConfirmListener confirmListener) {
        this.onConfirmListener = confirmListener;
    }

    public interface OnConfirmListener {
        void onCancel();

        void onConfirm(String num);
    }
}
