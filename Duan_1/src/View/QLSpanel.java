/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.SachDAO;
import DAO.TheLoaiDAO;
import Helper.Auth;
import Helper.Msgbox;
import Helper.MyQR;
import Helper.UtilityHelper;
import Helper.XDate;
import Helper.XImage;
import Model.DocGia;
import Model.Sach;
import Model.TheLoai;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 84985
 */
public class QLSpanel extends javax.swing.JPanel {

    SachDAO dao = new SachDAO();
    int row = -1;
    TheLoaiDAO tlDao = new TheLoaiDAO();
    DefaultComboBoxModel cbo;

    public QLSpanel() {
        initComponents();
        nhapMaQR();
        this.setBackground(Color.red);
        cbo = (DefaultComboBoxModel) this.cboMaTl.getModel();
        this.txtMaSach.setText("1");
        this.txtTrang.setText("0");
        this.txtGia.setText("0");
        fillTable();
        updateStatus();
        fillcombo();
    }

    public void fillcombo() {
        cbo.removeAllElements();
        List<TheLoai> listTL = tlDao.SelectALL();
        for (TheLoai tl : listTL) {
            cbo.addElement(tl);
        }
    }

    public boolean checkForm() {
        if (UtilityHelper.checkNull(txtTenSach, "Họ Tên")
                || UtilityHelper.checkSoTrang(txtTrang)
                || UtilityHelper.checkNgay(txtNgayNhap)
                || UtilityHelper.checkNull(txtTT, "Tình Trạng")
                || UtilityHelper.checkNull(txtNXB, "NXB")
                || UtilityHelper.checkGia(txtGia)
                || UtilityHelper.checkGia(txtGiamuon)) {
            return true;
        }
        return false;
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tbnSach.getModel();
        model.setRowCount(0);
        try {
            List<Sach> list = dao.selectByTen(this.txtTimKiem.getText());
            for (Sach sa : list) {
                Object[] row = {
                    sa.getMaSach(),
                    sa.getTenSach(),
                    sa.getSoTrang(),
                    sa.getGia(),
                    sa.getNgayNhap(),
                    sa.getGiamuon(),
                    sa.getTinhTrang(),
                    sa.getMaTL(),
                    sa.getNXB(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            Msgbox.alert(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }

    private void setForm(Sach sa) {
        txtMaSach.setText(sa.getMaSach() + "");
        txtTenSach.setText(sa.getTenSach());
        txtTrang.setText(sa.getSoTrang() + "");
        txtNgayNhap.setDate(sa.getNgayNhap());
        txtTT.setText(sa.getTinhTrang());
        txtNXB.setText(sa.getNXB());
        txtGia.setText(sa.getGia() + "");
        txtGiamuon.setText(sa.getGiamuon() + "");
        lblQR.setIcon(XImage.readQR(sa.getMaSach() + ".png"));
        lblQR.setToolTipText(sa.getQR() + ".png");
        TheLoai tl = tlDao.SelectByID(sa.getMaTL());

        cbo.setSelectedItem(tl);

    }

    public Sach getForm() {
        Sach sa = new Sach();
        sa.setMaSach(Integer.parseInt(txtMaSach.getText()));
        sa.setTenSach(txtTenSach.getText());
        sa.setSoTrang(Integer.parseInt(txtTrang.getText()));
        sa.setNgayNhap(txtNgayNhap.getDate());
        sa.setTinhTrang(txtTT.getText());
        sa.setNXB(txtNXB.getText());
        sa.setGia(Float.parseFloat(txtGia.getText()));
        TheLoai tl = (TheLoai) cboMaTl.getSelectedItem();
        System.out.println(tl.getMaTheLoai());
        sa.setMaTL(tl.getMaTheLoai());
        sa.setGiamuon(Float.parseFloat(txtGiamuon.getText()));
        sa.setQR(lblQR.getToolTipText());
        return sa;
    }

    public void updateStatus() {
        boolean edit = row >= 0;
        boolean first = row == 0;
        boolean last = row == tbnSach.getRowCount() - 1;
        btnUp.setEnabled(edit);
        btnAdd.setEnabled(!edit);
    }

    public void edit() {
        int masa = (Integer) tbnSach.getValueAt(row, 0);
        Sach sa = dao.SelectByID(masa);
        setForm(sa);
        updateStatus();
    }

    private void clearForm() {
        nhapMaQR();
        this.txtMaSach.setText("1");
        txtTenSach.setText("");
        txtTrang.setText("");
        txtNgayNhap.setDate(null);
        txtTT.setText("");
        txtNXB.setText("");
        txtGia.setText("");
        txtGiamuon.setText("");
        this.lblQR.setIcon(null);
        this.lblQR.setToolTipText("");
        row = -1;
        updateStatus();
    }

    private void nhapMaQR() {
        try {
            List<Sach> list = dao.selectQRNULl();
            for (Sach s : list) {
                Sach sach = s;
                maQR(sach);
                sach.setQR(lblQR.getToolTipText());
                dao.update(sach);
                System.out.println(s.getMaSach());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insert() {

        if (checkForm()) {
            return;
        } else {
            try {
                Sach sa = this.getForm();
                this.dao.insert(sa);
                this.fillTable();
                clearForm();
                Msgbox.alert(this, "Thêm Thành công");
            } catch (Exception e) {
                Msgbox.alert(this, "Thêm Thất Bại");
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (UtilityHelper.checkNull(txtTenSach, "Tên Sách")) {
            return;
        } else {
            try {
                Sach sa = getForm();
                maQR(sa);
                this.dao.update(sa);
                this.fillTable();

                Msgbox.alert(this, "Update Thành công");

            } catch (Exception e) {
                e.printStackTrace();
                Msgbox.alert(this, "Update Thất Bại");
            }
        }
    }

    public void maQR(Sach sach) {
        try {

            // The data that the QR code will contain
            String data = "Mã Sách : " + sach.getMaSach() + "\n"
                    + "Ten sach : " + sach.getTenSach() + "\n"
                    + "So Trang : " + sach.getSoTrang() + "\n"
                    + "Ngay nhap : " + sach.getNgayNhap() + "\n"
                    + "Tinh Trang: " + sach.getTinhTrang() + "\n"
                    + "NXB  : " + sach.getNXB() + "\n"
                    + "Gia sach : " + sach.getGia() + "\n"
                    + "Gia muon : " + sach.getGiamuon() + "\n"
                    + "The loai : " + cboMaTl.getSelectedItem();

            // The path where the image will get saved
            String path;
            path = "MaQR\\" + sach.getMaSach() + ".png";
            lblQR.setToolTipText(sach.getMaSach() + ".png");

            // Encoding charset
            String charset = "UTF-8";

            Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                    = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

            hashMap.put(EncodeHintType.ERROR_CORRECTION,
                    ErrorCorrectionLevel.L);

            MyQR.createQR(data, path, charset, hashMap, 149, 143);
            System.out.println("QR Code Generated!!! ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTrang = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTT = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnUp = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbnSach = new javax.swing.JTable();
        txtNgayNhap = new com.toedter.calendar.JDateChooser();
        txtNXB = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboMaTl = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtGiamuon = new javax.swing.JTextField();
        lblQR = new javax.swing.JLabel();
        btnAdd1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ SÁCH");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mã Sách");

        txtMaSach.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSach.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Tên Sách");

        txtTenSach.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Số Trang");

        txtTrang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrangActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Ngày Nhập");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Giá sách");

        txtGia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Thể Loại");

        txtTT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Tình Trạng");

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdd.setText("THÊM");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnNew.setText("MỚI");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUp.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnUp.setText("SỬA");
        btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        tbnSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Số trang", "Giá", "Ngày nhập", "Giá mượn", "Tình trạng", "Mã thể loại", "NXB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        tbnSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbnSachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbnSach);

        txtNgayNhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNgayNhap.setDateFormatString("yyyy-MM-dd");

        txtNXB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("NXB");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Giá mượn");

        txtGiamuon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblQR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAdd1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdd1.setText("Thêm Excel");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtTT, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                                .addComponent(cboMaTl, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lblQR, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtTrang)
                                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                            .addComponent(txtNXB, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiamuon, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(btnAdd)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(61, 61, 61)
                                                        .addComponent(btnUp, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnTimKiem)
                                                    .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(393, 393, 393)
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))))
                    .addComponent(lblQR, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addComponent(cboMaTl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtGiamuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNew)
                            .addComponent(btnAdd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAdd1)
                            .addComponent(btnUp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTimKiem)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnTimKiem, txtTimKiem});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrangActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpActionPerformed

        update();
        lblQR.setIcon(XImage.readQR(lblQR.getToolTipText()));

    }//GEN-LAST:event_btnUpActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tbnSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbnSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = this.tbnSach.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tbnSachMouseClicked
   public static String catchuoi(String s){
        String chuoi="";
        String[] split = s.split("\\.");
        chuoi = split[0];
        return chuoi;
    }
    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JFileChooser jfc = new JFileChooser();
        int i = jfc.showOpenDialog(this);
        if (i == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream file = new FileInputStream(jfc.getSelectedFile().getAbsoluteFile());
                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    Sach s = new Sach();
                    s.setTenSach(row.getCell(0).toString());
                    s.setSoTrang(Integer.parseInt(catchuoi(row.getCell(1).toString())));
                    s.setGia(Float.parseFloat(row.getCell(2).toString()));
                    s.setNgayNhap(sdf.parse(row.getCell(3).toString()));
                    s.setTinhTrang(row.getCell(4).toString());
                    s.setMaTL(Integer.parseInt(catchuoi(row.getCell(5).toString())));
                    s.setNXB(row.getCell(6).toString());
                    s.setGiamuon(Float.parseFloat(row.getCell(2).toString()));
                    dao.insert(s);
                }
                wb.close();
                file.close();
                clearForm();
                fillTable();
                Msgbox.alert(this, "Thêm thành công");
            } catch (Exception e) {
                Msgbox.alert(this, "Lỗi File");
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_btnAdd1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUp;
    private javax.swing.JComboBox<String> cboMaTl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblQR;
    private javax.swing.JTable tbnSach;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtGiamuon;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNXB;
    private com.toedter.calendar.JDateChooser txtNgayNhap;
    private javax.swing.JTextField txtTT;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTrang;
    // End of variables declaration//GEN-END:variables
}
