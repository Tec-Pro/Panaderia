/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Articulo;
import Modelos.Movimiento;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author Joako
 * 
 * Clase que se encarga de crear, modificar y eliminar artículos.
 */
public class GestionArticulos {
    
    
    public boolean Alta(Articulo a){
       abrirBase();
       Base.openTransaction();
       boolean res = a.saveIt();
       Base.commitTransaction();
       Base.close();
       return res;
    }
    
    public boolean Modificar(Articulo a){
       abrirBase();
       Base.openTransaction();
       boolean res = a.saveIt();
       Base.commitTransaction();
       Base.close();
       return res;
    }
    
    public boolean Borrar(Articulo a){
        abrirBase();
        Base.openTransaction();
        Articulo aux = Articulo.findById(a.getId());
        boolean res = aux.delete();
        Base.commitTransaction();
        Base.close();
        return res;
    }
    
    public LazyList<Articulo> listarArticulos(){
        return Articulo.findAll();
    }
    
    public LazyList<Articulo> buscarArticulo(String nombre, String tipo, String cod){
        abrirBase();
        LazyList<Articulo> res;
        res = Articulo.where("nombre like ? and tipo like ? and cod like ?", "%"+nombre+"%", "%"+tipo+"%","%"+cod+"%");
        Base.close();
        return res;
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }

    public Articulo getArticulo(String valueOf) {
        abrirBase();
        Articulo res = Articulo.findById(valueOf);
        return res;
    }
}
