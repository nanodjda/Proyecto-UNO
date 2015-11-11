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

public class CartaComodin extends Carta {

    public CartaComodin(cartasColores pColor, String pTexto){
        setColor(pColor);
        setTexto(pTexto);
    }

    @Override
    public void ejecutarAccion() {
        //Esta carta cumple su efecto al cambiar de color
    }

}
