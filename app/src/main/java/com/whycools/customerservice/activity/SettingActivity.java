package com.whycools.customerservice.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.whycools.customerservice.R;
import com.whycools.customerservice.base.BaseActivity;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.utils.DateUtile;
import com.whycools.customerservice.utils.SharedPreferencesUtil;
import com.whycools.customerservice.utils.TransparentDialogUtil;
import com.whycools.customerservice.utils.WriteLog;
import com.whycools.customerservice.utils.Zip;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置页面
 * Created by user on 2017-07-10.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.layout_set_back)
    LinearLayout layoutSetBack;//返回
    @BindView(R.id.setting_et_server_address)
    EditText settingEtServerAddress;//服务器输入框
    @BindView(R.id.setting_bt_server_address)
    Button settingBtServerAddress;//设置按钮
    @BindView(R.id.sp_warehouse)
    Spinner spWarehouse;
    @BindView(R.id.setting_sp_equipment)
    Spinner settingSpEquipment;
    @BindView(R.id.setting_cb_scan)
    CheckBox settingCbScan;//扫描枪选择框
    @BindView(R.id.setting_bt_uplog)
    Button settingBtUplog;//日志上传按钮
    @BindView(R.id.setting_bt_datareset)
    Button settingBtDatareset;//数据重置按钮
    @BindView(R.id.setting_bt_finish)
    Button settingBtFinish;//完成按钮
    @BindView(R.id.setting_bt_text)
    Button settingBtText;
    @BindView(R.id.bt_clerar)
    Button btClerar;//一键加速按钮
    @BindView(R.id.setting_bt_getpassword)
    Button btGetpassword;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {
        settingBtServerAddress.setOnClickListener(this);
        settingBtFinish.setOnClickListener(this);
        layoutSetBack.setOnClickListener(this);
        settingBtUplog.setOnClickListener(this);
        btGetpassword.setOnClickListener(this);

    }

    @Override
    public void initData() {
        settingEtServerAddress.setText(SharedPreferencesUtil.getData(mContext,"ServerAddress", SERVERDDRESS)+"");
        settingEtServerAddress.setSelection(settingEtServerAddress.getText().length());//将光标移至文字末尾

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_bt_server_address://服务器设置按钮
                String server_address=	settingEtServerAddress.getText().toString();

                if (server_address.endsWith("/")){
                    SharedPreferencesUtil.saveData(mContext,"ServerAddress",server_address);
                    Toast.makeText(SettingActivity.this, SETTING_SUCCES, Toast.LENGTH_SHORT).show();
                }else{
                    TransparentDialogUtil.showErrorMessage(mContext,SERVICE_ADDRESS_FORMAT);
                }
                break;
            case R.id.setting_bt_finish://完成按钮
                finish();
                break;
            case R.id.layout_set_back:
               finish();
                break;
            case R.id.setting_bt_uplog:
                if (!isNetworkAvailable(mContext)){
                    TransparentDialogUtil.showErrorMessage(mContext,NET_NOUSE);
                    return;
                }
                upLog();
                break;
            case R.id.setting_bt_getpassword:

                countDown();

                getPassword();
                break;
        }

    }
    private void upLog(){
        File file= new File(filePath+getserialnumber()+ DateUtile
                .date2String(new Date(), "yyyyMMdd") + ".log");
        RequestParams params = new RequestParams();
        params.addBodyParameter("attach", file, "txt");
        String url_serveraddress= SharedPreferencesUtil.getData(SettingActivity.this,"ServerAddress",SERVERDDRESS).toString();
        String url=url_serveraddress+"update/simpleUpload.jsp?path=applog";
        upFile(params,url);
    }
    private void upFile(RequestParams params, String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        //开始上传
                        TransparentDialogUtil.showLoadingMessage(SettingActivity.this, STARTUP, true);
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        //上传中
                        TransparentDialogUtil.showLoadingMessage(SettingActivity.this, UP_LOADING, true);
                    }

                    @Override
                    public void onSuccess(
                            ResponseInfo<String> responseInfo) {
                        //上传成功
                        TransparentDialogUtil.showSuccessMessage(SettingActivity.this, UP_SUCCES);

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        //上传失败
                        TransparentDialogUtil.showSuccessMessage(SettingActivity.this, UP_FAIL);
                    }
                });
    }

    public void getPassword(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                String username = SharedPreferencesUtil.getData(SettingActivity.this, "USERNAME", "").toString();
                String ServerAddress = SharedPreferencesUtil.getData(SettingActivity.this, "ServerAddress", SERVERDDRESS).toString();
                String url = ServerAddress + "whycools/changePassword.jsp?s=" + Zip.compress("ChangePassword '" + username + "','',''");
                WriteLog.contentToTxt(SettingActivity.this, "申请密码的连接=" + username + "\r\n" + url);//异常写入文档
                String result = RequestData.getResult(url);
                WriteLog.contentToTxt(SettingActivity.this, "申请密码返回结果" + result);
                handler.sendMessage(new Message());


            }
        }.start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(SettingActivity.this, "请不要重复请求，密码将以短信的方式发送到你的手机，请查看", Toast.LENGTH_LONG).show();
        }
    };
    public void countDown(){
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btGetpassword.setEnabled(false);
                btGetpassword.setText("已发送(" + millisUntilFinished / 1000 + ")");

            }

            @Override
            public void onFinish() {
                btGetpassword.setEnabled(true);
                btGetpassword.setText("没有收到重新获取");

            }
        }.start();

    }
}
