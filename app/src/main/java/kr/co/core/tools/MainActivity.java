package kr.co.core.tools;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;


import java.util.ArrayList;

import kr.co.core.tools.actionbar.ActionBarActivity;
import kr.co.core.tools.address.AddressMainAct;
import kr.co.core.tools.address.AddressSubAct;
import kr.co.core.tools.app_response.ResponseMainAct;
import kr.co.core.tools.date_picker.DatePickerMainAct;
import kr.co.core.tools.exoplayer.ExoMainAct2;
import kr.co.core.tools.grid_span_recyclerview.SpannedMainAct;
import kr.co.core.tools.mp4.Mp4MainAct;
import kr.co.core.tools.recapcha.RecapchaActivitiy2;
import kr.co.core.tools.share.ShareMainActivity;
import kr.co.core.tools.smsdelete.SmsDeleteMainAct;
import kr.co.core.tools.webview.WebViewMainAct;
import kr.co.core.tools.expandable_recyclerview.ExpandableMainAct;
import kr.co.core.tools.toolbar_hide.ToolbarHideMainAct;
import kr.co.core.tools.qrcode.QrMainAct;
import kr.co.core.tools.gif_loading.GifMainAct;
import kr.co.core.tools.activity_transition.TransitionMainAct;
import kr.co.core.tools.radio.RadioMainAct;
import kr.co.core.tools.retrofit.RetrofitMainAct;
import kr.co.core.tools.databinding.ActivityMainBinding;
import kr.co.core.tools.youtube.YoutubeACt;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Activity act;
    ArrayList<MainData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main, null);
        act = this;

        setListData();

        binding.rcvMain.setLayoutManager(new LinearLayoutManager(act));
        binding.rcvMain.setHasFixedSize(true);
        binding.rcvMain.setAdapter(new MainAdapter(act, list));
    }

    private void setListData() {
        list.add(new MainData("화면전환 애니메이션", TransitionMainAct.class));
        list.add(new MainData("GIF 로딩", GifMainAct.class));
        list.add(new MainData("Retrofit", RetrofitMainAct.class));
        list.add(new MainData("라디오", RadioMainAct.class));
        list.add(new MainData("Exo-Player", ExoMainAct2.class));
        list.add(new MainData("date picker", DatePickerMainAct.class));
        list.add(new MainData("공유하기", ShareMainActivity.class));
        list.add(new MainData("웹뷰", WebViewMainAct.class));
        list.add(new MainData("Recursive Recyclerview", ExpandableMainAct.class));
        list.add(new MainData("Hide Toolbar", ToolbarHideMainAct.class));
        list.add(new MainData("qr code", QrMainAct.class));
        list.add(new MainData("reCAPTCHA", RecapchaActivitiy2.class));
        list.add(new MainData("action bar", ActionBarActivity.class));
        list.add(new MainData("mp4 test", Mp4MainAct.class));
        list.add(new MainData("address", AddressMainAct.class));
        list.add(new MainData("youtube", YoutubeACt.class));
        list.add(new MainData("vrtest", YoutubeACt.class));
        list.add(new MainData("grid recycler spanned", SpannedMainAct.class));
        list.add(new MainData("응답남녀", ResponseMainAct.class));
        list.add(new MainData("문자삭제", SmsDeleteMainAct.class));
    }
}
