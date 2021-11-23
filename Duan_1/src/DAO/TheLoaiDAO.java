/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Helper.JDBC;
import Model.NhanVien;
import Model.TheLoai;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class TheLoaiDAO extends DAO<TheLoai, Integer> {

    private TheLoai getmodel(ResultSet rs) throws SQLException {
        TheLoai model = new TheLoai();
        model.setMaTheLoai(rs.getInt("matl"));
        model.setTenTheLoai(rs.getString("tentl"));
        return model;
    }

    @Override
    public void insert(TheLoai entity) {
        String sql = "INSERT INTO THELOAI(TENTL) VALUES (?)";
        JDBC.Update(sql, entity.getTenTheLoai());
    }

    @Override
    public List<TheLoai> SelectALL() {
        String sql = "SELECT * FROM THELOAI";
        return selectBySQL(sql);
    }

    @Override
    public TheLoai SelectByID(Integer entity) {
        String sql = "SELECT * FROM THELOAI WHERE MATL = ?";
        List<TheLoai> list = selectBySQL(sql, entity);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<TheLoai> selectBySQL(String sql, Object... args) {
        List<TheLoai> list = new ArrayList<TheLoai>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                TheLoai tl = getmodel(rs);
                list.add(tl);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(TheLoai entity) {
        String sql = "UPDATE THELOAI SET TENTL = ? WHERE MATL = ?";
        JDBC.Update(sql, entity.getTenTheLoai(), entity.getMaTheLoai());
    }

    public List<TheLoai> selectByTen(String name) {
        String sql = "SELECT * FROM THELOAI WHERE  TENTL like ?";
        return selectBySQL(sql, "%" + name + "%");
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
