/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author 84985
 */
public class Msgbox {
    
    public static void alert(Component parent,String message){
        JOptionPane.showMessageDialog(parent, message,"Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean Confirm(Component parent,String message){
       int result =  JOptionPane.showConfirmDialog(parent, message,"Xác nhận",
               JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       return result == JOptionPane.YES_OPTION;
    }
    
    public static String input(Component parent,String message){
        String value = JOptionPane.showInputDialog(parent,message,"Nhập thông tin",JOptionPane.INFORMATION_MESSAGE);
        return value;
    }
}
