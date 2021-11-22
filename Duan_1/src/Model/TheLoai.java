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
public class TheLoai {
    private int theLoai;
    private String tenTheLoai;

    public TheLoai() {
    }

    public TheLoai(int theLoai, String tenTheLoai) {
        this.theLoai = theLoai;
        this.tenTheLoai = tenTheLoai;
    }

    public int getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(int theLoai) {
        this.theLoai = theLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
    
}
