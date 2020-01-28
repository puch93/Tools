package kr.co.core.tools.webview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.core.tools.R;

public class TestBaseAdapter extends BaseAdapter {
    ArrayList<RecentData> array_data;
    private Context context;


    TestBaseAdapter(ArrayList<RecentData> array_data, Context context) {
        this.array_data = array_data;
        this.context = context;
    }



    @Override
    public int getCount() {
        return array_data.size();
    }

    @Override
    public Object getItem(int position) {
        return array_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RecentData data = array_data.get(position);
        final ViewHolderItem viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.browser_actions_context_menu_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.mText = (TextView) convertView.findViewById(R.id.browser_actions_menu_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.mText.setText(data.getUrl());

        return convertView;
    }

    private static class ViewHolderItem {
        TextView mText;
    }
}
