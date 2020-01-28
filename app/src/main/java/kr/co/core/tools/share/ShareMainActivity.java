package kr.co.core.tools.share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;


import java.io.File;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityShareMainBinding;

public class ShareMainActivity extends AppCompatActivity {
    ActivityShareMainBinding binding;
    Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_main, null);
        act = this;

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareKakao();
            }
        });
    }

    private void shareDefault() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "원하는 텍스트를 입력하세요");

        Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
        startActivity(chooser);
    }

    private void shareKakao() {
        String imagePath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/20190511_164936.jpg";
        File imageFile = new File(imagePath);

        /* 내부저장소 이미지 전달시 */
        Uri imageUri = null;
        if(imageFile.exists()) {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                imageUri = FileProvider.getUriForFile(act, "kr.co.core.tools.provider", imageFile);
            } else {
                imageUri = Uri.fromFile(imageFile);
            }
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setType("image/*");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=kr.co.chat.hitalk");
//        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share to..."));
    }
}
