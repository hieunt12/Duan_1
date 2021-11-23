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
import java.util.ArrayList;
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
        String sql = "INSERT INTO NhanVien(MANV,TENNV,SDT,MATKHAU,NGAYSINH,VAITRO,CCCD,EMAIL,DIACHI,"
                + "GIOITINH) VALUES (?,?,?,?,?,?,?,?,?,?)";
        JDBC.Update(sql, entity.getMaNV(),entity.getTenNV(),entity.getSDT(), entity.getMatKhau(), entity.getNgaySinh(),
                entity.isVaiTro(), entity.getCCCD(), entity.getEmail(), entity.getDiaChi(), entity.isGioiTinh());
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
        String sql = "SELECT * FROM NHANVIEN";
        return selectBySQL(sql);
    }

    @Override
    public NhanVien SelectByID(String entity) {
        try {
            NhanVien nv = null;
            String sql = "select * from NhanVien where MaNV = ?";
            ResultSet rs = JDBC.query(sql, entity);
            if (rs.next()) {
                nv = getmodel(rs);
            }
            rs.getStatement().close();
            return nv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NhanVien SelectByMail(String entity) {
        try {
            NhanVien nv = null;
            String sql = "select * from NhanVien where Email = ?";
            ResultSet rs = JDBC.query(sql, entity);
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
         List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString(1));
                entity.setTenNV(rs.getString(2));
                entity.setSDT(rs.getString(3));
                entity.setMatKhau(rs.getString(4));
                entity.setNgaySinh(rs.getDate(5));
                entity.setVaiTro(rs.getBoolean(6));
                entity.setCCCD(rs.getString(7));
                entity.setEmail(rs.getString(8));
                entity.setDiaChi(rs.getString(9));
                entity.setGioiTinh(rs.getBoolean(10));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NhanVien> selectByTen(String name){
        String sql = "SELECT * FROM NHANVIEN WHERE  TENNV Like ?";
        return selectBySQL(sql, "%"+name+"%");
    }
}
