/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.PhieuTra;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class PhieuTraDAO extends DAO<PhieuTra, Integer>{
    private PhieuTra getmodel(ResultSet rs) throws SQLException{
        PhieuTra pt = new PhieuTra();
        pt.setMaPT(rs.getInt("MaPT"));
        pt.setMaNV(rs.getString("MaNV"));
        pt.setMaThe(rs.getInt("maThe"));
        pt.setNgayThucTra(rs.getDate("NgayThucTra"));
        return pt;
    }
    @Override
    public void insert(PhieuTra entity) {
         String sql = "Insert into PhieuTra(MaNV,MaThe,NgayThucTra) Values(?,?,?)";   
         JDBC.Update(sql, entity.getMaNV(),entity.getMaThe(),entity.getNgayThucTra());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PhieuTra entity) {
       String sql = "Update PhieuTra set NgayThucTra = ? where MaPT = ?";   
         JDBC.Update(sql, entity.getNgayThucTra(),entity.getMaPT());    
    }

    @Override
    public List<PhieuTra> SelectALL() {
       String sql = "Select * from PhieuTra order by maPT desc";
        List<PhieuTra> list = selectBySQL(sql);
        return list;    
    }
     public List<PhieuTra> SelectTOp1() {
       String sql = "Select Top 1 * from PhieuTra order by maPT desc";
        List<PhieuTra> list = selectBySQL(sql);
        return list;    
    }

    @Override
    public PhieuTra SelectByID(Integer entity) {
        String sql = "Select * from PhieuTra where MaPT = ?";
        List<PhieuTra> list = selectBySQL(sql, entity);
        return list.size()>0?list.get(0):null;
      }

    @Override
    public List<PhieuTra> selectBySQL(String sql, Object... args) {
         try {
            List<PhieuTra> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while(rs.next()){
                PhieuTra pt = getmodel(rs);
                list.add(pt);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 
    }
    public PhieuTra selectTop1(){
        String sql = "select top 1 * from PhieuTra order by MaPT desc";
        List<PhieuTra> list = selectBySQL(sql);
        return list.get(0);
    }
     public List<Integer> SelectYear() {
       String sql = " Select Distinct year(NgayThucTra) from PhieuTra order by year(NgayThucTra) desc";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            System.out.println(list.size());
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }  
    }
    
}
