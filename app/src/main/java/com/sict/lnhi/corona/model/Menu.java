package com.sict.lnhi.corona.model;

import java.io.Serializable;

public class Menu implements Serializable {
    public int id;
    public String tenmon;
    public String mota;
    public String anh;
    public Integer gia;
    public int idtheloai;
    public int idth;

    public Menu(int id, String tenmon, String mota, String anh, Integer gia, int idtheloai, int idth) {
        this.id = id;
        this.tenmon = tenmon;
        this.mota = mota;
        this.anh = anh;
        this.gia = gia;
        this.idtheloai = idtheloai;
        this.idth = idth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public int getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(int idtheloai) {
        this.idtheloai = idtheloai;
    }

    public int getIdth() {
        return idth;
    }

    public void setIdth(int idth) {
        this.idth = idth;
    }
}