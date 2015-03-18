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
       Articulo nuevo = Articulo.create("codigo",a.get("codigo"),"nombre",a.get("nombre"),"tipo",a.get("tipo"),"precio",a.get("precio"),"descripcion",a.get("descripcion"));
       boolean res = nuevo.saveIt();
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
        Articulo aux = Articulo.first("codigo = ?", a.getString("codigo"));
        boolean res = aux.delete();
        Base.commitTransaction();
        Base.close();
        return res;
    }
    
    public LazyList<Articulo> listarArticulos(){
        abrirBase();
        LazyList<Articulo> res = Articulo.findAll();
        Base.close();
        return res;
    }
    
    public LazyList<Articulo> buscarArticulo(String texto){
        abrirBase();
        LazyList<Articulo> res;
        res = Articulo.where("nombre like ? or tipo like ? or codigo like ?", "%"+texto+"%", "%"+texto+"%","%"+texto+"%");
        Base.close();
        return res;
    }
    
    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }

    public Articulo getArticulo(String codigo) {
        abrirBase();
        Articulo res = Articulo.first("codigo = ?", codigo);
        return res;
    }
}
