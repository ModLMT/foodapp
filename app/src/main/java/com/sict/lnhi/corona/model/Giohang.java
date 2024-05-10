package com.sict.lnhi.corona.model;

public class Giohang {
    public int idma;
    public String tenma;
    public long gia;
    public String anh;
    public int soluong;

    public Giohang(int idma, String tenma, long gia, String anh, int soluong) {
        this.idma = idma;
        this.tenma = tenma;
        this.gia = gia;
        this.anh = anh;
        this.soluong = soluong;
    }

    public int getIdma() {
        return idma;
    }

    public void setIdma(int idma) {
        this.idma = idma;
    }

    public String getTenma() {
        return tenma;
    }

    public void setTenma(String tenma) {
        this.tenma = tenma;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}