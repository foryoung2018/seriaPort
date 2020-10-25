package com.licheedev.serialtool.activity.manage.update;

import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.OnClick;

/**
 * 安卓更新
 */
public class AndroidUpdateActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_android_update;
    }

    @OnClick({R.id.tvDownload,  R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvDownload:
                break;

            case R.id.btnBack:
                finish();
                break;

            case R.id.btLogout:
                break;


        }
    }

}
