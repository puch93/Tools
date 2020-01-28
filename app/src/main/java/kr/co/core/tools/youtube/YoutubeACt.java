package kr.co.core.tools.youtube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kr.co.core.tools.R;

public class YoutubeACt extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_act);

        /**
         * 썸네일 주소
         *  https://img.youtube.com/vi/이미지아이디/이미지형식.jpg
         * 이미지형식
         *  최대 해상도 썸네일(1920x1080) : maxresdefault.jpg
         *  고해상도 썸네일(1280x720, 1920x1080) : maxresdefault.jpg
         *  중간해상도 썸네일(640x480) : sddefault.jpg
         *  고품질 썸네일(480x360) : hqdefault.jpg
         *  중간품질 썸네일(320x180) : mqdefault.jpg
         *  보통품질 썸네일(120x90) : default.jpg
         *  표준형 썸네일(640x480) : sddefault.jpg
         *  https://youtu.be/ZRcnRtB2jFU?t=1
         */


//        String url = "https://www.youtube.com/watch?v=ZRcnRtB2jFU";  // 영상 주소
        String url = "https://youtu.be/ZRcnRtB2jFU?t=1";  // 영상 주소
        String resultUrl = "https://img.youtube.com/vi/";  // 썸네일 주소

        int idx = url.indexOf("?v=");
        url = url.substring(idx+3);

        resultUrl = resultUrl + url + "/maxresdefault.jpg";
        Log.e("TEST_HOME", "url: " + resultUrl);

        Glide.with(this).load(resultUrl).into(((ImageView) findViewById(R.id.iv_youtube)));
    }
}
