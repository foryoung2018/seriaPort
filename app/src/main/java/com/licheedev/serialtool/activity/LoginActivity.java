package com.licheedev.serialtool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.deposit.ClearDeviceTestActivity;
import com.licheedev.serialtool.activity.deposit.SelectDepositActivity;
import com.licheedev.serialtool.activity.manage.SetManageActivity;
import com.licheedev.serialtool.dialog.CurrenySelectUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.btLogin,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                // startActivity(new Intent(this, DepositActivity.class));
                startActivity(new Intent(this, SelectDepositActivity.class));
                finish();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


}
