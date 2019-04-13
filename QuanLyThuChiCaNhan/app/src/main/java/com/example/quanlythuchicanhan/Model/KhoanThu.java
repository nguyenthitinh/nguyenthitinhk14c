package com.example.quanlythuchicanhan.Model;

import java.io.Serializable;

public class KhoanThu implements Serializable {
    private int idthu;
    private int sotien;
    private String loaithu;
    private String ngaythu;
    private String cuthe;

    public KhoanThu(int idthu, int sotien, String loaithu, String ngaythu, String cuthe) {
        this.idthu = idthu;
        this.sotien = sotien;
        this.loaithu = loaithu;
        this.ngaythu = ngaythu;
        this.cuthe = cuthe;
    }

    public int getIdthu() {
        return idthu;
    }

    public void setIdthu(int idthu) {
        this.idthu = idthu;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getLoaithu() {
        return loaithu;
    }

    public void setLoaithu(String loaithu) {
        this.loaithu = loaithu;
    }

    public String getNgaythu() {
        return ngaythu;
    }

    public void setNgaythu(String ngaythu) {
        this.ngaythu = ngaythu;
    }

    public String getCuthe() {
        return cuthe;
    }

    public void setCuthe(String cuthe) {
        this.cuthe = cuthe;
    }
}


