package com.domker.study.androidstudy;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * 使用系统VideoView播放 resource 视频
 */
public class VideoActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private VideoView videoView;
    private SeekBar seekBar;
    private TextView playtime;
    private TextView totaltime;
    public static final int update = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);
        setTitle("VideoView");

        buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.bytedance));

        playtime = findViewById(R.id.video_now_time);
        totaltime = findViewById(R.id.video_total_time);
        seekBar = findViewById(R.id.video_seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                videoView.seekTo(progress);
            }
        });

        handler.sendEmptyMessage(update);
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == update) {
                int now = videoView.getCurrentPosition();
                int total = videoView.getDuration();
                seekBar.setMax(total);
                seekBar.setProgress(now);
                updateTime(playtime, now);
                updateTime(totaltime, total);
                handler.sendEmptyMessageDelayed(1, 1000);
            }
            return false;
        }
    });

    private void updateTime(TextView textView,int millisecond)
    {
        int seconds = millisecond / 1000;
        int hour = seconds / 3600 ;
        int minute = seconds % 3600 / 60;
        int second = seconds % 60 ;

        String time;
        time = String.format("%02d:%02d:%02d",hour,minute,second);

        textView.setText(time);
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }
}
