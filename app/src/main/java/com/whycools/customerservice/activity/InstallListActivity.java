package com.whycools.customerservice.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.whycools.customerservice.R;
import com.whycools.customerservice.adapter.FragmentAdapter;
import com.whycools.customerservice.base.BaseActivity;
import com.whycools.customerservice.fragment.InstallFinishFragment;
import com.whycools.customerservice.fragment.InstallNoFinishFragment;
import com.whycools.customerservice.fragment.InstallNoHanderFragment;
import com.whycools.customerservice.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * 安装
 * Created by user on 2017-07-26.
 */

public class InstallListActivity extends BaseActivity implements View.OnClickListener {




    private InstallNoHanderFragment installNoHanderFragment;//安装待处理
    private InstallFinishFragment installFinishFragment;//安装完成
    private InstallNoFinishFragment installNoFinishFragment;//安装未完成
    private List<Fragment> list = new ArrayList<Fragment>();

    private FragmentAdapter mFragmentAdapter;//fragment是适配器

    private LinearLayout install_list_layout_back;

    //按钮
    private Button install_list_bt1;
    private Button install_list_bt2;
    private Button install_list_bt3;
    //view
    private View install_list_view1;
    private View install_list_view2;
    private View install_list_view3;


    //viewpager
    private   NoScrollViewPager install_list_vp;



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_install_list);
        install_list_layout_back=(LinearLayout)findViewById(R.id.install_list_layout_back);

        install_list_bt1=(Button)findViewById(R.id.install_list_bt1);
        install_list_bt2=(Button)findViewById(R.id.install_list_bt2);
        install_list_bt3=(Button)findViewById(R.id.install_list_bt3);



        install_list_view1=(View) findViewById(R.id.install_list_view1);
        install_list_view2=(View) findViewById(R.id.install_list_view2);
        install_list_view3=(View) findViewById(R.id.install_list_view3);

        install_list_vp=(NoScrollViewPager)findViewById(R.id.install_list_vp);




    }

    @Override
    public void initListeners() {
        install_list_layout_back.setOnClickListener(this);
        install_list_bt1.setOnClickListener(this);
        install_list_bt2.setOnClickListener(this);
        install_list_bt3.setOnClickListener(this);



    }

    @Override
    public void initData() {

        installNoHanderFragment = new InstallNoHanderFragment();
        installFinishFragment=new InstallFinishFragment();
        installNoFinishFragment=new InstallNoFinishFragment();
        list.add(installNoHanderFragment);
        list.add(installFinishFragment);
        list.add(installNoFinishFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), list);
        install_list_vp.setAdapter(mFragmentAdapter);
        install_list_vp.setCurrentItem(0);
        install_list_view1.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
        install_list_view2.setBackgroundColor(this.getResources().getColor(R.color.wheat));
        install_list_view3.setBackgroundColor(this.getResources().getColor(R.color.wheat));

        install_list_bt1.setTextColor(this.getResources().getColor(R.color.lightseagreen));
        install_list_bt2.setTextColor(this.getResources().getColor(R.color.wheat));
        install_list_bt3.setTextColor(this.getResources().getColor(R.color.wheat));



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.install_list_layout_back:
                finish();
                break;
            case R.id.install_list_bt1:
                install_list_vp.setCurrentItem(0);
                install_list_view1.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
                install_list_view2.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                install_list_view3.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                install_list_bt1.setTextColor(this.getResources().getColor(R.color.lightseagreen));
                install_list_bt2.setTextColor(this.getResources().getColor(R.color.wheat));
                install_list_bt3.setTextColor(this.getResources().getColor(R.color.wheat));
                break;
            case R.id.install_list_bt2:
                install_list_vp.setCurrentItem(1);

                install_list_view1.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                install_list_view2.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
                install_list_view3.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                install_list_bt1.setTextColor(this.getResources().getColor(R.color.wheat));
                install_list_bt2.setTextColor(this.getResources().getColor(R.color.lightseagreen));
                install_list_bt3.setTextColor(this.getResources().getColor(R.color.wheat));
                break;
            case R.id.install_list_bt3:
                install_list_vp.setCurrentItem(2);

                install_list_view1.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                install_list_view2.setBackgroundColor(this.getResources().getColor(R.color.wheat));
                install_list_view3.setBackgroundColor(this.getResources().getColor(R.color.lightseagreen));
                install_list_bt1.setTextColor(this.getResources().getColor(R.color.wheat));
                install_list_bt2.setTextColor(this.getResources().getColor(R.color.wheat));
                install_list_bt3.setTextColor(this.getResources().getColor(R.color.lightseagreen));
                break;


        }

    }


}
