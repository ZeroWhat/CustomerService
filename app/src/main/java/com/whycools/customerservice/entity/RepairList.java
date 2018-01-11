package com.whycools.customerservice.entity;

/**
 * 维修
 * "Engineer": "",
 *"repairdate": "2017-07-27",
 *"repairtime": "",
 *"RepNo": "122039",
 *"gsm": "13801755369/",
 *"phoneno": "",
 *"problem": "不制冷",
 *"symptom": "",
 *"province": "上海",
 *"cityname": "上海市",
 *"areaname": "虹口区",
 *"address": "四川北路859号(潮江春饭店)",
 *"goodsno": "",
 *"goodsname": "大金空调FCY71MAV2C/RY71FQAY3C 380V 3P吊顶式",
 *"installstateId": ""
 * Created by user on 2017-07-27.
 */

public class RepairList {
    private String _id;
    private String Engineer;
    private String repairdate;
    private String repairtime;
    private String RepNo;
    private String gsm;
    private String name;
    private String memo;
    private String phoneno;
    private String problem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    private String symptom;
    private String province;
    private String cityname;
    private String areaname;
    private String address;
    private String goodsno;
    private String goodsname;
    private String stateId;

    @Override
    public String toString() {
        return

               Engineer + '\'' +
                ", '" + repairdate + '\'' +
                ",'" + repairtime + '\'' +
                ",'" + RepNo + '\'' +
                ", '" + gsm + '\'' +
                ", '" + name + '\'' +
                ", '" + memo + '\'' +
                ", '" + phoneno + '\'' +
                ", '" + problem + '\'' +
                ", '" + symptom + '\'' +
                ", '" + province + '\'' +
                ", '" + cityname + '\'' +
                ", '" + areaname + '\'' +
                ", '" + address + '\'' +
                ", '" + goodsno + '\'' +
                ", '" + goodsname + '\'';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEngineer() {
        return Engineer;
    }

    public void setEngineer(String engineer) {
        Engineer = engineer;
    }

    public String getRepairdate() {
        return repairdate;
    }

    public void setRepairdate(String repairdate) {
        this.repairdate = repairdate;
    }

    public String getRepairtime() {
        return repairtime;
    }

    public void setRepairtime(String repairtime) {
        this.repairtime = repairtime;
    }

    public String getRepNo() {
        return RepNo;
    }

    public void setRepNo(String repNo) {
        RepNo = repNo;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoodsno() {
        return goodsno;
    }

    public void setGoodsno(String goodsno) {
        this.goodsno = goodsno;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
}
