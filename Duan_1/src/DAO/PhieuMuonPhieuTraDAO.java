/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.PhieuMuonPhieuTra;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class PhieuMuonPhieuTraDAO extends DAO<PhieuMuonPhieuTra, Integer>{
    private PhieuMuonPhieuTra getmodel(ResultSet rs)throws SQLException{
      PhieuMuonPhieuTra pmpt = new PhieuMuonPhieuTra();
      pmpt.setID(rs.getInt("ID"));
      pmpt.setMaPM(rs.getInt("MapM"));
      pmpt.setMaPT(rs.getInt("MaPT"));
      return pmpt;
    }
    @Override
    public void insert(PhieuMuonPhieuTra entity) {
       String sql = "insert into PhieuMuon_PhieuTra(MaPm,MaPT) Values(?,?)";
        JDBC.Update(sql, entity.getMaPM(),entity.getMaPT());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PhieuMuonPhieuTra entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PhieuMuonPhieuTra> SelectALL() {
         String sql = "Select * from PhieuMuon_Phieutra ";
        List<PhieuMuonPhieuTra> list = selectBySQL(sql);
        return list; 
    }

    @Override
    public PhieuMuonPhieuTra SelectByID(Integer entity) {
     String sql = "Select * from Phieumuon_PhieuTra ";
        List<PhieuMuonPhieuTra> list = selectBySQL(sql, entity);
        return list.size()>0?list.get(0):null; 
    }

    @Override
    public List<PhieuMuonPhieuTra> selectBySQL(String sql, Object... args) {
        try {
            List<PhieuMuonPhieuTra> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while(rs.next()){
                PhieuMuonPhieuTra pt = getmodel(rs);
                list.add(pt);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } }
     public PhieuMuonPhieuTra selectTop1(){
        String sql = "select top 1 * from Phieumuon_PhieuTra order by MaPT desc";
        List<PhieuMuonPhieuTra> list = selectBySQL(sql);
        return list.size()>0?list.get(0):null;
    }
}
