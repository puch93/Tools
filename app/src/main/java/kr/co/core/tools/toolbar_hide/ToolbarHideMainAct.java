package kr.co.core.tools.toolbar_hide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityToolbarHideMainBinding;

public class ToolbarHideMainAct extends AppCompatActivity {
    ActivityToolbarHideMainBinding binding;
    Activity act;
    ActionBar actionBar;

    TestAdapter adapter;
    LinearLayoutManager manager;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_toolbar_hide_main, null);
        act = this;

//        setActionBar();

        recyclerView = binding.myRecyclerView;
        manager = new LinearLayoutManager(act);
        adapter = new TestAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

//    private void setActionBar() {
//        setSupportActionBar(binding.toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setTitle(null);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//    }
}
