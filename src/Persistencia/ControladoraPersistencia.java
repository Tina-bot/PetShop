package Persistencia;

import Logica.ClientesMasc;
import java.util.logging.Level;
import java.util.logging.Logger;

//esta controladora va a servir para mandar las peticiones de la logica a la base de datos
public class ControladoraPersistencia {

    ClientesMascJpaController jpaClientes = new ClientesMascJpaController();

    //crea la tabla y sus datos mandados desde la controladora de logica
    public void envioDatos(ClientesMasc cliente) {
        //trycatch por si falla algo a la hora de crearlo
        try {
            jpaClientes.create(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
