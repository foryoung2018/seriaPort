package com.licheedev.serialtool.activity.manage;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.deposit.DepositActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置管理界面
 */
public class SetManageActivity extends BaseActivity {

    @BindView(R.id.ibtn_manager)
    ImageButton ibtnManager;
    @BindView(R.id.ibtn_setting)
    ImageButton ibtnSetting;
    @BindView(R.id.ibtn_maintain)
    ImageButton ibtnMaintain;
    @BindView(R.id.ibtn_print)
    ImageButton ibtnPrint;
    @BindView(R.id.ibtn_device_register)
    ImageButton ibtnDeviceRegister;
    @BindView(R.id.ibtn_update)
    ImageButton ibtnUpdate;
    @BindView(R.id.ibtn_stop)
    ImageButton ibtnStop;
    @BindView(R.id.ibtn_close)
    ImageButton ibtnClose;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setmanager;
    }

    @OnClick({R.id.ibtn_manager, R.id.ibtn_setting, R.id.ibtn_maintain, R.id.ibtn_print
            , R.id.ibtn_device_register, R.id.ibtn_update, R.id.ibtn_stop, R.id.ibtn_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_manager:
                startActivity(new Intent(this, DepositActivity.class));
                break;
            case R.id.ibtn_setting:
                break;
            case R.id.ibtn_maintain:
                break;
            case R.id.ibtn_print:
                break;
            case R.id.ibtn_device_register:
                break;
            case R.id.ibtn_update:
                break;
            case R.id.ibtn_stop:
                break;
            case R.id.ibtn_close:
                break;
        }
    }

}
