package kr.co.core.tools.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityAddressMainBinding;

public class AddressMainAct extends AppCompatActivity implements View.OnClickListener {
    ActivityAddressMainBinding binding;
    Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_main, null);
        act = this;

        binding.btn01.setOnClickListener(this);
        binding.btn02.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_01:
                Intent intent = new Intent(act, AddressSubAct.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.btn_02:
                Intent intent2 = new Intent(act, AddressSubAct.class);
                intent2.putExtra("type", "2");
                startActivity(intent2);
                break;
        }
    }
}
