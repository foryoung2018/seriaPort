package com.licheedev.serialtool.activity.deposit;

import android.view.View;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 清机测试
 */
public class ClearDeviceTestActivity extends BaseActivity {

    @BindView(R.id.tvOriginalNum)
    TextView tvOriginalNum;
    @BindView(R.id.tvNewNum)
    TextView tvNewNum;
    @BindView(R.id.tvLead)
    TextView tvLead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_clear_device;
    }

    @OnClick({R.id.ibtn_back, R.id.ibtn_ok, R.id.ibtn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back:

                break;
            case R.id.ibtn_ok:

                break;
            case R.id.ibtn_cancel:
                finish();
                break;
        }
    }

}
