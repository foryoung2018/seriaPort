package com.licheedev.serialtool.activity.manage.maintain;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 存款异常处理
 */
public class DepositErrorActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_error;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick({R.id.tvfaultClear,R.id.tvDevicecheck,R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvfaultClear:
                break;

            case R.id.tvDevicecheck:
                startActivity(new Intent(this,DeviceSelfcheckActivity.class));
                break;
            case R.id.btnBack:
                finish();
                break;

            case R.id.btLogout:
                break;

        }
    }

}
