/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import util.cartasColores;

/**
 *
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public abstract class Carta {

    /** Variables **/
    private cartasColores color;
    private String texto;

    /** Constructor **/
    //Clase abstracta
    
    /** Metodos **/
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
