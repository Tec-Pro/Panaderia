/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Movimiento;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author alan
 */
public class GestionMovimientos {
    
    public Movimiento getMovimiento(String id){
        Movimiento m = Movimiento.first("id = ?", id);
        return m;
    }
    
    public LazyList<Movimiento> getMovimientos(){
        abrirBase();
        LazyList<Movimiento> lista = Movimiento.findAll();
        Base.close();
        return lista;
    }
    
    public boolean Alta(Movimiento m){
        abrirBase();
        Base.openTransaction();
        if(m.saveIt()){
            Base.commitTransaction();
            Base.close();
            return true;
        }
        Base.commitTransaction();
        Base.close();
        return false;
    }
    
    public void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }
}
