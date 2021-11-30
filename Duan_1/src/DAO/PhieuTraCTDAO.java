/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.PhieuTraCT;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class PhieuTraCTDAO extends DAO<PhieuTraCT, Integer> {

    @Override
    public void insert(PhieuTraCT entity) {
        String sql = "insert into PhieuTraCT(maPT,MaSach,TinhTrang,tienPhat) Values(?,?,?,?)";
        JDBC.Update(sql, entity.getMaPT(), entity.getMaSach(), entity.getTinhTrang(), entity.getTienPhat());

    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PhieuTraCT entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PhieuTraCT> SelectALL() {
        String sql = "Select * from PhieuMuon_Phieutra ";
        List<PhieuTraCT> list = selectBySQL(sql);
        return list;
    }

    @Override
    public PhieuTraCT SelectByID(Integer entity) {
        String sql = "Select * from PhieuTraCT where maPT = ?";
        List<PhieuTraCT> list = selectBySQL(sql, entity);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<PhieuTraCT> selectBySQL(String sql, Object... args) {
        try {
            List<PhieuTraCT> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                PhieuTraCT pt = new PhieuTraCT();
                pt.setMaPT(rs.getInt("Mapt"));
                pt.setMaSach(rs.getInt("MaSach"));
              
                pt.setTinhTrang(rs.getString("TinhTrang"));
                pt.setTienPhat(rs.getFloat("Tienphat"));
                list.add(pt);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
     public List<PhieuTraCT> SelectByMaPT(Integer entity) {
        String sql = "Select * from PhieuTraCT where maPT = ?";
        List<PhieuTraCT> list = selectBySQL(sql, entity);
        return list;
    }
    
}
