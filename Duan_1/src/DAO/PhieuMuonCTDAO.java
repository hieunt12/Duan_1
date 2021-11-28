/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.PhieuMuonCT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class PhieuMuonCTDAO extends DAO<PhieuMuonCT, Integer>{
    private PhieuMuonCT getModel(ResultSet rs) throws SQLException{
        PhieuMuonCT pm = new PhieuMuonCT();
        pm.setMaPM(rs.getInt("MaPM"));
        pm.setMaSach(rs.getInt("MaSach"));
        pm.setTrangThai(rs.getBoolean("TrangThai"));
        pm.setGhiChu(rs.getString("ghiChu"));
        pm.setID(rs.getInt("ID"));
        return pm;
    }
    @Override
    public void insert(PhieuMuonCT entity) {
        String sql = "insert into PhieuMuonCT(MaPM,MaSach,ghichu) Values(?,?,?)";
      JDBC.Update(sql,entity.getMaPM(),entity.getMaSach(),entity.getGhiChu());
        
    }
    public void updatePMCT(PhieuMuonCT entity){
        String sql = "Update PhieuMuonCT set trangThai=?,GhiChu=?,masach=? where ID = ?";
        JDBC.Update(sql,entity.isTrangThai(),entity.getGhiChu(),entity.getMaSach(),entity.getID());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PhieuMuonCT entity) {
       String sql = "update PhieuMuonCT set ghiChu = ? where ID = ?";
       JDBC.Update(sql, entity.getGhiChu(),entity.getID());
    }

    @Override
    public List<PhieuMuonCT> SelectALL() {
        String sql = "select * from PhieuMuonCT";
        return selectBySQL(sql);
    }

    @Override
    public PhieuMuonCT SelectByID(Integer entity) {
     String sql  = "select * from PhieuMuonCT where ID = ?";
        List<PhieuMuonCT> list = selectBySQL(sql, entity);
        return list.size()>0?list.get(0):null;    
    }

    @Override
    public List<PhieuMuonCT> selectBySQL(String sql, Object... args) {
        try {
            List<PhieuMuonCT> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while(rs.next()){
                PhieuMuonCT pm = getModel(rs);
                list.add(pm);
            }
            rs.getStatement().getConnection();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }    
    }
    public  List<PhieuMuonCT> selectBymaPM(Integer MaPM){
         String sql  = "select * from PhieuMuonCT where MaPM = ?";
        List<PhieuMuonCT> list = selectBySQL(sql, MaPM);
        return list;
    }
    public PhieuMuonCT Selectbymasach(Integer MaSach){
        String sql  = "select * from PhieuMuonCT where TrangThai = 0 and MaSach = ?";
        List<PhieuMuonCT> list = selectBySQL(sql,MaSach);
        return list.size()>0?list.get(0):null;
    }
    
    
}
