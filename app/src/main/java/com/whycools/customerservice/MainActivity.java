package com.whycools.customerservice;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.whycools.customerservice.activity.MenuActivity;
import com.whycools.customerservice.activity.SettingActivity;
import com.whycools.customerservice.base.BaseActivity;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.utils.DataSync;
import com.whycools.customerservice.utils.MyToast;
import com.whycools.customerservice.utils.SharedPreferencesUtil;
import com.whycools.customerservice.utils.TransparentDialogUtil;
import com.whycools.customerservice.utils.UpdateApp;
import com.whycools.customerservice.utils.VersionUtil;
import com.whycools.customerservice.utils.WriteLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
//登录页面
public class MainActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.main_tv_title)//title
    TextView mainTvTitle;
    @BindView(R.id.main_et_username)//用户名输入框
    EditText mainEtUsername;
    @BindView(R.id.main_ed_password)//密码输入框
    EditText mainEdPassword;
    @BindView(R.id.main_cb_remember_password)//记住密码选择框
    CheckBox mainCbRememberPassword;
    @BindView(R.id.main_bt_login)//登录按钮
    Button mainBtLogin;
    @BindView(R.id.main_bt_toupdate)//更新按钮
    Button mainBtToupdate;
    @BindView(R.id.main_bt_setting)//设置按钮
    Button mainBtSetting;

    private String url;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {
        mainBtLogin.setOnClickListener(this);
        mainBtSetting.setOnClickListener(this);
        mainBtToupdate.setOnClickListener(this);


    }

    @Override
    public void initData() {
        mainCbRememberPassword.setChecked(true);
        url= SharedPreferencesUtil.getData(mContext,"ServerAddress", SERVERDDRESS)+"";
        mainEtUsername.setText(SharedPreferencesUtil.getData(MainActivity.this,"USERNAME","").toString());
        mainEdPassword.setText(SharedPreferencesUtil.getData(MainActivity.this,"PASSWORD","").toString());
        mainEtUsername.setSelection(mainEtUsername.getText().length());
        mainEdPassword.setSelection(mainEdPassword.getText().length());
        mainTvTitle.setText("W"+getVersionName()+"."+getVersionCode());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_bt_login://登录页面
                if (isNetworkAvailable(MainActivity.this)){
                    login(mainEtUsername.getText().toString(),mainEdPassword.getText().toString());
                }else{
                    TransparentDialogUtil.showErrorMessage(MainActivity.this,NET_NOUSE);
                }
                break;
            case R.id.main_bt_toupdate:
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //申请权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    Toast.makeText(MainActivity.this, "请允许权限进行下载安装", Toast.LENGTH_SHORT).show();

                }else{
                    if (isNetworkAvailable(MainActivity.this)) {
                        updata();
                    }else{
                        TransparentDialogUtil.showErrorMessage(MainActivity.this,NET_NOUSE);
                    }
                }

                break;
            case R.id.main_bt_setting://跳转到设置页面
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }

    }
    private void login(final String username, String password){
        if (username.length()==0){
            MyToast.showToast_CENTER(MainActivity.this,MAIN_ET_USERNAME);
            return;
        }
        if (password.length()==0){
            MyToast.showToast_CENTER(MainActivity.this,MAIN_ED_PASSWORD);
            return;
        }
        SharedPreferencesUtil.saveData(MainActivity.this,"USERNAME",username);
        if (mainCbRememberPassword.isChecked()){
            SharedPreferencesUtil.saveData(mContext,"PASSWORD",password);
        }else{
            SharedPreferencesUtil.saveData(mContext,"PASSWORD","");
        }
        TransparentDialogUtil.showLoadingMessage(MainActivity.this,LOGIN_LOADING,false);
        RequestParams params = new RequestParams();
        params.addBodyParameter("user", username);
        params.addBodyParameter("pwd", password);
        String login_url=url+"whycools/getAccessRight.jsp?";
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, login_url,params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                                Log.i(TAG, "responseInfo: "+responseInfo.result);
                        TransparentDialogUtil.dismiss();
                        if (responseInfo.result!=null&&responseInfo.result.length()!=0){
                          // MyToast.showToast(MainActivity.this,LOGIN_SUCCES);
                            Toast.makeText(MainActivity.this, LOGIN_SUCCES, Toast.LENGTH_SHORT).show();
                            SharedPreferencesUtil.saveData(mContext,"USERID",responseInfo.result);
                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                            //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
                                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE)) {
                                   // Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
                                }
                                //申请权限
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                            } else {
                             //   Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                                WriteLog.contentToTxt(MainActivity.this,"版本号：W"+getVersionName()+getVersionCode()+"\r\n用户名"+username+"\r\n用户登录userid:"+responseInfo.result);

                            }

                        }else{
                            TransparentDialogUtil.showErrorMessage(MainActivity.this,USERNAME_PASSWORD_ERRO);

                        }

                    }
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        TransparentDialogUtil.showErrorMessage(MainActivity.this,LOGIN_FAIL);
                    }
                });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        url= SharedPreferencesUtil.getData(mContext,"ServerAddress", SERVERDDRESS)+"";
    }

    public  void  updata(){
        TransparentDialogUtil.showLoadingMessage(MainActivity.this,"正在连接中...",false);
        new Thread(){
            @Override
            public void run() {
                super.run();
                String url= SharedPreferencesUtil.getData(mContext,"ServerAddress",SERVERDDRESS).toString()+"whycools/getFileVersion.jsp?filename=CustomerService.apk";
                Log.i(TAG, "版本号的接口: "+url);
                String code= RequestData.HttpGet(url);
                Log.i(TAG, "版本号code: "+replaceBlank(code));
                if (replaceBlank(code).length()>0&&isNumeric(replaceBlank(code))){
                    Message msg=new Message();
                    msg.what=8;
                    Bundle bundle=new Bundle();
                    bundle.putString("code",replaceBlank(code));
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }else{
                    Message msg=new Message();
                    msg.what=9;
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 7:
                    TransparentDialogUtil.dismiss();
                    String reviseText=msg.getData().getString("reviseText");
                    showUpdataDialog(reviseText);
                    break;

                case 8:
                    int code=Integer.valueOf(msg.getData().getString("code"));
                    if(code<=getVersionCode( )){
                        TransparentDialogUtil.showInfoMessage(MainActivity.this,"已经是最新版本了");
                    }else{

                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                String url= SharedPreferencesUtil.getData(mContext,"ServerAddress",SERVERDDRESS).toString()+"whycools/getFileVersionDetailJson.jsp?filename=CustomerService.apk";
                                Log.i(TAG, "更新接口run: "+url);
                                String updateresult=RequestData.HttpGet(url);
                                Log.i(TAG, "updateresult: "+updateresult);
                                String reviseText="";
                                try {
                                    JSONObject obj=new JSONObject(updateresult);
                                    JSONArray array=obj.getJSONArray("results");
                                    Log.i(TAG, "array个数: "+array.length());
                                    for (int i = 0; i <1 ; i++) {
                                        JSONObject obj2=array.getJSONObject(i);
                                        reviseText=obj2.getString("reviseText");
                                        Log.i(TAG, "run: "+reviseText);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Message msg=new Message();
                                msg.what=7;
                                Bundle bundle=new Bundle();
                                bundle.putString("reviseText",reviseText);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                            }
                        }.start();

                    }

                    break;
                case 9:
                    TransparentDialogUtil.showErrorMessage(MainActivity.this,"更新失败");
                    break;
            }
        }
    };
    //更新对话框
    protected void showUpdataDialog(String reviseText) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("发现新版本");
        builer.setMessage(reviseText);
        // 当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Log.i(TAG,"下载apk,更新");
                        downLoadApk();
                    }
                });
        // 当点取消按钮时进行登录
        builer.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        LoginMain();
                    }
                });
        AlertDialog  dialog = builer.create();
        dialog.show();
    }
    //下载app文件
    public void downLoadApk() {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    String url_serveraddress= SharedPreferencesUtil.getData(mContext,"ServerAddress", SERVERDDRESS)+"";
                    String url=url_serveraddress+"update/CustomerService.apk";
                    Log.i(TAG, "版本更新url: "+url);
                    File file = VersionUtil.getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = -1;
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file),  "application/vnd.android.package-archive");// 注意：android不可改为大写,Android
        startActivity(intent);
    }

}
