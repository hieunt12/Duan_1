/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.util.Date;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author 84985
 */
public class UtilityHelper {

    public static boolean checkNullPass(JPasswordField txt, String title) {
        String pass = new String(txt.getPassword());
        if (pass.trim().isEmpty()) {
            Msgbox.alert(txt.getRootPane(), title + " Không được để trống");
            return true;
        }
        return false;
    }

    public static boolean checkPass(JPasswordField txt, String title) {
        if (UtilityHelper.checkNullPass(txt, title)) {
            return true;
        }
        String pass = new String(txt.getPassword());
        String mau = "\\w+";
        if (!pass.matches(mau)) {
            Msgbox.alert(txt.getRootPane(), title + "Không được chứa ký tự đặc biệt");
            return true;
        }
        return false;
    }

    public static boolean checkNull(JTextField txt, String title) {
        String pass = txt.getText();
        if (pass.trim().isEmpty()) {
            Msgbox.alert(txt.getRootPane(), title + " Không được để trống");
            return true;
        }
        return false;
    }

    public static boolean checkNgay(JDateChooser txt) {
        Date pass = txt.getDate();
        if (pass == null) {
            Msgbox.alert(txt.getRootPane(), "Ngày Không được để trống");
            return true;
        }
        return false;
    }

    public static boolean checkEmail(JTextField txt) {
        if (UtilityHelper.checkNull(txt, "Email")) {
            return true;
        }
        String mau = "\\w+@\\w+(\\.\\w+){1,2}";
        if (!txt.getText().matches(mau)) {
            Msgbox.alert(txt.getRootPane(), "Email sai định dạng");
            return true;
        }
        return false;
    }

    public static boolean checkSdt(JTextField txt) {
        if (UtilityHelper.checkNull(txt, "SĐT")) {
            return true;
        }
        String mau = "0\\d{9}";
        if (!txt.getText().matches(mau)) {
            Msgbox.alert(txt.getRootPane(), "SDT sai định dạng");
            return true;
        }
        return false;
    }

    public static boolean checkCMND(JTextField txt) {
        if (UtilityHelper.checkNull(txt, "CMND/CCCD")) {
            return true;
        }
        String mau = "\\d{9,12}";
        if (!txt.getText().matches(mau)) {
            Msgbox.alert(txt.getRootPane(), "CMND hoặc CCCD sai định dạng");
            return true;
        }
        return false;
    }

    public static boolean checkGia(JTextField txt) {
        if (UtilityHelper.checkNull(txt, "Giá")) {
            return true;
        }
        try {
            float gia = Float.parseFloat(txt.getText());
            if (gia < 0) {
                Msgbox.alert(txt.getRootPane(), "Giá phải lớn hơn 0");
                return true;
            }
        } catch (Exception e) {
            Msgbox.alert(txt.getRootPane(), "Giá phải là số");
            return true;
        }
        return false;
    }

    public static boolean checkSoTrang(JTextField txt) {
        if (UtilityHelper.checkNull(txt, "Số trang")) {
            return true;
        }
        try {
            int soTrang = Integer.parseInt(txt.getText());
            if (soTrang < 0) {
                Msgbox.alert(txt.getRootPane(), "Số trang phải lớn hơn 0");
                return true;
            }
        } catch (Exception e) {
            Msgbox.alert(txt.getRootPane(), "Số trang phải là số");
            return true;
        }
        return false;
    }
}
