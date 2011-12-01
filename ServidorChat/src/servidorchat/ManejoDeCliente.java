/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat;

import java.io.*;
import java.net.*;

/**
 *
 * @author Grupo 8
 */

public class ManejoDeCliente extends Thread {

    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private Socket socket = null;
    private ManejoDeChat sala = null;
    private int Identificador = 1;

    // Constructor
    ManejoDeCliente(ManejoDeChat sala, Socket socket, int identificadorEnLista) {
        this.sala = sala;
        this.socket = socket;
        this.Identificador = identificadorEnLista;
        try {
            dis = new DataInputStream(new BufferedInputStream(
                                                      socket.getInputStream()));

            dos = new DataOutputStream(new BufferedOutputStream(
                                                     socket.getOutputStream()));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Hilo del Cliente, cada cliente corre sobre su propio hilo dentro de 
    // un hilo el cual es referente a la sala.
    @Override
    public void run() {
        while (true) {
            try {
                String message = dis.readUTF();
                sala.replicarMensajePorSala(message);
            } catch (IOException ex) {
                sala.eliminarDeListaUsuario(Identificador);
                stop();
            }
        }
    }
    
    // Metodo encargado de enviar mensajes a los clientes.
    // Es ejecutado desde el hilo de la sala y llama a cada cliente conectado
    // a esa sala.

    public void enviarMensajeAClientes(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException ex) {
            sala.eliminarDeListaUsuario(Identificador);
            stop();
        }
    }

    // Metodo encargado de cerrar la conexion (puertos,buffer de entrada
    // y buffer de salida) por los que se, conecto el usuario.
    public void close() throws IOException {
        socket.close();
        dis.close();
        dos.close();
    }
}
