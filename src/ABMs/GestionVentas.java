/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Articulo;
import Modelos.ArticulosVentas;
import Modelos.Venta;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import panaderia.Triple;

/**
 *
 * @author alan
 */
public class GestionVentas {
    
private int idVenta;
    
    public int getIdVenta(){
        return idVenta;
    }
    
    public boolean Alta(Venta v){
        boolean result = true;
        abrirBase();
        Base.openTransaction();
        Venta venta = Venta.create("fecha", v.get("fecha"), "monto", v.get("monto"));
        if(venta.saveIt()){
            idVenta = venta.getInteger("id");
            Base.commitTransaction();
            Base.close();
            result = result && cargarArticulosVentas(v.getArticulos());
            return result;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    public boolean Modificar(Venta v){
        abrirBase();
        Base.openTransaction();
        if(v.saveIt()){
            Base.commitTransaction();
            Base.close();
            return true;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    public boolean Eliminar(Venta v){
        abrirBase();
        Base.openTransaction();
        int id = v.getInteger("id");
        if(v.delete()){
            ArticulosVentas.delete("venta_id = ?", id);;
            Base.commitTransaction();
            Base.close();
            return true;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    
    public boolean cargarArticulosVentas(LinkedList<Triple> listaArticulos){
        abrirBase();
        Base.openTransaction();
        boolean result = true;
        for(Triple t: listaArticulos){
            ArticulosVentas articulosVentas = ArticulosVentas.create("venta_id", idVenta, "articulo_id", t.getIdArticulo(), "cantidad_articulo", t.getCantidad(), "monto_articulo", t.getPrecioFinal());
            result = result && articulosVentas.saveIt();
        }
        Base.commitTransaction();
        Base.close();
        return result;
    }
    
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }
}
