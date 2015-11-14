/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import java.util.ArrayList;
import util.cartasColores;

/**
 * Esta clase se encarga de proveer Cartas o un Mazo
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class CartasFactory {

    /**
     * Este método crea cartas de acuerdo a los parámetros ingresados
     * @param pType - Tipo de la carta a crear
     * @param pColor - Color de la carta a crear
     * @param pTexto - Texto que poseerá la carta
     * @return - Carta creada de acuerdo a los parámetros
     */
    public static Carta makeCarta(String pType, cartasColores pColor, String pTexto) {
        switch (pType) {
            case "N" :
                return new CartaNumerica(pColor, pTexto);
            case "S" :
                return new CartaSalto(pColor, pTexto);
            case "R" :
                return new CartaReversa(pColor, pTexto);
            case "T" :
                return new CartaTome2(pColor, pTexto);
            case "C" :
                return new CartaComodin(pColor, pTexto);
            case "C4" :
                return new CartaComodinTome4(pColor, pTexto);
            default:
                return null;
        }
    }
    
    /**
     * Este método se encarga de crea un mazo completo de UNO
     * @return - Se retorna un mazo de UNO
     */
    public static ArrayList<Carta> makeMazo(){
        
        ArrayList<Carta> mazo = new ArrayList();
        cartasColores color = null;
        
        for(int x = 0; x < 2; x++){
            for(int i = 0; i < 4; i++){
                if(i == 0){
                    color = cartasColores.ROJO;
                } else if(i == 1){
                    color = cartasColores.AMARILLO;
                } else if(i == 2){
                    color = cartasColores.VERDE;
                } else if(i == 3){
                    color = cartasColores.AZUL;
                }
                
                // Cartas númericas de colores, solo se hacen una vez las de "0"
                for(int cont = 0; cont < 10; cont++){
                    if(x == 0){
                        mazo.add(makeCarta("N", color, String.valueOf(cont)));
                    } else if(x == 1 && cont != 9){
                        mazo.add(makeCarta("N", color, String.valueOf(cont + 1)));
                    }
                }
                
                //Cartas especiales que poseen color
                mazo.add(makeCarta("S", color, "SALTO"));
                mazo.add(makeCarta("R", color, "REVERSA"));
                mazo.add(makeCarta("T", color, "TOME 2"));
                
                //Cartas comodín, en el primer ciclo se crean los COMODINES normales
                //Luego los COMODINES TOME 4
                if(x == 0){
                    mazo.add(makeCarta("C", cartasColores.SINCOLOR, "COMODIN"));
                } else {
                    mazo.add(makeCarta("C4", cartasColores.SINCOLOR, "COMODIN TOME 4"));
                }
            }
        }
        return mazo;
    }

}
