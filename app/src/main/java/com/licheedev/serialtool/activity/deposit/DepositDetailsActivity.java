package com.licheedev.serialtool.activity.deposit;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licheedev.myutils.LogPlus;
import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.dapter.BaseRecyclerAdapter;
import com.licheedev.serialtool.activity.dapter.RecyclerViewHolder;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.comn.message.LogManager;
import com.licheedev.serialtool.dialog.CurrenySelectUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.licheedev.serialtool.activity.deposit.PaperCurrencyDepositActivity.REQUEST_CODE_DEPOSIT;
import static com.licheedev.serialtool.activity.deposit.PaperCurrencyDepositActivity.RESULT_CODE_DEPOSIT;
import static com.licheedev.serialtool.comn.message.LogManager.FINISH_DEPOSIT;
import static com.licheedev.serialtool.comn.message.LogManager.SAVE_SUCCESS_COMMAND;
import static com.licheedev.serialtool.util.constant.Money.Denomination_100_CNY;
import static com.licheedev.serialtool.util.constant.Money.Denomination_10_CNY;
import static com.licheedev.serialtool.util.constant.Money.Denomination_1_CNY;
import static com.licheedev.serialtool.util.constant.Money.Denomination_20_CNY;
import static com.licheedev.serialtool.util.constant.Money.Denomination_50_CNY;
import static com.licheedev.serialtool.util.constant.Money.Denomination_5_CNY;

/**
 * 存款明细
 */
public class DepositDetailsActivity extends BaseActivity implements BaseRecyclerAdapter.Delegate {

    @BindView(R.id.textView)
    TextView tvTitle;
    @BindView(R.id.tvPresentCount)
    TextView tvPresentCount;
    @BindView(R.id.tvPresentSum)
    TextView tvPresentSum;
    @BindView(R.id.tvAlreadyCount)
    TextView tvAlreadyCount;
    @BindView(R.id.tvAlreadySum)
    TextView tvAlreadySum;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private Dialog overDepositDialogdialog;

    private BaseRecyclerAdapter adapter;
    private List<DepositDetailBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_details;
    }

    @Override
    protected void initView() {
        super.initView();


        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new BaseRecyclerAdapter(this, this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btnOverDeposit, R.id.btnContiniu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOverDeposit:{
                overDepositDialogdialog = CurrenySelectUtil.showOverDepositDialog(this, new PaperCurrencyDepositActivity.Callback() {
                    @Override
                    public void onDialogClick(int which, Dialog dialog) {
                        if(which == 1){
                            SerialPortManager.instance().sendSaveAck();
                            setResult(RESULT_CODE_DEPOSIT);
                            finish();
                        }
                    }
                });
            }
            break;
            case R.id.btnContiniu:{
                SerialPortManager.instance().sendSaveAck();
                finish();
            }
            break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LogManager.ReceiveData data) {
        byte[] received = data.data;
        switch (data.what) {
            case SAVE_SUCCESS_COMMAND: {
                amountSaveMoney(data.data);
                amountReceiveMoney(data.data);
            }
            break;
        }
    }



    private void amountSaveMoney(byte[] received) {
        int CNY_100 = received[29] + (received[30]<<8);
        int CNY_50 = received[31] + (received[32]<<8);
        int CNY_20 = received[33] + (received[34]<<8);
        int CNY_10 = received[35] + (received[36]<<8);
        int CNY_5 = received[37] + (received[38]<<8);
        int CNY_1 = received[39] + (received[40]<<8);
        final String amount = " 共存  100x" + CNY_100 + " 50x" +
                CNY_50 + " 20x" + CNY_20 + " 10x" + CNY_10 + " 5x" + CNY_5 + " 1x" + CNY_1;

        LogPlus.e("read_thread",amount);
        long sum = CNY_100*Denomination_100_CNY +
                CNY_50*Denomination_50_CNY+
                CNY_20*Denomination_20_CNY+
                CNY_10*Denomination_10_CNY+
                CNY_5*Denomination_5_CNY+
                CNY_1*Denomination_1_CNY;
        long count = CNY_100+
                CNY_50+
                CNY_20+
                CNY_10+
                CNY_5+
                CNY_1;

        tvAlreadySum.setText(sum+"");
        tvAlreadyCount.setText(count+"");

    }

    private void amountReceiveMoney(byte[] received) {
        if(list != null){
            list.clear();
        }

        int CNY_100 = received[9] + (received[10]<<8);
        int CNY_50 = received[11] + (received[12]<<8);
        int CNY_20 = received[13] + (received[14]<<8);
        int CNY_10 = received[15] + (received[16]<<8);
        int CNY_5 = received[17] + (received[18]<<8);
        int CNY_1 = received[19] + (received[20]<<8);

        final String amount = " 收到  100x" + CNY_100 + " 50x" +
                CNY_50 + " 20x" + CNY_20 + " 10x" + CNY_10 + " 5x" + CNY_5 + " 1x" + CNY_1;
        LogPlus.e("read_thread",amount);

        if(CNY_100 > 0){
            DepositDetailBean depositDetailBean = new DepositDetailBean( Denomination_100_CNY,CNY_100);
            list.add(depositDetailBean);
        }
        if(CNY_50 > 0){
            DepositDetailBean depositDetailBean = new DepositDetailBean( Denomination_50_CNY,CNY_50);
            list.add(depositDetailBean);
        }
        if(CNY_20 > 0){
            DepositDetailBean depositDetailBean = new DepositDetailBean( Denomination_20_CNY,CNY_20);
            list.add(depositDetailBean);
        }
        if(CNY_10 > 0){
            DepositDetailBean depositDetailBean = new DepositDetailBean( Denomination_10_CNY,CNY_10);
            list.add(depositDetailBean);
        }
        if(CNY_5 > 0){
            DepositDetailBean depositDetailBean = new DepositDetailBean( Denomination_5_CNY,CNY_5);
            list.add(depositDetailBean);
        }
        if(CNY_1 > 0){
            DepositDetailBean depositDetailBean = new DepositDetailBean( Denomination_1_CNY,CNY_1);
            list.add(depositDetailBean);
        }

        long sum = CNY_100*Denomination_100_CNY +
                CNY_50*Denomination_50_CNY+
                CNY_20*Denomination_20_CNY+
                CNY_10*Denomination_10_CNY+
                CNY_5*Denomination_5_CNY+
                CNY_1*Denomination_1_CNY;
        long count = CNY_100+
                CNY_50+
                CNY_20+
                CNY_10+
                CNY_5+
                CNY_1;

        tvPresentSum.setText(sum+"");
        tvPresentCount.setText(count+"");

        if(adapter != null){
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public List<DepositDetailBean> getData() {
        return list;
    }

    @Override
    public int getView(ViewGroup parent, int viewType) {
        return R.layout.item_deposit_details;
    }

    @Override
    public <T> void bindView(RecyclerViewHolder holder, int position, T item) {
        View viewTop = holder.getView(R.id.viewTop);
        View viewBottom = holder.getView(R.id.viewBottom);
        if (position == 0) {
            viewTop.setVisibility(View.GONE);
        } else {
            viewTop.setVisibility(View.VISIBLE);
        }

        DepositDetailBean data = (DepositDetailBean) item;
        TextView tvDeposit = (TextView) holder.getView(R.id.tvDeposit);
        TextView tvCount = (TextView) holder.getView(R.id.tvCount);
        TextView tvSum = (TextView) holder.getView(R.id.tvSum);
        tvDeposit.setText(data.deposit +"");
        tvCount.setText(data.count +"");
        tvSum.setText(data.sum +"");

        if (position == list.size() - 1) {
            viewBottom.setVisibility(View.VISIBLE);
        }else {
            viewBottom.setVisibility(View.GONE);
        }

    }

    public static class DepositDetailBean{
        public int deposit ;
        public int count ;
        public int sum ;

        public DepositDetailBean(int deposit, int count) {
            this.deposit = deposit;
            this.count = count;
            this.sum = deposit*count;
        }
    }
}
