package com.whycools.customerservice.entity;

/**
 *
 * 安装
 * Created by user on 2017-07-25.
 *"InstallEngineer": "蒋正雷",
 *"installdate": "2017-07-25",
 *"installtime": "全天",
 *"orderno": "98774404",
 *"gsm": "13003145656/",
 *"phoneno": "",
 *"province": "上海",
 *"cityname": "上海市",
 *"areaname": "嘉定区",
 *"address": "铜川路2395弄嘉和坊103号602室",
 *"goodsno": "0101009A",
 *"goodsname": "三菱电机空调MSH-DF12VD 1.5P分体挂机",
 *"qty": "2.0000",
 *"shipstateId": "",
 *"installstateId": ""
 */

public class InstallList {
    private String _id;
    private String InstallEngineer;
    private String installdate;
    private String installtime;
    private String orderno;
    private String gsm;
    private String name;
    private String memo;
    private String phoneno;
    private String province;
    private String cityname;
    private String areaname;
    private String address;
    private String goodsno;

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

    private String goodsname;
    private String qty;
    private String shipstateId;
    private String installstateId;

    @Override
    public String toString() {
        return

                InstallEngineer + '\'' +
                ", '" + installdate + '\'' +
                ", '" + installtime + '\'' +
                ", '" + orderno + '\'' +
                ", '" + gsm + '\'' +
                ", '" + name + '\'' +
                ", '" + memo + '\'' +
                ", '" + phoneno + '\'' +
                ", '" + province + '\'' +
                ", '" + cityname + '\'' +
                ", '" + areaname + '\'' +
                ", '" + address + '\'' +
                ", '" + goodsno + '\'' +
                ", '" + goodsname + '\'' +
                ", '" + qty + '\'' +
                ", '" + shipstateId + '\'' ;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getInstallEngineer() {
        return InstallEngineer;
    }

    public void setInstallEngineer(String installEngineer) {
        InstallEngineer = installEngineer;
    }

    public String getInstalldate() {
        return installdate;
    }

    public void setInstalldate(String installdate) {
        this.installdate = installdate;
    }

    public String getInstalltime() {
        return installtime;
    }

    public void setInstalltime(String installtime) {
        this.installtime = installtime;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getShipstateId() {
        return shipstateId;
    }

    public void setShipstateId(String shipstateId) {
        this.shipstateId = shipstateId;
    }

    public String getInstallstateId() {
        return installstateId;
    }

    public void setInstallstateId(String installstateId) {
        this.installstateId = installstateId;
    }
}
