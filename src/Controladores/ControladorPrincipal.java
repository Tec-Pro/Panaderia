/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.AplicacionGUI;
import Interfaces.ArticulosGUI;
import Interfaces.CargarVentasGUI;
import Interfaces.MovimientosGUI;
import Interfaces.VentasGUI;
import Modelos.Movimiento;
import Modelos.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 *
 * @author alan
 */
public class ControladorPrincipal implements ActionListener{
    
    AplicacionGUI aplicacionGUI;
    MovimientosGUI movimientosGUI;
    CargarVentasGUI cargarVentaGUI;
    VentasGUI ventasGUI;
    
    
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
        ControladorMovimientos controladorMovimientos = new ControladorMovimientos(movimientosGUI);
        
        cargarVentaGUI = new CargarVentasGUI();
        ControladorCargarVentas controladorCargarVentas = new ControladorCargarVentas(cargarVentaGUI);
        
        ventasGUI = new VentasGUI();
        ControladorVentas controladorVentas = new ControladorVentas(ventasGUI);
        
        aplicacionGUI.getEscritorio().add(movimientosGUI);
        aplicacionGUI.getEscritorio().add(cargarVentaGUI);
        aplicacionGUI.getEscritorio().add(ventasGUI);
        
        aplicacionGUI.setVisible(true);
    }
    
    private void ActualizarListaMovimientos(){
        movimientosGUI.getTablaMovimientosDefault().setRowCount(0);
        abrirBase();
        LazyList<Movimiento> lista = Movimiento.findAll();
        
        for(Movimiento m : lista){
            Object[] row = new Object[5];
            row[0] = m.get("id");
            Persona p = Persona.first("id = ?", m.get("usuario_id"));
            row[1] = p.getString("nyap");
            row[2] = m.getString("tipo");
            row[3] = m.getBigDecimal("monto");
            row[4] = dateToMySQLDate(m.getDate("fecha"), true);
            movimientosGUI.getTablaMovimientosDefault().addRow(row);
        }
        Base.close();
    }
    /*paraMostrar == true: retorna la fecha en formato dd/mm/yyyy (formato pantalla)
     * paraMostrar == false: retorna la fecha en formato yyyy/mm/dd (formato SQL)
     */
    public String dateToMySQLDate(Date fecha, boolean paraMostrar) {
        if (paraMostrar) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(fecha);
        }
    }
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }
    
    public static void main(String[] args) {
        ControladorPrincipal aplicacion = new ControladorPrincipal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(aplicacionGUI.getBtnMovimientos())){
            movimientosGUI.setVisible(true);
            ActualizarListaMovimientos();
        }
        if(e.getSource().equals(aplicacionGUI.getBtnCargarVenta())){
            cargarVentaGUI.setVisible(true);
            cargarVentaGUI.getTxtCodigo().requestFocus();
        }
        if(e.getSource().equals(aplicacionGUI.getBtnVentas())){
            ventasGUI.setVisible(true);
        }
    }
}
