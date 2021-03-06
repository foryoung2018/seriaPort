package com.licheedev.serialtool.activity.manage.maintain;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 维护
 */
public class MaintainActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintain;
    }

    @OnClick({R.id.tvSysinfo, R.id.tvDeviceSet, R.id.tvDebug, R.id.tvControl
            , R.id.tvErrorClear, R.id.tvbillinit, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSysinfo:
                startActivity(new Intent(this, SystemInfoActivity.class));
                break;
            case R.id.tvDeviceSet:
                startActivity(new Intent(this, DeviceSetActivity.class));
                break;
            case R.id.tvDebug:
                startActivity(new Intent(this, DebugActivity.class));
                break;
            case R.id.tvControl:
                startActivity(new Intent(this, DeviceControlActivity.class));
                break;
            case R.id.tvErrorClear:
                startActivity(new Intent(this, DepositErrorActivity.class));
                break;
            case R.id.tvbillinit:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;
        }
    }

}
