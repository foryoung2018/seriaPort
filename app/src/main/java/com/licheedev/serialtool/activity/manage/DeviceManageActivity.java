package com.licheedev.serialtool.activity.manage;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备注册
 */
public class DeviceManageActivity extends BaseActivity {

    @BindView(R.id.editCode)
    EditText editCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_register;
    }

    @OnClick({R.id.btnBack, R.id.btnRegister, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnRegister:

                break;
            case R.id.btLogout:
                break;

        }
    }

}
