package com.yxld.xzs.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yxld.xzs.R;

import com.yxld.xzs.utils.NfcPatrolUtil;
import com.yxld.xzs.utils.StringUitls;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/1.
 */

public class StartPatrolView extends AutoLinearLayout {
    private TextView tvCountDown;
    private TextView tvStartPatrol;
    private long mTaskEndTime;
    private CountDownTimer mTimer;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what ==1){
                long time = mTaskEndTime - System.currentTimeMillis();
                if(time<0){
                    tvCountDown.setText("您已超出设定时间");
                }else {
                    String diffTime = StringUitls.calculteDiffTime(time);
                    tvCountDown.setText(diffTime);
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }

            }
        }
    };
    public StartPatrolView(Context context) {
        this(context,null);
    }

    public StartPatrolView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StartPatrolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_start_patrol,this);
        tvCountDown = (TextView) view.findViewById(R.id.tv_count_down);
        tvStartPatrol = (TextView) view.findViewById(R.id.tv_start_patrol);
    }

    public void cancelCountDown(){
        if(mTimer!=null){
            mTimer.cancel();
        }
    }

    public void onInit(){
        cancelCountDown();
        tvCountDown.setText("");
        tvStartPatrol.setText("开始巡检");
        mHandler.removeMessages(1);
        tvStartPatrol.setSelected(false);
        tvStartPatrol.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setStartTime(long taskStartTime,long taskEndTime){
        mTaskEndTime = taskEndTime;
        mTimer = new CountDownTimer(taskStartTime-System.currentTimeMillis(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String diffTime = StringUitls.calculteDiffTime(millisUntilFinished);
                tvCountDown.setText(diffTime);
            }

            @Override
            public void onFinish() {
                if(NfcPatrolUtil.hasRemainTask()){
                    tvStartPatrol.setText("正在巡检");
                }else {
                    tvStartPatrol.setText("开始巡检");
                }
                tvStartPatrol.setSelected(true);
                tvStartPatrol.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mClickListener!=null){
                            mClickListener.onClick(v);
                        }
                    }
                });

                if(mClickListener!=null){
                    mClickListener.onCountDownComplete();
                }
                mHandler.sendEmptyMessage(1);

            }
        };
        mTimer.start();
    }
    public void setXunjianStatusText(String text){
        tvStartPatrol.setText(text);
    }

    private OnStartPatrolClickListener mClickListener;
    public void setOnStartPatrolClickListener(OnStartPatrolClickListener listener){
        mHandler.removeMessages(1);
        mClickListener = listener;

    }

    public interface OnStartPatrolClickListener {
        void onClick(View view);
        void onCountDownComplete();
    }
}
