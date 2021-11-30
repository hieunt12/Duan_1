/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.TheThuVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 84985
 */
public class TheThuVienDAO extends DAO<TheThuVien, Integer> {

    private TheThuVien getmodel(ResultSet rs) throws SQLException {
        TheThuVien model = new TheThuVien();
        model.setMaThe(rs.getInt("MaThe"));
        model.setMaDG(rs.getInt("MaDG"));
        model.setNgayCap(rs.getDate("NgayCap"));
        model.setNgayhetHan(rs.getDate("NgayHetHan"));
        model.setHinh(rs.getString("Hinh"));
        model.setTinhTrang(rs.getBoolean("Tinhtrang"));
        model.setSoLanMuon(rs.getInt("SolanMuon"));
        return model;
    }

    @Override
    public void insert(TheThuVien entity) {
        String sql = "INSERT INTO TheThuVien(MaDG,NgayCap,Ngayhethan,Hinh) VALUES (?,?,?,?)";
        JDBC.Update(sql, entity.getMaDG(), entity.getNgayCap(), entity.getNgayhetHan(),entity.getHinh());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(TheThuVien entity) {
        String sql = "UPDATE TheThuVien SET MaDG=?,NgayCap=?,Ngayhethan=?,TinhTrang=?,Hinh=?"
                + " WHERE MaThe=?";
        JDBC.Update(sql, entity.getMaDG(), entity.getNgayCap(), entity.getNgayhetHan(),entity.isTinhTrang(),entity.getHinh(),
                entity.getMaThe());
    }
    public void updateSoLanMuon(int mathe){
    String sql = "UPDATE TheThuVien SET solanmuon=solanmuon - 1"
                + " WHERE MaThe=?";
        JDBC.Update(sql, mathe);
    }
     public void updateSoLanMuontang(int mathe){
    String sql = "UPDATE TheThuVien SET solanmuon=solanmuon + 1"
                + " WHERE MaThe=?";
        JDBC.Update(sql, mathe);
    }

    @Override
    public List<TheThuVien> SelectALL() {
        String sql = "select * from TheThuVien ";
        List<TheThuVien> list = selectBySQL(sql);
        return list;
    }

    @Override
    public TheThuVien SelectByID(Integer entity) {
        String sql = "select * from TheThuVien where MaThe=?";
        List<TheThuVien> list = selectBySQL(sql, entity);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<TheThuVien> selectBySQL(String sql, Object... args) {
        List<TheThuVien> list = new ArrayList<TheThuVien>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                TheThuVien ttv = getmodel(rs);
                list.add(ttv);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TheThuVien selectByDG(int name) {
        String sql = "SELECT * FROM TheThuVien WHERE  MaDG = ?";
        List<TheThuVien> list = selectBySQL(sql, name);
        return list.size()>0?list.get(0):null;
    }
    
    public List<TheThuVien> selectByThe(Date name) {
        String sql = "SELECT * FROM TheThuVien WHERE  NgayCap = ?";
        return selectBySQL(sql,  name );
    }
    public TheThuVien selectbyhetHan(int mathe){
        String sql = "Select * from TheThuVien where mathe = ? and TinhTrang = 0";
        List<TheThuVien> list = selectBySQL(sql,mathe);
        return list.size() > 0 ? list.get(0) : null;
    }
}
