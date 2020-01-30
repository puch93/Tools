package kr.co.core.tools.grid_span_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;


import org.jetbrains.annotations.NotNull;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivitySpannedMainBinding;

public class SpannedMainAct extends AppCompatActivity {
    ActivitySpannedMainBinding binding;
    SpannedGridLayoutManager manager;
    SpannedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spanned_main, null);

        manager = new SpannedGridLayoutManager(new SpannedGridLayoutManager.GridSpanLookup() {
            @Override
            public SpannedGridLayoutManager.SpanInfo getSpanInfo(int position) {
                if (position % 3 == 0 || position % 7 == 0) {
                    return new SpannedGridLayoutManager.SpanInfo(2, 2);
                } else {
                    return new SpannedGridLayoutManager.SpanInfo(1, 1);
                }
            }
        },3,1f);

        adapter = new SpannedAdapter();
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }
}
