package kr.co.core.tools.gif_loading;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class BaseAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void progressOn(Activity act) {
        BaseApplication.getInstance().progressON(act, null);
    }

    public void progressON(Activity act, String message) {
        BaseApplication.getInstance().progressON(act, message);
    }

    public void progressOFF() {
        BaseApplication.getInstance().progressOFF();
    }
}
