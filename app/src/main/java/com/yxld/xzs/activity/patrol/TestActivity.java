package com.yxld.xzs.activity.patrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.yxld.xzs.utils.MediaManager;
import com.yxld.xzs.view.AudioRecordButton;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.treat_audio)
    AudioRecordButton treatAudio;
    @BindView(R.id.treat_record)
    AutoLinearLayout treatRecord;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.root_layout_record)
    AutoLinearLayout rootLayoutRecord;

    private String mRecordFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        treatAudio.setCanRecord(true);
        treatAudio.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                mRecordFilePath  = filePath;
                treatRecord.setVisibility(View.VISIBLE);
                tvHint.setVisibility(View.GONE);
            }
        });

        treatRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaManager.release();
                MediaManager.playSound(mRecordFilePath);
            }
        });
    }
}
