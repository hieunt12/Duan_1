/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.DocGiaDAO;
import DAO.PhieuMuonCTDAO;
import DAO.PhieuMuonDAO;
import DAO.PhieuMuonPhieuTraDAO;
import DAO.PhieuTraCTDAO;
import DAO.PhieuTraDAO;
import DAO.SachDAO;
import DAO.TheThuVienDAO;
import Helper.Auth;
import Helper.Msgbox;
import Helper.UtilityHelper;
import Model.PhieuMuon;
import Model.PhieuMuonCT;
import Model.PhieuMuonPhieuTra;
import Model.PhieuTra;
import Model.PhieuTraCT;
import Model.Sach;
import Model.TheThuVien;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 84985
 */
public class PhieuTraPanel extends javax.swing.JPanel {

    PhieuMuonCTDAO ctdao = new PhieuMuonCTDAO();
    PhieuMuonDAO pmdao = new PhieuMuonDAO();
    SachDAO sdao = new SachDAO();
    DocGiaDAO dgdao = new DocGiaDAO();
    TheThuVienDAO ttvdao = new TheThuVienDAO();
    PhieuTraDAO ptdao = new PhieuTraDAO();
    PhieuMuonPhieuTraDAO pmptdao = new PhieuMuonPhieuTraDAO();
    PhieuMuonCTDAO pmdtdao = new PhieuMuonCTDAO();
    PhieuTraCTDAO ptctdao = new PhieuTraCTDAO();
    List<Object[]> listbangtam = new ArrayList<>();
    int row = -1;

    public PhieuTraPanel() {
        initComponents();
        filltablePMCT();
    }

    public void filltablePMCT() {
        try {
            DefaultTableModel mol = (DefaultTableModel) this.tblPMCT.getModel();
            mol.setRowCount(0);
            List<PhieuMuonCT> list = ctdao.selectByTrangThai();
            for (PhieuMuonCT ct : list) {
                PhieuMuon pm = pmdao.SelectByID(ct.getMaPM());
                Sach s = sdao.SelectByID(ct.getMaSach());
                mol.addRow(new Object[]{
                    pm.getMaThe(), ct.getMaPM(), ct.getMaSach(), s.getTenSach(), ct.getGhiChu()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filltablePMCTfind() {
        try {
            DefaultTableModel mol = (DefaultTableModel) this.tblPMCT.getModel();
            mol.setRowCount(0);
            List<PhieuMuonCT> list = ctdao.selectByTrangthaifind(Integer.parseInt(this.txtmapm.getText()));
            for (PhieuMuonCT ct : list) {
                PhieuMuon pm = pmdao.SelectByID(ct.getMaPM());
                Sach s = sdao.SelectByID(ct.getMaSach());
                mol.addRow(new Object[]{
                    pm.getMaThe(), ct.getMaPM(), ct.getMaSach(), s.getTenSach(), ct.getGhiChu()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkmaPM() {
        if (UtilityHelper.checkmaPM(txtmapm)) {
            return true;
        }
        if (ctdao.selectBymaPM(Integer.parseInt(this.txtmapm.getText())).size() == 0) {
            Msgbox.alert(this, "Mã phiếu mượn không tồn tại");
            return true;
        }
        return false;
    }

    public boolean checkform() {
        if (tblPMCT.getSelectedRow() < 0) {
            Msgbox.alert(this, "Phải chọn sách muốn thêm");
            return true;
        }
        
        if (tblbangtam.getRowCount() > 0) {
            int i = this.tblPMCT.getSelectedRow();
            int mathepm = (Integer) tblPMCT.getValueAt(i, 0);
            int mathebangtam = (Integer) tblbangtam.getValueAt(0, 3);
            if (mathepm != mathebangtam) {
                Msgbox.alert(this, "Mã Thẻ Trong Bảng Tạm là " + tblbangtam.getValueAt(0, 3));
                System.out.println(tblbangtam.getValueAt(0, 3) + "   " + tblPMCT.getValueAt(i, 0));
                return true;
            }
        }
        return false;
    }

    public void insertBangTam() {
        if (!checkform()) {
            int i = this.tblPMCT.getSelectedRow();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Object[] o = new Object[11];
            o[0] = (tblPMCT.getValueAt(i, 1));
            o[1] = (Auth.user.getMaNV());
            o[2] = (Auth.user.getTenNV());
            o[3] = (tblPMCT.getValueAt(i, 0));
            TheThuVien ttv = ttvdao.SelectByID((Integer) tblPMCT.getValueAt(i, 0));
            String tenDG = dgdao.SelectByID(ttv.getMaDG()).getTenDG();
            o[4] = (tenDG);
            o[9] = tblPMCT.getValueAt(i, 2);
            o[10] = tblPMCT.getValueAt(i, 3);
            o[5] = (sdf.format(new Date()));
            o[6] = (cbotinhtrang.getSelectedItem());
            Sach s = sdao.SelectByID((Integer) tblPMCT.getValueAt(i, 2));
            o[7] = (s.getGiamuon());
            o[8] = this.cbotinhtrang.getSelectedItem();
            listbangtam.add(o);
            Msgbox.alert(this, "Thêm vào thành công");
            filltableBangTam();
        }
    }

    public void filltableBangTam() {
        DefaultTableModel mol = (DefaultTableModel) this.tblbangtam.getModel();
        mol.setRowCount(0);
        for (Object[] x : listbangtam) {
            mol.addRow(new Object[]{
                x[0], x[1], x[2], x[3], x[4], x[9], x[10], x[5], x[6], x[7], x[8]
            });
        }

    }

    public void xacNhan() {
        if (tblbangtam.getRowCount() == 0) {
            Msgbox.alert(this, "Không có dữ liệu");
        } else {
            try {
                PhieuTra pt = new PhieuTra();
                pt.setMaNV(Auth.user.getMaNV());
                pt.setMaThe((Integer) tblbangtam.getValueAt(0, 3));
                SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-yyyy");
                pt.setNgayThucTra(sdt.parse((String) tblbangtam.getValueAt(0, 7)));
                ptdao.insert(pt);
                for (int i = 0; i < tblbangtam.getRowCount(); i++) {
                    try {
                        PhieuTra pt1 = ptdao.selectTop1();
                        PhieuMuonPhieuTra pmpt = new PhieuMuonPhieuTra();
                        pmpt.setMaPT(pt1.getMaPT());
                        pmpt.setMaPM((Integer) tblbangtam.getValueAt(i, 0));
                        pmptdao.insert(pmpt);
                    } catch (Exception e) {
                        continue;
                    }
                }
                for (int i = 0; i < tblbangtam.getRowCount(); i++) {
                    PhieuTra pt1 = ptdao.selectTop1();
                    PhieuTraCT ct = new PhieuTraCT();
                    ct.setMaphieumuon((Integer) tblbangtam.getValueAt(i, 0));
                    ct.setMaPT(pt1.getMaPT());
                    ct.setMaSach((Integer) tblbangtam.getValueAt(i, 5));
                    ct.setTinhTrang((String) tblbangtam.getValueAt(i, 10));
                    float tienphat = 0;
                    if (ct.getTinhTrang().equalsIgnoreCase("Hỏng sách,Mất sách")) {
                        Sach s = sdao.SelectByID(ct.getMaSach());
                        tienphat += s.getGia();
                        System.out.println("");
                    }
                    PhieuMuon pm = pmdao.SelectByID((Integer) tblbangtam.getValueAt(i, 0));
                    Calendar hethan = Calendar.getInstance();
                    hethan.setTime(pm.getNgayMuon());
                    hethan.add(Calendar.DATE, pm.getSoNgayMuon());
                    Date ngayhethan = hethan.getTime();
                    long quahan = (new Date().getTime() - ngayhethan.getTime()) / (1000 * 60 * 60 * 24);
                    if (quahan > 0) {
                        tienphat += (quahan * 5000);
                    }
                    ct.setTienPhat(tienphat);
                    ptctdao.insert(ct);
                    PhieuMuonCT pmct = new PhieuMuonCT();
                    pmct.setMaPM((Integer) tblbangtam.getValueAt(i, 0));
                    pmct.setMaSach((Integer) tblbangtam.getValueAt(i, 5));
                    pmct.setTrangThai(true);
                    ctdao.updatetrangthai(pmct);
                    ttvdao.updateSoLanMuontang((Integer) tblbangtam.getValueAt(i, 3));
                }
                listbangtam.clear();
                filltableBangTam();
                filltablePMCT();

            } catch (Exception e) {
                e.printStackTrace();
            }   
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtmapm = new javax.swing.JTextField();
        btnhienthi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPMCT = new javax.swing.JTable();
        btnthem = new javax.swing.JButton();
        btnhuy = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cbotinhtrang = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnxoa = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblbangtam = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ PHIẾU TRẢ");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 204));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã thẻ");

        txtmapm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmapmActionPerformed(evt);
            }
        });

        btnhienthi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnhienthi.setText("Hiển thị");
        btnhienthi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhienthiActionPerformed(evt);
            }
        });

        tblPMCT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblPMCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã thẻ", "Mã Phiếu Mượn", "Mã Sách", "Tên sách", "Ghi Chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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

        btnthem.setBackground(new java.awt.Color(0, 204, 255));
        btnthem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnthem.setText("Thêm vào phiếu trả");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnhuy.setBackground(new java.awt.Color(255, 153, 153));
        btnhuy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnhuy.setText("Hủy");
        btnhuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Tình trạng");

        cbotinhtrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bình thường", "Hỏng sách,Mất sách" }));
        cbotinhtrang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cbotinhtrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbotinhtrangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel2)
                .addGap(53, 53, 53)
                .addComponent(txtmapm, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnhienthi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(cbotinhtrang, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(306, 306, 306)
                                .addComponent(btnthem)
                                .addGap(54, 54, 54)
                                .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtmapm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhienthi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbotinhtrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(192, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lập phiếu trả", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        btnxoa.setBackground(new java.awt.Color(255, 153, 153));
        btnxoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnxoa.setText("Xóa ");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        tblbangtam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblbangtam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu mượn", "MaNV", "Tên Nhân viên", "Mã thẻ", "Tên độc giả", "Mã Sách", "Tên Sách", "Ngày thực trả", "Tình trạng", "Giá mượn", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblbangtam);

        jButton1.setBackground(new java.awt.Color(0, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Xác nhận");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Phiếu mượn");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(467, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(82, 82, 82))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu trả", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        row = tblbangtam.getSelectedRow();
        if (row == -1) {
            Msgbox.alert(this, "Phải chọn dòng muốn xóa");
            return;
        }
        listbangtam.remove(row);
        filltableBangTam();
        row = -1;
    }//GEN-LAST:event_btnxoaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        xacNhan();
        PhieuTraCTDialog pt = new PhieuTraCTDialog(new TrangChu(), true);
        pt.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbotinhtrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbotinhtrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbotinhtrangActionPerformed

    private void btnhuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyActionPerformed
        filltablePMCT();
       
        txtmapm.setText("");
        cbotinhtrang.setSelectedIndex(0);
    }//GEN-LAST:event_btnhuyActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        if (Msgbox.Confirm(this, "Xác nhận thêm vào Bảng Tạm")) {
            insertBangTam();
            filltablePMCT();
           
            txtmapm.setText("");
            cbotinhtrang.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void tblPMCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPMCTMouseClicked

    }//GEN-LAST:event_tblPMCTMouseClicked

    private void btnhienthiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhienthiActionPerformed
        if (!checkmaPM()) {
            filltablePMCTfind();
        }
    }//GEN-LAST:event_btnhienthiActionPerformed

    private void txtmapmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmapmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmapmActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PhieuTraCTDialog pt = new PhieuTraCTDialog(new TrangChu(), true);
        pt.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnhienthi;
    private javax.swing.JButton btnhuy;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbotinhtrang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblPMCT;
    private javax.swing.JTable tblbangtam;
    private javax.swing.JTextField txtmapm;
    // End of variables declaration//GEN-END:variables
}
