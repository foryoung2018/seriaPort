package com.licheedev.serialtool.activity.deposit;

import android.view.View;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 纸币存款
 */
public class PaperCurrencyDepositActivity extends BaseActivity {

    @BindView(R.id.tvCurrencyNum)
    TextView tvCurrencyNum;
    @BindView(R.id.tvMoneyNum)
    TextView tvMoneyNum;
    @BindView(R.id.tvRrfuse)
    TextView tvRrfuse;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_paper_currency_deposit;
    }

    @OnClick({R.id.ibtn_ok, R.id.ibtn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_ok:

                break;
            case R.id.ibtn_cancel:
                finish();
                break;
        }
    }

}
