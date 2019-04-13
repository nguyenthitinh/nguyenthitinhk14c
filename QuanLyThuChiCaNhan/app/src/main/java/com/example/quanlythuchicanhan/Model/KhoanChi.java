package com.example.quanlythuchicanhan.Model;

import java.io.Serializable;

public class KhoanChi implements Serializable {
    private Integer idchi;
    private Integer sotien;
    private String loaichi;
    private String ngaychi;
    private String cuthe;

    public KhoanChi(Integer idchi, Integer sotien, String loaichi, String ngaychi, String cuthe) {
        this.idchi = idchi;
        this.sotien = sotien;
        this.loaichi = loaichi;
        this.ngaychi = ngaychi;
        this.cuthe = cuthe;
    }

    public Integer getIdchi() {
        return idchi;
    }

    public void setIdchi(Integer idchi) {
        this.idchi = idchi;
    }

    public Integer getSotien() {
        return sotien;
    }

    public void setSotien(Integer sotien) {
        this.sotien = sotien;
    }

    public String getLoaichi() {
        return loaichi;
    }

    public void setLoaichi(String loaichi) {
        this.loaichi = loaichi;
    }

    public String getNgaychi() {
        return ngaychi;
    }

    public void setNgaychi(String ngaychi) {
        this.ngaychi = ngaychi;
    }

    public String getCuthe() {
        return cuthe;
    }

    public void setCuthe(String cuthe) {
        this.cuthe = cuthe;
    }
}
