/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 84985
 */
public class NhanVienDAO extends DAO<NhanVien, String> {

    private NhanVien getmodel(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setTenNV(rs.getString("TenNV"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        model.setSDT(rs.getString("SDT"));
        model.setCCCD(rs.getString("CCCD"));
        model.setNgaySinh(rs.getDate("NgaySinh"));
        model.setEmail(rs.getString("Email"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        return model;
    }

    @Override
    public void insert(NhanVien entity) {
        //(tennv,sdt,matkhau,ngaysinh)

    }

    @Override
    public void delete(String entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(NhanVien entity) {
        String sql = "UPDATE NHANVIEN SET tennv=?, sdt=?, matkhau=? ,ngaysinh=? ,vaitro=? , cccd=? ,email=?,diachi=?,gioitinh=? "
                + "WHERE manv=? ";
        JDBC.Update(sql, entity.getTenNV(), entity.getSDT(), entity.getMatKhau(), entity.getNgaySinh(), entity.isVaiTro(), entity.getCCCD(),
                entity.getEmail(), entity.getDiaChi(), entity.isGioiTinh(), entity.getMaNV());

    }

    @Override
    public List<NhanVien> SelectALL() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NhanVien SelectByID(String entity) {
        try {
            NhanVien nv = null;
            String sql = "select * from NhanVien where MaNV = ?";
            ResultSet rs = JDBC.query(sql,entity);
            if (rs.next()) {
                nv = getmodel(rs);
            }
            rs.getStatement().close();
            return nv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NhanVien> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
