/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.DocGia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class DocGiaDAO extends DAO<DocGia, Integer> {

    private DocGia getmodel(ResultSet rs) throws SQLException {
        DocGia model = new DocGia();
        model.setMaDG(rs.getInt("MaDG"));
        model.setTenDG(rs.getString("TenDG"));
        model.setNgaySinh(rs.getDate("NgaySinh"));
        model.setCCCD(rs.getString("CCCD"));
        model.setEmail(rs.getString("Email"));
        model.setGioiTinh(rs.getBoolean("gioitinh"));
        model.setSDT(rs.getString("SDT"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setMaNV(rs.getString("MaNV"));
        return model;
    }

    @Override
    public void insert(DocGia entity) {
        String sql = "INSERT INTO DocGia(TenDG,NgaySinh,CCCD,Email,gioitinh,SDT,DiaChi,MaNV"
                + ") VALUES (?,?,?,?,?,?,?,?)";
        JDBC.Update(sql, entity.getTenDG(), entity.getNgaySinh(), entity.getCCCD(), entity.getEmail(),
                entity.isGioiTinh(), entity.getSDT(), entity.getDiaChi(), entity.getMaNV());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(DocGia entity) {
        String sql = "UPDATE DocGia SET TenDG=?, NgaySinh=?, CCCD=? ,Email=? ,gioitinh=? , SDT=? ,DiaChi=? ,MaNV=? "
                + "WHERE MaDG=? ";
        JDBC.Update(sql, entity.getTenDG(), entity.getNgaySinh(), entity.getCCCD(), entity.getEmail(), entity.isGioiTinh(),
                entity.getSDT(), entity.getDiaChi(), entity.getMaNV(), entity.getMaDG());
    }

    @Override
    public List<DocGia> SelectALL() {
        String sql = "select * from DocGia ";
        List<DocGia> list = selectBySQL(sql);
        return list;
    }

    @Override
    public DocGia SelectByID(Integer entity) {
        String sql = "select * from DocGia where MaDG=?";
        List<DocGia> list = selectBySQL(sql, entity);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<DocGia> selectBySQL(String sql, Object... args) {
        List<DocGia> list = new ArrayList<DocGia>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                DocGia dg = getmodel(rs);

                list.add(dg);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<DocGia> selectByTen(String name) {
        String sql = "SELECT * FROM DocGia WHERE  TENDG Like ?";
        return selectBySQL(sql, "%" + name + "%");
    }

}
