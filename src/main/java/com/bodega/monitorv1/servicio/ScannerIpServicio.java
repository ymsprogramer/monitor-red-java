/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.servicio;

import com.bodega.monitorv1.Controlador.EscanerIpControlador;
import com.bodega.monitorv1.configuracion.ConfiguracionRed;
import com.bodega.monitorv1.configuracion.ManejoTemporales;
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.DispositivoRepository;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Map;
import org.pcap4j.core.PcapNetworkInterface;


/**
 *
 * @author yordin
 */
public class ScannerIpServicio {

    private DispositivoRepository repo;
    private ConfiguracionRed config ;
    private ManejoTemporales Manejo;

    public ScannerIpServicio(DispositivoRepository repo , ConfiguracionRed config , ManejoTemporales Manejo  ) {
        this.repo = repo;
        this.config =  config ;
        this.Manejo= Manejo ;

    }

    public ScannerIpServicio() {

    }

    public boolean EscaneoIp() throws UnknownHostException, IOException, Exception {
        
        config.obtenerInterfaz();
        
        String red = config.getBaseEscaneo();

        String objetivo;

        for (int i = 1; i < config.IpsUtilizables(); i++) {

            objetivo = red + i;
            InetAddress dirreccion = InetAddress.getByName(objetivo);

            if (dirreccion instanceof Inet4Address ipv4) {

                boolean respuesta = dirreccion.isReachable(500);

                if (respuesta) {
                    Dispositivo temporal  = new Dispositivo();
                    temporal.setIp(ipv4.getHostAddress());
                    int id = Manejo.generarIdLibre(i);
                    temporal.setId(id);
                    
                    temporal.setSobrenombre(" temporal ");
                    LocalDateTime hora_detteccion = LocalDateTime.now();
                    temporal.setFechaDeteccion(hora_detteccion );

                    repo.guardarmap(id ,temporal);
                    

                }
                
                

            } else {

                return false;

            }

        }
        
        return true;
    }

    public Map<Integer, Dispositivo> obtenerDispositivos() {
        return repo.obtenertodos();
    }
    
    public String IpDetectada(){
        
         return config.getRed();
    }
    
    public int prefijos(){
     
        return config.getPrefijo();
    }

}
