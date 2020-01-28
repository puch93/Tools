package kr.co.core.tools.gif_loading;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityGifMainBinding;

public class GifMainAct extends BaseAct {
    ActivityGifMainBinding binding;
    Activity act;

    private static final String TAG = "TEST_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gif_main, null);
        act = this;

        binding.btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
            }
        });
    }

    private void startProgress() {
        progressON(act, "Loading...");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressOFF();
            }
        }, 3500);
    }
}
