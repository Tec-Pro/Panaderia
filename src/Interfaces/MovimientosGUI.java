/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alan
 */
public class MovimientosGUI extends javax.swing.JInternalFrame {

    DefaultTableModel tablaMovimientosDefault;

    public MovimientosGUI() {
        initComponents();
        boxTipo.setEnabled(false);
        boxUsuario.setEnabled(false);
        txtCalendario.setEnabled(false);
        txtDescripcion.setEditable(false);
        txtMonto.setEditable(false);
        txtCalendario.setDate(Calendar.getInstance().getTime());
        tablaMovimientosDefault = (DefaultTableModel) tablaMovimientos.getModel();

       
        txtDesde.setToolTipText("Ver movimientos desde la fecha");
        txtDesde.setDateFormatString("dd/MM/yyyy");
        txtHasta.setToolTipText("Ver movimientos hasta la fecha");
        txtHasta.setDateFormatString("dd/MM/yyyy");
        txtCalendario.setDateFormatString("dd/MM/yyyy");
        txtDesde.getDateEditor().setEnabled(false);
        txtHasta.getDateEditor().setEnabled(false);
        txtHasta.setDate(Calendar.getInstance().getTime());

        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = "01/01/2015";
        Date fecha = null;
        try {

            fecha = formatoDelTexto.parse(strFecha);

        } catch (ParseException ex) {

            ex.printStackTrace();

        }
        txtDesde.setDate(fecha);

    }

    public void LimpiarVentana() {
        boxTipo.setSelectedIndex(0);
        boxUsuario.setSelectedIndex(0);
        txtCalendario.setDate(Calendar.getInstance().getTime());
        txtDescripcion.setText("");
        txtMonto.setText("");
    }

    public void ApreteBotonNuevoModificar() {
        boxTipo.setEnabled(true);
        boxUsuario.setEnabled(true);
        txtCalendario.setEnabled(true);
        txtDescripcion.setEditable(true);
        txtMonto.setEditable(true);
        btnNuevoGuardar.setText("Guardar");
        btnModif.setEnabled(false);
        btnElim.setText("Cancelar");
        btnElim.setEnabled(true);
    }

    public void ApreteBotonGuardarEliminarCancelar() {
        LimpiarVentana();
        boxTipo.setEnabled(false);
        boxUsuario.setEnabled(false);
        txtCalendario.setEnabled(false);
        txtDescripcion.setEditable(false);
        txtMonto.setEditable(false);
        btnNuevoGuardar.setText("Nuevo");
        btnModif.setEnabled(false);
        btnElim.setText("Eliminar");
        btnElim.setEnabled(false);
    }

    public void SeleccioneElementoDeLaTabla() {
        boxTipo.setEnabled(false);
        boxUsuario.setEnabled(false);
        txtCalendario.setEnabled(false);
        txtDescripcion.setEditable(false);
        txtMonto.setEditable(false);
        btnNuevoGuardar.setText("Nuevo");
        btnModif.setEnabled(true);
        btnElim.setText("Eliminar");
        btnElim.setEnabled(true);
    }

    public void setActionListener(ActionListener ac) {
        btnNuevoGuardar.addActionListener(ac);
        btnModif.addActionListener(ac);
        btnElim.addActionListener(ac);
        btnAgregarPersona.addActionListener(ac);
    }

    public JComboBox getBoxTipo() {
        return boxTipo;
    }

    public DefaultTableModel getTablaMovimientosDefault() {
        return tablaMovimientosDefault;
    }

    public JTable getTablaMovimientos() {
        return tablaMovimientos;
    }

    public JComboBox getBoxUsuario() {
        return boxUsuario;
    }

    public JButton getBtnAgregarPersona() {
        return btnAgregarPersona;
    }

    public JButton getBtnElim() {
        return btnElim;
    }

    public JButton getBtnModif() {
        return btnModif;
    }

    public JButton getBtnNuevoGuardar() {
        return btnNuevoGuardar;
    }

    public JComboBox getBusquedaBoxTipo() {
        return busquedaBoxTipo;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public JDateChooser getTxtCalendario() {
        return txtCalendario;
    }

    public JTextArea getTxtDescripcion() {
        return txtDescripcion;
    }

    public JDateChooser getTxtDesde() {
        return txtDesde;
    }

    public JDateChooser getTxtHasta() {
        return txtHasta;
    }

    public JTextField getTxtMonto() {
        return txtMonto;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaMovimientos = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        boxTipo = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCalendario = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        txtDesde = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        txtHasta = new com.toedter.calendar.JDateChooser();
        btnNuevoGuardar = new javax.swing.JButton();
        btnModif = new javax.swing.JButton();
        btnElim = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        boxUsuario = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        busquedaBoxTipo = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnAgregarPersona = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestion de movimientos");

        tablaMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Tipo", "Monto", "Fecha"
            }
        ));
        jScrollPane5.setViewportView(tablaMovimientos);

        jLabel15.setText("Usuario");

        jLabel16.setText("Tipo");

        boxTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "EGRESO PARTICULAR", "EGRESO PROVEEDOR", "INGRESO" }));

        jLabel17.setText("Monto");

        jLabel18.setText("Fecha");

        txtCalendario.setDateFormatString("dd-MM-yyyy");

        jLabel19.setText("Descripcion");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane6.setViewportView(txtDescripcion);

        jLabel20.setText("Desde");

        txtDesde.setDateFormatString("dd-MM-yyyy");

        jLabel21.setText("Hasta");

        txtHasta.setDateFormatString("dd-MM-yyyy");

        btnNuevoGuardar.setText("Nuevo");

        btnModif.setText("Modificar");
        btnModif.setEnabled(false);

        btnElim.setText("Eliminar");
        btnElim.setEnabled(false);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        boxUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar" }));

        jLabel22.setText("Tipo");

        busquedaBoxTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS", "EGRESO PARTICULAR", "EGRESO PROVEEDOR", "INGRESO" }));

        jLabel23.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        jLabel23.setText("Total    $");

        lblTotal.setFont(new java.awt.Font("Droid Sans", 1, 18)); // NOI18N
        lblTotal.setText("0.00");

        btnAgregarPersona.setText("+");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelImage1Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(115, 115, 115)
                            .addComponent(jLabel18))
                        .addGroup(panelImage1Layout.createSequentialGroup()
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(txtCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel19)
                        .addGroup(panelImage1Layout.createSequentialGroup()
                            .addComponent(btnNuevoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnModif, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnElim, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane6))
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(boxUsuario, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boxTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, 221, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarPersona)
                        .addGap(22, 22, 22)))
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5)
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelImage1Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(busquedaBoxTipo, 0, 130, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(txtDesde, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(txtHasta, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(11, 11, 11)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(boxUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarPersona))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnModif, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnElim, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNuevoGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jSeparator1))
                            .addGroup(panelImage1Layout.createSequentialGroup()
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22))
                                .addGap(9, 9, 9)
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(busquedaBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(lblTotal))
                                .addGap(14, 14, 14)))))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxTipo;
    private javax.swing.JComboBox boxUsuario;
    private javax.swing.JButton btnAgregarPersona;
    private javax.swing.JButton btnElim;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnNuevoGuardar;
    private javax.swing.JComboBox busquedaBoxTipo;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotal;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private javax.swing.JTable tablaMovimientos;
    private com.toedter.calendar.JDateChooser txtCalendario;
    private javax.swing.JTextArea txtDescripcion;
    private com.toedter.calendar.JDateChooser txtDesde;
    private com.toedter.calendar.JDateChooser txtHasta;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
