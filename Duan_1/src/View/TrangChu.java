/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Helper.Auth;
import Helper.Msgbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 *
 * @author 84985
 */
public class TrangChu extends javax.swing.JFrame {
    
    Border yellow_border = BorderFactory.createMatteBorder(1, 0, 1, 0, Color.yellow);
    Border default_border = BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(46, 49, 49));
    JLabel[] menulable = new JLabel[9];
    
    public TrangChu() {
        initComponents();
        this.setLocationRelativeTo(null);
        ThongTinCaNhan tt = new ThongTinCaNhan();
        tt.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
        ParentPanel.add(tt);
        tt.updateUI();
        Border panel_border = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray);
        addactionToMenu();
        menulable[0] = this.lblmenuitem1;
        menulable[1] = this.lblmenuitem2;
        menulable[2] = this.lblmenuitem3;
        menulable[3] = this.lblmenuitem4;
        menulable[4] = this.lblmenuitem5;
        menulable[5] = this.lblmenuitem6;
        menulable[6] = this.lblmenuitem7;
        menulable[7] = this.lblmenuitem8;
        menulable[8] = this.lblmenuitem9;
        
    }
    
    void setlableBackground(JLabel lable) {
        for (JLabel lbl : menulable) {
            lbl.setBackground(new Color(46, 49, 49));
            lbl.setForeground(Color.white);
        }
        
        lable.setForeground(Color.YELLOW);
    }
    
    void addactionToMenu() {
        Component[] component = jpanel_menu.getComponents();
        for (Component comt : component) {
            if (comt instanceof JLabel) {
                JLabel lable = (JLabel) comt;
                lable.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setlableBackground(lable);
                        
                        switch (lable.getText().trim()) {
                            case "Quản lý sách":
                                ParentPanel.removeAll();
                                QLSpanel s = new QLSpanel();
                                s.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                
                                ParentPanel.add(s);
                                s.updateUI();
                                
                                break;
                            case "Quản lý thể loại":
                                ParentPanel.removeAll();
                                TheLoaiPanel tl = new TheLoaiPanel();
                                tl.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                
                                ParentPanel.add(tl);
                                tl.updateUI();
                                break;
                            case "Quản lý nhân viên":
                                if(!Auth.isManager()){
                                    Msgbox.alert(null,"Quản lý mới được sử dụng");
                                }else{
                                    ParentPanel.removeAll();
                                NhanVienPanel nv = new NhanVienPanel();
                                nv.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                
                                ParentPanel.add(nv);
                                nv.updateUI();
                                }
                                break;
                            case "Quản lý độc giả":
                                ParentPanel.removeAll();
                                DocGiaPanel dg = new DocGiaPanel();
                                dg.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                
                                ParentPanel.add(dg);
                                dg.updateUI();
                                break;
                            case "Quản lý thẻ thư viện":
                                ParentPanel.removeAll();
                                TheThuVienPanel ttv = new TheThuVienPanel();
                                ttv.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                ParentPanel.add(ttv);
                                ttv.updateUI();
                                break;
                            case "Quản lý phiếu mượn":
                                ParentPanel.removeAll();
                                PhieuMuonPanel pm = new PhieuMuonPanel();
                                pm.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                ParentPanel.add(pm);
                                pm.updateUI();
                                break;
                            case "Quản lý phiếu trả":
                                ParentPanel.removeAll();
                                PhieuTraPanel pt = new PhieuTraPanel();
                                pt.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                ParentPanel.add(pt);
                                pt.updateUI();
                                break;
                            case "Thống kê":
                                if(!Auth.isManager()){
                                    Msgbox.alert(null, "Quản lý mới được sử dụng");
                                }else{
                                    ParentPanel.removeAll();
                                ThongKePanel tk = new ThongKePanel();
                                tk.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                ParentPanel.add(tk);
                                tk.updateUI();
                                }
                                break;
                            case "Trang chủ":
                                ParentPanel.removeAll();
                                ThongTinCaNhan tt = new ThongTinCaNhan();
                                tt.setSize(ParentPanel.getWidth(), ParentPanel.getHeight());
                                ParentPanel.add(tt);
                                tt.updateUI();
                                break;
                        }
                    }
                    
                    @Override
                    public void mousePressed(MouseEvent e) {
                    }
                    
                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        lable.setBorder(yellow_border);
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        lable.setBorder(default_border);
                    }
                });
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

        jpanel_menu = new javax.swing.JPanel();
        lblmenuitem1 = new javax.swing.JLabel();
        lblmenuitem2 = new javax.swing.JLabel();
        lblmenuitem3 = new javax.swing.JLabel();
        lblmenuitem4 = new javax.swing.JLabel();
        lblmenuitem5 = new javax.swing.JLabel();
        lblmenuitem6 = new javax.swing.JLabel();
        lblmenuitem7 = new javax.swing.JLabel();
        lblmenuitem9 = new javax.swing.JLabel();
        lblmenuitem8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        ParentPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jpanel_menu.setBackground(new java.awt.Color(51, 51, 51));

        lblmenuitem1.setBackground(new java.awt.Color(255, 255, 255));
        lblmenuitem1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem1.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem1.setText("  Trang chủ");
        lblmenuitem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem2.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem2.setText("  Quản lý sách");
        lblmenuitem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem3.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem3.setText("  Quản lý thể loại");
        lblmenuitem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem4.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem4.setText("  Quản lý độc giả");
        lblmenuitem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem5.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem5.setText("  Quản lý thẻ thư viện");
        lblmenuitem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem6.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem6.setText("  Quản lý phiếu mượn");
        lblmenuitem6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem7.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem7.setText("  Quản lý phiếu trả");
        lblmenuitem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem9.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem9.setText("  Thống kê");
        lblmenuitem9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblmenuitem8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lblmenuitem8.setForeground(new java.awt.Color(255, 255, 255));
        lblmenuitem8.setText("  Quản lý nhân viên");
        lblmenuitem8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanel_menuLayout = new javax.swing.GroupLayout(jpanel_menu);
        jpanel_menu.setLayout(jpanel_menuLayout);
        jpanel_menuLayout.setHorizontalGroup(
            jpanel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblmenuitem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
            .addComponent(lblmenuitem6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblmenuitem8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpanel_menuLayout.setVerticalGroup(
            jpanel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblmenuitem1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblmenuitem2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblmenuitem3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblmenuitem4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblmenuitem5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblmenuitem6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblmenuitem7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblmenuitem8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblmenuitem9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setBackground(new java.awt.Color(255, 255, 51));
        jLabel3.setFont(new java.awt.Font("Verdana", 1, 17)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("QUẢN LÝ THƯ VIỆN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
        );

        ParentPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ParentPanelLayout = new javax.swing.GroupLayout(ParentPanel);
        ParentPanel.setLayout(ParentPanelLayout);
        ParentPanelLayout.setHorizontalGroup(
            ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 978, Short.MAX_VALUE)
        );
        ParentPanelLayout.setVerticalGroup(
            ParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanel_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ParentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanel_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Msgbox.Confirm(this, "Bạn có muốn đăng xuất không ?")) {
            this.dispose();
            Login_form lg = new Login_form();
            lg.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ParentPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jpanel_menu;
    private javax.swing.JLabel lblmenuitem1;
    private javax.swing.JLabel lblmenuitem2;
    private javax.swing.JLabel lblmenuitem3;
    private javax.swing.JLabel lblmenuitem4;
    private javax.swing.JLabel lblmenuitem5;
    private javax.swing.JLabel lblmenuitem6;
    private javax.swing.JLabel lblmenuitem7;
    private javax.swing.JLabel lblmenuitem8;
    private javax.swing.JLabel lblmenuitem9;
    // End of variables declaration//GEN-END:variables
}
