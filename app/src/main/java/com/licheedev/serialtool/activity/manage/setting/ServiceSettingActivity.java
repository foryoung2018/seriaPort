package com.licheedev.serialtool.activity.manage.setting;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务器设定
 */
public class ServiceSettingActivity extends BaseActivity {

    @BindView(R.id.editIp)
    EditText editIp;
    @BindView(R.id.btnUpload)
    Button btnUpload;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        btnUpload.setText(getResources().getString(R.string.set));
    }

    @OnClick({R.id.btnBack, R.id.btnUpload, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnUpload:
                break;
            case R.id.btLogout:
                break;

        }
    }

}
