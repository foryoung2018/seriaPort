package com.licheedev.serialtool.activity.deposit;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 存款管理
 */
public class DepositManageActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_manage;
    }

    @OnClick({R.id.tv_current_deposit, R.id.tv_record, R.id.tv_ping, R.id.ibtn_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent(DepositManageActivity.this, DepositRecordActivity.class);
        switch (view.getId()) {
            case R.id.tv_current_deposit: //当前存款
                intent.putExtra("iscurrent", true);
                startActivity(intent);
                break;
            case R.id.tv_record:
                intent.putExtra("iscurrent", false);
                startActivity(intent);
                break;
            case R.id.tv_ping:

                break;
            case R.id.ibtn_back:
                finish();
                break;
        }
    }

}
