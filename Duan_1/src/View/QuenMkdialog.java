/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DAO.NhanVienDAO;
import Helper.Auth;
import Helper.Msgbox;
import Helper.UtilityHelper;
import java.awt.Color;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.swing.JPasswordField;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author 84985
 */
public class QuenMkdialog extends javax.swing.JDialog {

    NhanVienDAO nvDao = new NhanVienDAO();

    public QuenMkdialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public boolean checkNull() {
        String newpass = new String(this.txtNewPass.getText());
        String cfpass = new String(this.txtNewPass1.getText());
        if (UtilityHelper.checkNullPass((JPasswordField) this.txtNewPass, "Mật khẩu mới ")) {

            return true;
        }
        if (!newpass.equals(cfpass)) {
            Msgbox.alert(this, "Mật Khẩu xác nhận không đúng");
            return true;
        }
        return false;
    }
    String newPass = RandomStringUtils.randomNumeric(6);

    public void newCode() throws MessagingException, AddressException {
        String mail = this.txtMail.getText();

        System.out.println(newPass);
        if (mail.trim().isEmpty()) {
            Msgbox.alert(this, "Tên Tài khoản không để trống");
            this.txtMail.setBackground(Color.red);
            return;
        }
        Auth.user = this.nvDao.SelectByMail(mail);
        if (Auth.user == null) {
            Msgbox.alert(this, "Mail không Chính xác");
        } else {
            //Msgbox.alert(this,"Mail chính xác!");
            Auth.user.setMatKhau(newPass);
            this.nvDao.update(Auth.user);
            try {
                Properties p = new Properties();
                p.put("mail.smtp.auth", "true");
                p.put("mail.smtp.starttls.enable", "true");
                p.put("mail.smtp.host", "smtp.mailtrap.io");
                p.put("mail.smtp.port", 587);
                p.put("mail.smtp.ssl.trust", "*");
                String accountName = "9b57d174573ae4";
                String accountPassword = "3c2c0fa99367e9";
                Session s = Session.getInstance(p,
                        new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(accountName, accountPassword);
                    }
                });
                String from = accountName;
                String to = mail;
                String subject = "Mật khẩu mới của bạn";
                String body = "PassWord: " + newPass;
                BodyPart messagePart = new MimeBodyPart();
                messagePart.setText(body);
                Message mess = new MimeMessage(s);
                mess.setFrom(new InternetAddress(from));
                mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mess.setSubject(subject);
                mess.setText(body);
                Transport.send(mess);
                Msgbox.alert(this, "Mã xác nhận mới đã được gửi tới mail của bạn");
                txtCode.setEnabled(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void maXacNhan() {
        String check = txtCode.getText();
        if (check.equalsIgnoreCase(newPass)) {
            txtNewPass.setEnabled(true);
            txtNewPass1.setEnabled(true);
            Msgbox.alert(this, "Mời bạn nhập mật khẩu mới");
        }
        else{
            Msgbox.alert(this, "Mã xác nhận không chính xác");
        }
    }

    public void doiPass() {
        String newpass = new String(this.txtNewPass.getPassword());
        String cfpass = new String(this.txtNewPass1.getPassword());
        if (newpass.trim().isEmpty()) {
            Msgbox.alert(this, "Mật Khẩu không được để trống");
            return;
        } else if (!newpass.equals(cfpass)) {
            Msgbox.alert(this, "Mật Khẩu xác nhận không đúng");
            return;
        } else {
            Auth.user.setMatKhau(new String(this.txtNewPass.getPassword()));
            this.nvDao.update(Auth.user);
            Msgbox.alert(this, "Đổi Mật Khẩu thành công");

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCode = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        txtNewPass = new javax.swing.JPasswordField();
        txtNewPass1 = new javax.swing.JPasswordField();
        btnMail = new javax.swing.JButton();
        btnDone = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUÊN MẬT KHẨU");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Email");

        txtCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCode.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Mật khẩu mới");

        txtMail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mã xác nhận");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Xác nhận mật khẩu mới");

        btnCode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCode.setText("Xác nhận mã");
        btnCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodeActionPerformed(evt);
            }
        });

        btnHuy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnHuy.setText("Hủy ");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        txtNewPass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNewPass.setEnabled(false);

        txtNewPass1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtNewPass1.setEnabled(false);

        btnMail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnMail.setText("Gửi mã");
        btnMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMailActionPerformed(evt);
            }
        });

        btnDone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDone.setText("Xác nhận");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnDone)
                        .addGap(34, 34, 34)
                        .addComponent(btnHuy)
                        .addGap(11, 11, 11))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txtNewPass1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCode, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtNewPass))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMail))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCode))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(14, 14, 14)
                .addComponent(txtNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtNewPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnDone))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        doiPass();
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMailActionPerformed
        try {
            newCode();
        } catch (MessagingException ex) {
            Logger.getLogger(QuenMkdialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnMailActionPerformed

    private void btnCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodeActionPerformed
        maXacNhan();
    }//GEN-LAST:event_btnCodeActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

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
            java.util.logging.Logger.getLogger(QuenMkdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMkdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMkdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMkdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuenMkdialog dialog = new QuenMkdialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCode;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnMail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtMail;
    private javax.swing.JPasswordField txtNewPass;
    private javax.swing.JPasswordField txtNewPass1;
    // End of variables declaration//GEN-END:variables
}
