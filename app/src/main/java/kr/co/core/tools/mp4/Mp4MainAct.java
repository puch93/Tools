package kr.co.core.tools.mp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.core.tools.R;

public class Mp4MainAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp4_main);

        (findViewById(R.id.btn_1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mp4MainAct.this, Mp4Act.class));
            }
        });

        (findViewById(R.id.btn_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mp4MainAct.this, Mp4Act2.class));
            }
        });
    }
}
