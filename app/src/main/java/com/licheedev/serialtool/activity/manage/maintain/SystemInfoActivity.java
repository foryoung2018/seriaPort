package com.licheedev.serialtool.activity.manage.maintain;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 系统信息
 */
public class SystemInfoActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_systeminfo;
    }

    @OnClick({R.id.tvsysteminfo, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvsysteminfo:
                startActivity(new Intent(this, SysDetailsInfoActivity.class));
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
