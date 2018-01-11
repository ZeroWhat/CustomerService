package com.whycools.customerservice;

import android.os.Environment;

/**
 * Created by Zero on 2016-12-05.
 */

public interface AppInterface {
    String SERVERDDRESS="http://home.whycools.com:89/";
    String filePath= Environment.getExternalStorageDirectory() + "/CustomerService/"+"log/";
    String LOADATA = "正在加载中...";
    String LOCATION_URL="http://api.map.baidu.com/location/ip?ak=fWrY58aTZHQPhgOhqtCItxUiOsNfGf9P&coor=bd09ll";
    String BAIDU1="http://api.map.baidu.com/geocoder/v2/";
    String BAIDU2="http://api.map.baidu.com/routematrix/v2/driving?output=json&origins=";

    String NET_NOUSE = "没有网络能干啥，去设置中开启网络吧";

    String MAIN_ET_USERNAME="用户名不能为空";
    String MAIN_ED_PASSWORD="密码不能为空";
    String SETTING_SUCCES="设置成功";
    String SERVICE_ADDRESS_FORMAT="格式不正确，最后一位必须为‘/'";
    String LOGIN_SUCCES="登录成功";
    String USERNAME_PASSWORD_ERRO="用户名或密码错误";
    String LOGIN_FAIL="登录失败";
    String LOGIN_LOADING="正在登录中...";
    String STARTUP="开始上传";
    String UP_LOADING="上传中...";
    String UP_SUCCES="上传成功";
    String UP_FAIL="上传失败";

    int ONE=0x0001;
    int TWO=0x0002;
    int THREE=0x0003;
    int FOUR=0x0004;
    int FIVE=0x0005;
    int SIX=0x0006;
    int SWVEN=0x0007;
    int EIGHT=0x0008;
    int NINE=0x0009;
    int TEN=0x0010;

    /**
     *
     *1.用户登录
     *whycools/getAccessRight.jsp?user=&pwd=
     * 返回userid
     *
     * 2.apk更新
     *update/CustomerService.apk
     *
     * 3.安装清单
     * rent/rProxy.jsp?deviceid=&s=(压缩字符串)——"getInstallList4gun userid"
     *
     * 4.维修清单
     * rent/rProxy.jsp?deviceid=&s=(压缩字符串)——"getRepairList4gun userid"
     *
     * 5.安装待处理  已完工和未完工
     * rent/rProxy.jsp?deviceid=&s=（压缩字符串）——"InstallDoneConfirm  userid  ,   orderno , 'date ', 3, '已完工'"
     * rent/rProxy.jsp?deviceid=&s=（压缩字符串）——"InstallDoneConfirm  userid  ,   orderno , 'date ',2 , '填写已完工的原因'"
     *
     *
     * 6.维修待处理  已维修和未未维修
     * *rent/rProxy.jsp?deviceid=&s=（压缩字符串）——"RepairDoneConfirm  userid  ,   orderno , 'date ', 3, '已完工'"
     * rent/rProxy.jsp?deviceid=&s=（压缩字符串）——"RepairDoneConfirm  userid  ,   orderno , 'date ',2 , '填写已完工的原因'"
     *
     */
}
