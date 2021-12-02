/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.PhieuMuon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class PhieuMuonDAO extends DAO<PhieuMuon, Integer> {

    private PhieuMuon getModel(ResultSet rs) throws SQLException {
        PhieuMuon pm = new PhieuMuon();
        pm.setMaNV(rs.getString("MaNV"));
        pm.setMaPM(rs.getInt("maPM"));
        pm.setMaThe(rs.getInt("MaThe"));
        pm.setNgayMuon(rs.getDate("NgayMuon"));
        pm.setSoNgayMuon(rs.getInt("SoNgayMuon"));
        pm.setTienDatCoc(rs.getFloat("TienDatCoc"));
        return pm;
    }

    @Override
    public void insert(PhieuMuon entity) {
        String sql = "insert into PhieuMuon(maNV,MaThe,SoNgayMuon,TienDatCoc) Values(?,?,?,?)";
        JDBC.Update(sql, entity.getMaNV(), entity.getMaThe(), entity.getSoNgayMuon(),entity.getTienDatCoc());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PhieuMuon entity) {
        String sql = "Update PhieuMuon set SoNgayMuon = ? where MaPM = ? ";
        JDBC.Update(sql, entity.getSoNgayMuon(), entity.getMaPM());
    }

    public void updatetrangthai(int maPM) {
        String sql = "Update PhieuMuon set Trangthai=1 where MaPM = ? ";
        JDBC.Update(sql, maPM);
    }

    @Override
    public List<PhieuMuon> SelectALL() {
        String sql = "Select * from PhieuMuon";
        return selectBySQL(sql);
    }

    @Override
    public PhieuMuon SelectByID(Integer entity) {
        String sql = "Select * from PhieuMuon where MaPM = ?";
        List<PhieuMuon> list = selectBySQL(sql, entity);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<PhieuMuon> selectBySQL(String sql, Object... args) {
        try {
            List<PhieuMuon> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                PhieuMuon pm = getModel(rs);
                list.add(pm);
            }
            rs.getStatement().getConnection();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<PhieuMuon> selectByMaNV(String manv) {
        String sql = "select * from PhieuMuon where MaNV like ?  and TrangThai = 0 order by MaPM desc";
        List<PhieuMuon> list = selectBySQL(sql, "%" + manv + "%");
        return list;
    }
    public List<PhieuMuon> selectByPMdatra(String manv) {
        String sql = "select * from PhieuMuon where MaNV like ?  and TrangThai = 1 order by MaPM desc";
        List<PhieuMuon> list = selectBySQL(sql, "%" + manv + "%");
        return list;
    }

    public PhieuMuon selectByTop1() {
        String sql = "select top 1 * from Phieumuon order by maPM desc";
        List<PhieuMuon> list = selectBySQL(sql);
        return list.size() > 0 ? list.get(0) : null;
    }

}
