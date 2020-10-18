package com.licheedev.serialtool.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.deposit.PaperCurrencyDepositActivity;
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

    public static Dialog showContinueDepositDialog(Context context, final PaperCurrencyDepositActivity.Callback callback){
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.deposit_continue_view, null, false);
        ImageButton btConfirm = view.findViewById(R.id.btConfirm);
        ImageButton btCancel = view.findViewById(R.id.btCancel);

        final AlertDialog alertDialog = new AlertDialog
                .Builder(context)
                .create();
        alertDialog.setView(view);
        alertDialog.show();
        alertDialog.getWindow().setLayout(350, 190);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDialogClick(0, alertDialog);
            }
        });
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                callback.onDialogClick(1, alertDialog);
            }
        });
        return alertDialog;
    }

    public static Dialog showExitFailDialog(Context context,final PaperCurrencyDepositActivity.Callback callback, String message) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.store_money_tip_view, null, false);
        ImageButton btConfirm = view.findViewById(R.id.btConfirm);
        TextView tvMessage = view.findViewById(R.id.tvMessage);

        final AlertDialog alertDialog = new AlertDialog
                .Builder(context)
                .create();
        alertDialog.setView(view);
        alertDialog.show();
        alertDialog.getWindow().setLayout(350, 190);
        tvMessage.setText(message);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                callback.onDialogClick(1, alertDialog);
            }
        });
        return alertDialog;

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
