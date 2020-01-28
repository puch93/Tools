package kr.co.core.tools.radio;

import android.app.Activity;
import android.app.ProgressDialog;
import androidx.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityRadioMainBinding;

public class RadioMainAct extends AppCompatActivity implements View.OnClickListener {
    ActivityRadioMainBinding binding;
    Activity act;
    public static final String TAG = "TEST_HOME";

    Uri selectedUri;
    MediaPlayer mediaPlayer;
    ProgressDialog asyncDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_radio_main, null);
        act = this;

        binding.btn01.setOnClickListener(this);
        binding.btn02.setOnClickListener(this);
        binding.btn03.setOnClickListener(this);

        binding.btnStart.setOnClickListener(this);
        binding.btnStop.setOnClickListener(this);
    }

    private void playMedia() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(act, selectedUri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e(TAG, "onPrepared");
                    progressStop();
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMedia() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void progressStart() {
        asyncDialog = new ProgressDialog(act);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("준비중...");
        asyncDialog.setCancelable(false);
        asyncDialog.show();
    }

    private void progressStop() {
        asyncDialog.cancel();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_01:
                progressStart();
                binding.tvTitle.setText(binding.btn01.getText());
                selectedUri = Uri.parse(getString(R.string.ebs_eng));
                initMedia();
                playMedia();
                break;

            case R.id.btn_02:
                progressStart();
                binding.tvTitle.setText(binding.btn02.getText());
                selectedUri = Uri.parse(getString(R.string.ebs_book));
                initMedia();
                playMedia();
                break;

            case R.id.btn_03:
                progressStart();
                binding.tvTitle.setText(binding.btn03.getText());
                selectedUri = Uri.parse(getString(R.string.example));
                initMedia();
                playMedia();
                break;


            case R.id.btn_start:
                if (mediaPlayer != null && !mediaPlayer.isPlaying())
                    mediaPlayer.start();
                else
                    Toast.makeText(act, "이미 재생중입니다", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_stop:
                if (mediaPlayer != null && mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
        }
    }
}
