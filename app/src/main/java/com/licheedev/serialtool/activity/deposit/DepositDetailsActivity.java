package com.licheedev.serialtool.activity.deposit;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.dapter.BaseRecyclerAdapter;
import com.licheedev.serialtool.activity.dapter.RecyclerViewHolder;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.dialog.CurrenySelectUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 存款明细
 */
public class DepositDetailsActivity extends BaseActivity implements BaseRecyclerAdapter.Delegate {

    @BindView(R.id.textView)
    TextView tvTitle;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private Dialog overDepositDialogdialog;

    private BaseRecyclerAdapter adapter;
    private List<Integer> list = new ArrayList<>();

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

        list.add(1);
        list.add(1);
        list.add(1);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btnOverDeposit, R.id.btnContiniu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOverDeposit:

                overDepositDialogdialog = CurrenySelectUtil.showOverDepositDialog(this, new PaperCurrencyDepositActivity.Callback() {
                    @Override
                    public void onDialogClick(int which, Dialog dialog) {
                       finish();
                    }
                });

                break;
            case R.id.btnContiniu:

                break;
        }
    }

    @Override
    public List<Integer> getData() {
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

        if (position == list.size() - 1) {
            viewBottom.setVisibility(View.VISIBLE);
        }else {
            viewBottom.setVisibility(View.GONE);
        }

    }
}
