/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.servicio;

/**
 *
 * @author Yordin
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.Repository;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Scanner;

public class Scaneos {

    private BufferedReader br;
    private boolean escaneo_redip = false;
    private boolean escaneo_mac = false;
    private static final Scanner in = new Scanner(System.in);
    private Repository lista = new Repository();

    public void Scaneoip() throws UnknownHostException, IOException {
        /* la variable estado es para indicar a la funcion si el escaneo esta bien realizado 
        o no y lo manda a la varible global escaneo_redip*/
        boolean estado = true;
        System.out.println(" digite la red a la que se desea escanear  ejm :192.168.1.");
        String red = in.nextLine();

        for (int i = 1; i < 256; i++) {

            String objetivo = red + i;

            InetAddress dirreccion = InetAddress.getByName(objetivo);

            if (dirreccion instanceof Inet4Address ipv4) {

                boolean respuesta = dirreccion.isReachable(500);

                if (respuesta) {
                    System.out.println(" encontrado activo encontrado" + ipv4.getHostAddress());
                    Dispositivo nuevo = new Dispositivo();
                    nuevo.setIp(ipv4.getHostAddress());
                    nuevo.setId(i);

                    lista.dispositivos.put(i, nuevo);
                }

            } else {
                System.out.println("No es una dirección IPv4.");
                estado = false;

            }

            escaneo_redip = estado;
        }

    }

    public void mostrar_ip() {
        if (!escaneo_redip) {
            System.out.println("priemro debes realizar el esaceno de ip ");
            return;
        }
        for (var entry : lista.dispositivos.entrySet()) {

            Integer id = entry.getKey();
            Dispositivo dispositivo = entry.getValue();

            System.out.println("ID: " + id + " | IP: " + dispositivo.getIp());
        }

    }

    public void Scaneos_mac() throws IOException {

        if (!escaneo_redip) {
            boolean cont = true;
            do {

                System.out.println(" el escaneo de la red ip no se a realizado"
                        + "la tabla arp no esta actulizada deseas hacer un escaneo de ip primero ? si:1  no:0 ");
                int respuesta = in.nextInt();

                switch (respuesta) {

                    case 1 -> {
                        System.out.println(" rediriguiendo al esacaneo de ip");
                        Scaneoip();
                        break;
                    }

                    case 0 -> {
                        System.out.println("deseas continuar y hacer el escaneo de arp o salir  seguir:1  salir:0");
                        int respuestai = in.nextInt();

                        if (respuestai == 0) {
                            return;

                        } else {
                            cont = false;
                        }
                        break;
                    }

                    default -> {
                        System.out.println(" opcion no disponible ");
                        break;
                    }
                }
            } while (cont);

        }

        ProcessBuilder pb = new ProcessBuilder("arp", "-a");

        Process proceso = pb.start();

        br = new BufferedReader(
                new InputStreamReader(proceso.getInputStream())
        );

        System.out.println(" escaneo listo");
        escaneo_mac = true;

    }

    public void Mostrar_mac() throws IOException {
        if (!escaneo_mac) {
            System.out.println(" priemro debes relaizar el escaneo de mac ");
            return;
        }
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    }

}
