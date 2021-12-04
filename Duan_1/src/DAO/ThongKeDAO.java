/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hai
 */
public class ThongKeDAO {
    
     private List<Object[]> getListOfArray(String sql,String[] cols,Object... args){
           List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while(rs.next()){
                Object[] vail = new Object[cols.length];
                for(int i = 0;i< cols.length;i++){
                    vail[i] = rs.getObject(cols[i]);
                   
                }
                 list.add(vail);
            }
                rs.getStatement().getConnection().close();
        } catch (Exception e) {
         throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<Object[]> getThongKeDocGia(){
        String sql = "{Call TKDocGia}";
        String[] cols = {"MaDG","TenDG","MaThe","SoSachMuon"};
        return this.getListOfArray(sql, cols);
    }
    
     public List<Object[]> getSach(){
        String sql = "{Call TKSach}";
        String[] cols = {"MaSach","TenSach","NXB","SoLanMUon"};
        return this.getListOfArray(sql, cols);
    }
   
     public List<Object[]> getDoanhThu(int nam){
        String sql = "{Call DoanhThu(?)}";
        String[] cols = {"Thang","giamuon","tienphat","TongTien"};
        return this.getListOfArray(sql, cols, nam);
    }
}
