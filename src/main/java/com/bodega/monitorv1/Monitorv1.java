/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.bodega.monitorv1;

import com.bodega.monitorv1.Controlador.EscanerIpControlador;
import com.bodega.monitorv1.Controlador.ScannerMacControlador;
import com.bodega.monitorv1.configuracion.ConfiguracionRed;
import com.bodega.monitorv1.repository.DispositivoRepository;
import com.bodega.monitorv1.servicio.Scaneos;
import com.bodega.monitorv1.servicio.ScannerIpServicio;
import com.bodega.monitorv1.servicio.ScannerMacServicio;
import java.io.IOException;

/**
 *
 * @author Yordin
 */
import java.util.*;

public class Monitorv1 {

    public static void main(String[] args) throws IOException, Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Hello World!");

        ConfiguracionRed config = new ConfiguracionRed();
        DispositivoRepository repo_map = new DispositivoRepository();

        ScannerIpServicio servicioip = new ScannerIpServicio(repo_map, config);
        ScannerMacServicio servicioMac = new ScannerMacServicio(repo_map, config);
        Scaneos scan = new Scaneos();
        ScannerMacControlador MacScanner = new ScannerMacControlador(servicioMac);
        EscanerIpControlador ipscanner = new EscanerIpControlador(servicioip);

        boolean respuesta = true;

        do {

            System.out.println("-----menu----------");
            System.out.println(" 1 : Scaneo de ip  ");
            System.out.println(" 2 : scaneo de mac ");
            System.out.println(" 3 : mostrar el scaneo de mac ");
            System.out.println(" 4 : mostrar el scaneo de ip");
            System.out.println(" 0 : salir ");

            System.out.println("diguita una respuesta");
            int answ = in.nextInt();

            switch (answ) {

                case 1 -> {
                    ipscanner.redEscaneoip();
                    break;
                }
                case 2 -> {
                    if (ipscanner.isEscaneoIpRealizado()) {
                        MacScanner.RedScaneoMac();
                    } else {
                        System.out.println("primero debes realizar un escaneo de IP para cargar la tabla arp");
                    }
                    break;
                }

                case 3 -> {

                    MacScanner.mostrarDispositivos();

                    break;
                }

                case 4 -> {
                    ipscanner.mostrarDispositivos();
                    break;
                }
                case 0 -> {
                    respuesta = false;
                    break;
                }

            }

        } while (respuesta);

    }
}
