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
    private boolean tinhTrang;
    private String hinh;
    private int soLanMuon;

    public TheThuVien() {
    }

    public TheThuVien(int maThe, int maDG, Date ngayCap, Date ngayhetHan, boolean tinhTrang, String hinh, int soLanMuon) {
        this.maThe = maThe;
        this.maDG = maDG;
        this.ngayCap = ngayCap;
        this.ngayhetHan = ngayhetHan;
        this.tinhTrang = tinhTrang;
        this.hinh = hinh;
        this.soLanMuon = soLanMuon;
    }
     
    public int getSoLanMuon() {
        return soLanMuon;
    }

    public void setSoLanMuon(int soLanMuon) {
        this.soLanMuon = soLanMuon;
    }

   

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
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

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    

 

}
