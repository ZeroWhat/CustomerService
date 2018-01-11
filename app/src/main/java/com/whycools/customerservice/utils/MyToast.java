package com.whycools.customerservice.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.whycools.customerservice.R;

/**
 * Created by user on 2017-07-10.
 */

public class MyToast {
    //自定义toast  显示位置 中间
    public static void  showToast_CENTER(Context context,String str){
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.red));
        textView.setTextSize(20);
        textView.setText(str);
        toast.setView(textView);
        toast.show();
    }
    public static void  showToast(Context context,String str){
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setTextSize(20);
        textView.setText(str);
        toast.setView(textView);
        toast.show();
    }
}
