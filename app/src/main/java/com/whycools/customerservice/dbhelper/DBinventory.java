package com.whycools.customerservice.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 创建所有数据库表
 * Created by Zero on 2016-12-05.
 */
public class DBinventory extends SQLiteOpenHelper {


    public DBinventory(Context context) {
        super(context, "inventory.db", null, 1);
        Log.i("创建数据字段", "成功: ");
    }

    // 数据库第一次创建时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table temporary (_id INTEGER PRIMARY KEY AUTOINCREMENT,barcode nvarchar(100))");
        Log.i("创建表", "onCreate: temporary成功");

        // 服务器下载  安装清单①
        db.execSQL("create table InstallList (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "InstallEngineer nvarchar(10)," +//安装工姓名    蒋正雷
                "installdate nvarchar(20)," +//安装日期         2017-07-25
                "installtime nvarchar(20)," + // 安装时间       全天
                "orderno nvarchar(12)," + // 单据编号       98774404
                "gsm nvarchar(20)," + // 电话号码       13003145656
                "name nvarchar(20)," + // 联系人      张三
                "memo nvarchar(20)," + // 备注       验收发票，安装工备铁架不收费备管收费，提前电联，派安装工上门看安装位置
                "phoneno nvarchar(15), " + //
                "province nvarchar(4)," + // 城市     上海
                "cityname nvarchar(10)," + // 市     上海市
                "areaname nvarchar(20)," + // 区        嘉定区
                "address nvarchar(100)," + // 地址        嘉定区
                "goodsno nvarchar(20) ," + // 产品id      0101009A
                "goodsname nvarchar(50) ," + // 产品名称    三菱电机空调MSH-DF12VD 1.5P分体挂机
                "qty int ," + // 数量 2.0000
                "shipstateId int ," + //送货状态
                "installstateId int )");//安装状态

        // 服务器下载  维修清单②
        db.execSQL("create table RepairList (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Engineer nvarchar(10)," +//维修工姓名    蒋正雷
                "repairdate nvarchar(20)," +//维修日期         2017-07-25
                "repairtime nvarchar(20)," + // 维修时间       全天
                "RepNo nvarchar(12)," + // 单据编号       98774404
                "gsm nvarchar(20)," + // 电话号码       13003145656
                "name nvarchar(20)," + // 联系人      张三
                "memo nvarchar(20)," + // 备注       验收发票，安装工备铁架不收费备管收费，提前电联，派安装工上门看安装位置
                "phoneno nvarchar(15), " + //
                "problem nvarchar(100)," + // 保修内容     不制冷
                "symptom nvarchar(100)," + // 故障现象
                "province nvarchar(4)," + // 省        上海市
                "cityname nvarchar(10)," + // 市        嘉定区
                "areaname nvarchar(20) ," + // 区      0101009A
                "address nvarchar(100) ," + // 地址    三菱电机空调MSH-DF12VD 1.5P分体挂机
                "goodsno nvarchar(20) ," + // 产品编号
                "goodsname nvarchar(50) ," + //产品名称
                "stateId int )");//维修状态


    }


    // 数据库文件版本号发生变化时调用
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}