package com.licheedev.serialtool.activity.manage.maintain;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 系统调试
 */
public class SystemDebugActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_debug;
    }

    @OnClick({R.id.tv_printdebug, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_printdebug:
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
