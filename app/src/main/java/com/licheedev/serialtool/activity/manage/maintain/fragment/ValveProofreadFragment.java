package com.licheedev.serialtool.activity.manage.maintain.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseFragment;
import com.licheedev.serialtool.activity.dapter.BaseRecyclerAdapter;
import com.licheedev.serialtool.activity.dapter.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/***
 * 阀值校对
 */
public class ValveProofreadFragment extends BaseFragment implements BaseRecyclerAdapter.Delegate {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private BaseRecyclerAdapter adapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    public int getFragmentViewById() {
        return R.layout.fragment_valve_proofread;
    }


    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        adapter = new BaseRecyclerAdapter(getActivity(), this);
        mRecyclerView.setAdapter(adapter);

        list.add(1);
        list.add(1);
        list.add(1);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<Integer> getData() {
        return list;
    }

    @Override
    public int getView(ViewGroup parent, int viewType) {
        return R.layout.item_valve_proofread;
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
