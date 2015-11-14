/**
 * Instituto Tecnológico de Costa Rica
 * Proyecto de Programación Orientada a Objetos
 * Juego UNO
 */

package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import model.Carta;
import model.CartaComodin;
import model.CartaComodinTome4;
import model.CartaReversa;
import model.CartasFactory;
import model.Jugador;
import util.cartasColores;
import view.UnoView;

/**
 * Fecha 11/11/2015
 * @author(s) David Díaz Aguilar - 2014004725
 *            Arturo Luna Izaguirre - 2014110993
 */

public class Partida {

    /** Variables **/
    private static Partida INSTANCE = null;
    private static UnoView vista;
    private static ServerSocket server;
    
    private static ArrayList<Jugador> jugadores = new ArrayList();
    private static ArrayList<Carta> mazo = new ArrayList();
    private static ArrayList<Carta> descarte = new ArrayList();
    
    private static Jugador jugadorActual;
    private static Boolean reversa = false;
    private static Boolean saltarTurno = false;
    private static Boolean dosSiguiente = false;
    private static Boolean cuatroSiguiente = false;
    
    private static int TAMANHO_MANO = 7;
    
    /**
     * Se encarga de inicializar la clase partida, crea el servidor y se le 
     * asigna el puerto 7778
     * Tambien se crea y asigna una vista al controlador
     */
    private Partida(){
        try {
            server = new ServerSocket(7778);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        vista = new UnoView(this.INSTANCE);
    }

    /** Metodos **/
    /**
     * Este método se encarga de crear el mazo y escoger de forma aleatoria una 
     * carta con la que se iniciará el juego. Si la carta es un comodín o una 
     * carta reversa se debe volver a "barajar" el mazo y sacar una nueva.
     * Por último se reparten las cartas a los jugadores que estén registrados 
     * en la partida.
     */
    public static void iniciarPartida(){
        //Creo el mazo y lo desordeno
        mazo = CartasFactory.makeMazo();
        do {
            Collections.shuffle(mazo);
        } while(mazo.get(mazo.size() - 1) instanceof CartaComodin ||
                mazo.get(mazo.size() - 1) instanceof CartaComodinTome4 ||
                mazo.get(mazo.size() - 1) instanceof CartaReversa);
        
        //Agrego una en descarte y la quito del mazo
        descarte.add(mazo.get(mazo.size() - 1));
        mazo.remove(mazo.size() - 1);
        
        //Reparto las cartas a los jugadores
        repartirCartas();
    }
    
    /**
     * Este método se encarga de repartir las cartas a los jugadores registrados
     * de acuerdo al tamaño de mano.
     */
    public static void repartirCartas(){
        for(Jugador pJugador : jugadores){
            for(int i = 0; i < TAMANHO_MANO; i++){
                pJugador.addCarta(mazo.get(0));
                mazo.remove(0);
            }
        }
    }
    
    /**
     * Este método se encarga de enviar mensajes por consola al jugador que se 
     * le indique en el primer parámetro.
     * @param pJugador - Jugador a enviar el mensaje
     * @param pMensaje - Mensaje a enviar al jugador
     */
    public static void imprimir(Jugador pJugador, String pMensaje){
        try {
            PrintWriter clientOut = new PrintWriter(pJugador.getSocket().getOutputStream(), true);
            clientOut.println(pMensaje);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Este método se encarga de hacer el registro de un jugador.
     */
    public static void registrarJugador(){
        try {
            Jugador nuevo = new Jugador(); //Se crea un jugador
            nuevo.setSocket(server.accept()); //Se espera una conexión por medio de Telnet
            BufferedReader clientIn = new BufferedReader(
                    new InputStreamReader(nuevo.getSocket().getInputStream()));
            imprimir(nuevo, "Digite el nombre de jugador: ");
            nuevo.setNombre(clientIn.readLine());
            System.out.println("Se agregó el siguiente jugador: " + nuevo.getNombre());
            jugadores.add(nuevo);
            jugadorActual = jugadores.get(jugadores.size() - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Este método se encarga de tomar la carta que el usuario elige y la coloca
     * en el mazo de descarte
     * @param eleccion - Posición de la carta en la mano
     */
    public static void usarCarta(int eleccion){
        descarte.add(jugadorActual.getMano().get(eleccion));
        jugadorActual.removeCarta(jugadorActual.getMano().get(eleccion));
    }
    
    /**
     * Este método se encarga de comprobar que el mazo no se encuentre vacío, de
     * ser positivo, se le asigna el mazo de descarte y se rebaraja
     */
    public static void comprobarMazo(){
        if(mazo.size() == 0){ 
            Carta cartaActual = getTopeDescarte();
            descarte.remove(descarte.size() - 1);
            mazo.addAll(descarte);
            Collections.shuffle(mazo);
            descarte.clear();
            descarte.add(cartaActual);
        }
    }
    
    /**
     * Se encarga de cambiar el estado si una carta activa el efecto de SALTO
     */
    public static void siguientePierdeTurno(){
        saltarTurno = true;
    }
    
    /**
     * Se encarga de cambiar el estado si una carta activa el efecto de REVERSA
     */
    public static void cambiarSentido(){
        reversa = true;
    }
    
    /**
     * Se encarga de cambiar el estado si una carta activa el efecto de DOS AL
     * SIGUIENTE
     */
    public static void darDosAlSiguiente(){
        dosSiguiente = true;
    }
    
    /**
     * Se encarga de cambiar el estado si una carta activa el efecto de CUATRO
     * AL SIGUIENTE
     */
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
    
    /**
     * Este método se encarga de ejecutar la acción de la carta
     * @param pCarta - Carta a ejecutar el efecto
     */
    public static void ejecutarCarta(Carta pCarta){
        pCarta.ejecutarAccion();
    }
    
    /**
     * Este método se encarga de comprobar si la elección del usuario es válida
     * @param pEleccion - Posición en la mano de la carta
     * @return
     * @throws Exception 
     */
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
    
    /**
     * Este método se encarga de "decidir" cual es el siguiente jugador de 
     * acuerdo a la carta en el top de descarte.
     */
    public static void siguienteJugador(){
        //Ejecuto la carta en el tope de descarte
        ejecutarCarta(getTopeDescarte());
        
        //Compruebo si se debe revertir el orden
        if(reversa){
            Collections.reverse(jugadores);
        }
        reversa = false;
        
        //Se cambia del jugador anterior al jugador que ahora tendrá el turno
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
            if(jugadores.indexOf(jugadorActual) == jugadores.size() - 1){ //Se salta al jugador luego de aplicado el efecto
                jugadorActual = jugadores.get(0);
            } else {
                jugadorActual = jugadores.get(jugadores.indexOf(jugadorActual) + 1);
            }
        }
        //Se "apagan" los efectos
        cuatroSiguiente = false;
        dosSiguiente = false;
        saltarTurno = false;
    }
    
    /**
     * Este método se encarga de iniciar la vista de la aplicación
     * @throws Exception 
     */
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
    
    public static ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    
    public static int cantJugadores(){
        return jugadores.size();
    }
}
