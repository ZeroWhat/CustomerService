package com.whycools.customerservice.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.util.Log;

import java.io.File;

/**
 * Created by user on 2017-07-10.
 */

public class UpdateApp {
    //下载app文件
    public static void downLoadApk(final Context context, final String url) {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = VersionUtil.getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(file,context);
                    pd.dismiss(); // 结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = -1;
                }
            }
        }.start();
    }

    // 安装apk
    protected static void installApk(File file,Context context) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file),  "application/vnd.android.package-archive");// 注意：android不可改为大写,Android
        context.startActivity(intent);
    }
}
