/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import controller.Partida;
import util.cartasColores;

/**
 * Modelo de una CARTA SALTO
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class CartaSalto extends Carta{

    /**
     * Constructor de la carta
     * @param pColor - Color de la carta
     * @param pTexto - Texto de la carta
     */
    public CartaSalto(cartasColores pColor, String pTexto){
        setColor(pColor);
        setTexto(pTexto);
    }
    
    /**
     * Este método se encarga de activar el efecto de la carta
     */
    @Override
    public void ejecutarAccion() {
        Partida.siguientePierdeTurno();
    }

}
