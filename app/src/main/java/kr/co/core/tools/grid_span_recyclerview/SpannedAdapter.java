package kr.co.core.tools.grid_span_recyclerview;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.core.tools.R;

public class SpannedAdapter extends RecyclerView.Adapter<SpannedAdapter.GridItemViewHolder> {
    int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.MAGENTA, Color.YELLOW};

    @NonNull
    @Override
    public GridItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spanned, parent, false);
        SpannedAdapter.GridItemViewHolder viewHolder = new SpannedAdapter.GridItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridItemViewHolder holder, int position) {
        holder.textView.setText(String.valueOf(position));
        holder.itemView.setBackgroundColor(colors[position % colors.length]);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class GridItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        GridItemViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.text);
        }
    }

}
