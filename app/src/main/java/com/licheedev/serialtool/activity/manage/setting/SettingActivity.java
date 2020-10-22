package com.licheedev.serialtool.activity.manage.setting;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 设定
 * 收款机
 */
public class SettingActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.tvDevice, R.id.tvNetwork, R.id.tvFunction, R.id.tvenvelope, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvDevice:
                startActivity(new Intent(SettingActivity.this, DeviceSettingActivity.class));
                break;
            case R.id.tvNetwork:
                startActivity(new Intent(SettingActivity.this, NetworkSettingActivity.class));
                break;
            case R.id.tvFunction:
                startActivity(new Intent(SettingActivity.this, FunctionSettingActivity.class));
                break;
            case R.id.tvenvelope:
                startActivity(new Intent(SettingActivity.this, EnvelopeSettingActivity.class));
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
