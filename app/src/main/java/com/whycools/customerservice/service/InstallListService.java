package com.whycools.customerservice.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whycools.customerservice.dbhelper.DBinventory;
import com.whycools.customerservice.entity.InstallList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-07-25.
 */

public class InstallListService {

    DBinventory dDBinventory;
    Context context;

    public InstallListService(Context context) {
        if(dDBinventory==null){
            this.dDBinventory = new DBinventory(context);
        }
        this.context = context;
    }

    // 添加数据
    public void addInstallList(InstallList installlist) {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();

        Log.i("Stores数据库表", "添加成功");
        db.execSQL(
                "insert into InstallList(" +
                        "InstallEngineer," + //
                        "installdate," + //
                        "installtime," + //
                        "orderno," + //
                        "gsm," + //
                        "name," + //
                        "memo," + //
                        "phoneno," + //
                        "province," + //
                        "cityname," + //
                        "areaname," + //
                        "address," + //
                        "goodsno," + //
                        "goodsname," + //
                        "qty," + //
                        "shipstateId," + //
                        "installstateId" + //
                        ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[] {
                        installlist.getInstallEngineer(),
                        installlist.getInstalldate(),
                        installlist.getInstalltime(),
                        installlist.getOrderno(),
                        installlist.getGsm(),
                        installlist.getName(),
                        installlist.getMemo(),
                        installlist.getPhoneno(),
                        installlist.getProvince(),
                        installlist.getCityname(),
                        installlist.getAreaname(),
                        installlist.getAddress(),
                        installlist.getGoodsno(),
                        installlist.getGoodsname(),
                        installlist.getQty(),
                        installlist.getShipstateId(),
                        installlist.getInstallstateId()
                });
    }

    // 清除数据表所有内容
    public void clearInstallList() {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM InstallList");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // Toast.makeText(context, "删除了", Toast.LENGTH_SHORT).show();
        Log.i("InstallList数据表", "数据清除了");
    }
    // 删出某一条数据
    public void deleteInstallList(String _id) {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();
        db.execSQL("delete from InstallList where _id=?", new String[] { _id });
        Log.i("InstallList数据库", "成功删除");
    }
    // 修改某一条数据
    public void updateInstallList(String  InstallstateId,String _id) {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();
        Log.i("InstallList数据库", "_id: "+_id);
        Log.i("InstallList数据库", "InstallstateId: "+InstallstateId);
        db.execSQL("update InstallList set installstateId=? where _id=?",
                new Object[] { InstallstateId,_id });
        Log.i("InstallList数据库", "修改数据成功----------"+InstallstateId+"--------"+_id);

    }


    // 查询某一条数据
    public InstallList findInstallList(String orderno) {
        InstallList installlist = new InstallList();
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from InstallList where orderno=?",
                new String[] { orderno });
        if (cursor.moveToFirst()) {
//            String _id = cursor.getString(cursor.getColumnIndex("_id")); //

            String InstallEngineer = cursor.getString(cursor.getColumnIndex("InstallEngineer")); //
            String installdate = cursor.getString(cursor.getColumnIndex("installdate")); //
            String installtime = cursor.getString(cursor.getColumnIndex("installtime")); //
         //   String orderno = cursor.getString(cursor.getColumnIndex("orderno")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String qty = cursor.getString(cursor.getColumnIndex("qty")); //
            String shipstateId = cursor.getString(cursor.getColumnIndex("shipstateId")); //
            String installstateId = cursor.getString(cursor.getColumnIndex("installstateId")); //
            Log.i("-------------------", "installstateId>>>>>>>>>>>>>>>>>>: "+installstateId);

            installlist.setInstallEngineer(InstallEngineer);
            installlist.setInstalldate(installdate);
            installlist.setInstalltime(installtime);
            installlist.setOrderno(orderno);
            installlist.setGsm(gsm);
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

        }
        Log.i("InstallList数据库", "查找一条数据正确");
        cursor.close();
        return installlist;
    }

    // 查找数据库两面全部数据
    public List<InstallList> getAllInstallList() {
        List<InstallList> data = new ArrayList<InstallList>();
        if(dDBinventory==null){
            dDBinventory = new DBinventory(context);
        }
        Log.i("--------", "dDBinventory:--------------- "+dDBinventory);

        Log.i("--------", "db:--------------- "+dDBinventory.getReadableDatabase());
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from InstallList", null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndex("_id")); //
            String InstallEngineer = cursor.getString(cursor.getColumnIndex("InstallEngineer")); //
            String installdate = cursor.getString(cursor.getColumnIndex("installdate")); //
            String installtime = cursor.getString(cursor.getColumnIndex("installtime")); //
            String orderno = cursor.getString(cursor.getColumnIndex("orderno")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String qty = cursor.getString(cursor.getColumnIndex("qty")); //
            String shipstateId = cursor.getString(cursor.getColumnIndex("shipstateId")); //
            String installstateId = cursor.getString(cursor.getColumnIndex("installstateId")); //
            if(!installstateId.equals("3")&&!installstateId.equals("2")){
                InstallList installlist = new InstallList();
                installlist.set_id(_id);
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

                data.add(installlist);

            }


        }
        Log.i("InstallList数据库", "查找所有数据正确");
        cursor.close();
        return data;
    }

    // 查找数据库两面全部数据
    public List<InstallList> getAllYInstallList() {
        List<InstallList> data = new ArrayList<InstallList>();
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from InstallList", null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndex("_id")); //
            String InstallEngineer = cursor.getString(cursor.getColumnIndex("InstallEngineer")); //
            String installdate = cursor.getString(cursor.getColumnIndex("installdate")); //
            String installtime = cursor.getString(cursor.getColumnIndex("installtime")); //
            String orderno = cursor.getString(cursor.getColumnIndex("orderno")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String qty = cursor.getString(cursor.getColumnIndex("qty")); //
            String shipstateId = cursor.getString(cursor.getColumnIndex("shipstateId")); //
            String installstateId = cursor.getString(cursor.getColumnIndex("installstateId")); //
            if (orderno.equals("98806428")){
                Log.i("已完成--------", "installstateId:-------installstateId "+installstateId);
            }
            if(installstateId.equals("3")){
                InstallList installlist = new InstallList();
                installlist.set_id(_id);
                installlist.setInstallEngineer(InstallEngineer);
                installlist.setInstalldate(installdate);
                installlist.setInstalltime(installtime);
                installlist.setOrderno(orderno);
                Log.i("已完成--------", "orderno:-------orderno "+orderno);
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
                Log.i("已完成--------", "installstateId:-------》》》》installstateId "+installstateId);

                data.add(installlist);

            }


        }
        Log.i("InstallList数据库", "查找所有数据正确");
        cursor.close();
        return data;
    }


    // 查找数据库两面全部数据
    public List<InstallList> getAllNInstallList() {
        List<InstallList> data = new ArrayList<InstallList>();
        if(dDBinventory==null){
            dDBinventory = new DBinventory(context);
        }
        Log.i("--------", "dDBinventory:--------------- "+dDBinventory);

        Log.i("--------", "db:--------------- "+dDBinventory.getReadableDatabase());
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from InstallList", null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndex("_id")); //
            String InstallEngineer = cursor.getString(cursor.getColumnIndex("InstallEngineer")); //
            String installdate = cursor.getString(cursor.getColumnIndex("installdate")); //
            String installtime = cursor.getString(cursor.getColumnIndex("installtime")); //
            String orderno = cursor.getString(cursor.getColumnIndex("orderno")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String qty = cursor.getString(cursor.getColumnIndex("qty")); //
            String shipstateId = cursor.getString(cursor.getColumnIndex("shipstateId")); //
            String installstateId = cursor.getString(cursor.getColumnIndex("installstateId")); //
            if(installstateId.equals("2")){
                InstallList installlist = new InstallList();
                installlist.set_id(_id);
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

                data.add(installlist);

            }


        }
        Log.i("InstallList数据库", "查找所有数据正确");
        cursor.close();
        return data;
    }
}
