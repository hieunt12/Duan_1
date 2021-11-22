/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author 84985
 */
public class TheThuVien {
    private int maThe;
    private int maDG;
    private Date ngayCap;
    private Date ngayhetHan;
    private String tinhTrang;

    public TheThuVien() {
    }

    public TheThuVien(int maThe, int maDG, Date ngayCap, Date ngayhetHan, String tinhTrang) {
        this.maThe = maThe;
        this.maDG = maDG;
        this.ngayCap = ngayCap;
        this.ngayhetHan = ngayhetHan;
        this.tinhTrang = tinhTrang;
    }

    public int getMaThe() {
        return maThe;
    }

    public void setMaThe(int maThe) {
        this.maThe = maThe;
    }

    public int getMaDG() {
        return maDG;
    }

    public void setMaDG(int maDG) {
        this.maDG = maDG;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(Date ngayCap) {
        this.ngayCap = ngayCap;
    }

    public Date getNgayhetHan() {
        return ngayhetHan;
    }

    public void setNgayhetHan(Date ngayhetHan) {
        this.ngayhetHan = ngayhetHan;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
}
