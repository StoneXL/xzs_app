package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.widget.TextView;

import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.utils.PatternHelper;
import com.yxld.xzs.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yxld.xzs.activity.Navigation.AccountSafeActivity.SP_PATTERN_STATE;

/**
 * @author xlei
 * @Date 2018/4/9.
 */

public class PatternCheckActivity extends BaseActivity {

    @BindView(R.id.pattern_indicator_view)
    PatternIndicatorView patternIndicatorView;
    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockerView;
    @BindView(R.id.text_msg)
    TextView textMsg;
    private PatternHelper patternHelper;
    private int type; //1表示关闭手势密码 2 表示修改手势密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_check);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    protected void initData() {
        type = getIntent().getIntExtra("inter", 1);
        if (type == 1) {
            toolbar.setTitle("关闭手势密码");
        } else {
            toolbar.setTitle("修改手势密码");
        }
        this.patternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(PatternLockerView view) {
            }

            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {
            }

            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                boolean isError = !isPatternOk(hitList);
                view.updateStatus(isError);
                patternIndicatorView.updateState(hitList, isError);
                updateMsg();
            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });
        if (type == 2) {
            this.textMsg.setText("验证原手势密码图案");
        } else if (type == 1) {
            this.textMsg.setText("验证手势密码图案");
        }
        this.patternHelper = new PatternHelper();
    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForChecking(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        this.textMsg.setText(this.patternHelper.getMessage());
        this.textMsg.setTextColor(this.patternHelper.isOk() ?
                getResources().getColor(R.color.blue) :
                getResources().getColor(R.color.red));
    }

    private void finishIfNeeded() {
        if (this.patternHelper.isFinish()) {
            if (type == 2) {
                if (this.patternHelper.isOk()) {
                    startActivity(PatternProveActivity.class);
                } else {
                    this.textMsg.setText("验证原手势密码图案失败");
                    ToastUtil.showToast(PatternCheckActivity.this, "修改手势密码图案失败");
                }
                finish();
            } else if (type == 1) {
                if (this.patternHelper.isOk()) {
                    sp.edit().putBoolean(SP_PATTERN_STATE, false).apply();
                } else {
                    this.textMsg.setText("验证手势密码图案失败");
                    ToastUtil.showToast(PatternCheckActivity.this, "关闭手势密码失败");
                }
                finish();
            }


        }
    }
}
