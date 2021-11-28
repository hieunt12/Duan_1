/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.DocGiaDAO;
import DAO.TheThuVienDAO;
import Helper.Msgbox;
import Helper.UtilityHelper;
import Helper.XImage;
import Model.TheLoai;
import Model.TheThuVien;
import com.toedter.calendar.JCalendar;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 84985
 */
public class TheThuVienPanel extends javax.swing.JPanel {

    TheThuVienDAO dao = new TheThuVienDAO();
    int row = -1;
    DocGiaDAO dgdao = new DocGiaDAO();

    public TheThuVienPanel() {
        initComponents();
        updateTinhTrang();
        fillTable();
        updateStatus();
        this.lbllogo.setIcon(XImage.read("noImage.png"));
        this.lbllogo.setToolTipText("noImage.png");
        this.txtMaThe.setText("0");
        
    }
    public void updateTinhTrang(){
        List<TheThuVien> list = dao.SelectALL();
        for(TheThuVien ttv : list){         
            Calendar ca2 = Calendar.getInstance();      
            Calendar ca1 = Calendar.getInstance();    
            ca2.setTime(ttv.getNgayhetHan());
            ca1.setTime(new Date());
            if(ca2.before(ca1)){
                ttv.setTinhTrang(false);
                dao.update(ttv);
            }
        }
    }

    public boolean checkForm() {
        if (UtilityHelper.checkNull(txtMaDG, "Mã độc giả")
                || UtilityHelper.checkNgay(txtNgayCap)) {
            return true;
        }
        if (dgdao.SelectByID(Integer.parseInt(this.txtMaDG.getText())) == null) {
            Msgbox.alert(this, "Độc giả không tồn tại");
            return true;
        }
//        
        return false;
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tbnTTV.getModel();
        model.setRowCount(0);
        try {

            List<TheThuVien> list = dao.SelectALL();
            for (TheThuVien ttv : list) {
                Object[] row = {
                    ttv.getMaThe(),
                    ttv.getMaDG(),
                    ttv.getNgayCap(),
                    ttv.getNgayhetHan(),
                    ttv.getHinh(),
                    ttv.isTinhTrang() ? "Còn hạn" : "Hết hạn"};
                model.addRow(row);
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }

    private void setForm(TheThuVien ttv) {
        txtMaThe.setText(ttv.getMaThe() + "");
        txtMaDG.setText(ttv.getMaDG() + "");
        txtNgayCap.setDate(ttv.getNgayCap());
        txtNgayHet.setDate(ttv.getNgayhetHan());
        if (ttv.getHinh() != null) {
            this.lbllogo.setIcon(XImage.read(ttv.getHinh()));
            this.lbllogo.setToolTipText(ttv.getHinh());

        } else {
            this.lbllogo.setIcon(XImage.read("noImage.png"));
            this.lbllogo.setToolTipText("noImage.png");
        }

    }

    public TheThuVien getForm() {
        TheThuVien ttv = new TheThuVien();
        int mathe = Integer.parseInt(this.txtMaThe.getText());
        ttv.setMaThe(mathe);
        ttv.setMaDG(Integer.parseInt(txtMaDG.getText()));
        ttv.setNgayCap(txtNgayCap.getDate());
        ttv.setNgayhetHan(txtNgayHet.getDate());
        ttv.setHinh(this.lbllogo.getToolTipText());
        System.out.println(ttv.getHinh());
        return ttv;
    }

    public void updateStatus() {
        boolean edit = row >= 0;
        boolean first = row == 0;
        boolean last = row == tbnTTV.getRowCount() - 1;
        txtMaDG.setEnabled(!edit);
        btnAdd.setEnabled(!edit);
        txtNgayHet.setEnabled(edit);
        btnUp.setEnabled(edit);
        txtNgayCap.setEnabled(!edit);

    }

    public void edit() {
        int mattv = (Integer) tbnTTV.getValueAt(row, 0);
        TheThuVien ttv = dao.SelectByID(mattv);
        setForm(ttv);
        updateStatus();
    }

    private void clearForm() {
        TheThuVien ttv = new TheThuVien();
        setForm(ttv);
        row = -1;
        updateStatus();
    }

    private void insert() {
        if (checkForm()) {
            return;
        } else {

            int madg = Integer.parseInt(txtMaDG.getText());
            if (dao.selectByDG(madg) != null) {
                Msgbox.alert(this, "Độc giả đã được đăng ký");
                return;
            } else {
                try {
                    TheThuVien ttv = this.getForm();
                    Calendar ngayhethan = Calendar.getInstance();
                    ngayhethan.setTime(txtNgayCap.getDate());
                    ngayhethan.add(Calendar.MONTH, 6);
                    ttv.setNgayhetHan(ngayhethan.getTime());
                    this.dao.insert(ttv);
                    this.fillTable();
                    this.clearForm();
                    Msgbox.alert(this, "Thêm Thành công");                 
                } catch (Exception e) {
                    Msgbox.alert(this, "Thêm Thất Bại");
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        if (UtilityHelper.checkNgay(txtNgayHet)) {
            return;
        } else {
            Calendar ca1 = Calendar.getInstance();
            Calendar ca2 = Calendar.getInstance();
            ca1.setTime(txtNgayCap.getDate());
            ca2.setTime(txtNgayHet.getDate());
            if (ca2.before(ca1)) {
                Msgbox.alert(this, "Ngày hết phải sau ngày cấp");
                return;
            }
            try {
                TheThuVien ttv = getForm();
                dao.update(ttv);
                this.fillTable();
                Msgbox.alert(this, "Update Thành công");
            } catch (Exception e) {
                e.printStackTrace();
                Msgbox.alert(this, "Update Thất Bại");

            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaThe = new javax.swing.JTextField();
        txtMaDG = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbllogo = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        txtNgayCap = new com.toedter.calendar.JDateChooser();
        txtNgayHet = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbnTTV = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ THẺ THƯ VIỆN");

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Thẻ thư viện");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Mã độc giả");

        txtMaThe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMaThe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaThe.setEnabled(false);

        txtMaDG.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMaDG.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Ngày cấp thẻ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Ngày hết hạn");

        lbllogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbllogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbllogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lbllogo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdd.setText("THÊM");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUp.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUp.setText("SỬA");
        btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnNew.setText("MỚI");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        txtNgayCap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNgayCap.setDateFormatString("yyyy-MM-dd");

        txtNgayHet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNgayHet.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(74, 74, 74)
                        .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4))
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaThe, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                            .addComponent(txtMaDG)
                            .addComponent(txtNgayCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayHet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtMaThe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtMaDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addComponent(jLabel8))
                            .addComponent(txtNgayCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txtNgayHet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUp)
                    .addComponent(btnAdd)
                    .addComponent(btnNew))
                .addContainerGap())
        );

        tbnTTV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã thẻ", "Mã độc giả", "Ngày cấp thẻ", "Ngày hết hạn", "Hình", "TinhTrang"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbnTTV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbnTTVMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbnTTV);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        insert();

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpActionPerformed
        update();
    }//GEN-LAST:event_btnUpActionPerformed

    private void tbnTTVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbnTTVMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = this.tbnTTV.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tbnTTVMouseClicked

    private void lbllogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbllogoMouseClicked
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileName = jfc.getSelectedFile();
            XImage.Save(fileName);
            ImageIcon icon = XImage.read(fileName.getName());
            this.lbllogo.setIcon(icon);
            this.lbllogo.setToolTipText(fileName.getName());
        }
    }//GEN-LAST:event_lbllogoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnUp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbllogo;
    private javax.swing.JTable tbnTTV;
    private javax.swing.JTextField txtMaDG;
    private javax.swing.JTextField txtMaThe;
    private com.toedter.calendar.JDateChooser txtNgayCap;
    private com.toedter.calendar.JDateChooser txtNgayHet;
    // End of variables declaration//GEN-END:variables
}
