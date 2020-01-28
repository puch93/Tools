package kr.co.core.tools.actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityActionBarBinding;
import kr.co.core.tools.util.Common;

public class ActionBarActivity extends AppCompatActivity {
    ActivityActionBarBinding binding;
    Activity act;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_action_bar, null);
        act = this;

        setActionBar();

    }


    private void setActionBar() {
        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(null);

//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
//                | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.wt_icon_back_wh_191022);

//        Drawable drawable= getResources().getDrawable(R.drawable.baseline_home_black_36dp);
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(newdrawable);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.appbar_actionbar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.action_search:
//                Common.showToast(act, "action_search");
//                return true;
//
//            case R.id.action_reload:
//                Common.showToast(act, "action_reload");
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
