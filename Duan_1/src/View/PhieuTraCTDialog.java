/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.DocGiaDAO;
import DAO.NhanVienDAO;
import DAO.PhieuTraCTDAO;
import DAO.PhieuTraDAO;
import DAO.SachDAO;
import DAO.TheThuVienDAO;
import Model.DocGia;
import Model.NhanVien;
import Model.PhieuTra;
import Model.PhieuTraCT;
import Model.Sach;
import Model.TheThuVien;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 84985
 */
public class PhieuTraCTDialog extends javax.swing.JDialog {

    PhieuTraDAO ptdao = new PhieuTraDAO();
    PhieuTraCTDAO ctdao = new PhieuTraCTDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    TheThuVienDAO ttvdao = new TheThuVienDAO();
    DocGiaDAO dgdao = new DocGiaDAO();
    SachDAO sdao = new SachDAO();

    public PhieuTraCTDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        filltablePT();
    }

    void filltablePT() {
        try {
            DefaultTableModel mol = (DefaultTableModel) this.tblphieumuon.getModel();
            mol.setRowCount(0);
            List<PhieuTra> list = ptdao.SelectALL();
            for (PhieuTra x : list) {
                NhanVien nv = nvdao.SelectByID(x.getMaNV());
                TheThuVien ttv = ttvdao.SelectByID(x.getMaThe());
                DocGia dg = dgdao.SelectByID(ttv.getMaDG());
                mol.addRow(new Object[]{
                    x.getMaPT(), x.getMaNV(), nv.getTenNV(), x.getMaThe(), dg.getTenDG(), x.getNgayThucTra()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void filltablePTCT() {
        try {
            float tongtien = 0;
            DefaultTableModel mol = (DefaultTableModel) this.tblptct.getModel();
            mol.setRowCount(0);
            int i = this.tblphieumuon.getSelectedRow();
            List<PhieuTraCT> list = ctdao.SelectByMaPT((Integer) tblphieumuon.getValueAt(i, 0));
            for (PhieuTraCT x : list) {
                Sach s = sdao.SelectByID(x.getMaSach());
                mol.addRow(new Object[]{
                    x.getMaPT(), x.getMaSach(), s.getTenSach(), s.getGiamuon(), x.getTinhTrang(), x.getTienPhat(), s.getGiamuon() + x.getTienPhat()
                });
                tongtien += (s.getGiamuon() + x.getTienPhat());
            }
            this.lbltongtien.setText(tongtien + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void xuat() {
        JFileChooser jfc = new JFileChooser();
        int j = jfc.showSaveDialog(this);
        if (j == JFileChooser.APPROVE_OPTION) {
            String filename = jfc.getSelectedFile().getAbsolutePath();
            try {
                Workbook workbook = new XSSFWorkbook();

                Sheet sheet = workbook.createSheet("Employee");
                Row headerRow = sheet.createRow(0);
                int rowNum = 1;
                headerRow.createCell(0)
                        .setCellValue("Mã Phiếu trả");
                headerRow.createCell(1)
                        .setCellValue("Mã Sách");
                headerRow.createCell(2)
                        .setCellValue("Tên sách");
                headerRow.createCell(3)
                        .setCellValue("Giá mượn");
                headerRow.createCell(4)
                        .setCellValue("Tình trạng");
                headerRow.createCell(5)
                        .setCellValue("Tiền phạt");
                headerRow.createCell(6)
                        .setCellValue("Tổng tiền");

                for (int i = 0; i < tblptct.getRowCount(); i++) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0)
                            .setCellValue((Integer) tblptct.getValueAt(i, 0));
                    row.createCell(1)
                            .setCellValue((Integer) tblptct.getValueAt(i, 1));
                    row.createCell(2)
                            .setCellValue((String) tblptct.getValueAt(i, 2));
                    row.createCell(3)
                            .setCellValue((Float) tblptct.getValueAt(i, 3));
                    row.createCell(4)
                            .setCellValue((String) tblptct.getValueAt(i, 4));
                    row.createCell(5)
                            .setCellValue((Float) tblptct.getValueAt(i, 5));
                    row.createCell(6)
                            .setCellValue((Float) tblptct.getValueAt(i, 6));
                    
                }
                headerRow = sheet.createRow(4);
                headerRow.createCell(6)
                        .setCellValue("Tổng tiền"+lbltongtien.getText()+"VND");
                FileOutputStream fileOut = new FileOutputStream(new File(filename + ".xlsx"));
                    workbook.write(fileOut);
                    fileOut.close();
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

        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblphieumuon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblptct = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbltongtien = new javax.swing.JLabel();
        btnxuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        tblphieumuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MaPT", "MaNV", "Tên Nhân viên", "Mã thẻ", "Tên độc giả", "Ngày thực trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        jScrollPane2.setViewportView(tblphieumuon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(255, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu trả", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        tblptct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu trả", "Mã Sách", "Tên sách", "Giá mượn", "Tình trạng", "Tiền phạt", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblptct);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Tổng tiền :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("VNĐ");

        lbltongtien.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbltongtien.setText("jLabel3");

        btnxuat.setBackground(new java.awt.Color(0, 255, 255));
        btnxuat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnxuat.setText("Xuất danh sách");
        btnxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnxuat)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbltongtien)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addGap(73, 73, 73))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lbltongtien))
                .addGap(41, 41, 41)
                .addComponent(btnxuat)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phiếu trả chi tiết", jPanel2);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblphieumuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblphieumuonMouseClicked
        filltablePTCT();
        this.jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_tblphieumuonMouseClicked

    private void btnxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxuatActionPerformed
        xuat();
    }//GEN-LAST:event_btnxuatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PhieuTraCTDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhieuTraCTDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhieuTraCTDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhieuTraCTDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PhieuTraCTDialog dialog = new PhieuTraCTDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnxuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbltongtien;
    private javax.swing.JTable tblphieumuon;
    private javax.swing.JTable tblptct;
    // End of variables declaration//GEN-END:variables
}
