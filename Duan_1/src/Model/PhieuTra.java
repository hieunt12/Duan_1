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
public class PhieuTra {
    private int maPT;
    private String maNV;
    private int maThe;
    private Date NgayThucTra;

    public PhieuTra() {
    }

    public PhieuTra(int maPT, String maNV, int maThe, Date NgayThucTra) {
        this.maPT = maPT;
        this.maNV = maNV;
        this.maThe = maThe;
        this.NgayThucTra = NgayThucTra;
    }

    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getMaThe() {
        return maThe;
    }

    public void setMaThe(int maThe) {
        this.maThe = maThe;
    }

    public Date getNgayThucTra() {
        return NgayThucTra;
    }

    public void setNgayThucTra(Date NgayThucTra) {
        this.NgayThucTra = NgayThucTra;
    }
    
}
