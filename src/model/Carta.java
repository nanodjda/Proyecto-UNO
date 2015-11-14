/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import util.cartasColores;

/** 
 * Modelo de una CARTA
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public abstract class Carta {

    /** Variables **/
    private cartasColores color;
    private String texto;
    
    /** Metodos **/
    /**
     * Este método se encarga de ejecutar la acción de la carta
     */
    abstract public void ejecutarAccion();

    /** Getters & setters **/
    public cartasColores getColor() {
        return color;
    }

    public void setColor(cartasColores color) {
        this.color = color;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
}
