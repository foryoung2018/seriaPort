package com.licheedev.serialtool.activity.deposit;

import android.content.Intent;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.manage.SetManageActivity;

import butterknife.OnClick;

/**
 * 选择存款方式
 */
public class SelectDepositActivitys extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_deposit;
    }

    @OnClick({R.id.ibtn_paper_select, R.id.ibtn_other_select, R.id.ibtn_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_paper_select:
                startActivity(new Intent(this, PaperCurrencyDepositActivity.class));
                break;
            case R.id.ibtn_other_select:
                startActivity(new Intent(this, OtherDepositActivity.class));
                break;
            case R.id.ibtn_record:
               // startActivity(new Intent(this, SetManageActivity.class));
                startActivity(new Intent(this, DepositRecordActivity.class));
                break;
        }
    }

}
