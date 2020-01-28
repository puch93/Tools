package kr.co.core.tools.mp4;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import kr.co.core.tools.R;

public class Mp4Act2 extends AppCompatActivity implements SurfaceHolder.Callback{
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;

    Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mp4);
        act = this;

        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int width = surfaceView.getWidth();
        int height = surfaceView.getHeight();

        float boxWidth = width;
        float boxHeight = height;

        float videoWidth = 0;
        float videoHeight = 0;

        if (mediaPlayer == null) {
//            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(act, R.raw.police2);
//            videoWidth = mediaPlayer.getVideoWidth();
//            videoHeight = mediaPlayer.getVideoHeight();
            Log.e("TEST_HOME", "videoWidth: " + videoWidth + ", videoHeight: " + videoHeight );
        } else {
            mediaPlayer.reset();
        }

        try {
            videoWidth = mediaPlayer.getVideoWidth();
            videoHeight = mediaPlayer.getVideoHeight();

            float wr = boxWidth / videoWidth;
            float hr = boxHeight / videoHeight;
            float ar = videoWidth / videoHeight;

            if(wr > hr) {
                width = (int) (boxHeight * ar);
            } else {
                height = (int) (boxWidth / ar);
            }

            Log.e("TEST_HOME", "width: " + width + ", height: " + height );
            surfaceHolder.setFixedSize(width, height);

//            String path = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
//            mediaPlayer.setDataSource(path);

//            mediaPlayer.setVolume(0, 0); //볼륨 제거

            mediaPlayer.setDisplay(surfaceHolder); // 화면 호출

//            mediaPlayer.prepare(); // 비디오 load 준비
//            mediaPlayer.setOnCompletionListener(completionListener); // 비디오 재생 완료 리스너

            mediaPlayer.start();

        } catch (Exception e) {
            Log.e("TEST_HOME","surface view error : " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
