package com.whycools.customerservice.adapter;

import android.content.Context;
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
import com.whycools.customerservice.entity.RepairList;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.service.RepairListService;
import com.whycools.customerservice.utils.SharedPreferencesUtil;
import com.whycools.customerservice.utils.Zip;

import java.util.List;

/**
 * Created by user on 2017-07-26.
 */

public class RepairListAdapter extends BaseAdapter implements AppInterface {
    private List<RepairList> list = null;
    private LayoutInflater layoutInflater = null;
    private Context context;
    private ListView listView;
    private RepairListAdapter.ViewHolder viewHolder = null;
    public RepairListAdapter(Context context, List<RepairList> list) {
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
            viewHolder = new RepairListAdapter.ViewHolder();

            convertView = layoutInflater.inflate(R.layout.repair_list_item,parent,false);
            viewHolder.repair_list_item_tv_context = (TextView) convertView.findViewById(R.id.repair_list_item_tv_context);
            viewHolder.repair_list_item_bt = (Button) convertView.findViewById(R.id.repair_list_item_bt);
            viewHolder.repair_list_item_background = (LinearLayout) convertView.findViewById(R.id.repair_list_item_background);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RepairListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.repair_list_item_bt.setVisibility(View.GONE);
        viewHolder.repair_list_item_tv_context.setText("维修师傅："+list.get(position).getEngineer()+"\r\n维修日期："+list.get(position).getRepairdate()+"\r\n维修时间："+list.get(position).getRepairtime()+"\r\n单据编号"+list.get(position).getRepNo()+"\r\n电话号码："+list.get(position).getGsm()+"\r\n固定电话："+list.get(position).getPhoneno()+"\r\n保修内容："+list.get(position).getProblem()+"\r\n故障现象："+list.get(position).getSymptom()+"\r\n省："+list.get(position).getProvince()+"\r\n市："+list.get(position).getCityname()+"\r\n区："+list.get(position).getAreaname()+"\r\n地址："+list.get(position).getAddress()+"\r\n产品编号："+list.get(position).getGoodsno()+"\r\n产品名称："+list.get(position).getGoodsname());


        return convertView;
    }

    private  class ViewHolder {
        TextView repair_list_item_tv_context = null;
        Button repair_list_item_bt = null;
        LinearLayout repair_list_item_background=null;


    }


}