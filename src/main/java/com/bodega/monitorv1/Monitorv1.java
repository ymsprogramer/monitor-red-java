/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.bodega.monitorv1;

import com.bodega.monitorv1.servicio.Scaneos;
import java.io.IOException;

/**
 *
 * @author Yordin
 */
import java.util.*;

public class Monitorv1 {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Hello World!");

        Scaneos scan = new Scaneos();
        boolean respuesta = true;

        do {

            System.out.println("-----menu----------");
            System.out.println(" scaneo de ip : 1");
            System.out.println("scaneo de mac : 2");
            System.out.println("msotarar el scaneo de mac  : 3");
            System.out.println("mostrar el scaneo de ip : 4");
            System.out.println("salir : 0 ");

            System.out.println("diguita una respuesta");
            int answ = in.nextInt();

            switch (answ) {

                case 1 -> {
                    scan.Scaneoip();
                    break;
                }
                case 2 -> {
                    scan.Scaneos_mac();
                     break;
                }

                case 3 -> {
                    scan.Mostrar_mac();
                     break;
                }

                case 4 -> {
                    scan.mostrar_ip();
                     break;
                }
                case 0 -> {
                    respuesta= false;
                     break;
                }

            }

        } while (respuesta);

    }
}
