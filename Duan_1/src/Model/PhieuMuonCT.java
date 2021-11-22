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
public class PhieuMuonCT {
    private int MaPM;
    private int MaSach;
    private boolean trangThai;
    private String ghiChu;

    public PhieuMuonCT() {
    }

    public PhieuMuonCT(int MaPM, int MaSach, boolean trangThai, String ghiChu) {
        this.MaPM = MaPM;
        this.MaSach = MaSach;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public int getMaPM() {
        return MaPM;
    }

    public void setMaPM(int MaPM) {
        this.MaPM = MaPM;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int MaSach) {
        this.MaSach = MaSach;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
}
