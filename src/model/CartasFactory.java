/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package model;

import java.util.ArrayList;
import util.cartasColores;

/**
 *
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class CartasFactory {

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
                
                for(int cont = 0; cont < 10; cont++){
                    if(x == 0){
                        mazo.add(makeCarta("N", color, String.valueOf(cont)));
                    } else if(x == 1 && cont != 9){
                        mazo.add(makeCarta("N", color, String.valueOf(cont + 1)));
                    }
                }
                
                mazo.add(makeCarta("S", color, "SALTO"));
                mazo.add(makeCarta("R", color, "REVERSA"));
                mazo.add(makeCarta("T", color, "TOME 2"));
                
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
