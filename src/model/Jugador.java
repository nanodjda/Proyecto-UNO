/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
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

    public void addCarta(Carta pCarta) {
        this.mano.add(pCarta);
    }
    
    public void removeCarta(Carta pCarta) {
        this.mano.remove(pCarta);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
