/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Esta clase es el modelo de un jugador, cada jugador posee un socket por el 
 * cual el servidor se comunicará
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class Jugador {

    /** Variables **/
    private String nombre;
    private ArrayList<Carta> mano = new ArrayList();
    private Socket socket;

    /** Constructor **/
    public Jugador(){
        socket = new Socket();
    }

    /** Metodos **/

    /**
     * Este método se encarga de agregar una carta a la mano del jugador
     * @param pCarta - Carta a agregar
     */
    public void addCarta(Carta pCarta) {
        this.mano.add(pCarta);
    }
    
    /**
     * Este método se encarga de quitar un carta de la mano del jugador
     * @param pCarta - Carta a remover
     */
    public void removeCarta(Carta pCarta) {
        this.mano.remove(pCarta);
    }
    
    /** Getters & setters **/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
