package com.sict.lnhi.corona.model;

public class Donhang {
    public int id;
    public int madonhang;
    public int mamonan;
    public String tenmonan;
    public int giatien;
    public int soluong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(int madonhang) {
        this.madonhang = madonhang;
    }

    public int getMamonan() {
        return mamonan;
    }

    public void setMamonan(int mamonan) {
        this.mamonan = mamonan;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Donhang(int id, int madonhang, int mamonan, String tenmonan, int giatien, int soluong) {
        this.id = id;
        this.madonhang = madonhang;
        this.mamonan = mamonan;
        this.tenmonan = tenmonan;
        this.giatien = giatien;
        this.soluong = soluong;
    }
}