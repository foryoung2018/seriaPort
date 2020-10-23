package com.licheedev.serialtool.activity.manage.update;

import android.view.View;
import android.widget.Button;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更新设置
 */
public class UpdateSettingActivity extends BaseActivity {

    @BindView(R.id.btnUpload)
    Button btnUpload;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        btnUpload.setText(getResources().getString(R.string.set));
    }

    @OnClick({R.id.btnUpload, R.id.btnBack, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.btnUpload:

                break;

            case R.id.btnBack:
                finish();
                break;

            case R.id.btLogout:
                break;


        }
    }

}
