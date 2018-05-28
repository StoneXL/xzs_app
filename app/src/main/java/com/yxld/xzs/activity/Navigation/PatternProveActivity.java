package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.widget.TextView;

import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.utils.PatternHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yxld.xzs.activity.Navigation.AccountSafeActivity.SP_PATTERN_STATE;

/**
 * @author xlei
 * @Date 2018/4/9.
 */
public class PatternProveActivity extends BaseActivity {
    @BindView(R.id.pattern_indicator_view)
    PatternIndicatorView patternIndicatorView;
    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockerView;
    @BindView(R.id.text_msg)
    TextView textMsg;
    private PatternHelper patternHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_prove);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    protected void initData() {
        this.patternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(PatternLockerView view) {
            }

            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {
            }

            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                boolean isOk = isPatternOk(hitList);
                view.updateStatus(!isOk);
                patternIndicatorView.updateState(hitList, !isOk);
                updateMsg();
            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });
        this.textMsg.setText("设置手势密码图案");
        this.patternHelper = new PatternHelper();
    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForSetting(hitList);
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
            sp.edit().putBoolean(SP_PATTERN_STATE, true).apply();
            finish();
        }
    }
}
