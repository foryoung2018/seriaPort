package com.licheedev.serialtool.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.util.constant.Money;

import java.util.Arrays;
import java.util.List;

public class CurrenySelectUtil {

    public static void showCurreny(Context context) {
        List<String> strings = Arrays.asList(Money.CURRENCY_ARRAY);
        DialogItemAdapter adapter = new DialogItemAdapter(context, strings);
        LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.currency_select_view, null, false);
        ListView currencyList = view.findViewById(R.id.currencyList);
        currencyList.setAdapter(adapter);
        AlertDialog alertDialog = new AlertDialog
                .Builder(context)
                .create();
        alertDialog.setView(view);
        alertDialog.show();
        alertDialog.getWindow().setLayout(500, 170);
    }

    public static class DialogItemAdapter extends BaseAdapter {
        //这里可以传递个对象，用来控制不同的item的效果
        //比如每个item的背景资源，选中样式等
        public List<String> list;
        LayoutInflater inflater;

        public DialogItemAdapter(Context context, List<String> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int i) {
            if (i == getCount() || list == null) {
                return null;
            }
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.dialog_item, null);
                holder.typeTextview = (TextView) convertView.findViewById(R.id.typeTextview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (holder.typeTextview != null) {
                holder.typeTextview.setText(getItem(position));
            }
            return convertView;
        }

        public static class ViewHolder {
            public TextView typeTextview;
        }
    }

}
