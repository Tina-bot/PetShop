package Logica;
//Controladora tendra metodos para hacer de intermediario entre las demas carpetas/clases

import Persistencia.ControladoraPersistencia;

public class Controladora {
    //conectamos esta controladora de logica con la controladora de persistencia con este objeto
    ControladoraPersistencia control = new ControladoraPersistencia();

    //metodo para enviar los datos agarrados y llevarlos a la controladora de Persistencia
    public void envioDatos(int num_cliente, String nombre_perro, String raza, String color, String alergico, String atencion_especial, String nombre_duenio, int celular_duenio, String observaciones) {
        ClientesMasc produ = new ClientesMasc();
        produ.setNum_cliente(num_cliente);
        produ.setNombre_perro(nombre_perro);
        produ.setRaza(raza);
        produ.setColor(color);
        produ.setAlergico(alergico);
        produ.setAtencion_especial(atencion_especial);
        produ.setNombre_duenio(nombre_duenio);
        produ.setCelular_duenio(celular_duenio);
        produ.setObservaciones(observaciones);

        //le pasamos nuestro objeto ya con sus datos guardados a la controladora de Persistencia
        control.envioDatos(produ);

    }

}
