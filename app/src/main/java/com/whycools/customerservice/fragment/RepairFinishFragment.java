package com.whycools.customerservice.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whycools.customerservice.AppInterface;
import com.whycools.customerservice.R;
import com.whycools.customerservice.base.BaseFragment;
import com.whycools.customerservice.entity.RepairList;
import com.whycools.customerservice.service.RepairListService;

import java.util.List;

/**
 * Created by user on 2017-08-01.
 */

public class RepairFinishFragment extends BaseFragment {
    private static String TAG="维修完成";



    private RepairListAdapter adapter;
    private RepairListService repairlistservice;

    private ListView fragment_installnohander_lv;
    private boolean isFirst=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_installnohander, container, false);
        fragment_installnohander_lv=(ListView)view.findViewById(R.id.fragment_installnohander_lv);

        repairlistservice=new RepairListService(getActivity());
        List<RepairList> list=repairlistservice.getAllYRepairList();
        adapter=new RepairListAdapter(getActivity(),list);
        fragment_installnohander_lv.setAdapter(adapter);
        isFirst=true;


        return view;
    }

    @Override
    protected void lazyLoad() {
        if(isFirst){
            repairlistservice=new RepairListService(getActivity());
            List<RepairList> list=repairlistservice.getAllYRepairList();
            adapter=new RepairListAdapter(getActivity(),list);
            fragment_installnohander_lv.setAdapter(adapter);

        }


    }



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
                viewHolder.repair_bt_copy = (Button) convertView.findViewById(R.id.repair_bt_copy);
                viewHolder.repair_list_tv1 = (TextView) convertView.findViewById(R.id.repair_list_tv1);
                viewHolder.repair_list_tv1_1 = (TextView) convertView.findViewById(R.id.repair_list_tv1_1);
                viewHolder.repair_list_tv2 = (TextView) convertView.findViewById(R.id.repair_list_tv2);
                viewHolder.repair_list_tv2_2 = (TextView) convertView.findViewById(R.id.repair_list_tv2_2);
                viewHolder.repair_list_tv_phone = (Button) convertView.findViewById(R.id.repair_list_tv_phone);
                viewHolder.repair_list_item_bt = (Button) convertView.findViewById(R.id.repair_list_item_bt);
                viewHolder.repair_list_item_nbt = (Button) convertView.findViewById(R.id.repair_list_item_nbt);
                viewHolder.repair_list_item_background = (LinearLayout) convertView.findViewById(R.id.repair_list_item_background);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RepairListAdapter.ViewHolder) convertView.getTag();
            }
            viewHolder.repair_list_item_bt.setVisibility(View.GONE);
            viewHolder.repair_list_item_nbt.setVisibility(View.GONE);
            viewHolder.repair_list_item_tv_context.setText("维修师傅："+list.get(position).getEngineer()+"\r\n维修日期："+list.get(position).getRepairdate()+"\r\n单据编号"+list.get(position).getRepNo()+"\r\n保修内容："+list.get(position).getProblem()+"\r\n产品名称："+list.get(position).getGoodsname());
            viewHolder.repair_list_tv1.setText("联系电话：");
            viewHolder.repair_list_tv1_1.setText(list.get(position).getGsm());
            viewHolder.repair_list_tv2.setText("详细地址：");
            viewHolder.repair_list_tv2_2.setText(list.get(position).getProvince()+list.get(position).getCityname()+list.get(position).getAreaname()+list.get(position).getAddress());
            viewHolder.repair_list_tv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, "拨打电话", Toast.LENGTH_SHORT).show();
                    //用intent启动拨打电话
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission
                                .CALL_PHONE)) {
                            // Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
                        }
                        //申请权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 0);

                    } else {
                        //   Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                        final  String []  phonenumbers=list.get(position).getGsm().split("/");
                        if (phonenumbers.length==1){
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+list.get(position).getGsm()));
                            startActivity(intent);
                        }else{
                            //   Toast.makeText(ScanOnceActivity.this, "大于1个数据", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            //  builder.setIcon(R.drawable.transparent_info);
                            builder.setTitle("电话列表");
                            //    指定下拉列表的显示数据


                            builder.setItems(phonenumbers, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface,  int i) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phonenumbers[i]));
                                    startActivity(intent);


                                    //   Toast.makeText(ScanOnceActivity.this, ""+cities[i]+i, Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.show();
                        }

                    }
                }
            });
            viewHolder.repair_bt_copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(list.get(position).toString());
                    Toast.makeText(context, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }

        private  class ViewHolder {
            TextView repair_list_item_tv_context = null;
            Button repair_bt_copy = null;
            Button repair_list_item_bt = null;
            Button repair_list_item_nbt = null;
            LinearLayout repair_list_item_background=null;

            TextView repair_list_tv1=null;
            TextView repair_list_tv1_1=null;
            TextView repair_list_tv2=null;
            TextView repair_list_tv2_2=null;

            Button repair_list_tv_phone=null;


        }




    }
}

