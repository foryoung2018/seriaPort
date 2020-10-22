package com.licheedev.serialtool.activity.deposit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.dapter.BaseRecyclerAdapter;
import com.licheedev.serialtool.activity.dapter.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 存款记录
 */
public class DepositRecordActivity extends BaseActivity implements BaseRecyclerAdapter.Delegate {

    @BindView(R.id.textView)
    TextView tvTitle;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.btnUpload)
    Button btnallprint;
    @BindView(R.id.btLogout)
    Button btnUpload;


    private BaseRecyclerAdapter adapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_record;
    }

    @Override
    protected void initView() {
        super.initView();

        btnallprint.setText(getResources().getString(R.string.record_allprint));
        btnUpload.setText(getResources().getString(R.string.record_updaloddata));
        boolean iscurrent = getIntent().getBooleanExtra("iscurrent", false);
        if (iscurrent) {
            tvTitle.setText(getResources().getString(R.string.current_deposit));
        } else {
            tvTitle.setText(getResources().getString(R.string.print_deposit_record));
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new BaseRecyclerAdapter(this, this);
        mRecyclerView.setAdapter(adapter);

        list.add(1);
        list.add(1);
        list.add(1);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btnBack, R.id.btnUpload, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnUpload: //汇总打印
                break;
            case R.id.btLogout: //上传数据

                break;
        }
    }

    @Override
    public List<Integer> getData() {
        return list;
    }

    @Override
    public int getView(ViewGroup parent, int viewType) {
        return R.layout.item_deposit_record;
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
        } else {
            viewBottom.setVisibility(View.GONE);
        }

    }
}
