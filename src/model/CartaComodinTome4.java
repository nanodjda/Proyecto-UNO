/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import controller.Partida;
import util.cartasColores;

/**
 *
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class CartaComodinTome4 extends Carta {

    public CartaComodinTome4(cartasColores pColor, String pTexto){
        setColor(pColor);
        setTexto(pTexto);
    }

    @Override
    public void ejecutarAccion() {
        Partida.darCuatroAlSiguiente();
    }

}
