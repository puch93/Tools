package kr.co.core.tools.address;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.co.core.tools.R;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private Activity act;
    private List<PhoneBook> list;

    public ContactsAdapter(Activity act, List<PhoneBook> list) {
        this.act = act;
        this.list = list;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address, viewGroup, false);
        ContactsAdapter.ViewHolder viewHolder = new ContactsAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int i) {
        holder.tv_idx.setText(list.get(i).getId());
        holder.tv_name.setText(list.get(i).getName());
        holder.tv_address.setText(list.get(i).getTel());

        holder.ll_all_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setItems(List<PhoneBook> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_idx, tv_name, tv_address;
        LinearLayout ll_all_area;

        ViewHolder(View v) {
            super(v);
            tv_idx = (TextView) v.findViewById(R.id.tv_idx);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_address = (TextView) v.findViewById(R.id.tv_address);

            ll_all_area = (LinearLayout) v.findViewById(R.id.ll_all_area);
        }
    }
}
