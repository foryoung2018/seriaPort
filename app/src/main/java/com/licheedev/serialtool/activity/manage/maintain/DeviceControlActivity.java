package com.licheedev.serialtool.activity.manage.maintain;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备控制
 */
public class DeviceControlActivity extends BaseActivity {

    @BindView(R.id.btLogout)
    Button mandatorymodeBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_control;
    }

    @Override
    protected void initView() {
        super.initView();
        mandatorymodeBtn.setText(getResources().getString(R.string.mandatorymode));
    }

    @OnClick({R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;

            case R.id.btLogout:
                break;

        }
    }

}
