/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author David
 */
public class ProyectoUNO {

    private static Partida partida;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        iniciar();
    }
    
    private static void iniciar(){
        partida = Partida.getInstance();
        partida.startApplication();
    }
}
