package com.whycools.customerservice.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.whycools.customerservice.R;
import com.whycools.customerservice.adapter.FragmentAdapter;
import com.whycools.customerservice.base.BaseActivity;
import com.whycools.customerservice.fragment.RepairFinishFragment;
import com.whycools.customerservice.fragment.RepairNoFinishFragment;
import com.whycools.customerservice.fragment.RepairNoHanderFragment;
import com.whycools.customerservice.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修
 * Created by user on 2017-07-26.
 */

public class RepairListActivity extends BaseActivity implements View.OnClickListener {

    private RepairFinishFragment repairFinishFragment;//维修完成
    private RepairNoFinishFragment repairNoFinishFragment;//维修未完成
    private RepairNoHanderFragment repairNoHanderFragment;//维修待处理

    private FragmentAdapter mFragmentAdapter;//fragment是适配器

    private LinearLayout repair_list_layout_back;//返回按钮


    //button
    private Button repair_list_bt1;
    private Button repair_list_bt3;
    private Button repair_list_bt2;

    //view
    private View repair_list_view1;
    private View repair_list_view2;
    private View repair_list_view3;


    //viewpager
    private NoScrollViewPager repair_list_vp;

    private List<Fragment>  list=new ArrayList<Fragment>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_repair_list);

        repair_list_layout_back=(LinearLayout) findViewById(R.id.repair_list_layout_back);


        repair_list_bt1=(Button)findViewById(R.id.repair_list_bt1);
        repair_list_bt2=(Button)findViewById(R.id.repair_list_bt2);
        repair_list_bt3=(Button)findViewById(R.id.repair_list_bt3);


        repair_list_view1=(View) findViewById(R.id.repair_list_view1);
        repair_list_view2=(View) findViewById(R.id.repair_list_view2);
        repair_list_view3=(View) findViewById(R.id.repair_list_view3);

        repair_list_vp=(NoScrollViewPager) findViewById(R.id.repair_list_vp);

        repairFinishFragment=new RepairFinishFragment();
        repairNoFinishFragment=new RepairNoFinishFragment();
        repairNoHanderFragment=new RepairNoHanderFragment();
        list.add(repairNoHanderFragment);
        list.add(repairFinishFragment);
        list.add(repairNoFinishFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), list);
        repair_list_vp.setAdapter(mFragmentAdapter);
        repair_list_vp.setCurrentItem(0);

        repair_list_view1.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
        repair_list_view2.setBackgroundColor(this.getResources().getColor(R.color.wheat));
        repair_list_view3.setBackgroundColor(this.getResources().getColor(R.color.wheat));

        repair_list_bt1.setTextColor(this.getResources().getColor(R.color.lightseagreen));
        repair_list_bt2.setTextColor(this.getResources().getColor(R.color.wheat));
        repair_list_bt3.setTextColor(this.getResources().getColor(R.color.wheat));

    }

    @Override
    public void initListeners() {
        repair_list_layout_back.setOnClickListener(this);
        repair_list_bt1.setOnClickListener(this);
        repair_list_bt2.setOnClickListener(this);
        repair_list_bt3.setOnClickListener(this);


    }

    @Override
    public void initData() {
//        repairlistservice = new RepairListService(this);
//        List<RepairList> list = repairlistservice.getAllRepairList();
//        adapter = new RepairListAdapter(this, list);
//        adapter.setListView(repairListLv);
//        repairListLv.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.repair_list_layout_back:
                finish();
                break;
            case R.id.repair_list_bt1:
                repair_list_vp.setCurrentItem(0);
                repair_list_view1.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
                repair_list_view2.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                repair_list_view3.setBackgroundColor(this.getResources().getColor(R.color.wheat));

                repair_list_bt1.setTextColor(this.getResources().getColor(R.color.lightseagreen));
                repair_list_bt2.setTextColor(this.getResources().getColor(R.color.wheat));
                repair_list_bt3.setTextColor(this.getResources().getColor(R.color.wheat));

                break;
            case R.id.repair_list_bt2:
                repair_list_vp.setCurrentItem(1);
                repair_list_view1.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                repair_list_view2.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
                repair_list_view3.setBackgroundColor(this.getResources().getColor(R.color.wheat));

                repair_list_bt1.setTextColor(this.getResources().getColor(R.color.wheat));
                repair_list_bt2.setTextColor(this.getResources().getColor(R.color.lightseagreen));
                repair_list_bt3.setTextColor(this.getResources().getColor(R.color.wheat));

                break;
            case R.id.repair_list_bt3:
                repair_list_vp.setCurrentItem(2);
                repair_list_view1.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                repair_list_view2.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                repair_list_view3.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));

                repair_list_bt1.setTextColor(this.getResources().getColor(R.color.wheat));
                repair_list_bt2.setTextColor(this.getResources().getColor(R.color.wheat));
                repair_list_bt3.setTextColor(this.getResources().getColor(R.color.lightseagreen));

                break;
        }
    }
}
