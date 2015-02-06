/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.AplicacionGUI;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author alan
 */
public class ControladorPrincipal {
    
    AplicacionGUI aplicacionGUI;
    
    public ControladorPrincipal(){
         try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        aplicacionGUI = new AplicacionGUI();
        aplicacionGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
        aplicacionGUI.setVisible(true);
    }
    
    public static void main(String[] args) {
        ControladorPrincipal aplicacion = new ControladorPrincipal();
    }
}
