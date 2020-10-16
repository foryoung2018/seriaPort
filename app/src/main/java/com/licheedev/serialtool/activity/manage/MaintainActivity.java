package com.licheedev.serialtool.activity.manage;

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
                break;
            case R.id.tvDeviceSet:
                break;
            case R.id.tvDebug:
                break;
            case R.id.tvControl:
                break;
            case R.id.tvErrorClear:
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
