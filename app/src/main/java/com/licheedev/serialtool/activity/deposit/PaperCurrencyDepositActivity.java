package com.licheedev.serialtool.activity.deposit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.licheedev.myutils.LogPlus;
import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.MainActivity;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.comn.message.LogManager;
import com.licheedev.serialtool.dialog.CurrenySelectUtil;
import com.licheedev.serialtool.util.ToastUtil;
import com.licheedev.serialtool.util.constant.Money;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.licheedev.serialtool.comn.message.LogManager.COUNT_COMMAND;
import static com.licheedev.serialtool.comn.message.LogManager.EXIT_WORK_COMMAND;
import static com.licheedev.serialtool.comn.message.LogManager.SAVE_SUCCESS_COMMAND;

public class PaperCurrencyDepositActivity extends BaseActivity {

    int[] commandWorkMode =new  int[]{0xA1,0xA2,0xA3,0xA4,/*STX 4byte*/
            0x12,0x00,/*size 2byte*/
            0x21,/*CMD 1byte*/
            0x02,0x00,0x01,0x08,0x00,0x04,0x02,0x06,0x01,0x00,0x01,0x01,0x01,0x05,/*DATA1 14byte*/
            0xBB,0xBB,0x39}; //进入工作模式

    @BindView(R.id.tvCurrencyNum)
    TextView tvCurrencyNum;
    @BindView(R.id.tvMoneyNum)
    TextView tvMoneyNum;
    @BindView(R.id.tvRrfuse)
    TextView tvRrfuse;

    Deposit deposit;
    boolean exit;
    Dialog continueDepositDialogdialog, exitFailDialog0,exitFailDialog1;

    public static class Deposit {

        public Deposit(long moneyNum, long currencyNum, long refuse) {
            this.moneyNum = moneyNum;
            this.currencyNum = currencyNum;
            this.refuse = refuse;
        }

        long moneyNum;
        long currencyNum;
        long refuse;

        boolean isEmpty(){
            return moneyNum ==0 && currencyNum==0 && refuse ==0;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swtichWorkMode();
    }

    private void swtichWorkMode() {
        SerialPortManager.instance().sendCommand(SerialPortManager.byteArrayToHexString(commandWorkMode));
    }

    @OnClick({R.id.ibtn_ok, R.id.ibtn_cancel, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtn_ok:
                SerialPortManager.instance().sendSaveCommand();
                break;
            case R.id.ibtn_cancel:
                if(deposit == null || deposit.isEmpty()){
                    finish();
                    return;
                }
                SerialPortManager.instance().sendStatusCommand();
                continueDepositDialogdialog = CurrenySelectUtil.showContinueDepositDialog(this, new Callback() {
                    @Override
                    public void onDialogClick(int which, Dialog dialog) {
                        if (which == 0) {
                            SerialPortManager.instance().sendExitWorkModeCommand();
                        }
                    }
                });
                break;
            case R.id.button4:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LogManager.ReceiveData  data) {
        byte[] received = data.data;
        switch (data.what){
            case COUNT_COMMAND:{
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
                tvCurrencyNum.setText(""+count);
                int money = CNY_100 * Money.Denomination_100_CNY +
                        CNY_50 * Money.Denomination_50_CNY+
                        CNY_20 * Money.Denomination_20_CNY+
                        CNY_10 * Money.Denomination_10_CNY+
                        CNY_5 * Money.Denomination_5_CNY+
                        CNY_1 * Money.Denomination_1_CNY;
                tvMoneyNum.setText(""+money);
                int reject = received[49];
                tvRrfuse.setText(""+reject);

                deposit = new Deposit(money,count,reject);
            }
            break;
            case EXIT_WORK_COMMAND:{
                /**
                 * 06H 退出成功
                 * 15H 未处在工作模式，不能执行退出操作
                 * 16H 收钞口有纸币，不能执行退出操作
                 * 17H 退钞口有纸币，不能执行退出操作
                 * 18H 置钞口有纸币，不能执行退出操作
                 */




                if(((char)(received[7]&0xff)==0x06)) {
                    LogPlus.e("read_thread","退出成功");

                    finish();
                }else if(((char)(received[7]&0xff)==0x15)) {

                    LogPlus.e("read_thread","不能执行退出操作");

                }else if(((char)(received[7]&0xff)==0x16)) {

                    SerialPortManager.instance().openMaskDoor();
                    if(exitFailDialog0 != null && exitFailDialog0.isShowing()){
                        exitFailDialog0.dismiss();
                    }

                    exitFailDialog0 = CurrenySelectUtil.showExitFailDialog(this, new Callback() {
                        @Override
                        public void onDialogClick(int which, Dialog dialog) {
                            SerialPortManager.instance().closeMaskDoor();
                        }
                    }, "收钞口有纸币，不能执行退出操作");

                }else if(((char)(received[7]&0xff)==0x17)) {

                    if(exitFailDialog1 != null && exitFailDialog1.isShowing()){
                        exitFailDialog1.dismiss();
                    }

                    exitFailDialog1 = CurrenySelectUtil.showExitFailDialog(this, new Callback() {
                        @Override
                        public void onDialogClick(int which, Dialog dialog) {

                        }

                    }, "退钞口有纸币，不能执行退出操作");

                }else if(((char)(received[7]&0xff)==0x18)) {
                    SerialPortManager.instance().openMaskDoor();
                    ToastUtil.show(this,"请取出置钞口钞票");
                    LogPlus.e("read_thread","置钞口有纸币，不能执行退出操作");

                }
            }
            break;
            case SAVE_SUCCESS_COMMAND:{
                SerialPortManager.instance().sendSaveAck();
            }
            break;
        }



    }

    public interface Callback{
        void onDialogClick(int which,Dialog dialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SerialPortManager.instance().sendExitWorkModeCommand();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_paper_currency_deposit;
    }


}
