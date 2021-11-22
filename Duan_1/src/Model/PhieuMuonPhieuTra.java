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
public class PhieuMuonPhieuTra {
    private int ID,maPM,maPT;

    public PhieuMuonPhieuTra() {
    }

    public PhieuMuonPhieuTra(int ID, int maPM, int maPT) {
        this.ID = ID;
        this.maPM = maPM;
        this.maPT = maPT;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }
    
}
