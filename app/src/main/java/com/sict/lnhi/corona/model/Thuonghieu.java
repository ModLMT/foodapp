package com.sict.lnhi.corona.model;

public class Thuonghieu {
    public int id;
    public String ten;
    public String anh;
    public String mota;
    public String diachi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Thuonghieu(int id, String ten, String anh, String mota, String diachi) {
        this.id = id;
        this.ten = ten;
        this.anh = anh;
        this.mota = mota;
        this.diachi = diachi;
    }
}