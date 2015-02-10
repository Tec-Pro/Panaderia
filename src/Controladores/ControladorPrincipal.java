/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.AplicacionGUI;
import Interfaces.MovimientosGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author alan
 */
public class ControladorPrincipal implements ActionListener{
    
    AplicacionGUI aplicacionGUI;
    MovimientosGUI movimientosGUI;
    
    public ControladorPrincipal(){
         try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        aplicacionGUI = new AplicacionGUI();
        aplicacionGUI.setActionListener(this);
        aplicacionGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        movimientosGUI = new MovimientosGUI();
        
        aplicacionGUI.getEscritorio().add(movimientosGUI);
        
        ControladorMovimientos controladorMovimientos = new ControladorMovimientos(movimientosGUI);
        aplicacionGUI.setVisible(true);
    }
    
    public static void main(String[] args) {
        ControladorPrincipal aplicacion = new ControladorPrincipal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(aplicacionGUI.getBtnMovimientos())){
            movimientosGUI.setVisible(true);
        }
    }
}
