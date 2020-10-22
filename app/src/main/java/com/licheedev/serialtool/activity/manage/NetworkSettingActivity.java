package com.licheedev.serialtool.activity.manage;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 网络设定
 */
public class NetworkSettingActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_network_setting;
    }

    @OnClick({R.id.tvCash, R.id.tvpwd, R.id.tvSerial, R.id.btnBack
            , R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCash: //收款机设定
                startActivity(new Intent(this, CashRegisterSettingActivity.class));
                break;
            case R.id.tvpwd:
                break;
            case R.id.tvSerial:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
