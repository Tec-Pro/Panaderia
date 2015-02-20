/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.GestionVentas;
import Interfaces.CargarVentasGUI;
import Modelos.Articulo;
import Modelos.Venta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import panaderia.Triple;

/**
 *
 * @author alan
 */
public class ControladorCargarVentas implements ActionListener, CellEditorListener {

    CargarVentasGUI cargarVentaGUI;
    GestionVentas gestionVentas;

    public ControladorCargarVentas(CargarVentasGUI cv) {
        cargarVentaGUI = cv;
        cargarVentaGUI.setActionListener(this);
        gestionVentas = new GestionVentas();

    }

    public Venta ObtenerDatosVenta() {
        abrirBase();
        BigDecimal cero = new BigDecimal(0);
        LinkedList<Triple> listaArticulos = new LinkedList();
        for (int i = 0; i < cargarVentaGUI.getTablaVentaDefault().getRowCount(); i++) {
            Object idArticulo = cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 0);
            Double doubleCantidad = Double.valueOf(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 2)));
            BigDecimal cantidad = BigDecimal.valueOf(doubleCantidad);
            Double doublePrecioFinal = Double.valueOf(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 3)));
            BigDecimal precioFinal = BigDecimal.valueOf(doublePrecioFinal);
            listaArticulos.add(new Triple(idArticulo, cantidad, precioFinal));
        }
            Venta v = new Venta(listaArticulos);
            v.set("fecha", dateToMySQLDate(Calendar.getInstance().getTime(), false));
            v.setBigDecimal("monto", cargarVentaGUI.getLblTotal().getText());
            return v;
        }

    

    public boolean DatosOK() {
        if (cargarVentaGUI.getTablaVenta().getRowCount() == 0) {
            return false;
        }
        return true;
    }

    public void actualizarMonto() {
        BigDecimal importe;
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < cargarVentaGUI.getTablaVenta().getRowCount(); i++) {
            BigDecimal precio_unit = new BigDecimal(String.valueOf(cargarVentaGUI.getTablaVenta().getValueAt(i, 3)));
            importe = ((BigDecimal) cargarVentaGUI.getTablaVenta().getValueAt(i, 2)).multiply(precio_unit).setScale(2, RoundingMode.CEILING);
            cargarVentaGUI.getTablaVentaDefault().setValueAt(importe, i, 4);
            total = total.add((BigDecimal) cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 4)).setScale(2, RoundingMode.CEILING);
        }
        cargarVentaGUI.getLblTotal().setText(total.setScale(2, RoundingMode.CEILING).toString());

    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }

    public void setCellEditor() {
        for (int i = 0; i < cargarVentaGUI.getTablaVenta().getRowCount(); i++) {
            cargarVentaGUI.getTablaVenta().getCellEditor(i, 2).addCellEditorListener(this);
            cargarVentaGUI.getTablaVenta().getCellEditor(i, 3).addCellEditorListener(this);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       /* if (e.getSource().equals(cargarVentaGUI.getQuitarBtn())) {
            if (cargarVentaGUI.getTablaVenta().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                cargarVentaGUI.getTablaVentaDefault().removeRow(cargarVentaGUI.getTablaVenta().getSelectedRow());
                actualizarMonto();
            }
        }*/
        if (e.getSource().equals(cargarVentaGUI.getBtnRegVenta())) {
            if (DatosOK()) {
                if (gestionVentas.Alta(ObtenerDatosVenta())) {
                    JOptionPane.showMessageDialog(cargarVentaGUI, "Venta registrada exitosamente!");
                } else {
                    JOptionPane.showMessageDialog(cargarVentaGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(cargarVentaGUI, "La lista de productos esta vacia.", "Atencion!", JOptionPane.WARNING_MESSAGE);
            }
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
    public void editingStopped(ChangeEvent e) {
        actualizarMonto();
    }

    @Override
    public void editingCanceled(ChangeEvent e) {}
}
