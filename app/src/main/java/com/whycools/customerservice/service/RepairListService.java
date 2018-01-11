package com.whycools.customerservice.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whycools.customerservice.dbhelper.DBinventory;
import com.whycools.customerservice.entity.InstallList;
import com.whycools.customerservice.entity.RepairList;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修
 * Created by user on 2017-07-27.
 */

public class RepairListService {
    DBinventory dDBinventory;
    Context context;

    public RepairListService(Context context) {
        if(dDBinventory==null){
            this.dDBinventory = new DBinventory(context);
        }
        this.context = context;
    }

    // 添加数据
    public void addRepairList(RepairList repairlist) {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();

        Log.i("Stores数据库表", "添加成功");
        db.execSQL(
                "insert into RepairList(" +
                        "Engineer," + //
                        "repairdate," + //
                        "repairtime," + //
                        "RepNo," + //
                        "gsm," + //
                        "name," + //
                        "memo," + //
                        "phoneno," + //
                        "problem," + //
                        "symptom," + //
                        "province," + //
                        "cityname," + //
                        "areaname," + //
                        "address," + //
                        "goodsno," + //
                        "goodsname," + //
                        "stateId" + //
                        ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[] {
                        repairlist.getEngineer(),
                        repairlist.getRepairdate(),
                        repairlist.getRepairtime(),
                        repairlist.getRepNo(),
                        repairlist.getGsm(),
                        repairlist.getName(),
                        repairlist.getMemo(),
                        repairlist.getPhoneno(),
                        repairlist.getProblem(),
                        repairlist.getSymptom(),
                        repairlist.getProvince(),
                        repairlist.getCityname(),
                        repairlist.getAreaname(),
                        repairlist.getAddress(),
                        repairlist.getGoodsno(),
                        repairlist.getGoodsname(),
                        repairlist.getStateId()
                });
    }

    // 清除数据表所有内容
    public void clearRepairList() {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM RepairList");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // Toast.makeText(context, "删除了", Toast.LENGTH_SHORT).show();
        Log.i("RepairList数据表", "数据清除了");
    }
    // 删出某一条数据
    public void deleteRepairList(String _id) {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();
        db.execSQL("delete from RepairList where _id=?", new String[] { _id });
        Log.i("InstallList数据库", "成功删除");
    }
    // 修改某一条数据
    public void updateRepairList(String  stateId,String _id) {
        SQLiteDatabase db = dDBinventory.getWritableDatabase();
        db.execSQL("update RepairList set stateId=? where _id=?",
                new Object[] { stateId,_id });
        Log.i("InstallList数据库", "修改数据成功----------"+stateId+"--------"+_id);

    }


    // 查询某一条数据
    public RepairList findRepairList(String _id) {
        RepairList repairlist = new RepairList();
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from RepairList where _id=?",
                new String[] { _id });
        if (cursor.moveToFirst()) {
//            String _id = cursor.getString(cursor.getColumnIndex("_id")); //

            String Engineer = cursor.getString(cursor.getColumnIndex("Engineer")); //
            String repairdate = cursor.getString(cursor.getColumnIndex("repairdate")); //
            String repairtime = cursor.getString(cursor.getColumnIndex("repairtime")); //
            String RepNo = cursor.getString(cursor.getColumnIndex("RepNo")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String problem = cursor.getString(cursor.getColumnIndex("problem")); //
            String symptom = cursor.getString(cursor.getColumnIndex("symptom")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String stateId = cursor.getString(cursor.getColumnIndex("stateId")); //

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

        }
        Log.i("InstallList数据库", "查找一条数据正确");
        cursor.close();
        return repairlist;
    }

    // 查找数据库两面全部数据
    public List<RepairList> getAllRepairList() {
        List<RepairList> data = new ArrayList<RepairList>();
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from RepairList", null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndex("_id")); //
            String Engineer = cursor.getString(cursor.getColumnIndex("Engineer")); //
            String repairdate = cursor.getString(cursor.getColumnIndex("repairdate")); //
            String repairtime = cursor.getString(cursor.getColumnIndex("repairtime")); //
            String RepNo = cursor.getString(cursor.getColumnIndex("RepNo")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String problem = cursor.getString(cursor.getColumnIndex("problem")); //
            String symptom = cursor.getString(cursor.getColumnIndex("symptom")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String stateId = cursor.getString(cursor.getColumnIndex("stateId")); //
            if(!stateId.equals("3")&&!stateId.equals("2")){
                RepairList repairlist = new RepairList();
                repairlist.set_id(_id);
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
                data.add(repairlist);

            }



        }
        Log.i("InstallList数据库", "查找所有数据正确");
        cursor.close();
        return data;
    }

    // 查找数据库两面全部数据
    public List<RepairList> getAllYRepairList() {
        List<RepairList> data = new ArrayList<RepairList>();
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from RepairList", null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndex("_id")); //
            String Engineer = cursor.getString(cursor.getColumnIndex("Engineer")); //
            String repairdate = cursor.getString(cursor.getColumnIndex("repairdate")); //
            String repairtime = cursor.getString(cursor.getColumnIndex("repairtime")); //
            String RepNo = cursor.getString(cursor.getColumnIndex("RepNo")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String problem = cursor.getString(cursor.getColumnIndex("problem")); //
            String symptom = cursor.getString(cursor.getColumnIndex("symptom")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String stateId = cursor.getString(cursor.getColumnIndex("stateId")); //
            if(stateId.equals("3")){
                RepairList repairlist = new RepairList();
                repairlist.set_id(_id);
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
                data.add(repairlist);

            }



        }
        Log.i("InstallList数据库", "查找所有数据正确");
        cursor.close();
        return data;
    }


    // 查找数据库两面全部数据
    public List<RepairList> getAllNRepairList() {
        List<RepairList> data = new ArrayList<RepairList>();
        SQLiteDatabase db = dDBinventory.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from RepairList", null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(cursor.getColumnIndex("_id")); //
            String Engineer = cursor.getString(cursor.getColumnIndex("Engineer")); //
            String repairdate = cursor.getString(cursor.getColumnIndex("repairdate")); //
            String repairtime = cursor.getString(cursor.getColumnIndex("repairtime")); //
            String RepNo = cursor.getString(cursor.getColumnIndex("RepNo")); //
            String gsm = cursor.getString(cursor.getColumnIndex("gsm")); //
            String name = cursor.getString(cursor.getColumnIndex("name")); //
            String memo = cursor.getString(cursor.getColumnIndex("memo")); //
            String phoneno = cursor.getString(cursor.getColumnIndex("phoneno")); //
            String problem = cursor.getString(cursor.getColumnIndex("problem")); //
            String symptom = cursor.getString(cursor.getColumnIndex("symptom")); //
            String province = cursor.getString(cursor.getColumnIndex("province")); //
            String cityname = cursor.getString(cursor.getColumnIndex("cityname")); //
            String areaname = cursor.getString(cursor.getColumnIndex("areaname")); //
            String address = cursor.getString(cursor.getColumnIndex("address")); //
            String goodsno = cursor.getString(cursor.getColumnIndex("goodsno")); //
            String goodsname = cursor.getString(cursor.getColumnIndex("goodsname")); //
            String stateId = cursor.getString(cursor.getColumnIndex("stateId")); //
            if(stateId.equals("2")){
                RepairList repairlist = new RepairList();
                repairlist.set_id(_id);
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
                data.add(repairlist);

            }



        }
        Log.i("InstallList数据库", "查找所有数据正确");
        cursor.close();
        return data;
    }
}
