/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.GestionMovimientos;
import ABMs.GestionPersona;
import Interfaces.MovimientosGUI;
import Modelos.Articulo;
import Modelos.Movimiento;
import Modelos.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author alan
 */
public class ControladorMovimientos implements ActionListener{

    MovimientosGUI movimientosGUI;
    GestionMovimientos gestionMovimientos;
    GestionPersona gestionPersona;
    
    public ControladorMovimientos(MovimientosGUI moviGUI){
        movimientosGUI = moviGUI;
        movimientosGUI.setActionListener(this);
        gestionMovimientos = new GestionMovimientos();
        gestionPersona = new GestionPersona();
        CargarPersonasBox();
        
        movimientosGUI.getTablaMovimientos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (movimientosGUI.getTablaMovimientos().getSelectedRowCount() == 1){
                     movimientosGUI.SeleccioneElementoDeLaTabla();
                     ObtenerDatosMovimientoSeleccionado();
                }else{
                   movimientosGUI.LimpiarVentana();
                   movimientosGUI.ApreteBotonGuardarEliminarCancelar();
                }
            }
        });
        
    }
    
    private void CargarPersonasBox(){
        LinkedList<String> lista = gestionPersona.getNombrePersonas();
        for(String p : lista){
            movimientosGUI.getBoxUsuario().addItem(p);
        }
    }
    
    public boolean DatosObligatoriosOK(){
        if((!movimientosGUI.getBoxUsuario().getSelectedItem().equals("Seleccionar")) && 
           (!movimientosGUI.getBoxTipo().getSelectedItem().equals("Seleccionar")) &&
           (!movimientosGUI.getTxtMonto().getText().equals(""))){
            return true;  
        }else{
            return false;
        }
    }
    
    private boolean FormatoOK(){
        try{
            Double monto = Double.valueOf(movimientosGUI.getTxtMonto().getText());
        } catch (NumberFormatException | ClassCastException e) {
           JOptionPane.showMessageDialog(movimientosGUI, "Error en monto. Solo se admiten numeros. Los decimales se escriben despues de un . (punto) Ej 250.50", "Error de formato", JOptionPane.ERROR_MESSAGE);
           return false;
        }
        
        return true;
    }
    
    public Movimiento ObtenerDatosMovimiento(String id){
        Movimiento m = new Movimiento();
        if(id != null){
            m = gestionMovimientos.getMovimiento(id);
        }
        m.setDate("fecha", dateToMySQLDate(movimientosGUI.getTxtCalendario().getDate(), false));
        Double monto = Double.valueOf(movimientosGUI.getTxtMonto().getText());
        m.setBigDecimal("monto", BigDecimal.valueOf(monto).setScale(2, RoundingMode.CEILING));
        m.setString("usuario_id", gestionPersona.getIdPersona(movimientosGUI.getBoxUsuario().getSelectedItem().toString()));
        m.setString("tipo", movimientosGUI.getBoxTipo().getSelectedItem());
        m.setString("descripcion", movimientosGUI.getTxtDescripcion().getText());
        return m;
    }
    
    private void ActualizarListaMovimientos(){
        movimientosGUI.getTablaMovimientosDefault().setRowCount(0);
        LazyList<Movimiento> lista = gestionMovimientos.getMovimientos();
        abrirBase();
        for(Movimiento m : lista){
            Object[] row = new Object[5];
            row[0] = m.get("id");
            row[1] = gestionPersona.getNombrePersona(m.getString("id"));
            row[2] = m.getString("tipo");
            row[3] = m.getBigDecimal("monto");
            row[4] = dateToMySQLDate(m.getDate("fecha"), true);
            movimientosGUI.getTablaMovimientosDefault().addRow(row);
        }
        Base.close();
    }
    
    public void ObtenerDatosMovimientoSeleccionado(){
        abrirBase();
        int row = movimientosGUI.getTablaMovimientos().getSelectedRow();
        Movimiento m = Movimiento.first("id = ?", movimientosGUI.getTablaMovimientosDefault().getValueAt(row, 0));
        movimientosGUI.getBoxTipo().setSelectedItem(m.getString("tipo"));
        movimientosGUI.getTxtMonto().setText(m.getBigDecimal("monto").setScale(2, RoundingMode.CEILING).toString());
        Date fecha = new Date(dateToMySQLDate(m.getDate("fecha"), true));
        movimientosGUI.getTxtCalendario().setDate(fecha);
        movimientosGUI.getTxtDescripcion().setText(m.getString("descripcion"));
        Persona p = Persona.first("id = ?", m.get("usuario_id"));
        movimientosGUI.getBoxUsuario().setSelectedItem(p.getString("nyap"));
        Base.close();
    }
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(movimientosGUI.getBtnNuevoGuardar())){
            if(movimientosGUI.getBtnNuevoGuardar().getText().equals("Nuevo")){
                movimientosGUI.LimpiarVentana();
                movimientosGUI.ApreteBotonNuevoModificar();
            }else{// boton guardar
                if(DatosObligatoriosOK()){
                    if(FormatoOK()){
                        if(gestionMovimientos.Alta(ObtenerDatosMovimiento(null))){
                            JOptionPane.showMessageDialog(movimientosGUI, "Movimiento registrado exitosamente!", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                            movimientosGUI.ApreteBotonGuardarEliminarCancelar();
                            ActualizarListaMovimientos();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(movimientosGUI, "Falta seleccionar el usuario, el tipo de movimiento o el monto!", "Atencion!", JOptionPane.WARNING_MESSAGE);
                }
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
