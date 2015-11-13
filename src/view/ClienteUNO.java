/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author David
 */
public class ClienteUNO {
    
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    
    public static void main(String[] args) {
        try {
            // Make connection and initialize streams
            Scanner leer = new Scanner(System.in);
            
            Socket socket = new Socket("localhost", 7778);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String mensaje;
            
            System.out.println("Digite el nombre de jugador; ");
            
            
            while(true){
                while(true){
                    mensaje = in.readLine();
                    if(mensaje == null){
                        break;
                    }
                    System.out.println(mensaje);
                }
                out.println(leer.nextLine());
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
