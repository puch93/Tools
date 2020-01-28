package kr.co.core.tools.toolbar_hide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.core.tools.R;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private Activity act;


    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview_a11, viewGroup, false);
        TestAdapter.ViewHolder viewHolder = new TestAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int i) {

        holder.ll_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return 10;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_area;

        ViewHolder(View v) {
            super(v);
            ll_area = (LinearLayout) v.findViewById(R.id.ll_area);
        }
    }
}
