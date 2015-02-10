/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.MovimientosGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author alan
 */
public class ControladorMovimientos implements ActionListener{

    MovimientosGUI movimientosGUI;
    
    public ControladorMovimientos(MovimientosGUI moviGUI){
        movimientosGUI = moviGUI;
        movimientosGUI.setActionListener(this);
        
        movimientosGUI.getTablaMovimientos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (movimientosGUI.getTablaMovimientos().getSelectedRowCount() == 1){
                    JOptionPane.showMessageDialog(movimientosGUI, "cambio seleccion");
                    movimientosGUI.SeleccioneElementoDeLaTabla();
                }else{
                   JOptionPane.showMessageDialog(movimientosGUI, "no seleccionaste nada");
                   movimientosGUI.LimpiarVentana();
                   movimientosGUI.ApreteBotonGuardarEliminarCancelar();
                }
            }
        });
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(movimientosGUI.getBtnNuevoGuardar())){
            if(movimientosGUI.getBtnNuevoGuardar().getText().equals("Nuevo")){
                movimientosGUI.LimpiarVentana();
                movimientosGUI.ApreteBotonNuevoModificar();
                JOptionPane.showMessageDialog(movimientosGUI, "apretaste btn nuevo");
            }else{
                movimientosGUI.ApreteBotonGuardarEliminarCancelar();
                JOptionPane.showMessageDialog(movimientosGUI, "apretaste btn guardar");
            }
        }
        
        if(e.getSource().equals(movimientosGUI.getBtnModif())){
            movimientosGUI.ApreteBotonNuevoModificar();
            JOptionPane.showMessageDialog(movimientosGUI, "apretaste btn modificar");
        }
        
        if(e.getSource().equals(movimientosGUI.getBtnElim())){
            if(movimientosGUI.getBtnElim().getText().equals("Eliminar")){
                movimientosGUI.ApreteBotonGuardarEliminarCancelar();
                JOptionPane.showMessageDialog(movimientosGUI, "apretaste btn eliminar");
            }else{
                movimientosGUI.ApreteBotonGuardarEliminarCancelar();
                JOptionPane.showMessageDialog(movimientosGUI, "apretaste btn cancelar");
            }
        }
    }
    
}
