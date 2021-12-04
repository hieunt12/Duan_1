/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.DocGiaDAO;
import DAO.NhanVienDAO;
import DAO.PhieuMuonCTDAO;
import DAO.PhieuMuonDAO;
import DAO.SachDAO;
import DAO.TheLoaiDAO;
import DAO.TheThuVienDAO;
import Helper.Auth;
import Helper.Msgbox;
import Helper.UtilityHelper;
import Model.DocGia;
import Model.NhanVien;
import Model.PhieuMuon;
import Model.PhieuMuonCT;
import Model.Sach;
import Model.TheLoai;
import Model.TheThuVien;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 84985
 */
public class PhieuMuonPanel extends javax.swing.JPanel {

    PhieuMuonDAO dao = new PhieuMuonDAO();
    TheThuVienDAO ttvdao = new TheThuVienDAO();
    PhieuMuonCTDAO ctdao = new PhieuMuonCTDAO();
    SachDAO sdao = new SachDAO();
    TheLoaiDAO tldao = new TheLoaiDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    DocGiaDAO dgdao = new DocGiaDAO();
    int row = -1;
    int rowPMCT = -1;

    public PhieuMuonPanel() {
        initComponents();
        init();
    }

    public void init() {
        updateTrangThai();
        filltableSach();
        filltablePhieumuon();
        filltablePhieumuondatra();
        
    }
    public void updateTrangThai(){
        boolean check = true;
         List<PhieuMuon> list = dao.SelectALL();
         for(PhieuMuon pm :list){
             List<PhieuMuonCT> listct = ctdao.selectBymaPM(pm.getMaPM());
             for(PhieuMuonCT ct:listct){
                 if(!ct.isTrangThai()){
                     check = false;
                 }
             }
             if(check){
                 dao.updatetrangthai(pm.getMaPM());
             }
             check = true;
         }
    }

    void filltableSach() {
        try {
            DefaultTableModel mol = (DefaultTableModel) this.tblSach.getModel();
            mol.setRowCount(0);
            List<Sach> list = sdao.selectSach();
            for (Sach s : list) {
                TheLoai tl = tldao.SelectByID(s.getMaTL());
                mol.addRow(new Object[]{
                    s.getMaSach(), s.getTenSach(), s.getSoTrang(), s.getGia(), s.getNgayNhap(), s.getGiamuon(),s.getTinhTrang(), tl.getTenTheLoai(), s.getNXB()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void filltablePhieumuon() {
        try {
            DefaultTableModel mol = (DefaultTableModel) tblphieumuonchuatra.getModel();
            mol.setRowCount(0);
            List<PhieuMuon> list = dao.selectByMaNV(this.txttimkiem.getText());
            for (PhieuMuon pm : list) {
                NhanVien nv = nvdao.SelectByID(pm.getMaNV());
                DocGia dg = dgdao.selectDOcGia(pm.getMaThe());
                mol.addRow(new Object[]{
                    pm.getMaPM(), pm.getMaNV(), nv.getTenNV(), pm.getMaThe(), dg.getTenDG(), pm.getNgayMuon(), pm.getSoNgayMuon(),pm.getTienDatCoc()
                });
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi dữ liệu");
            e.printStackTrace();
        }
    }
     void filltablePhieumuondatra() {
        try {
            DefaultTableModel mol = (DefaultTableModel) tblphieumuondatra.getModel();
            mol.setRowCount(0);
            List<PhieuMuon> list = dao.selectByPMdatra(this.txttimkiem.getText());
            for (PhieuMuon pm : list) {
                NhanVien nv = nvdao.SelectByID(pm.getMaNV());
                DocGia dg = dgdao.selectDOcGia(pm.getMaThe());
                mol.addRow(new Object[]{
                    pm.getMaPM(), pm.getMaNV(), nv.getTenNV(), pm.getMaThe(), dg.getTenDG(), pm.getNgayMuon(), pm.getSoNgayMuon()
                });
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi dữ liệu");
            e.printStackTrace();
        }
    }

    boolean checkform() {
        if (tblSach.getSelectedRows().length <= 0) {
            Msgbox.alert(this, "Chưa chọn sách mượn");
            return true;
        }
        if (UtilityHelper.checkmathe(this.txtmathe)
                || UtilityHelper.checkSoNgay(this.txtsongaymuon) 
              ) {
            return true;
        }
        if (ttvdao.SelectByID(Integer.parseInt(txtmathe.getText())) == null) {
            Msgbox.alert(this, "Mã thẻ không tồn tại");
            return true;
        }
        if (ttvdao.selectbyhetHan(Integer.parseInt(txtmathe.getText())) != null) {
            Msgbox.alert(this, "Thẻ đã hết hạn");
            return true;
        }
        return false;
    }

    public PhieuMuon getformPM() {
        PhieuMuon pm = new PhieuMuon();
        pm.setMaNV(Auth.user.getMaNV());
        pm.setMaThe(Integer.parseInt(this.txtmathe.getText()));
        pm.setSoNgayMuon(Integer.parseInt(this.txtsongaymuon.getText()));
         int[] sachmuon = tblSach.getSelectedRows();
         float tiendatcoc=0;
         for(int i :sachmuon){
             tiendatcoc+=(Float)tblSach.getValueAt(i, 3);
         }
        pm.setTienDatCoc(tiendatcoc/2);
        return pm;
    }


    public void clearform() {
        this.txtmathe.setText("");
        this.txtsongaymuon.setText("");
       
        filltableSach();
    }

    public void insert() {
        if (!checkform()) {
            TheThuVien ttv = ttvdao.SelectByID(Integer.parseInt(this.txtmathe.getText()));
            int[] sachmuon = tblSach.getSelectedRows();
            if (ttv.getSoLanMuon() < sachmuon.length) {
                Msgbox.alert(this, "Bạn còn" + ttv.getSoLanMuon() + " lần mượn!");
            } else {
                if (Msgbox.Confirm(this, "Xác nhận lập phiếu mượn")) {
                    try {
                        PhieuMuon pm = getformPM();
                        dao.insert(pm);
                        PhieuMuon pm1 = dao.selectByTop1();
                        for (int i : sachmuon) {
                            int masach = (Integer) tblSach.getValueAt(i, 0);
                            PhieuMuonCT ct = new PhieuMuonCT();
                            ct.setMaPM(pm1.getMaPM());
                            ct.setMaSach(masach);
                            ctdao.insert(ct);
                            ttvdao.updateSoLanMuon(pm1.getMaThe());
                            jTabbedPane1.setSelectedIndex(1);
                        }
                        filltablePhieumuon();
                        clearform();
                        Msgbox.alert(this, "Lập phiếu mượn thành công");
                    } catch (Exception e) {
                        Msgbox.alert(this, "Thêm thất bại");
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    public void update() {
        try {
            for(int i = 0;i < tblphieumuonchuatra.getRowCount();i++){
                int maPM = (Integer) tblphieumuonchuatra.getValueAt(i, 0);
                int songaymuon =(Integer) tblphieumuonchuatra.getValueAt(i, 6);
                
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPM(maPM);
                pm.setSoNgayMuon(songaymuon);
                dao.update(pm);
               
            }
             Msgbox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            e.printStackTrace();
            Msgbox.alert(this, "Lỗi dữ liệu");
            return;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtmathe = new javax.swing.JTextField();
        txtsongaymuon = new javax.swing.JTextField();
        btnhuy = new javax.swing.JButton();
        btnlapphieumuon = new javax.swing.JButton();
        btnthongtin = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnsua = new javax.swing.JButton();
        btnmoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblphieumuonchuatra = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txttimkiem = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txttimkiem1 = new javax.swing.JTextField();
        btntimkiem1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblphieumuondatra = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ PHIẾU MƯỢN");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        tblSach.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Số trang", "Giá Sách", "Ngày nhập", "Giá mượn", "Tình trạng", "Mã thể loại", "NXB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSach);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã thẻ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Số ngày mượn");

        btnhuy.setBackground(new java.awt.Color(255, 102, 102));
        btnhuy.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnhuy.setText("Hủy");
        btnhuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyActionPerformed(evt);
            }
        });

        btnlapphieumuon.setBackground(new java.awt.Color(51, 153, 255));
        btnlapphieumuon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnlapphieumuon.setText("Lập phiếu mượn");
        btnlapphieumuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlapphieumuonActionPerformed(evt);
            }
        });

        btnthongtin.setText("Thông tin");
        btnthongtin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthongtinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 21, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(28, 28, 28)
                                .addComponent(txtmathe, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnthongtin)
                                .addGap(85, 85, 85)
                                .addComponent(jLabel3)
                                .addGap(76, 76, 76)
                                .addComponent(txtsongaymuon, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(btnlapphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(160, 160, 160))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtmathe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnthongtin)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtsongaymuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(63, 63, 63)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlapphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sách mượn", jPanel4);

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        btnsua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsua.setText("Cập nhật");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        btnmoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnmoi.setText("MỚI");
        btnmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmoiActionPerformed(evt);
            }
        });

        tblphieumuonchuatra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblphieumuonchuatra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu mượn", "Mã nhân viên", "Tên Nhân viên", "Mã thẻ", "Tên độc giả", "Ngày mượn", "Số ngày mượn", "Tiền đặt cọc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblphieumuonchuatra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblphieumuonchuatraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblphieumuonchuatra);

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm Mã Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        txttimkiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btntimkiem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btntimkiem.setText("Tìm");
        btntimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btntimkiem, txttimkiem});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu mượn chưa trả", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm Mã Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        txttimkiem1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btntimkiem1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btntimkiem1.setText("Tìm");
        btntimkiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiem1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(txttimkiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btntimkiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttimkiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblphieumuondatra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblphieumuondatra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu mượn", "Mã nhân viên", "Tên Nhân viên", "Mã thẻ", "Tên độc giả", "Ngày mượn", "Số ngày mượn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblphieumuondatra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblphieumuondatraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblphieumuondatra);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(461, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(160, 160, 160)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(74, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Phiếu mượn đã trả", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblphieumuonchuatraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblphieumuonchuatraMouseClicked

        if (evt.getClickCount() == 2) {
            int row = tblphieumuonchuatra.getSelectedRow();
            int mapm = (Integer) tblphieumuonchuatra.getValueAt(row, 0);
            PhieuMuonCTDialog ct = new PhieuMuonCTDialog(new TrangChu(), true,mapm);
            ct.setVisible(true);
        }

    }//GEN-LAST:event_tblphieumuonchuatraMouseClicked

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        update();
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        filltablePhieumuon();
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
          int i = tblSach.getSelectedColumn();
          System.out.println(i);
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
        filltablePhieumuon();
    }//GEN-LAST:event_btnmoiActionPerformed

    private void btnthongtinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthongtinActionPerformed
        thongtindsdocgia tt = new thongtindsdocgia(new TrangChu(), true);
        tt.setVisible(true);
    }//GEN-LAST:event_btnthongtinActionPerformed

    private void btnlapphieumuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlapphieumuonActionPerformed
        this.insert();
    }//GEN-LAST:event_btnlapphieumuonActionPerformed

    private void btnhuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyActionPerformed
        clearform();
    }//GEN-LAST:event_btnhuyActionPerformed

    private void btntimkiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btntimkiem1ActionPerformed

    private void tblphieumuondatraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblphieumuondatraMouseClicked
         if (evt.getClickCount() == 2) {
            int row = tblphieumuondatra.getSelectedRow();
             System.out.println(row);
            int mapm = (Integer) tblphieumuondatra.getValueAt(row, 0);
            
            PhieuMuonCTDialog ct = new PhieuMuonCTDialog(new TrangChu(), true,mapm);
            ct.setVisible(true);
        }
    }//GEN-LAST:event_tblphieumuondatraMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnhuy;
    private javax.swing.JButton btnlapphieumuon;
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthongtin;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btntimkiem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblSach;
    private javax.swing.JTable tblphieumuonchuatra;
    private javax.swing.JTable tblphieumuondatra;
    private javax.swing.JTextField txtmathe;
    private javax.swing.JTextField txtsongaymuon;
    private javax.swing.JTextField txttimkiem;
    private javax.swing.JTextField txttimkiem1;
    // End of variables declaration//GEN-END:variables

}
