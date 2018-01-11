package com.whycools.customerservice.net;

import android.util.Log;

import com.whycools.customerservice.utils.Zip;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Zero on 2016-12-05.
 */

public class RequestData {

    //网络数据请求    返回 数据需要zip解码  否则为乱码
    public  static String getResult(String sqlurl){
        try {
            URL url = new URL(sqlurl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();//基于HTTP协议的连接对象
            conn.setConnectTimeout(5000);//请求超时时间 5s
            conn.setRequestMethod("GET");//请求方式
            String result="";
            Log.i("网络请求响应码", "getResult: "+conn.getResponseCode());
            if(conn.getResponseCode() == 200){//响应码==200 请求成功
                InputStream inputStream = conn.getInputStream();//得到输入流
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = inputStream.read(buffer)) != -1){
                    arrayOutputStream.write( buffer, 0, len);
                }
                inputStream.close();
                arrayOutputStream.close();
                byte[] newby = Zip.GZIPUncompress(arrayOutputStream.toByteArray());
                if (newby!=null){
                    result = new String(newby, "utf-8");
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
//            Log.i("网络请求数据错误",e.getMessage());
            return "";
        }
    }

    /**
     * get请求
     */
    public static String HttpGet(String URL) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse httpResp = httpClient.execute(httpGet);
            if(httpResp.getStatusLine().getStatusCode()==200){
                return EntityUtils.toString(httpResp.getEntity(),"UTF-8");
            } else {
                return "get请求失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }finally {
            httpClient.getConnectionManager().shutdown();//释放链接
        }
    }
}
