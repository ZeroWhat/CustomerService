package com.whycools.customerservice.utils;

import android.content.Context;
import android.telephony.TelephonyManager;


import com.whycools.customerservice.AppInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by QL on 2016-10-14.
 */
public class WriteLog implements AppInterface{
    /**
     * 创建文件
     * @param fileName
     * @return
     */
    public static boolean createFile(File fileName)throws Exception {
        boolean flag=false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 读TXT文件内容
     * @param fileName
     * @return
     */
    public static String readTxtFile(File fileName)throws Exception {
        String result=null;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            fileReader=new FileReader(fileName);
            bufferedReader=new BufferedReader(fileReader);
            try{
                String read=null;
                while((read=bufferedReader.readLine())!=null){
                    result=result+read+"\r\n";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        }
        System.out.println("读取出来的文件内容是："+"\r\n"+result);
        return result;
    }


    public static boolean writeTxtFile(String content, File fileName)throws Exception {
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
//   mm=new RandomAccessFile(fileName,"rw");
//   mm.writeBytes(content);
            flag=true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }



    public static void contentToTxt(Context context , String content) {
        String getserialnumber="ae01";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (tm.getDeviceId()!=null&&!tm.getDeviceId().equals("0")){

            getserialnumber= tm.getDeviceId();

        }else{
            getserialnumber= "ae01";
        }
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        //获取当前时间
        String date   =   formatter.format(curDate);
        String fileNamePath= filePath +  getserialnumber+ DateUtile .date2String(new Date(),"yyyyMMdd") + ".log";
        try {
            File f = new File(filePath);
            File f2 = new File(fileNamePath);
            if (!f.exists()) {
                f.mkdirs();// 目录不存在则创建
            }
            if(!f2.exists()){
                f2.createNewFile();//文件不存在择创建
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(f2, true)));
            out.write(date+content+"\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
             out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
