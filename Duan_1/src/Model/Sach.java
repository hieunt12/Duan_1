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
public class Sach {

    private int maSach;
    private String tenSach;
    private int soTrang;
    private float gia;
    private Date ngayNhap;
    private int maTL;
    private String NXB;
    private String tinhTrang;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int soTrang, float gia, Date ngayNhap, int maTL, String NXB, String tinhTrang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soTrang = soTrang;
        this.gia = gia;
        this.ngayNhap = ngayNhap;
        this.maTL = maTL;
        this.NXB = NXB;
        this.tinhTrang = tinhTrang;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getMaTL() {
        return maTL;
    }

    public void setMaTL(int maTL) {
        this.maTL = maTL;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
