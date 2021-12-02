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
public class PhieuMuon {
    private int maPM;
    private String maNV;
    private Date ngayMuon;
    private int MaThe;
    private int soNgayMuon;
    private boolean trangthai;
    private float TienDatCoc;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, String maNV, Date ngayMuon, int MaThe, int soNgayMuon, boolean trangthai, float TienDatCoc) {
        this.maPM = maPM;
        this.maNV = maNV;
        this.ngayMuon = ngayMuon;
        this.MaThe = MaThe;
        this.soNgayMuon = soNgayMuon;
        this.trangthai = trangthai;
        this.TienDatCoc = TienDatCoc;
    }

    public float getTienDatCoc() {
        return TienDatCoc;
    }

    public void setTienDatCoc(float TienDatCoc) {
        this.TienDatCoc = TienDatCoc;
    }
   

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

   

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getMaThe() {
        return MaThe;
    }

    public void setMaThe(int MaThe) {
        this.MaThe = MaThe;
    }

    public int getSoNgayMuon() {
        return soNgayMuon;
    }

    public void setSoNgayMuon(int soNgayMuon) {
        this.soNgayMuon = soNgayMuon;
    }
    
}
