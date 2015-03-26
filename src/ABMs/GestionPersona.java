/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABMs;

import Modelos.Persona;
import java.util.LinkedList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 *
 * @author alan
 */
public class GestionPersona {

    public LinkedList<String> getNombrePersonas() {
        abrirBase();
        LazyList<Persona> lista = Persona.findAll();
        LinkedList<String> personas = new LinkedList<>();
        if (!lista.isEmpty()) {
            for (Persona p : lista) {
                personas.add(p.getString("nyap"));
            }
        }
        Base.close();
        return personas;
    }

    public String getIdPersona(String nombre) {
        abrirBase();
        Persona p = Persona.first("nyap = ?", nombre);
        Base.close();
        return p.getString("id");
    }

    public String getNombrePersona(String id) {
        abrirBase();
        Persona p = Persona.first("id = ?", id);
        String n = p.getString("nyap");
        Base.close();
        return n;
    }
    
    

    private void abrirBase() {
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/panaderia", "root", "root");
        }
    }
}
