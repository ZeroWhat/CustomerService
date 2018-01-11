package com.whycools.customerservice.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.whycools.customerservice.AppInterface;
import com.whycools.customerservice.MainActivity;
import com.whycools.customerservice.R;
import com.whycools.customerservice.base.BaseFragment;
import com.whycools.customerservice.entity.InstallList;
import com.whycools.customerservice.entity.RepairList;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.service.InstallListService;
import com.whycools.customerservice.utils.SharedPreferencesUtil;
import com.whycools.customerservice.utils.TransparentDialogUtil;
import com.whycools.customerservice.utils.WriteLog;
import com.whycools.customerservice.utils.Zip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-07-31.
 */

public class InstallNoHanderFragment extends BaseFragment implements OnItemClickListener {
    private static String TAG="安装未处理";


    private InstallListAdapter adapter;
    private InstallListService installListService;


    private ListView fragment_installnohander_lv;

    private boolean isFirst=false;

    private AlertView mAlertViewExt;
    private TextView make_result;//修理结果：完成
    private Spinner sp_billno_in;//内机编码
    private Spinner sp_billno_out;//外机编码
    private EditText handle_result;//处理方式
    private EditText money_number;//收费：30

    private InputMethodManager imm;
    private List<String> sp_list = new ArrayList<String>();
    private ArrayAdapter<String> sp_adapter;
    private String isfinisth="";
    private String Install_id="";
    private int list_position=0;
    private  List<InstallList> relist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_installnohander, container, false);

        fragment_installnohander_lv=(ListView)view.findViewById(R.id.fragment_installnohander_lv);

        installListService = new InstallListService(view.getContext());
        relist = installListService.getAllInstallList();
        installListService.findInstallList("98806428");
        Log.i(TAG, "initData: 所有数据的个数" + relist.size());

        Log.i(TAG, "initData: 移除过后的所有数据的个数" + relist.size());
        adapter = new InstallListAdapter(getActivity(), relist);
        adapter.setListView(fragment_installnohander_lv);
        fragment_installnohander_lv.setAdapter(adapter);
        isFirst=true;


        return view;
    }
    public void mDialog(String isfinish){
        //拓展窗口
        mAlertViewExt = new AlertView("提示", null, "取消", null, new String[]{"确认"}, getActivity(), AlertView.Style.Alert, this);
        ViewGroup extView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.alertext_form,null);
        make_result=(TextView) extView.findViewById(R.id.make_result);
        sp_billno_in=(Spinner) extView.findViewById(R.id.sp_billno_in);
        sp_billno_out=(Spinner) extView.findViewById(R.id.sp_billno_out);
        handle_result=(EditText) extView.findViewById(R.id.handle_result);
        money_number=(EditText) extView.findViewById(R.id.money_number);
        LinearLayout layout4=(LinearLayout)extView.findViewById(R.id.layout4);
        make_result.setText(isfinish);
        if (isfinish.equals("未完成")){
            layout4.setVisibility(View.GONE);
        }
        /*
        etName = (EditText) extView.findViewById(R.id.etName);
        etpassword = (EditText) extView.findViewById(R.id.etpassword);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen=imm.isActive();
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 120 :0);
                System.out.println(isOpen);
            }
        });
        etpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen=imm.isActive();
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 120 :0);
                System.out.println(isOpen);
            }
        });*/
        mSpinnrt( extView);
        mAlertViewExt.addExtView(extView);
    }
    private void getBarcodeData(final List<InstallList> listdata, final int position){

        sp_list.clear();
        //  sp_list.add("请选择编号");

        new Thread(){
            @Override
            public void run() {
                super.run();
                String result = RequestData.getResult("http://home.whycools.com:89/" + "rent/rProxy.jsp?deviceid=" + "&s=" +Zip.compress("getInstallInnerno '"+listdata.get(position).getOrderno()+"'"));

                Log.i(TAG, "请求的结果为---------: "+"http://home.whycools.com:89/" + "rent/rProxy.jsp?deviceid=" + "&s=" +Zip.compress("getInstallInnerno '"+listdata.get(position).getOrderno()+"'"));
                Log.i(TAG, "请求的结果为---------: "+result);
                Log.i(TAG, "请求的结果为---------: "+"getInstallInnerno '"+listdata.get(position).getOrderno()+"'");
                if (result.length()>10){
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(result);

                        JSONArray array = obj.getJSONArray("results");

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject data = array.getJSONObject(i);
                            String barcode = data.has("barcode") ? data.getString("barcode") : "";//
                            sp_list.add(barcode);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i(TAG, "----------------: "+position);
                Log.i(TAG, "----------------: "+listdata.get(position).getOrderno());

                Message msg=new Message();
                msg.what=1;
                Bundle bundle=new Bundle();
                bundle.putInt("index",position);
                msg.setData(bundle);
                handler.sendMessage(msg);


            }
        }.start();

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                mDialog(isfinisth);
                mAlertViewExt.show();
            }else if(msg.what==2){
                installListService = new InstallListService(getActivity());
                relist= installListService.getAllInstallList();
                Log.i(TAG, "initData: 所有数据的个数" + relist.size());

                Log.i(TAG, "initData: 移除过后的所有数据的个数" + relist.size());
                adapter = new InstallListAdapter(getActivity(), relist);
                adapter.setListView(fragment_installnohander_lv);
                fragment_installnohander_lv.setAdapter(adapter);

            }

        }
    };
    private String str_billno_in;
    private String str_billno_out;
    private void mSpinnrt(ViewGroup extView){
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项


        //  sp_billno_in = (Spinner)extView.findViewById(R.id.sp_billno_in);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        sp_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, sp_list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        sp_billno_in.setAdapter(sp_adapter);
        sp_billno_out.setAdapter(sp_adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        sp_billno_in.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getActivity(), ""+sp_adapter.getItem(i), Toast.LENGTH_SHORT).show();
                str_billno_in=sp_adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_billno_out.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), ""+sp_adapter.getItem(i), Toast.LENGTH_SHORT).show();
                str_billno_out=sp_adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void lazyLoad() {

        if(isFirst){
            installListService = new InstallListService(getActivity());
            List<InstallList> list  = installListService.getAllInstallList();
            Log.i(TAG, "initData: 所有数据的个数" + list.size());

            Log.i(TAG, "initData: 移除过后的所有数据的个数" + list.size());
            adapter = new InstallListAdapter(getActivity(), list);
            adapter.setListView(fragment_installnohander_lv);
            fragment_installnohander_lv.setAdapter(adapter);

        }


    }

    @Override
    public void onItemClick(Object o, int position) {
        if (position==0){
            String result="\"修理结果\": \""+make_result.getText()+"\",\"内机编号\": \""+str_billno_in+"\",\"外机编号\": \""+str_billno_out+"\",\"处理方式\": \""+handle_result.getText()+"\",\"收费\": \""+money_number.getText()+"\"";
            Log.i(TAG, "----------------------------: \""+result);
            Log.i(TAG, "----------------------------: "+"\"修理结果\": \""+make_result.getText()+"\",\"内机编号\": \""+str_billno_in+"\",\"外机编号\": \""+str_billno_out+"\",\"处理方式\": \""+handle_result.getText()+"\",\"收费\": \""+money_number.getText()+"\"");
         //   Toast.makeText(getActivity(), ""+result, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "--------------------------: "+make_result);
            Log.i(TAG, "------------------list_position: "+list_position);
            Log.i(TAG, "------------------getOrderno: "+relist.get(list_position).getOrderno());

            if (isfinisth.equals("完成")){
                updata(relist.get(list_position).getOrderno(), relist.get(list_position).getInstalldate(),"3",result);
            }else{
                updata(relist.get(list_position).getOrderno(), relist.get(list_position).getInstalldate(),"2",result);

            }
        }

    }


    public class InstallListAdapter extends BaseAdapter implements AppInterface {
        private List<InstallList> list = null;
        private LayoutInflater layoutInflater = null;
        private final Context context;
        private ListView listView;

        public InstallListAdapter(Context context, List<InstallList> list) {
            this.list = list;
            this.context = context;
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
            InstallListAdapter.ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new InstallListAdapter.ViewHolder();

                convertView = layoutInflater.inflate(R.layout.install_list_item, parent, false);
                viewHolder.install_list_item_tv_context = (TextView) convertView.findViewById(R.id.install_list_item_tv_context);
                viewHolder.install_bt_copy = (Button) convertView.findViewById(R.id.install_bt_copy);
                viewHolder.install_list_tv1 = (TextView) convertView.findViewById(R.id.install_list_tv1);
                viewHolder.install_list_tv1_1 = (TextView) convertView.findViewById(R.id.install_list_tv1_1);
                viewHolder.install_list_tv2 = (TextView) convertView.findViewById(R.id.install_list_tv2);
                viewHolder.install_list_tv2_2 = (TextView) convertView.findViewById(R.id.install_list_tv2_2);
                viewHolder.install_list_tv_phone = (Button) convertView.findViewById(R.id.install_list_tv_phone);
                viewHolder.install_list_item_bt = (Button) convertView.findViewById(R.id.install_list_item_bt);
                viewHolder.install_list_item_nbt = (Button) convertView.findViewById(R.id.install_list_item_nbt);
                viewHolder.install_list_item_background = (LinearLayout) convertView.findViewById(R.id.install_list_item_background);
                viewHolder.install_list_item_tv_isstate = (TextView) convertView.findViewById(R.id.install_list_item_tv_isstate);


                convertView.setTag(viewHolder);
            } else {
                viewHolder = (InstallListAdapter.ViewHolder) convertView.getTag();
            }

            if (list.get(position).getShipstateId().equals("3")){
                viewHolder.install_list_item_tv_isstate.setText("——————已送达——————");
                viewHolder.install_list_item_tv_isstate.setTextColor(context.getResources().getColor(R.color.green));
                Log.i(TAG, "<<<<<<<<<<<<>>>>>>>>>>>>: 已送达------");
            } else if (list.get(position).getShipstateId().equals("1")){
                viewHolder.install_list_item_tv_isstate.setText("——————已发车——————");
                viewHolder.install_list_item_tv_isstate.setTextColor(context.getResources().getColor(R.color.black));
                Log.i(TAG, "<<<<<<<<<<<<>>>>>>>>>>>>: 已发车");
            } else{
                viewHolder.install_list_item_tv_isstate.setText("——————未发车——————");
                viewHolder.install_list_item_tv_isstate.setTextColor(context.getResources().getColor(R.color.red));
                Log.i(TAG, "<<<<<<<<<<<<>>>>>>>>>>>>: 未发车>>>>>>");
            }
            viewHolder.install_list_item_tv_context.setText("安装师傅：" + list.get(position).getInstallEngineer() + "\r\n安装日期：" + list.get(position).getInstalldate() + "\r\n安装时间：" + list.get(position).getInstalltime() + "\r\n订单编号：" + list.get(position).getOrderno() + "\r\n机器编号：" + list.get(position).getGoodsno()+ "\r\n数量：" + list.get(position).getQty() + "\r\n机器名称：" + list.get(position).getGoodsname()+ "\r\n联系人：" + list.get(position).getName()+ "\r\n备注内容：" + list.get(position).getMemo() );
            viewHolder.install_list_tv1.setText("联系电话：");
            viewHolder.install_list_tv1_1.setText(list.get(position).getGsm());
            viewHolder.install_list_tv2.setText("详细地址：");
            viewHolder.install_list_tv2_2.setText( list.get(position).getProvince()  + list.get(position).getCityname() + list.get(position).getAreaname() + list.get(position).getAddress() );

            if (list.get(position).getInstallstateId().equals("3")) {
//            list.remove(position);
                viewHolder.install_list_item_bt.setText("已完成");
                viewHolder.install_list_item_background.setBackgroundColor(context.getResources().getColor(R.color.green));
            } else {
                viewHolder.install_list_item_bt.setText(R.string.finish);
                viewHolder.install_list_item_nbt.setText(R.string.nfinish);
                viewHolder.install_list_item_background.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            viewHolder.install_list_item_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isfinisth="完成";
                    getBarcodeData(list,position);
                    Install_id=list.get(position).get_id();
                    list_position=position;

                }
            });

            viewHolder.install_list_item_nbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isfinisth="未完成";
                    getBarcodeData(list,position);
                    Install_id=list.get(position).get_id();
                    list_position=position;

                }
            });
            viewHolder.install_list_tv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, "拨打电话", Toast.LENGTH_SHORT).show();
                    //用intent启动拨打电话
                    //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
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
            viewHolder.install_bt_copy.setOnClickListener(new View.OnClickListener() {
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

        private class ViewHolder {
            TextView install_list_item_tv_context = null;
            Button install_bt_copy = null;
            Button install_list_item_bt = null;
            Button install_list_item_nbt = null;
            LinearLayout install_list_item_background = null;

            TextView install_list_tv1=null;
            TextView install_list_tv1_1=null;
            TextView install_list_tv2=null;
            TextView install_list_tv2_2=null;
            Button install_list_tv_phone=null;
            TextView install_list_item_tv_isstate=null;


        }

        public void upView(int index,String state,String reason) {
            int visiblePosition = listView.getFirstVisiblePosition();

            View view = listView.getChildAt(index - visiblePosition);
            final InstallListAdapter.ViewHolder holderOne = new InstallListAdapter.ViewHolder();
            holderOne.install_list_item_bt = (Button) view.findViewById(R.id.install_list_item_bt);
            holderOne.install_list_item_background = (LinearLayout) view.findViewById(R.id.install_list_item_background);
            holderOne.install_list_item_background.setBackgroundColor(context.getResources().getColor(R.color.green));
            list.get(index).setInstallstateId(state);
            InstallListService installListService = new InstallListService(context);
            installListService.updateInstallList(state, list.get(index).get_id());
            updata(list.get(index).getOrderno(), list.get(index).getInstalldate(),state,reason);
            holderOne.install_list_item_bt.setText("已完成");
        }

        public void setListView(ListView listView) {
            this.listView = listView;
        }


        private void showAlertDialog(final int position) {
            final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
            dialog.setView(LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog, null));
            dialog.show();
            dialog.getWindow().setContentView(R.layout.alert_dialog);
            Button nofinish_bt_ok = (Button) dialog.findViewById(R.id.nofinish_bt_ok);
            Button nofinish_bt_no = (Button) dialog.findViewById(R.id.nofinish_bt_no);
            final EditText nofinish_ed = (EditText) dialog.findViewById(R.id.nofinish_ed);
            nofinish_bt_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String str = nofinish_ed.getText().toString();
                    if (str.length()==0) {
                        Toast.makeText(getActivity(), R.string.reason, Toast.LENGTH_SHORT).show();
                    } else {
                        upView(position,"2",str);
                        list.remove(position);
                        // 通过程序我们知道删除了，但是怎么刷新ListView呢？
                        // 只需要重新设置一下adapter
                        fragment_installnohander_lv.setAdapter(adapter);

                        dialog.dismiss();
                    }
                }
            });
            nofinish_bt_no.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
        }

    }
    public void updata(final String orderno, final String date,final String state,final String reason) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = SharedPreferencesUtil.getData(getActivity(), "ServerAddress", SERVERDDRESS).toString();
                String userid = SharedPreferencesUtil.getData(getActivity(), "USERID", "").toString();
                Log.i(TAG, "InstallDoneConfirm  " + userid + ", " + orderno + ", '" + date + "', "+state+", '"+reason+"'");
                String url_ = url + "rent/rProxy.jsp?deviceid=&s=" + Zip.compress("InstallDoneConfirm  " + userid + ", " + orderno + ", '" + date + "', "+state+", '"+reason+"'");
              //  Log.i("安装", "run:没有压缩的数据 " + "InstallDoneConfirm  " + userid + ", " + orderno + ", '" + date + "', 3, '已完工'");
                Log.i("安装", "run: -------------url" + url_);
                String resu = RequestData.getResult(url_);
                InstallListService installListService = new InstallListService(getActivity());
                installListService.updateInstallList(state,Install_id);
                Log.i(TAG, "Install_id-----------------Install_id: "+Install_id);
                Message msg=new Message();
                msg.what=2;
                handler.sendMessage(msg);


            }
        }.start();


    }


}
