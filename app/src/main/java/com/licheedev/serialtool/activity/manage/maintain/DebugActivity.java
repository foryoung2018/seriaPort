package com.licheedev.serialtool.activity.manage.maintain;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.manage.manage.DeviceManageActivity;

import butterknife.OnClick;

/**
 * 调试
 */
public class DebugActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_debug;
    }

    @OnClick({R.id.tvsysytemclear, R.id.tvsysdebug, R.id.btnBack
            , R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvsysytemclear:
               // startActivity(new Intent(DebugActivity.this, UserManageActivity.class));
                break;
            case R.id.tvsysdebug: //系统调试
                startActivity(new Intent(DebugActivity.this, SystemDebugActivity.class));
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btLogout:
                break;

        }
    }

}
