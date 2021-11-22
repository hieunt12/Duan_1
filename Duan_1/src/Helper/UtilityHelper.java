/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.awt.Color;
import javax.swing.JPasswordField;

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
}
