package com.licheedev.serialtool.activity.manage;

import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 收款机设定
 */
public class CashRegisterSettingActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_register_setting;
    }

    @OnClick({R.id.tvNetwork, R.id.tvServer,  R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvNetwork:
                break;
            case R.id.tvServer:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
