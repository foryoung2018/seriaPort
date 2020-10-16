package com.licheedev.serialtool.activity.manage;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 机器管理
 */
public class DeviceRegisterActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_manage;
    }

    @OnClick({R.id.tvdevicemanage, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvdevicemanage:
                startActivity(new Intent(DeviceRegisterActivity.this, NetWorkTestActivity.class));
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
