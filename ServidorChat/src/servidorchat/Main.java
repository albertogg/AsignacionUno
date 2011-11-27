/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static int puerto1 = 5431;
    public static int puerto2 = 5432;
    public static int puerto3 = 5433;
    public static int puerto4 = 5434;
    
    public static void main(String[] args) {
        
        System.out.println("Servidor encendido con sus 4 salas.");
        
        ManejoDeChat sala1 = new ManejoDeChat(puerto1);      
        ManejoDeChat sala2 = new ManejoDeChat(puerto2);
        ManejoDeChat sala3 = new ManejoDeChat(puerto3);
        ManejoDeChat sala4 = new ManejoDeChat(puerto4);
        
        // Arrancamos el hilo de las salas.
        sala1.start();
        sala2.start();
        sala3.start();
        sala4.start();
    }
}
