package com.licheedev.serialtool.activity;

import android.app.AlertDialog;
import android.content.Context;
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

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.deposit.DepositActivity;
import com.licheedev.serialtool.dialog.CurrenySelectUtil;
import com.licheedev.serialtool.util.constant.Money;

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
               // startActivity(new Intent(this, DepositActivity.class));
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case R.id.btCurren:
                CurrenySelectUtil.showCurreny(LoginActivity.this);
                break;
        }
    }




    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


}
