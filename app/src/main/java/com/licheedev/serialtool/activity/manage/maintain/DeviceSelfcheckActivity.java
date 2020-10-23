package com.licheedev.serialtool.activity.manage.maintain;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.activity.dapter.TabAdapter;
import com.licheedev.serialtool.activity.manage.maintain.fragment.SenserStatusFragment;
import com.licheedev.serialtool.activity.manage.maintain.fragment.ValveProofreadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备自检
 */
public class DeviceSelfcheckActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private TabAdapter tabAdapter;    //定义Tab 的adapter
    private List<Fragment> list_fragment; //定义要装fragment的列表
    private List<String> list_title;   //tab名称列表

    private SenserStatusFragment fragment1;
    private ValveProofreadFragment fragment2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_selfcheck;
    }

    @Override
    protected void initView() {
        super.initView();
        //初始化各fragment
        fragment1 = new SenserStatusFragment();
        fragment2 = new ValveProofreadFragment();
        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(fragment1);
        list_fragment.add(fragment2);

        list_title = new ArrayList<>();
        list_title.add(getResources().getString(R.string.senser_status));
        list_title.add(getResources().getString(R.string.valvejiaodui));
        //为TabLayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabAdapter = new TabAdapter(getSupportFragmentManager(), list_fragment, list_title);

        //viewpager加载adapter
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(3);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick({R.id.btnBack, R.id.btnRerfsh, R.id.btnjiaozhun, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnRerfsh: //刷新

                break;
            case R.id.btnjiaozhun: //校准

                break;
            case R.id.btnLogout: //注销

                break;
        }
    }


}
