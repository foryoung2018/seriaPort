package com.licheedev.serialtool.activity.manage;

import android.view.View;
import android.widget.EditText;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网络测试
 */
public class NetWorkTestActivity extends BaseActivity {

    @BindView(R.id.editIp)
    EditText editIp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_network_test;
    }

    @OnClick({R.id.btnBack, R.id.btnTest, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnTest:
                break;
            case R.id.btLogout:
                break;

        }
    }

}
