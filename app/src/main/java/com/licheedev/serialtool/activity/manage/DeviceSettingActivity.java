package com.licheedev.serialtool.activity.manage;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 机器设定
 */
public class DeviceSettingActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView tvtitle;
    @BindView(R.id.tvdevicemanage)
    TextView tvdevice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_manage;

    }

    @Override
    protected void initView() {
        super.initView();
        tvtitle.setText(getResources().getString(R.string.set_device));
        tvdevice.setText(getResources().getString(R.string.system_time));
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
