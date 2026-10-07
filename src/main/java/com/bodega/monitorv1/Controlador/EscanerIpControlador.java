/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.Controlador;

/**
 *
 * @author yordin
 */
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.servicio.ScannerIpServicio;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

public class EscanerIpControlador implements mostrarDispositivos {

    private boolean escaneoIpRealizado = false;

    private final static Scanner in = new Scanner(System.in);

    private ScannerIpServicio servicio;

    public EscanerIpControlador(ScannerIpServicio servicio) {
        this.servicio = servicio;

    }

    public EscanerIpControlador(boolean escaneoIpRealizado) {

        this.escaneoIpRealizado = escaneoIpRealizado;
    }

    public EscanerIpControlador() {
    }

    public void redEscaneoip() throws IOException, Exception {

        boolean respuesta = servicio.EscaneoIp();

        if (respuesta) {

            System.out.println(" Escaneo finalizado con exito sobre la red : " + servicio.IpDetectada() + "/ " + servicio.prefijos());
            escaneoIpRealizado = true;

        } else {
            System.out.println(" error al intentar esacnnear ip no valida  ");

        }

    }

    @Override
    public void mostrarDispositivos() {

        Map<Integer, Dispositivo> dispositivos = servicio.obtenerDispositivos();

        if (dispositivos.isEmpty()) {
            System.out.println("no se ha hecho ningun escaneo de IP ");
            return;
        }

        for (Map.Entry<Integer, Dispositivo> u : dispositivos.entrySet()) {

            Integer id = u.getKey();
            Dispositivo dispositivo = u.getValue();
            LocalDateTime hora = dispositivo.getFechaDeteccion();
            DateTimeFormatter formato
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            System.out.println(
                    "ID: " + id
                    + " | IP: " + dispositivo.getIp()
                    + " | FECHA " + hora.format(formato)
            );
        }
    }

    public boolean isEscaneoIpRealizado() {
        return escaneoIpRealizado;
    }

    public void setEscaneoIpRealizado(boolean escaneoIpRealizado) {
        this.escaneoIpRealizado = escaneoIpRealizado;
    }

}
