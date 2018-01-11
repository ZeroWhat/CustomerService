package com.whycools.customerservice;

import android.app.Application;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.whycools.customerservice.utils.DateUtile;
import com.whycools.customerservice.utils.log.CrashHandler;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import de.mindpipe.android.logging.log4j.LogConfigurator;


/**
 * Created by Administrator on 2016/9/22.
 */
public class AppApplication extends Application {
    private static final String TAG="application";

    public LogConfigurator logConfigurator;
    public Logger logger = null;

    /**
     * 存储卡根目录
     */
    public static String EXTERNALSTORAGEDIR = Environment.getExternalStorageDirectory().getPath();

    /**
     * 程序使用的目录
     */
    public final static String DIR_APP = "LLogDemo";

    /**
     * 日志保存目录
     */
    private final static String DIR_LOG = "log";

    @Override
    public void onCreate() {
        super.onCreate();

        initLogConfig();

    }


    public void initLogConfig() {
        //初始化日志信息
        initLoginConfig();
        //初始化系统配置
        createAppDir();
        //logger.info("程序启动");
//        AndroidCrash.getInstance().setLogFileDir(getLogDir()).setCrashReporter
//                (myCrashListener);
//        AndroidCrash.getInstance().init(getApplicationContext());

        // 新加方法
        CrashHandler catchHandler = CrashHandler.getInstance( getserialnumber());
        catchHandler.init(getApplicationContext());
    }



    /**
     * 创建程序目录文件夹
     */
    private  void createAppDir() {
        //检查日志文件目录是否存在
        File logDir = new File(getLogDir());
        if (!logDir.exists())
            logDir.mkdirs();
        File nomedia = new File(EXTERNALSTORAGEDIR + File.separator + DIR_APP, ".nomedia");
        try {
            nomedia.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取日志文件存取目录
     *
     * @return
     */
    public static String getLogDir() {
        return EXTERNALSTORAGEDIR + File.separator + DIR_APP + File.separator + DIR_LOG;
    }




    /**
     * 初始化日志信息配置
     */
    private void initLoginConfig() {
        //初始化日志设置
        this.logConfigurator = new LogConfigurator();
        //设置日志文件名称
        this.logConfigurator.setFileName(getLogDir() + File.separator + getserialnumber()+ DateUtile
                .date2String(new Date(), "yyyyMMdd") + ".log");
        this.logConfigurator.setFilePattern("%d [%p]-[%c.%M(%L)] %m %n");
        //设置日志级别
        this.logConfigurator.setRootLevel(Level.DEBUG);
        this.logConfigurator.setLevel("org.apache", Level.ERROR);
        //设置最大文件大小
        this.logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        this.logConfigurator.setImmediateFlush(true);
        //应用日志设置
        this.logConfigurator.configure();
        //初始化日志
        this.logger = Logger.getLogger(AppApplication.class);
    }


    public String getserialnumber(){
        TelephonyManager tm = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if (tm.getDeviceId()!=null&&!tm.getDeviceId().equals("0")){
            Log.i(TAG, "枪id为: "+tm.getDeviceId());
            return tm.getDeviceId();

        }else{
            return "ae01";
        }
    }


}
