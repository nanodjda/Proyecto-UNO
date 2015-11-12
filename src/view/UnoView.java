/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package view;

import controller.Partida;
import java.util.Scanner;
import model.Carta;
import model.CartaComodin;
import model.CartaComodinTome4;
import util.cartasColores;

/**
 *
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class UnoView {

    /** Variables **/
    private Partida controlador;
    private Scanner leer = new Scanner(System.in);
    private int eleccion, eleccionColor, contador;


    /** Constructor **/
    public UnoView(Partida pPartida){
        this.controlador = pPartida;
    }

    /** Metodos **/
    
    public void start() throws Exception{
        //Agrego los jugadores
        controlador.registrarJugador("David"); //0
        controlador.registrarJugador("Arturo"); //1
        controlador.registrarJugador("Roger"); //2
        controlador.registrarJugador("Blacky"); //3
        
        
        controlador.iniciarPartida();
        
        
        //Reparto las cartas
        controlador.repartirCartas();
        
        System.out.println("Empieza la partida!!\n");
        
        while(true){
            contador = 1;
            System.out.println("\nEl turno de: " + controlador.getJugadorActual().getNombre());
            while(true){
                try {
                    System.out.println("\nMazo de Descarte: \nTexto -> " + controlador.getTopeDescarte().getTexto()
                            + " Color -> " + controlador.getTopeDescarte().getColor());
                    System.out.println("\nTus cartas:");
                    for(Carta pCarta : controlador.getJugadorActual().getMano()){
                        System.out.println(contador + " - Texto-> " + pCarta.getTexto() + " Color-> " + pCarta.getColor());
                        contador++;
                    }
                    System.out.println("X - Cualquier otro número para tomar una del mazo!");
                    System.out.print("\nEscoge la carta: ");
                    eleccion = leer.nextInt() - 1;
                    if(controlador.validarEleccion(eleccion)){
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                contador = 1;
            }
            
            // Si es una carta comodín se le pide al usuario cual color debe seguir
            if(controlador.getCarta(eleccion) instanceof CartaComodin || 
               controlador.getCarta(eleccion) instanceof CartaComodinTome4){
                controlador.escogerColor(controlador.getCarta(eleccion), cartaComodin());
            }
            
            //Se acciona la carta usada
            controlador.cartaUsada(eleccion);
            
            //Se chequea si hay ganador
            if(controlador.getGanador()){
                break;
            }
            
            //Se sigue con el siguiente jugador
            controlador.siguienteJugador();
        }
        
        System.out.println("\nEl ganador es: " + controlador.getJugadorActual().getNombre());
    }
    
    public void start2() throws Exception{
        //Agrego los jugadores
        controlador.registrarJugador("David"); //0
        controlador.registrarJugador("Arturo"); //1
        controlador.registrarJugador("Roger"); //2
        controlador.registrarJugador("Blacky"); //3
        
        
        controlador.iniciarPartida();
        
        System.out.println("Empieza la partida!!\n");
        
        while(true){
            
            //Se sigue con el siguiente jugador
            controlador.siguienteJugador2();
            
            contador = 1;
            System.out.println("\nEl turno de: " + controlador.getJugadorActual().getNombre());
            while(true){
                try {
                    System.out.println("\nMazo de Descarte: \nTexto -> " + controlador.getTopeDescarte().getTexto()
                            + " Color -> " + controlador.getTopeDescarte().getColor());
                    System.out.println("\nTus cartas:");
                    for(Carta pCarta : controlador.getJugadorActual().getMano()){
                        System.out.println(contador + " - Texto-> " + pCarta.getTexto() + " Color-> " + pCarta.getColor());
                        contador++;
                    }
                    System.out.println("X - Cualquier otro número para tomar una del mazo!");
                    System.out.print("\nEscoge la carta: ");
                    eleccion = leer.nextInt() - 1;
                    if(controlador.validarEleccion(eleccion)){
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                contador = 1;
            }
            
            // Si es una carta comodín se le pide al usuario cual color debe seguir
            if(controlador.getCarta(eleccion) instanceof CartaComodin || 
               controlador.getCarta(eleccion) instanceof CartaComodinTome4){
                controlador.escogerColor(controlador.getCarta(eleccion), cartaComodin());
            }
            
            //Se acciona la carta usada
            controlador.usarCarta(eleccion);
            
            //Se chequea si hay ganador
            if(controlador.getGanador()){
                break;
            }
        }
        
        System.out.println("\nEl ganador es: " + controlador.getJugadorActual().getNombre());
    }

    public cartasColores cartaComodin(){
        while(true){
            System.out.println("\nCarta comodín:");
            System.out.println("1 - ROJO");
            System.out.println("2 - AMARILLO");
            System.out.println("3 - VERDE");
            System.out.println("4 - AZUL");
            System.out.println("\nSeleccion el color: ");
            eleccionColor = leer.nextInt();
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
    
    /** Getters & setters **/

}
