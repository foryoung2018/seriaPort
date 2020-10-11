package com.licheedev.serialtool.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.licheedev.myutils.LogPlus;
import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.util.constant.Money;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DepositActivity extends BaseActivity {

    int[] commandWorkMode =new  int[]{0xA1,0xA2,0xA3,0xA4,/*STX 4byte*/
            0x12,0x00,/*size 2byte*/
            0x21,/*CMD 1byte*/
            0x02,0x00,0x01,0x08,0x00,0x04,0x02,0x06,0x01,0x00,0x01,0x01,0x01,0x05,/*DATA1 14byte*/
            0xBB,0xBB,0x39}; //进入工作模式

    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvReject)
    TextView tvReject;
    @BindView(R.id.btDetail)
    Button btDetail;
    @BindView(R.id.btOK)
    Button btOK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialPortManager.instance().sendExitWorkModeCommand();
        swtichWorkMode();
    }

    private void swtichWorkMode() {
        SerialPortManager.instance().sendCommand(SerialPortManager.byteArrayToHexString(commandWorkMode));
    }

    @OnClick({R.id.btDetail,R.id.btOK, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btDetail:
                break;
            case R.id.btOK:
                SerialPortManager.instance().sendSaveCommand();
                break;
            case R.id.button4:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(byte[]  received) {

        int CNY_100 = received[9] + (received[10]<<8);
        int CNY_50 = received[11] + (received[12]<<8);
        int CNY_20 = received[13] + (received[14]<<8);
        int CNY_10 = received[15] + (received[16]<<8);
        int CNY_5 = received[17] + (received[18]<<8);
        int CNY_1 = received[19] + (received[20]<<8);
        final String amount = " 收到  100x" + CNY_100 + " 50x" +
                CNY_50 + " 20x" + CNY_20 + " 10x" + CNY_10 + " 5x" + CNY_5 + " 1x" + CNY_1;
        LogPlus.e("read_thread",amount);

        int count = CNY_100+
                CNY_50+
                CNY_20+
                CNY_10+
                CNY_5+
                CNY_1;
        tvCount.setText("张数："+count);
        int money = CNY_100 * Money.Denomination_100_CNY +
                CNY_50 * Money.Denomination_50_CNY+
                CNY_20 * Money.Denomination_20_CNY+
                CNY_10 * Money.Denomination_10_CNY+
                CNY_5 * Money.Denomination_5_CNY+
                CNY_1 * Money.Denomination_1_CNY;
        tvAmount.setText("金额："+money);
        int reject = received[49];
        tvReject.setText("拒钞："+reject);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SerialPortManager.instance().sendExitWorkModeCommand();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit;
    }


}
