/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ABMs.GestionArticulos;
import Interfaces.ArticulosGUI;
import Modelos.Articulo;
import Modelos.Movimiento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public ControladorArticulos(ArticulosGUI artGui){
        articulosGui = artGui;
        articulosGui.setActionListener(this);
        gestionArticulos = new GestionArticulos();
        cargarListaArt();
        articulosGui.getTablaArticulos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            });
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(articulosGui.getBtnNuevo())){
            //Se oprimió el botón nuevo para crear un nuevo artículo.
            switch (articulosGui.getBtnNuevo().getText()) {
                case "Nuevo":
                      articulosGui.limpiarPantalla();
                      articulosGui.habilitarGuardarCancelar();
                    break;
                case "Guardar":
                    if (mod){
                        abrirBase();
                        int row = articulosGui.getTablaArticulos().getSelectedRow();
              //          Articulo aux = Articulo.first("id = ?", articulosGui.getTablaArticulosDefault().getValueAt(row, 0));
                   //     aux.set("nombre",articulosGui.getTxtArticulo(),"tipo",articulosGui.getBoxTipo().getSelectedItem(),"precio",articulosGui.getTxtPrecio(),"descripcion",articulosGui.getTxtDescripcion());
                  //      gestionArticulos.Modificar(aux);
                 //       articulosGui.ApretarEliminarCancelarGuardar();
                        mod = false;
                    } else{
                        abrirBase();
                        gestionArticulos.Alta(Articulo.create("nombre",articulosGui.getTxtNombre(),"tipo",articulosGui.getBoxTipo().getSelectedItem().toString(),"id",articulosGui.getTxtCodigo(),"precio",articulosGui.getTxtPrecio(),"descripcion",articulosGui.getTxtDescripcion()));
                        articulosGui.habilitarNuevo();
                    }   
                    break;
            }
        } else if (e.getSource().equals(articulosGui.getBtnModificar())){
            mod = true;
    //        articulosGui.habilitarGuardarCancelar();
        } else if (e.getSource().equals(articulosGui.getBtnEliminar())){
            switch(articulosGui.getBtnEliminar().getText()){
                case "Cancelar":
                    articulosGui.habilitarNuevo();
                    mod = false;
                    break;
                case "Eliminar":
                    Integer resp = JOptionPane.showConfirmDialog(articulosGui, "¿Desea borrar el movimiento seleccionado?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    int row = articulosGui.getTablaArticulos().getSelectedRow();
            //        Articulo a = gestionArticulos.getArticulo(String.valueOf(articulosGui.getTablaArticulosDefault().getValueAt(row, 0)));
             //       if(gestionArticulos.Borrar(a)){
            //            cargarListaArt();
            //            JOptionPane.showMessageDialog(articulosGui, "Movimiento eliminado correctamente", "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
            //        }else{
             //           JOptionPane.showMessageDialog(articulosGui, "Ocurrio un error", "Error", JOptionPane.ERROR_MESSAGE);
             //       }
                }
             //   articulosGui.ApretarEliminarCancelarGuardar();
                break;
            }
        }
    }

    private void cargarListaArt() {
        LazyList<Articulo> arts = gestionArticulos.listarArticulos();
        abrirBase();
        for (Articulo a: arts){
            Object[] row = new Object[4];
            row[0] = a.get("id");
            row[1] = a.get("nombre");
            row[2] = a.get("tipo");
            row[3] = a.get("precio");
        //    articulosGui.getTablaArticulosDefault().addRow(row);
        }
        Base.close();
    }
    
    
        public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }
}
