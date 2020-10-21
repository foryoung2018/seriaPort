package com.licheedev.serialtool.activity.deposit;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.dapter.BaseRecyclerAdapter;
import com.licheedev.serialtool.activity.dapter.RecyclerViewHolder;
import com.licheedev.serialtool.activity.manage.SetManageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 存款记录
 */
public class SelectDepositActivity extends BaseActivity implements BaseRecyclerAdapter.Delegate {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private BaseRecyclerAdapter adapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_record;
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

    @OnClick({R.id.btnBack, R.id.btnUpload, R.id.btLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                startActivity(new Intent(this, PaperCurrencyDepositActivity.class));
                break;
            case R.id.btnUpload:
                startActivity(new Intent(this, OtherDepositActivity.class));
                break;
            case R.id.btLogout:
               // startActivity(new Intent(this, SetManageActivity.class));
                startActivity(new Intent(this, DepositRecordActivity.class));
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

    }
}
