/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import controller.Partida;
import util.cartasColores;

/**
 * Modelo de una CARTA TOME 2
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class CartaTome2 extends Carta{

    /**
     * Constructor de la carta
     * @param pColor - Color de la carta
     * @param pTexto - Texto de la carta
     */
    public CartaTome2(cartasColores pColor, String pTexto){
        setColor(pColor);
        setTexto(pTexto);
    }

    /**
     * Este método se encarga de activar el efecto de la carta
     */
    @Override
    public void ejecutarAccion() {
        Partida.darDosAlSiguiente();
    }

}
