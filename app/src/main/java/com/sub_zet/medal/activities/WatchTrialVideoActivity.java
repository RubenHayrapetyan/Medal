package com.sub_zet.medal.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.sub_zet.medal.R;
import com.sub_zet.medal.databinding.ActivityWatchTrialVideoBinding;

import androidx.databinding.DataBindingUtil;

public class WatchTrialVideoActivity extends BaseActivity {

    private ActivityWatchTrialVideoBinding binding;
    private CountDownTimer countDownTimer;

    private boolean youCanSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_trial_video);

        binding.setVideoUrl(videoLink());
        clickListeners();
        setTimer();
    }

    private void enableSkipBtn() {
        binding.skipTxt.setAlpha(1);
        binding.skipLinear.setClickable(true);
        binding.timerTxt.setVisibility(View.GONE);
        youCanSkip = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    private void setTimer() {
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {

                binding.timerTxt.setText((millisUntilFinished / 1000) + "");
            }

            @Override
            public void onFinish() {
                enableSkipBtn();
            }
        };
        countDownTimer.start();
    }

    private void clickListeners() {
        binding.skipLinear.setOnClickListener(v -> {
            if (youCanSkip) {
                startActivity(new Intent(this, MenuActivity.class));
                finish();
            }
        });
    }

    private String videoLink() {
        // Stex backendic kstanam link@, kdnem linki poxaren
        String link = "https://www.youtube.com/watch?v=";

            Intent intent = getIntent();

        return intent.getStringExtra("video_tutorial_url");
    }
}
