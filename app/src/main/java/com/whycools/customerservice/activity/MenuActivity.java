package com.whycools.customerservice.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whycools.customerservice.AppInterface;
import com.whycools.customerservice.R;
import com.whycools.customerservice.base.BaseActivity;
import com.whycools.customerservice.entity.InstallList;
import com.whycools.customerservice.fragment.InstallNoHanderFragment;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.service.InstallListService;
import com.whycools.customerservice.utils.DataSync;
import com.whycools.customerservice.utils.SharedPreferencesUtil;
import com.whycools.customerservice.utils.Zip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017-07-10.
 */

public class MenuActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0; // 系统权限管理页面的参数
    @BindView(R.id.menu_bt_scan)//扫描按钮
            Button menuBtScan;
    @BindView(R.id.menu_bt_sync)//同步按钮
            Button menuBtSync;
    @BindView(R.id.menu_bt_scanup)//条码上传
            Button menuBtScanup;
    @BindView(R.id.menu_bt_back)//返回
            Button menuBtBack;
    @BindView(R.id.menu_bt_install)//安装
    Button menuBtInstall;
    @BindView(R.id.menu_bt_repair)//维修
    Button menuBtRepair;


    private String url;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {
        menuBtScan.setOnClickListener(this);
        menuBtSync.setOnClickListener(this);
        menuBtScanup.setOnClickListener(this);
        menuBtBack.setOnClickListener(this);
        menuBtInstall.setOnClickListener(this);
        menuBtRepair.setOnClickListener(this);

    }

    @Override
    public void initData() {
        url = SharedPreferencesUtil.getData(mContext, "ServerAddress", SERVERDDRESS) + "";

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_bt_scan:
                break;
            case R.id.menu_bt_sync:
                getData();
                break;
            case R.id.menu_bt_scanup:
                break;
            case R.id.menu_bt_back:
                finish();
                break;
            case R.id.menu_bt_install:
                startActivity(new Intent(this,InstallListActivity.class));
                break;
            case R.id.menu_bt_repair:
                startActivity(new Intent(this,RepairListActivity.class));
                break;
        }
    }

    private void getData(){
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                        .READ_PHONE_STATE)) {
                    Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
                }
                //申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_WRITE_EXTERNAL_STORAGE);

            } else {
                Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                new DataSync(MenuActivity.this, url);
            }


        }

    }




}
