/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Grupo 8
 */

public class ManejoDeChat extends Thread {

    private ArrayList<ManejoDeCliente> listado = new 
                                                   ArrayList<ManejoDeCliente>();
    
    private int puerto;
    private ServerSocket puertoSala = null;
    private int cantidadUsuarios = 0;

    // Constructor
    public ManejoDeChat(int puerto) {
        this.puerto = puerto;
        try {
            puertoSala = new ServerSocket(puerto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Hilo de la Sala, se inicializan las cuatro salas asgnandole a cada 
    // una de ellas un hilo.
    @Override
    public void run() {
        while (true) {
            try {
                Socket socketUsuario = puertoSala.accept();
                agregarAListaUsuario(socketUsuario);
            } catch (IOException ex) {
                System.out.println("Error aceptando usuario");
            }

        }
    }
    
    // Metodo encargado de agregar usuarios a la lista de usuarios de la sala
    // Esta lista es usada para porer replicar el mensaje a cada cliente y
    // iniciar el hilo del cliente

    private void agregarAListaUsuario(Socket socketUsuario) {
        ManejoDeCliente usuarioz = new ManejoDeCliente(this, 
                                               socketUsuario, cantidadUsuarios);
        
        listado.add(usuarioz);
        usuarioz.start();
        cantidadUsuarios++;
    }

    // Metodo encargado de replicar el mensaje por todos los clientes
    // que se encuentren conectados a una sala.
    public synchronized void replicarMensajePorSala(String message) {
        for (int i = 0; i < listado.size(); i++) {
            listado.get(i).enviarMensajeAClientes(message);
        }
    }
    
    // Metodo encargado de eliminar usuarios de la lista de usuarios, utilizada
    // para replicar el mensaje.
    public synchronized void eliminarDeListaUsuario(int ID) {
        try {
            ManejoDeCliente usuarioAEliminar = listado.get(ID);
            listado.indexOf(ID);
            listado.remove(ID);
            cantidadUsuarios--;
            usuarioAEliminar.close();
            usuarioAEliminar.stop();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
