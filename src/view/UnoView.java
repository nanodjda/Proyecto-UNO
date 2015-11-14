/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package view;

import controller.Partida;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import model.Carta;
import model.CartaComodin;
import model.CartaComodinTome4;
import model.Jugador;
import util.cartasColores;

/**
 * Esta clase se encarga de hacer el manejo de la interfaz de la aplicación
 * Fecha 10/11/2015
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class UnoView {

    /** Variables **/
    private Partida controlador;
    private Scanner leer = new Scanner(System.in);
    private int eleccion, eleccionColor, contador;


    /** Constructor **/
    /**
     * Se encarga de iniciar una interfaz y se asigna el controlador
     * @param pPartida - Controlador que dará uso a la interfaz
     */
    public UnoView(Partida pPartida){
        this.controlador = pPartida;
    }

    /** Metodos **/
    /**
     * Este método se encarga de iniciar la vista, imprimir y hacer las solici-
     * tudes necesarias a los jugadores en la partida
     * @throws Exception 
     */
    public void start() throws Exception{
        //Agrego los jugadores
        do{
            System.out.println("Esperando a los jugadores...");
            System.out.println("Cantidad agregados: " + controlador.cantJugadores());
            controlador.registrarJugador();
            if(controlador.cantJugadores() > 1 && controlador.cantJugadores() != 4){
                System.out.println("Iniciar ya? (Y/N)");
                String opc = leer.nextLine();
                if(opc.equals("Y")){
                    break;
                } else if(opc.equals("N")){
                } else {
                    System.out.println("Debe escoger una opción válida");
                }
            } else if(controlador.cantJugadores() == 4){
                break;
            }
        } while(controlador.cantJugadores() < 4);
        
        controlador.iniciarPartida();
        
        System.out.println("Empieza la partida!!\n");
        
        while(true){
            //Se sigue con el siguiente jugador
            controlador.siguienteJugador();
            for(Jugador pJugador : controlador.getJugadores()){
                if(!pJugador.equals(controlador.getJugadorActual())){
                    controlador.imprimir(pJugador, "\nEl turno de: " + controlador.getJugadorActual().getNombre());
                    controlador.imprimir(pJugador, "\nMazo de Descarte: \nTexto -> " + controlador.getTopeDescarte().getTexto()
                            + " Color -> " + controlador.getTopeDescarte().getColor());
                }
            }
            contador = 1;
            System.out.println("\nEl turno de: " + controlador.getJugadorActual().getNombre());
            while(true){
                try {
                    controlador.imprimir(controlador.getJugadorActual(), "\nEl turno de: " + controlador.getJugadorActual().getNombre());
                    controlador.imprimir(controlador.getJugadorActual(), "\nMazo de Descarte: \nTexto -> " + controlador.getTopeDescarte().getTexto()
                            + " Color -> " + controlador.getTopeDescarte().getColor());
                    controlador.imprimir(controlador.getJugadorActual(), "\nTus cartas:");
                    for(Carta pCarta : controlador.getJugadorActual().getMano()){
                        controlador.imprimir(controlador.getJugadorActual(), contador + " - Texto-> " + pCarta.getTexto() + " Color-> " + pCarta.getColor());
                        contador++;
                    }
                    controlador.imprimir(controlador.getJugadorActual(), "X - Cualquier otro número para tomar una del mazo!");
                    controlador.imprimir(controlador.getJugadorActual(), "\nEscoge la carta: ");
                    BufferedReader clientIn = new BufferedReader(
                            new InputStreamReader(controlador.getJugadorActual().getSocket().getInputStream()));
                    eleccion = Integer.parseInt(clientIn.readLine()) - 1;
                    if(controlador.validarEleccion(eleccion)){
                        break;
                    }
                } catch (Exception e) {
                    controlador.imprimir(controlador.getJugadorActual(), e.getMessage());
                }
                contador = 1;
            }
            
            // Si es una carta comodín se le pide al usuario cual color debe seguir
            if(controlador.getCarta(eleccion) instanceof CartaComodin || 
               controlador.getCarta(eleccion) instanceof CartaComodinTome4){
                controlador.escogerColor(controlador.getCarta(eleccion), cartaComodin());
            }
            
            //Se coloca la carta en descarte
            controlador.usarCarta(eleccion);
            
            //Se chequea si hay ganador
            if(controlador.getGanador()){
                break;
            }
        }
        for(Jugador pJugador : controlador.getJugadores()){
            controlador.imprimir(pJugador, "\nEl ganador es: " + controlador.getJugadorActual().getNombre());
        }
        leer.nextLine();
    }
    
    /**
     * Método se encarga de solicitar al usuario un color si es una carta comodín
     * @return
     * @throws IOException 
     */
    public cartasColores cartaComodin() throws IOException{
        while(true){
            controlador.imprimir(controlador.getJugadorActual(), "\nCarta comodín:");
            controlador.imprimir(controlador.getJugadorActual(), "1 - ROJO");
            controlador.imprimir(controlador.getJugadorActual(), "2 - AMARILLO");
            controlador.imprimir(controlador.getJugadorActual(), "3 - VERDE");
            controlador.imprimir(controlador.getJugadorActual(), "4 - AZUL");
            controlador.imprimir(controlador.getJugadorActual(), "\nSeleccion el color: ");
            BufferedReader clientIn = new BufferedReader(
                            new InputStreamReader(controlador.getJugadorActual().getSocket().getInputStream()));
                    eleccionColor = Integer.parseInt(clientIn.readLine());
            switch(eleccionColor){
                case 1:
                    return cartasColores.ROJO;
                case 2:
                    return cartasColores.AMARILLO;
                case 3:
                    return cartasColores.VERDE;
                case 4:
                    return cartasColores.AZUL;
            }
        }
    }
}
