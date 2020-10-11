package com.licheedev.serialtool.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.util.constant.Money;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.btSave)
    Button btSave;
    @BindView(R.id.btCurren)
    Button btCurren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.btSave,R.id.btCurren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSave:
                startActivity(new Intent(this,DepositActivity.class));
                finish();
                break;
            case R.id.btCurren:
                showCurreny();
                break;
        }
    }

    private void showCurreny() {
        DialogItemAdapter adapter = new DialogItemAdapter(LoginActivity.this, iniDatas());
        LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.currency_select_view, null, false);
        ListView currencyList = view.findViewById(R.id.currencyList);
        currencyList.setAdapter(adapter);
        AlertDialog alertDialog = new AlertDialog
                .Builder(LoginActivity.this)
                .create();
        alertDialog.setView(view);
        alertDialog.show();
        alertDialog.getWindow().setLayout(500,170);
    }

    public List<String> iniDatas() {
        List<String> strings = Arrays.asList(Money.CURRENCY_ARRAY);
        return strings;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
            if(holder.typeTextview != null){
                holder.typeTextview.setText(getItem(position));
            }
            return convertView;
        }

        public static class ViewHolder {
            public TextView typeTextview;
        }
    }

}
