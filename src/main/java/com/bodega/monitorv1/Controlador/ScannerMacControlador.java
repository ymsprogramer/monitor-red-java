/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.Controlador;

import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.DispositivoRepository;
import com.bodega.monitorv1.servicio.ScannerMacServicio;
import java.util.Map;

/**
 *
 * @author yordin
 */
public class ScannerMacControlador implements mostrarDispositivos {

    private ScannerMacServicio servicio;
   

    public ScannerMacControlador(ScannerMacServicio servicio) {
        this.servicio = servicio;

    }

    public void RedScaneoMac() throws Exception {

        boolean p = servicio.escanearMacs();

        if (p) {
            System.out.println(" Scaneo de mac relaizado con exito ");
        }

    }

    @Override
    public void mostrarDispositivos() {

        Map<Integer, Dispositivo> dispositivos = servicio.obtenerDispositivos();
                if (dispositivos.isEmpty()){
        System.out.println("no se ha hecho nigun escaneo de Mac ");
        return;
        }

        for (Map.Entry<Integer, Dispositivo> u : dispositivos.entrySet()) {

            Integer id = u.getKey();
            Dispositivo dispositivo = u.getValue();
            String mac = dispositivo.getMac();

            System.out.println(
                    "ID: " + id
                    + " | IP: " + dispositivo.getIp()
                    + " MAC" + mac
            );
        }
    }
}
