package com.licheedev.serialtool.activity.manage.print;

import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 系统打印
 */
public class SystemPrintActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_print;
    }

    @OnClick({R.id.tvcreenbill, R.id.tvclearrecord, R.id.tvdepositrecored, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_depositdata:
                break;

            case R.id.tv_bill_data:
                break;


            case R.id.btnBack:
                finish();
                break;

            case R.id.btLogout:
                break;

        }
    }

}
