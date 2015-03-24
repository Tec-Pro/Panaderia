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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private final ControladorVentas controladorVentas;

    public ControladorCargarVentas(CargarVentasGUI cv, ControladorVentas controlador) {
        cargarVentaGUI = cv;
        cargarVentaGUI.setActionListener(this);
        controladorVentas = controlador;
        gestionVentas = new GestionVentas();

        cargarVentaGUI.getTxtCodigo().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaKeyReleased(evt);
            }
        });

        cargarVentaGUI.getTablaVenta().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargarVentaGUI.getTxtCodigo().requestFocus();//pongo el cursor sobre el campo del codigo
            }
        });

    }

    private int articuloYaCargado(Integer id) {
        for (int i = 0; i < cargarVentaGUI.getTablaVenta().getRowCount(); i++) {
            if (Integer.valueOf((String) cargarVentaGUI.getTablaVentaDefault().getValueAt(i, 0)).equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private void busquedaKeyReleased(KeyEvent evt) {
        String codigo = cargarVentaGUI.getTxtCodigo().getText();
        if (codigo.length() >= 13) {
            Integer id = Integer.valueOf(codigo.substring(2, 7));//Selecciono solo el id del producto
            int lineaArticulo = articuloYaCargado(id);
            abrirBase();
            Articulo articulo = Articulo.first("codigo = ?", id);
            if (articulo != null) {
                if (lineaArticulo == -1) {
                    Object row[] = new Object[6];
                    row[0] = articulo.getString("codigo");
                    row[1] = articulo.getString("nombre");
                    if (articulo.getString("tipo").equals("PESABLE")) {
                        BigDecimal a = new BigDecimal(codigo.substring(7, 9) + "." + codigo.substring(9, 12));
                        row[2] = a;
                    } else {
                        row[2] = BigDecimal.valueOf(1.00);
                    }
                    row[3] = articulo.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString();
                    row[4] = articulo.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString();
                    cargarVentaGUI.getTablaVentaDefault().addRow(row);
                } else {
                    // Lo que se hace dentro de este else es sumar en uno o en el peso que sea a la cantidad del articulo si ya estaba en el carrito.
                    if (articulo.getString("tipo").equals("PESABLE")) {
                        Double viejaCantidad = new Double(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(lineaArticulo, 2)));
                        BigDecimal viejaCantidadBD = BigDecimal.valueOf(viejaCantidad);
                        BigDecimal uno = new BigDecimal(codigo.substring(7, 9) + "." + codigo.substring(9, 12));
                        BigDecimal nuevaCantidad = viejaCantidadBD.add(uno);
                        cargarVentaGUI.getTablaVentaDefault().setValueAt(nuevaCantidad, lineaArticulo, 2);
                    } else {
                        Double viejaCantidad = new Double(String.valueOf(cargarVentaGUI.getTablaVentaDefault().getValueAt(lineaArticulo, 2)));
                        BigDecimal viejaCantidadBD = BigDecimal.valueOf(viejaCantidad);
                        BigDecimal uno = new BigDecimal(1);
                        BigDecimal nuevaCantidad = viejaCantidadBD.add(uno);
                        cargarVentaGUI.getTablaVentaDefault().setValueAt(nuevaCantidad, lineaArticulo, 2);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(cargarVentaGUI, "Producto no encontrado!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            Base.close();

            cargarVentaGUI.getTxtCodigo().setText("");//limpio el campo de id
            setCellEditor();
            actualizarMonto();
        }
    }

    public Venta ObtenerDatosVenta() {
        abrirBase();
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
        v.setBigDecimal("monto", cargarVentaGUI.getTxtTotal().getText());
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
        cargarVentaGUI.getTxtTotal().setText("");
        cargarVentaGUI.getTxtTotal().setText(total.setScale(2, RoundingMode.CEILING).toString());

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
        if (e.getSource().equals(cargarVentaGUI.getBtnQuitar())) {
            if (cargarVentaGUI.getTablaVenta().getSelectedRow() != -1) {//-1 retorna getSelectedRow si no hay fila seleccionada
                cargarVentaGUI.getTablaVentaDefault().removeRow(cargarVentaGUI.getTablaVenta().getSelectedRow());
                actualizarMonto();
            }
        }
        if (e.getSource().equals(cargarVentaGUI.getBtnRegVenta())) {
            if (DatosOK()) {
                if (gestionVentas.Alta(ObtenerDatosVenta())) {
                    JOptionPane.showMessageDialog(cargarVentaGUI, "Venta registrada exitosamente!");
                    cargarVentaGUI.getTablaVentaDefault().setRowCount(0);
                    cargarVentaGUI.getTxtCodigo().setText("");
                    cargarVentaGUI.getTxtTotal().setText("");
                    controladorVentas.cargarListaVentas();
                } else {
                    JOptionPane.showMessageDialog(cargarVentaGUI, "Ocurrio un error, intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(cargarVentaGUI, "La lista de productos esta vacia.", "Atencion!", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource().equals(cargarVentaGUI.getBtnCancelar())) {
            cargarVentaGUI.getTablaVentaDefault().setRowCount(0);
            cargarVentaGUI.getTxtCodigo().setText("");
            cargarVentaGUI.getTxtTotal().setText("");
        }
        cargarVentaGUI.getTxtCodigo().requestFocus();//pongo el cursor sobre el campo del codigo
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
        cargarVentaGUI.getTxtCodigo().requestFocus();//pongo el cursor sobre el campo del codigo
    }

    @Override
    public void editingCanceled(ChangeEvent e) {
    }
}
