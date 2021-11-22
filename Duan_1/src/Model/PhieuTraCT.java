/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author 84985
 */
public class PhieuTraCT {
    private int maPT;
    private int maSach;
    private float GiaMuon;
    private String tinhTrang;
    private float tienPhat;

    public PhieuTraCT() {
    }

    public PhieuTraCT(int maPT, int maSach, float GiaMuon, String tinhTrang, float tienPhat) {
        this.maPT = maPT;
        this.maSach = maSach;
        this.GiaMuon = GiaMuon;
        this.tinhTrang = tinhTrang;
        this.tienPhat = tienPhat;
    }

    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public float getGiaMuon() {
        return GiaMuon;
    }

    public void setGiaMuon(float GiaMuon) {
        this.GiaMuon = GiaMuon;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public float getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(float tienPhat) {
        this.tienPhat = tienPhat;
    }
    
}
