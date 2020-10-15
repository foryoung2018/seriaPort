package com.licheedev.serialtool.activity.deposit;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.LoginActivity;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.dialog.CurrenySelectUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 存款方式选择
 */
public class DepositSelectActivity extends BaseActivity {

    @BindView(R.id.ibtn_currency_select)
    ImageButton ibtnCurrencySelect;
    @BindView(R.id.ibtn_other_select)
    ImageButton ibtnOtherSelect;
    @BindView(R.id.ibtn_coin)
    ImageButton ibtnCoin;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnBack)
    Button btnBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_select;
    }

    @OnClick({R.id.ibtn_currency_select, R.id.ibtn_other_select, R.id.ibtn_coin, R.id.btnBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_currency_select:
                //币种选择
                CurrenySelectUtil.showCurreny(DepositSelectActivity.this);
                break;
            case R.id.ibtn_other_select:
                //其余存款方式选择
                tvTitle.setText(getResources().getString(R.string.deposit_other_select));
                ibtnCurrencySelect.setVisibility(View.GONE);
                ibtnOtherSelect.setVisibility(View.GONE);

                ibtnCoin.setVisibility(View.VISIBLE);
                break;
            case R.id.ibtn_coin:
                //硬币存款
                startActivity(new Intent(DepositSelectActivity.this, DepositActivity.class));
                break;
            case R.id.btnBack:
                if (ibtnCoin.getVisibility() == View.VISIBLE) {
                    tvTitle.setText(getResources().getString(R.string.deposit_select));
                    ibtnCurrencySelect.setVisibility(View.VISIBLE);
                    ibtnOtherSelect.setVisibility(View.VISIBLE);

                    ibtnCoin.setVisibility(View.GONE);
                } else {
                    finish();
                }
                break;
        }
    }

}
