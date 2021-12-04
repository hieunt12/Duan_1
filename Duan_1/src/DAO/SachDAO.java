package DAO;

import Helper.JDBC;
import Model.Sach;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 84985
 */
public class SachDAO extends DAO<Sach, Integer> {

    private Sach getmodel(ResultSet rs) throws SQLException {
        Sach model = new Sach();
        model.setMaSach(rs.getInt("MaSach"));
        model.setTenSach(rs.getString("TenSach"));
        model.setSoTrang(rs.getInt("SoTrang"));
        model.setGia(rs.getFloat("Gia"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        model.setNXB(rs.getString("NXB"));
        model.setMaTL(rs.getInt("MaTL"));
        model.setTinhTrang(rs.getString("TinhTrang"));
        model.setGiamuon(rs.getFloat("GiaMuon"));
        model.setQR(rs.getString("QRCode"));
        return model;
    }

    @Override
    public void insert(Sach entity) {
        String sql = "INSERT INTO Sach(Masach,TenSach,SoTrang,Gia,NgayNhap,TinhTrang,MaTL,NXB,GiaMuon,QRCode"
                + ") VALUES (?,?,?,?,?,?,?,?,?,?)";
        JDBC.Update(sql, entity.getMaSach(),entity.getTenSach(), entity.getSoTrang(), entity.getGia(),
                entity.getNgayNhap(), entity.getTinhTrang(), entity.getMaTL(), entity.getNXB(),entity.getGiamuon(),entity.getQR());
    }

    @Override
    public void delete(Integer entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Sach entity) {
        String sql = "UPDATE Sach SET TenSach=?,SoTrang=?,Gia=?,NgayNhap=?,TinhTrang=?,MaTL=?,NXB=?,GiaMuon=?,QRCode=? "
                + "WHERE MaSach=? ";
        JDBC.Update(sql, entity.getTenSach(), entity.getSoTrang(), entity.getGia(),
                entity.getNgayNhap(), entity.getTinhTrang(), entity.getMaTL(), entity.getNXB(),entity.getGiamuon(),entity.getQR(), entity.getMaSach());
    }
    
    @Override
    public List<Sach> SelectALL() {
        String sql = "select * from Sach ";
        List<Sach> list = selectBySQL(sql);
        return list;
    }

    @Override
    public Sach SelectByID(Integer entity) {
        String sql = "select * from Sach where MaSach=?";
        List<Sach> list = selectBySQL(sql, entity);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Sach> selectBySQL(String sql, Object... args) {
        List<Sach> list = new ArrayList<Sach>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                Sach sa = getmodel(rs);
                list.add(sa);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Sach> selectByTen(String name) {
        String sql = "SELECT * FROM Sach WHERE  TenSach Like ?";
        return selectBySQL(sql, "%" + name + "%");
    }
    public List<Sach> selectSach(){
        String sql = "Select * from Sach where MaSach not in(select masach from PhieuMuonCT where TrangThai = 0)";
        return selectBySQL(sql);
    }
     public Sach selectTop1() {
        String sql = "SELECT top 1* FROM Sach order by masach desc";
       List<Sach> list = selectBySQL(sql);
        return list.size() > 0 ? list.get(0) : null;
    }
}
