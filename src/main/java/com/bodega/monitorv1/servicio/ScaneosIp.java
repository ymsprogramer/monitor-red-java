/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.servicio;

/**
 *
 * @author Yordin
 */
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.Repository;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Scanner;

public class ScaneosIp {

    private static final Scanner in = new Scanner(System.in);
    private Repository lista = new Repository ();

    public void Scaneoip() throws UnknownHostException, IOException {
        
        
        
        System.out.println(" digite la red a la que se desea escanear  ejm :192.168.1.");
        String red  = in.nextLine();

        for (int i = 1; i < 256; i++) {
          
            String objetivo = red + i ;
               
            InetAddress dirreccion = InetAddress.getByName(objetivo);

            if (dirreccion instanceof Inet4Address ipv4) {
                
                
                boolean respuesta = dirreccion.isReachable(500);
                
                if(respuesta){
                System.out.println(" encontrado activo encontrado"+ ipv4.getHostAddress());
                Dispositivo nuevo = new Dispositivo ();
                nuevo.setIp(ipv4.getHostAddress());
                nuevo.setId(i);
                
               lista.dispositivos.put(i, nuevo);
                }
                                
            } else {
                System.out.println("No es una dirección IPv4.");
            }
        }

    }
    
    public void mostrar (){
    for ( var  entry : lista.dispositivos.entrySet()) {

    Integer id = entry.getKey();
    Dispositivo dispositivo = entry.getValue();

   System.out.println("ID: " + id + " | IP: " + dispositivo.getIp());
}
        
    }
}
