package com.licheedev.serialtool.activity.manage.maintain;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.manage.NetWorkTestActivity;

import butterknife.OnClick;

/**
 * 机器设置
 *
 */
public class DeviceSetActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintain_deviceset;
    }

    @OnClick({ R.id.btnBack, R.id.btLogout})
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
