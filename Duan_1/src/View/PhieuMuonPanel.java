/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.PhieuMuonCTDAO;
import DAO.PhieuMuonDAO;
import DAO.SachDAO;
import DAO.TheThuVienDAO;
import Helper.Auth;
import Helper.Msgbox;
import Helper.UtilityHelper;
import Model.PhieuMuon;
import Model.PhieuMuonCT;
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
    int row = -1;
    int rowPMCT = -1;

    public PhieuMuonPanel() {
        initComponents();
        init();

    }

    public void init() {
        this.txtmanv.setText(Auth.user.getMaNV());
        this.txtmapm.setText("0");
        txtpmctmapm.setToolTipText("0");
        txtpmctmapm.setText("0");
        updateStatus();
        updateStatusPMCT();
        filltablePhieumuon();
        filltablePMCT();
    }

    void filltablePhieumuon() {
        try {
            DefaultTableModel mol = (DefaultTableModel) tblphieumuon.getModel();
            mol.setRowCount(0);
            List<PhieuMuon> list = dao.selectByMaNV(this.txttimkiem.getText());
            for (PhieuMuon pm : list) {
                mol.addRow(new Object[]{
                    pm.getMaPM(), pm.getMaNV(), pm.getMaThe(), pm.getNgayMuon(), pm.getSoNgayMuon()
                });
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi dữ liệu");
            e.printStackTrace();
        }
    }

    boolean checkform() {
        if (UtilityHelper.checkNull(this.txtmathe, "Mã thẻ")
                || UtilityHelper.checkSoNgay(this.txtsongaymuon)) {
            return true;
        }
        if (ttvdao.SelectByID(Integer.parseInt(txtmathe.getText())) == null) {
            Msgbox.alert(this, "Mã thẻ không tồn tại");
            return true;
        }
        return false;
    }

    public PhieuMuon getformPM() {
        PhieuMuon pm = new PhieuMuon();
        pm.setMaNV(this.txtmanv.getText());
        pm.setMaPM(Integer.parseInt(this.txtmapm.getText()));
        pm.setMaThe(Integer.parseInt(this.txtmathe.getText()));
        pm.setSoNgayMuon(Integer.parseInt(this.txtsongaymuon.getText()));
        return pm;
    }

    public void setform(PhieuMuon pm) {
        this.txtmapm.setText(pm.getMaPM() + "");
        this.txtmanv.setText(pm.getMaNV());
        this.txtmathe.setText(pm.getMaThe() + "");
        this.txtsongaymuon.setText(pm.getSoNgayMuon() + "");
    }

    public void updateStatus() {
        boolean edit = this.row >= 0;
        btnthem.setEnabled(!edit);
        btnsua.setEnabled(edit);
        this.txtmathe.setEnabled(!edit);
    }

    public void clearform() {
        PhieuMuon pm = new PhieuMuon();
        pm.setMaNV(Auth.user.getMaNV());
        setform(pm);
        this.row = -1;
        updateStatus();
    }

    public void edit() {
        int maPM = (Integer) this.tblphieumuon.getValueAt(row, 0);
        PhieuMuon pm = dao.SelectByID(maPM);
        setform(pm);
        this.txtpmctmapm.setText(pm.getMaPM() + "");
        updateStatus();

    }

    public void insert() {
        if (!checkform()) {
            TheThuVien ttv = ttvdao.SelectByID(Integer.parseInt(this.txtmathe.getText()));
            if (ttv.getSoLanMuon() >= 3) {
                Msgbox.alert(this, "Bạn đã mượn đủ 3 quyển");
            } else {
                try {
                    PhieuMuon pm = getformPM();
                    dao.insert(pm);
                    filltablePhieumuon();
                    clearform();
                    Msgbox.alert(this, "Thêm thành công");
                } catch (Exception e) {
                    Msgbox.alert(this, "Thêm thất bại");
                    e.printStackTrace();
                }
            }

        }
    }

    public void update() {
        if (!checkform()) {
            try {
                PhieuMuon pm = getformPM();
                dao.update(pm);
                filltablePhieumuon();
                Msgbox.alert(this, "Thêm thành công");
            } catch (Exception e) {
                Msgbox.alert(this, "Thêm thất bại");
                e.printStackTrace();
            }

        }
    }

    public void filltablePMCT() {
        try {
            DefaultTableModel mol = (DefaultTableModel) this.tblPMCT.getModel();
            mol.setRowCount(0);
            List<PhieuMuonCT> list = ctdao.SelectALL();
            for (PhieuMuonCT ct : list) {
                mol.addRow(new Object[]{
                    ct.getID(), ct.getMaPM(), ct.getMaSach(), ct.getGhiChu()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Msgbox.alert(this, "Lỗi dữ liệu");
        }
    }

    public PhieuMuonCT getformPMCT() {
        PhieuMuonCT ct = new PhieuMuonCT();
        ct.setID(Integer.parseInt(this.txtpmctmapm.getToolTipText()));
        ct.setMaPM(Integer.parseInt(this.txtpmctmapm.getText()));
        ct.setMaSach(Integer.parseInt(this.txtmasach.getText()));
        ct.setGhiChu(this.txtghichu.getText());
        return ct;
    }

    public void setformPMCT(PhieuMuonCT ct) {
        this.txtpmctmapm.setToolTipText(ct.getID() + "");
        this.txtpmctmapm.setText(ct.getMaPM() + "");
        this.txtmasach.setText(ct.getMaSach() + "");
        this.txtghichu.setText(ct.getGhiChu());
    }

    public void updateStatusPMCT() {
        boolean edit = this.rowPMCT >= 0;
        btnpmctThem.setEnabled(!edit);
        btnPmCTSua.setEnabled(edit);
        this.txtmasach.setEnabled(!edit);
    }

    public void clearformPMCT() {
        PhieuMuonCT pm = new PhieuMuonCT();
        pm.setMaPM(Integer.parseInt(this.txtpmctmapm.getText()));
        pm.setID(0);
        setformPMCT(pm);
        this.rowPMCT = -1;
        updateStatusPMCT();
    }

    boolean checkformPMCT() {
        if(Integer.parseInt(this.txtpmctmapm.getText()) <= 0){
             Msgbox.alert(this, "Mời bạn chọn Phiếu mượn trong Bảng Phiếu mượn");
            return true;
        }
        if (UtilityHelper.checkMaSach(txtmasach)) {
            return true;
        }
        if (sdao.SelectByID(Integer.parseInt(this.txtmasach.getText())) == null) {
            Msgbox.alert(this, "Mã sách không tồn tại");
            return true;
        }
        if (ctdao.Selectbymasach(Integer.parseInt(this.txtmasach.getText())) != null) {
            Msgbox.alert(this, "Sách này đã được mượn");
            return true;
        }

        return false;
    }

    void editPMCT() {
        int id = (Integer) this.tblPMCT.getValueAt(rowPMCT, 0);
        PhieuMuonCT ct = ctdao.SelectByID(id);
        setformPMCT(ct);
        updateStatusPMCT();
    }

    public void insertPMCT() {

        if (!checkformPMCT()) {
            TheThuVien ttv = ttvdao.SelectByID(Integer.parseInt(this.txtmathe.getText()));
            if (ttv.getSoLanMuon() >= 3) {
                Msgbox.alert(this, "Đã mượn đủ 3 quyển");
            } else {
                try {
                    PhieuMuonCT ct = getformPMCT();
                    ctdao.insert(ct);
                    ttvdao.updateSoLanMuon(Integer.parseInt(this.txtmathe.getText()));
                    Msgbox.alert(this, "Thêm Thành công");
                    filltablePMCT();
                    clearformPMCT();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void udpatePMCT() {
        try {
                PhieuMuonCT ct = getformPMCT();
                ctdao.updatePMCT(ct);
                Msgbox.alert(this, "Cập nhật Thành công");
                filltablePMCT();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnmoi = new javax.swing.JButton();
        txtmapm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtmathe = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblphieumuon = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txttimkiem = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtmanv = new javax.swing.JTextField();
        txtsongaymuon = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtpmctmapm = new javax.swing.JTextField();
        txtmasach = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnpmctThem = new javax.swing.JButton();
        btnPmCTSua = new javax.swing.JButton();
        btnPMCTLamMoi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPMCT = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtghichu = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ PHIẾU MƯỢN");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        btnthem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnthem.setText("THÊM");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnsua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsua.setText("SỬA");
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

        txtmapm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtmapm.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Mã PM");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Mã thẻ");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Số ngày mượn");

        txtmathe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblphieumuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu mượn", "Mã nhân viên", "Mã thẻ", "Ngày mượn", "Số ngày mượn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblphieumuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblphieumuonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblphieumuon);

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

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
                .addContainerGap()
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem))
                .addGap(6, 6, 6))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btntimkiem, txttimkiem});

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Mã nhân viên");

        txtmanv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtmanv.setEnabled(false);

        txtsongaymuon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(txtmapm, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnthem)
                                        .addGap(87, 87, 87)))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtmathe, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtsongaymuon, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 126, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtmapm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtmathe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtmanv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtsongaymuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnsua)
                    .addComponent(btnmoi))
                .addGap(39, 39, 39)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Phiếu mượn", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        txtpmctmapm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtpmctmapm.setEnabled(false);

        txtmasach.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Mã sách");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Mã PM");

        btnpmctThem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpmctThem.setText("Thêm ");
        btnpmctThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpmctThemActionPerformed(evt);
            }
        });

        btnPmCTSua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPmCTSua.setText("Sửa ");
        btnPmCTSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPmCTSuaActionPerformed(evt);
            }
        });

        btnPMCTLamMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPMCTLamMoi.setText("Làm mới");
        btnPMCTLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPMCTLamMoiActionPerformed(evt);
            }
        });

        tblPMCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Mã Phiếu Mượn", "Mã Sách", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPMCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPMCTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPMCT);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Ghi chú");

        txtghichu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(225, 225, 225)
                                .addComponent(btnpmctThem)
                                .addGap(115, 115, 115)
                                .addComponent(btnPmCTSua, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addComponent(btnPMCTLamMoi))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtmasach, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtpmctmapm, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(90, 90, 90)
                                        .addComponent(jLabel15)
                                        .addGap(39, 39, 39)
                                        .addComponent(txtghichu, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 182, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtpmctmapm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtghichu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtmasach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnpmctThem)
                    .addComponent(btnPmCTSua)
                    .addComponent(btnPMCTLamMoi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu mượn chi tiết", jPanel3);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnPMCTLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPMCTLamMoiActionPerformed
        clearformPMCT();
    }//GEN-LAST:event_btnPMCTLamMoiActionPerformed

    private void tblphieumuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblphieumuonMouseClicked
        this.row = this.tblphieumuon.getSelectedRow();
        edit();
        if (evt.getClickCount() == 2) {
            this.jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_tblphieumuonMouseClicked

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
        clearform();
    }//GEN-LAST:event_btnmoiActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        insert();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        update();
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        filltablePhieumuon();
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void btnpmctThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpmctThemActionPerformed
        insertPMCT();
    }//GEN-LAST:event_btnpmctThemActionPerformed

    private void tblPMCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPMCTMouseClicked
        this.rowPMCT = this.tblPMCT.getSelectedRow();
        editPMCT();
    }//GEN-LAST:event_tblPMCTMouseClicked

    private void btnPmCTSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPmCTSuaActionPerformed
        udpatePMCT();
    }//GEN-LAST:event_btnPmCTSuaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPMCTLamMoi;
    private javax.swing.JButton btnPmCTSua;
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnpmctThem;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblPMCT;
    private javax.swing.JTable tblphieumuon;
    private javax.swing.JTextField txtghichu;
    private javax.swing.JTextField txtmanv;
    private javax.swing.JTextField txtmapm;
    private javax.swing.JTextField txtmasach;
    private javax.swing.JTextField txtmathe;
    private javax.swing.JTextField txtpmctmapm;
    private javax.swing.JTextField txtsongaymuon;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables

}
