package com.whycools.customerservice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.whycools.customerservice.AppInterface;
import com.whycools.customerservice.R;
import com.whycools.customerservice.entity.InstallList;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.service.InstallListService;
import com.whycools.customerservice.utils.SharedPreferencesUtil;
import com.whycools.customerservice.utils.Zip;

import java.util.List;

/**
 * Created by Zero on 2016-12-12.
 */

public class InstallListAdapter extends BaseAdapter implements AppInterface{
    private List<InstallList> list = null;
    private LayoutInflater layoutInflater = null;
    private Context context;
    private ListView listView;
    private InstallListAdapter.ViewHolder viewHolder = null;
    public InstallListAdapter(Context context, List<InstallList> list) {
        this.list = list;
        this.context=context;
        layoutInflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new InstallListAdapter.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.install_list_item,parent,false);
            viewHolder.install_list_item_tv_context = (TextView) convertView.findViewById(R.id.install_list_item_tv_context);
            viewHolder.install_list_item_bt = (Button) convertView.findViewById(R.id.install_list_item_bt);
            viewHolder.install_list_item_background = (LinearLayout) convertView.findViewById(R.id.install_list_item_background);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (InstallListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.install_list_item_bt.setVisibility(View.GONE);
        viewHolder.install_list_item_tv_context.setText("安装师傅："+list.get(position).getInstallEngineer()+"\r\n安装日期："+list.get(position).getInstalldate()+"\r\n安装时间："+list.get(position).getInstalltime()+"\r\n订单编号："+list.get(position).getOrderno()+"\r\n电话号码："+list.get(position).getGsm()+"\r\n:"+list.get(position).getPhoneno()+"\r\n省："+list.get(position).getProvince()+"\r\n市："+list.get(position).getCityname()+"\r\n区："+list.get(position).getAreaname()+"\r\n地址："+list.get(position).getAddress()+"\r\n机器编号："+list.get(position).getGoodsno()+"\r\n机器名称："+list.get(position).getGoodsname()+"\r\n数量："+list.get(position).getQty());
//


        return convertView;
    }

    private  class ViewHolder {
        TextView install_list_item_tv_context = null;
        Button install_list_item_bt = null;
        LinearLayout install_list_item_background=null;


    }


}

