package com.licheedev.serialtool.activity.manage.print;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.deposit.DepositManageActivity;
import com.licheedev.serialtool.activity.deposit.DepositRecordActivity;

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
            case R.id.tvcreenbill:
                break;

            case R.id.tvclearrecord:
                startActivity(new Intent(this, ClearingRecordActivity.class));
                break;

            case R.id.tvdepositrecored: //存款记录
                startActivity(new Intent(this, DealRecordActivity.class));
                break;

            case R.id.btnBack:
                finish();
                break;

            case R.id.btLogout:
                break;

        }
    }

}
