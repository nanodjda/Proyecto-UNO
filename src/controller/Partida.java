/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package controller;

import java.util.ArrayList;
import java.util.Collections;
import model.Carta;
import model.CartaComodin;
import model.CartaComodinTome4;
import model.CartasFactory;
import model.Jugador;
import model.Logica;
import util.cartasColores;
import view.UnoView;

/**
 *
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class Partida {

    /** Variables **/
    private static Partida INSTANCE = null;
    private static Logica logica;
    private static UnoView vista;
    
    private static ArrayList<Jugador> jugadores = new ArrayList();
    private static ArrayList<Carta> mazo = new ArrayList();
    private static ArrayList<Carta> descarte = new ArrayList();
    
    private static Jugador jugadorActual;
    private static Boolean reversa = false;
    private static Boolean saltarTurno = false;
    private static Boolean dosSiguiente = false;
    private static Boolean cuatroSiguiente = false;
    
    private static int TAMANHO_MANO = 7;
    
    /** Constructor **/
    private Partida(){
        logica = new Logica();
        vista = new UnoView(this.INSTANCE);
    }

    /** Metodos **/
    public static void iniciarPartida(){
        //Creo el mazo y lo desordeno
        mazo = CartasFactory.makeMazo();
        do {
            Collections.shuffle(mazo);
        } while(mazo.get(mazo.size() - 1) instanceof CartaComodin ||
                mazo.get(mazo.size() - 1) instanceof CartaComodinTome4);
        
        descarte.add(mazo.get(mazo.size() - 1));
        mazo.remove(mazo.size() - 1);
        
        repartirCartas();
        
    }
    
    public static void repartirCartas(){
        for(Jugador pJugador : jugadores){
            for(int i = 0; i < TAMANHO_MANO; i++){
                pJugador.addCarta(mazo.get(0));
                mazo.remove(0);
            }
        }
    }
    
    public static void registrarJugador(String pNombre){
        Jugador nuevo = new Jugador(pNombre);
        jugadores.add(nuevo);
        jugadorActual = jugadores.get(jugadores.size() - 1);
    }
    
    public static void usarCarta(int eleccion){
        descarte.add(jugadorActual.getMano().get(eleccion));
        jugadorActual.removeCarta(jugadorActual.getMano().get(eleccion));
    }
    
    public static void comprobarMazo(){
        if(mazo.size() == 0){ //Si se vacía el mazo, se le asignan las cartas de descarte y se "baraja" de nuevo
            mazo.addAll(descarte);
            Collections.shuffle(mazo);
            descarte.clear();
            descarte.add(mazo.get(mazo.size() - 1));
            mazo.remove(mazo.size() - 1);
        }
    }
    
    public static void siguientePierdeTurno(){
        saltarTurno = true;
    }
    
    public static void cambiarSentido(){
        reversa = true;
    }
    
    public static void darDosAlSiguiente(){
        dosSiguiente = true;
    }
    
    public static void darCuatroAlSiguiente(){
        cuatroSiguiente = true;
    }
    
    /**
     * Se cambia el color de la carta
     * @param pCarta - Carta a cambiar el color
     * @param pColor - Color al que se cambiará la carta
     */
    public static void escogerColor(Carta pCarta, cartasColores pColor){
        pCarta.setColor(pColor);
    }
    
    public static void ejecutarCarta(Carta pCarta){
        pCarta.ejecutarAccion();
    }
    
    public static Boolean validarEleccion(int pEleccion) throws Exception{
        if(pEleccion < jugadorActual.getMano().size() && pEleccion > -1){
            if(getCarta(pEleccion) instanceof CartaComodin || getCarta(pEleccion) instanceof CartaComodinTome4){
                return true;
            }
            if(getCarta(pEleccion).getColor() == getTopeDescarte().getColor() ||
               getCarta(pEleccion).getTexto().equals(getTopeDescarte().getTexto())){
                return true;
            } else {
                throw new Exception("Debe seleccionar una carta válida!");
            }
        } else {
            jugadorActual.addCarta(mazo.get(mazo.size() - 1)); //Si el jugador no digita una carta en la mano,
            mazo.remove(mazo.size() - 1);                      //se le da una del mazo
            comprobarMazo();
            return false;
        }
    }
    
    public static void siguienteJugador(){
        //Ejecuto la carta en el tope de descarte
        ejecutarCarta(getTopeDescarte());
        
        //Compruebo si se debe revertir el orden
        if(reversa){
            Collections.reverse(jugadores);
        }
        reversa = false;
        
        //Se cambia al jugador que tiene el turno
        if(jugadores.indexOf(jugadorActual) == jugadores.size() - 1){
            jugadorActual = jugadores.get(0);
        } else {
            jugadorActual = jugadores.get(jugadores.indexOf(jugadorActual) + 1);
        }
        
        //Si se activa alguna carta especial se aplica el efecto y se salta al jugador
        if(saltarTurno || dosSiguiente || cuatroSiguiente){
            if(dosSiguiente){ //Se realiza solo sobre el jugador al que se le aplica el efecto
                for(int i = 0; i < 2; i++){
                    comprobarMazo();
                    jugadorActual.addCarta(mazo.get(mazo.size() - 1));
                    mazo.remove(mazo.get(mazo.size() - 1));
                }
            }
            if(cuatroSiguiente){ //Se realiza solo sobre el jugador al que se le aplica el efecto
                for(int i = 0; i < 4; i++){
                    comprobarMazo();
                    jugadorActual.addCarta(mazo.get(mazo.size() - 1));
                    mazo.remove(mazo.get(mazo.size() - 1));
                }
            }
            if(jugadores.indexOf(jugadorActual) == jugadores.size() - 1){
                jugadorActual = jugadores.get(0);
            } else {
                jugadorActual = jugadores.get(jugadores.indexOf(jugadorActual) + 1);
            }
        }
        cuatroSiguiente = false;
        dosSiguiente = false;
        saltarTurno = false;
    }
    
    public void startApplication() throws Exception{
        vista.start();
    }
    
    /** Getters & setters **/
    public static Partida getInstance(){
        if (INSTANCE == null) {
        INSTANCE = new Partida();
        }
        return INSTANCE;
    }
    
    public static Jugador getJugadorActual(){
        return jugadorActual;
    }
    
    public static Carta getTopeDescarte(){
        return descarte.get(descarte.size() - 1);
    }
    
    public static Carta getCarta(int eleccion){
        return jugadorActual.getMano().get(eleccion);
    }
    
    public static boolean getGanador(){
        if(jugadorActual.getMano().size() == 1){
            return true;
        }
        return false;
    }
}
