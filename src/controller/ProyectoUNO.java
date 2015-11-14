/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * Fecha 11/11/2015
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */
public class ProyectoUNO {

    private static Partida partida;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        iniciar();
    }
    
    /**
     * Metodo se encarga de iniciar la aplicación
     * @throws Exception 
     */
    private static void iniciar() throws Exception{
        partida = Partida.getInstance();
        partida.startApplication();
    }
}
