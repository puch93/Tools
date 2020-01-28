package kr.co.core.tools.activity_transition;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import kr.co.core.tools.R;

public class TransitionMainAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
    }
}
