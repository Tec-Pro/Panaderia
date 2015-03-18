/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.GestionArticulos;
import Interfaces.ArticulosGUI;
import Modelos.Articulo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author joako
 */
public class ControladorArticulos implements ActionListener {

    private final ArticulosGUI articulosGui;
    private final GestionArticulos gestionArticulos;
    private boolean mod;

    public ControladorArticulos(ArticulosGUI artGui) {
        articulosGui = artGui;
        articulosGui.setActionListener(this);
        gestionArticulos = new GestionArticulos();
        cargarListaArt();
        articulosGui.getTablaArticulos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (articulosGui.getTablaArticulos().getSelectedRowCount() == 1) {
                    articulosGui.seleccionTabla();
                    cargarDatosElementoSeleccionado();
                } else {
                    articulosGui.limpiarPantalla();
                }
                mod = false;
            }

        });
        
        articulosGui.getTxtBuscador().addKeyListener(new KeyAdapter(){

            @Override
            public void keyReleased(KeyEvent evt){
                cargarListaArt(gestionArticulos.buscarArticulo(articulosGui.getTxtBuscador().getText()));
            }
            
        });
    }

    private void cargarDatosElementoSeleccionado() {
        abrirBase();
        int row = articulosGui.getTablaArticulos().getSelectedRow();
        Articulo a = Articulo.first("codigo = ?", articulosGui.getTablaArticulos().getValueAt(row, 0));
        articulosGui.getTxtCodigo().setText(a.getString("codigo"));
        articulosGui.getTxtDescripcion().setText(a.getString("descripcion"));
        articulosGui.getBoxTipo().setSelectedItem(a.getString("tipo"));
        articulosGui.getTxtNombre().setText(a.getString("nombre"));
        articulosGui.getTxtPrecio().setText(a.getBigDecimal("precio").setScale(2, RoundingMode.CEILING).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(articulosGui.getBtnNuevo())) {
            //Se oprimió el botón nuevo para crear o guardar un artículo.
            switch (articulosGui.getBtnNuevo().getText()) {
                //Limpio la pantalla y habilito el guardado.
                case "Nuevo":
                    articulosGui.limpiarPantalla();
                    articulosGui.habilitarGuardarCancelar();
                    break;
                //Doy de alta o modifico según sea el caso.
                case "Guardar":
                    if (datosOk()) {
                        if (mod) {
                            abrirBase();
                            int row = articulosGui.getTablaArticulos().getSelectedRow();
                            Articulo aux = Articulo.first("codigo = ?", articulosGui.getDefaultTablaArticulos().getValueAt(row, 0));
                            aux.set("nombre", articulosGui.getTxtNombre().getText(), "tipo", articulosGui.getBoxTipo().getSelectedItem(), "precio", articulosGui.getTxtPrecio().getText().replace(",", "."), "descripcion", articulosGui.getTxtDescripcion().getText());
                            gestionArticulos.Modificar(aux);
                            mod = false;
                            JOptionPane.showMessageDialog(articulosGui, "Producto modificado exitosamente!", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                        } else {   
                            Articulo a = new Articulo();
                            a.setString("codigo", articulosGui.getTxtCodigo().getText());
                            a.setString("nombre", articulosGui.getTxtNombre().getText());
                            a.setString("tipo", articulosGui.getBoxTipo().getSelectedItem().toString());
                            a.setBigDecimal("precio", articulosGui.getTxtPrecio().getText().replace(",", "."));
                            a.setString("descripcion", articulosGui.getTxtDescripcion().getText());
                            gestionArticulos.Alta(a);
                            JOptionPane.showMessageDialog(articulosGui, "Producto creado exitosamente!", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                        }
                        articulosGui.habilitarNuevo();
                        cargarListaArt();
                    } else {
                        JOptionPane.showMessageDialog(articulosGui, "Error: Alguno de los campos obligatorios está vacío", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        } else if (e.getSource().equals(articulosGui.getBtnModificar())) {
            mod = true;
            articulosGui.habilitarGuardarCancelar();
            articulosGui.getTxtCodigo().setEnabled(false);
            articulosGui.getBoxTipo().setEnabled(true);
        } else if (e.getSource().equals(articulosGui.getBtnEliminar())) {
            switch (articulosGui.getBtnEliminar().getText()) {
                case "Cancelar":
                    articulosGui.habilitarNuevo();
                    mod = false;
                    break;
                case "Eliminar":
                    Integer resp = JOptionPane.showConfirmDialog(articulosGui, "¿Desea borrar el artículo seleccionado?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        int row = articulosGui.getTablaArticulos().getSelectedRow();
                        Articulo a = Articulo.first("codigo = ?", articulosGui.getDefaultTablaArticulos().getValueAt(row, 0));
                        if (gestionArticulos.Borrar(a)) {
                            cargarListaArt();
                            JOptionPane.showMessageDialog(articulosGui, "Producto eliminado correctamente", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(articulosGui, "Ocurrio un error", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    articulosGui.habilitarNuevo();
                    break;
            }
        }
    }

    private void cargarListaArt(LazyList<Articulo> arts){
        articulosGui.getDefaultTablaArticulos().setRowCount(0);
        abrirBase();
        for (Articulo a : arts) {
            Object[] row = new Object[4];
            row[0] = a.get("codigo");
            row[1] = a.get("nombre");
            row[2] = a.get("precio");
            row[3] = a.get("tipo");
            articulosGui.getDefaultTablaArticulos().addRow(row);
        }
        Base.close();
    }
    
    private void cargarListaArt() {
        LazyList<Articulo> arts = gestionArticulos.listarArticulos();
        abrirBase();
        articulosGui.getDefaultTablaArticulos().setRowCount(0);
        for (Articulo a : arts) {
            Object[] row = new Object[4];
            row[0] = a.get("codigo");
            row[1] = a.get("nombre");
            row[2] = a.get("precio");
            row[3] = a.get("tipo");
            articulosGui.getDefaultTablaArticulos().addRow(row);
        }
        Base.close();
    }

    private boolean datosOk() {
        return (!articulosGui.getTxtCodigo().getText().equals("")
                && !articulosGui.getTxtPrecio().getText().equals("")
                && !articulosGui.getTxtNombre().getText().equals("")
                && !articulosGui.getBoxTipo().getSelectedItem().equals("Seleccionar"));
    }

    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }
}
