package kr.co.core.tools.mp4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import kr.co.core.tools.R;
import kr.co.core.tools.util.Common;

public class Mp4Act extends AppCompatActivity implements SurfaceHolder.Callback {
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;

    Activity act;
    int current = 0;
    private static final int a1 = 101;
    private static final int a2 = 102;
    private static final int a3 = 103;

    int width = 0;
    int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mp4);
        act = this;

        current = a1;

        if (mediaPlayer == null) {
//            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(act, R.raw.soldier1);
//            videoWidth = mediaPlayer.getVideoWidth();
//            videoHeight = mediaPlayer.getVideoHeight();
//            Log.e("TEST_HOME", "videoWidth: " + videoWidth + ", videoHeight: " + videoHeight);
        } else {
            mediaPlayer.reset();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer = null;

                switch (current) {
                    case a1:
                        current = a2;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mediaPlayer = MediaPlayer.create(act, R.raw.soldier1);
                                surfaceHolder.setFixedSize(width, height);
                                mediaPlayer.setDisplay(surfaceHolder); // 화면 호출
                                mediaPlayer.start();
                            }
                        }, 3000);
                        break;

                    case a2:
                        current = a3;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mediaPlayer = MediaPlayer.create(act, R.raw.soldier1);
                                surfaceHolder.setFixedSize(width, height);
                                mediaPlayer.setDisplay(surfaceHolder); // 화면 호출
                                mediaPlayer.start();
                            }
                        }, 3000);
                        break;

                    case a3:
                        Common.showToast(act, "3번 재생완료");
                        break;
                    default:
                }
            }
        });


        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width = surfaceView.getWidth();
        height = surfaceView.getHeight();

        float boxWidth = width;
        float boxHeight = height;

        float videoWidth = 0;
        float videoHeight = 0;

//        if (mediaPlayer == null) {
////            mediaPlayer = new MediaPlayer();
//            mediaPlayer = MediaPlayer.create(act, R.raw.soldier1);
//            videoWidth = mediaPlayer.getVideoWidth();
//            videoHeight = mediaPlayer.getVideoHeight();
//            Log.e("TEST_HOME", "videoWidth: " + videoWidth + ", videoHeight: " + videoHeight);
//        } else {
//            mediaPlayer.reset();
//        }


        try {
            float wr = boxWidth / videoWidth;
            float hr = boxHeight / videoHeight;
            float ar = videoWidth / videoHeight;

            if (wr > hr) {
                width = (int) (boxHeight * ar);
            } else {
                height = (int) (boxWidth / ar);
            }

            Log.e("TEST_HOME", "width: " + width + ", height: " + height);
            surfaceHolder.setFixedSize(width, height);

//            String path = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
//            mediaPlayer.setDataSource(path);

//            mediaPlayer.setVolume(0, 0); //볼륨 제거

            mediaPlayer.setDisplay(surfaceHolder); // 화면 호출

//            mediaPlayer.prepare(); // 비디오 load 준비
//            mediaPlayer.setOnCompletionListener(completionListener); // 비디오 재생 완료 리스너

            mediaPlayer.start();

        } catch (Exception e) {
            Log.e("TEST_HOME", "surface view error : " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer.reset();
        }
    }
}
