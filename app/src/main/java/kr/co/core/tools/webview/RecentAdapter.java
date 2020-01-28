package kr.co.core.tools.webview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.co.core.tools.R;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private ArrayList<RecentData> array_data;

    public RecentAdapter(ArrayList<RecentData> array_data) {
        this.array_data = array_data;
    }

    @NonNull
    @Override
    public RecentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recent_search, viewGroup, false);
        RecentAdapter.ViewHolder viewHolder = new RecentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentAdapter.ViewHolder holder, int i) {
        holder.tv_url.setText(array_data.get(i).getUrl());
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TEST_HOME", "recyclerview item clicked");
            }
        });

        if (i == array_data.size() - 1) {
            holder.iv_line.setVisibility(View.VISIBLE);
        } else {
            holder.iv_line.setVisibility(View.GONE);
        }
    }

    public RecentData getItem(int position) {
        return array_data.get(position);
    }

    @Override
    public int getItemCount() {
        return array_data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_url;
        LinearLayout ll_item;
        ImageView iv_line;

        ViewHolder(View v) {
            super(v);
            tv_url = (TextView) v.findViewById(R.id.tv_url);
            ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
            iv_line = (ImageView) v.findViewById(R.id.iv_line);
        }
    }
}