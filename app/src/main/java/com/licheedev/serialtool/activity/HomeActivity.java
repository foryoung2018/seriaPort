package com.licheedev.serialtool.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.deposit.DepositActivity;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.deposit.DepositSelectActivity;
import com.licheedev.serialtool.activity.manage.SetManageActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首选项
 *
 */
public class HomeActivity extends BaseActivity {

    @BindView(R.id.ibtn_deposit_select)
    ImageButton ibtnDepositselect;
    @BindView(R.id.ibtn_clear_device)
    ImageButton ibtnClearDevice;
    @BindView(R.id.ibtn_setmanage)
    ImageButton ibtnSetmannage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @OnClick({R.id.ibtn_deposit_select, R.id.ibtn_clear_device, R.id.ibtn_setmanage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_deposit_select:
                startActivity(new Intent(this, DepositSelectActivity.class));
                break;
            case R.id.ibtn_clear_device:

                break;
            case R.id.ibtn_setmanage:
                startActivity(new Intent(this, SetManageActivity.class));
                break;
        }
    }

}
