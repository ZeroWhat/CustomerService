package com.whycools.customerservice.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.whycools.customerservice.AppInterface;
import com.whycools.customerservice.entity.InstallList;
import com.whycools.customerservice.entity.RepairList;
import com.whycools.customerservice.net.RequestData;
import com.whycools.customerservice.service.InstallListService;
import com.whycools.customerservice.service.RepairListService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;

/**
 * Created by user on 2017-07-13.
 */

public class DataSync implements AppInterface{
    private static String TAG="同步按钮请求";
    private Context context;
    private String url;
    private InstallListService installlistservice;
    private RepairListService repairlistservice;
    public DataSync(Context context,String url){
        this.context=context;
        this.url=url;
        installlistservice=new InstallListService(context);
        repairlistservice=new RepairListService(context);
        TransparentDialogUtil.showLoadingMessage(context,"安装派工同步中...",true);
        InstallListThread.start();

    }

    public Thread InstallListThread=new Thread(){
        @Override
        public void run() {
            super.run();
            String userid="getInstallList4gun  "+SharedPreferencesUtil.getData(context,"USERID","").toString();
            Log.i("维修派工清单url", "run: --------------压缩"+userid);
            String installlist_url = url + "rent/rProxy.jsp?deviceid=" + "&s=" + Zip.compress(userid);
            Log.i("维修派工清单url", "run: --------------"+installlist_url);
            WriteLog.contentToTxt(context,"安装清单请求连接userid="+userid+"\r\n"+installlist_url);//异常写入文档
            String result = RequestData.getResult(installlist_url);
            WriteLog.contentToTxt(context,"安装清单请求数据结果"+result);
            Log.i(TAG, "安装清单请求数据结果"+result);

            if (result.length()<15){
                installlistservice.clearInstallList();//清除数据库表中的数据
                result="数据请求失败，请重新请求";
//                Log.i(TAG, "数据请求失败，请重新请求");
            }else{
                installlistservice.clearInstallList();//清除数据库表中的数据

                try{

                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);
                        String InstallEngineer = data.has("InstallEngineer") ? data.getString("InstallEngineer") : "";//
                        String installdate = data.has("installdate") ? data.getString("installdate") : "";//
                        Log.i(TAG, "installdate: "+installdate);
                        String installtime = data.has("installtime") ? data.getString("installtime") : "";//
                        String orderno = data.has("orderno") ? data.getString("orderno") : "";    //
                        Log.i(TAG, "orderno: "+orderno);
                        String gsm = data.has("gsm") ? data.getString("gsm") : "";//""
                        Log.i(TAG, "gsm: "+gsm);
                        String name = data.has("name") ? data.getString("name") : "";//""
                        Log.i(TAG, "name: "+name);
                        String memo = data.has("memo") ? data.getString("memo") : "";//""
                        Log.i(TAG, "memo: "+memo);
                        String phoneno = data.has("phoneno") ? data.getString("phoneno") : "";//
                        Log.i(TAG, "phoneno: "+phoneno);
                        String province = data.has("province") ? data.getString("province") : "";//
                        Log.i(TAG, "province: "+province);
                        String cityname = data.has("cityname") ? data.getString("cityname") : "";//
                        String areaname = data.has("areaname") ? data.getString("areaname") : "";//
                        String address = data.has("address") ? data.getString("address") : "";//
                        String goodsno = data.has("goodsno") ? data.getString("goodsno") : "";//
                        String goodsname = data.has("goodsname") ? data.getString("goodsname") : "";//
                        String qty = data.has("qty") ? data.getString("qty") : "";//-1
                        Log.i(TAG, "qty: "+qty);
                        String shipstateId = data.has("shipstateId") ? data.getString("shipstateId") : "";//
                        Log.i(TAG, "shipstateId:------------------------ "+shipstateId);
                        String installstateId = data.has("installstateId") ? data.getString("installstateId") : "";//


                        Log.i(TAG, "installstateId: "+installstateId);
                        InstallList installlist=new InstallList();
                        installlist.setInstallEngineer(InstallEngineer);
                        installlist.setInstalldate(installdate);
                        installlist.setInstalltime(installtime);
                        installlist.setOrderno(orderno);
                        installlist.setGsm(gsm);
                        installlist.setName(name);
                        installlist.setMemo(memo);
                        installlist.setPhoneno(phoneno);
                        installlist.setProvince(province);
                        installlist.setCityname(cityname);
                        installlist.setAreaname(areaname);
                        installlist.setAddress(address);
                        installlist.setGoodsno(goodsno);
                        installlist.setGoodsname(goodsname);
                        installlist.setQty(qty);
                        installlist.setShipstateId(shipstateId);
                        installlist.setInstallstateId(installstateId);

                        installlistservice.addInstallList(installlist);


                    }
                    Log.i(TAG, "数据请求成功添加数据");
                    result="数据请求成功";
                }catch (Exception e){
                    Log.i(TAG, "错误: "+e.getMessage());
                }
            }
            Message msg=new Message();
            msg.what=ONE;
            Bundle bundle=new Bundle();
            bundle.putString("InstallList",result);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ONE:
                    TransparentDialogUtil.dismiss();
                    msg.getData().getString("InstallList");
                    Log.i("派工清单", "handleMessage: ------"+msg.getData().getString("InstallList"));
//                    Toast.makeText(context, ""+msg.getData().getString("InstallList"), Toast.LENGTH_LONG).show();
                    TransparentDialogUtil.showLoadingMessage(context,"维修派工同步中...",true);
                    RepairListThred.start();
                    break;
                case TWO:
                    TransparentDialogUtil.dismiss();
                    msg.getData().getString("RepairList");
                    Log.i("维修清单", "handleMessage: ------"+msg.getData().getString("RepairList"));
//                    Toast.makeText(context, ""+msg.getData().getString("RepairList"), Toast.LENGTH_LONG).show();
                    sendjlCircle();
                    break;
            }
        }
    };

    //维修
    private Thread RepairListThred=new Thread(){
        @Override
        public void run() {
            super.run();

            String userid="getRepairList4gun  "+SharedPreferencesUtil.getData(context,"USERID","").toString();
            Log.i("维修派工清单url", "run: --------------压缩"+userid);
            String repairlist_url = url + "rent/rProxy.jsp?deviceid=" + "&s=" + Zip.compress(userid);
            Log.i("维修派工清单url", "run: --------------"+repairlist_url);
            WriteLog.contentToTxt(context,"维修清单请求连接userid="+userid+"\r\n"+repairlist_url);//异常写入文档
            String result = RequestData.getResult(repairlist_url);
            WriteLog.contentToTxt(context,"维修清单请求数据结果"+result);
            Log.i(TAG, "维修清单请求数据结果"+result);
            if (result.length()<15){
                repairlistservice.clearRepairList();//清除数据库表中的数据
                result="数据请求失败，请重新请求";
//                Log.i(TAG, "数据请求失败，请重新请求");
            }else{
                repairlistservice.clearRepairList();//清除数据库表中的数据

                try{

                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray("results");


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);
                        String Engineer = data.has("Engineer") ? data.getString("Engineer") : "";//
                        String repairdate = data.has("repairdate") ? data.getString("repairdate") : "";//
                        Log.i(TAG, "repairdate: "+repairdate);
                        String repairtime = data.has("repairtime") ? data.getString("repairtime") : "";//
                        String RepNo = data.has("RepNo") ? data.getString("RepNo") : "";    //
                        Log.i(TAG, "RepNo: "+RepNo);
                        String gsm = data.has("gsm") ? data.getString("gsm") : "";//""
                        Log.i(TAG, "gsm: "+gsm);
                        String name = data.has("name") ? data.getString("name") : "";//""
                        Log.i(TAG, "name: "+name);
                        String memo = data.has("memo") ? data.getString("memo") : "";//""
                        Log.i(TAG, "memo: "+memo);
                        String phoneno = data.has("phoneno") ? data.getString("phoneno") : "";//
                        Log.i(TAG, "phoneno: "+phoneno);
                        String problem = data.has("problem") ? data.getString("problem") : "";//
                        Log.i(TAG, "problem: "+problem);
                        String symptom = data.has("symptom") ? data.getString("symptom") : "";//
                        String province = data.has("province") ? data.getString("province") : "";//
                        String cityname = data.has("cityname") ? data.getString("cityname") : "";//
                        String areaname = data.has("areaname") ? data.getString("areaname") : "";//
                        String address = data.has("address") ? data.getString("address") : "";//
                        String goodsno = data.has("goodsno") ? data.getString("goodsno") : "";//-1
                        Log.i(TAG, "goodsno: "+goodsno);
                        String goodsname = data.has("goodsname") ? data.getString("goodsname") : "";//
                        String stateId = data.has("stateId") ? data.getString("stateId") : "";//

                        RepairList repairlist = new RepairList();

                        repairlist.setEngineer(Engineer);
                        repairlist.setRepairdate(repairdate);
                        repairlist.setRepairtime(repairtime);
                        repairlist.setRepNo(RepNo);
                        repairlist.setGsm(gsm);
                        repairlist.setName(name);
                        repairlist.setMemo(memo);
                        repairlist.setPhoneno(phoneno);
                        repairlist.setProblem(problem);
                        repairlist.setSymptom(symptom);
                        repairlist.setProvince(province);
                        repairlist.setCityname(cityname);
                        repairlist.setAreaname(areaname);
                        repairlist.setAddress(address);
                        repairlist.setGoodsno(goodsno);
                        repairlist.setGoodsname(goodsname);
                        repairlist.setStateId(stateId);

                        repairlistservice.addRepairList(repairlist);


                    }
                    Log.i(TAG, "数据请求成功添加数据");
                    result="数据请求成功";
                }catch (Exception e){
                    Log.i(TAG, "错误: "+e.getMessage());
                }
            }
            Message msg=new Message();
            msg.what=TWO;
            Bundle bundle=new Bundle();
            bundle.putString("RepairList",result);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };

    /**
     * 上传错误的log日志
     */
    private void sendjlCircle( ) {
        String getserialnumber="ae01";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (tm.getDeviceId()!=null&&!tm.getDeviceId().equals("0")){

            getserialnumber= tm.getDeviceId();

        }else{
            getserialnumber= "ae01";
        }
        String fileNamePath= filePath +  getserialnumber+ DateUtile .date2String(new Date(),"yyyyMMdd") + ".log";
        File file= new File(fileNamePath);
        String lid="1c827923-620b-47be-a8c7-7b34d87d705f";
        RequestParams params = new RequestParams();
        params.addBodyParameter("attach", file, "txt");
        String url_serveraddress= SharedPreferencesUtil.getData(context,"ServerAddress",SERVERDDRESS).toString();
        //http://10.10.10.2:89/rent/simpleUpload
        String url=url_serveraddress+"update/simpleUpload.jsp?path=applog";
        uploadMethod(params,url);
    }
    private void uploadMethod(RequestParams params, String uploadHost) {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, uploadHost, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(
                            ResponseInfo<String> responseInfo) {
                        //上传成功
                        TransparentDialogUtil.showSuccessMessage(context,"日志上传成功");

                    }

                    @Override
                    public void onFailure(HttpException error,
                                          String msg) {
                        //上传失败
                        TransparentDialogUtil.showSuccessMessage(context,"日志上传失败");
                    }
                });

    }



}
