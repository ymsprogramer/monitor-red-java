/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.bodega.monitorv1;

import com.bodega.monitorv1.Controlador.EscanerIpControlador;
import com.bodega.monitorv1.Controlador.ScannerMacControlador;
import com.bodega.monitorv1.configuracion.CambiosPendientes;
import com.bodega.monitorv1.configuracion.ConfiguracionFlujoDatos;
import com.bodega.monitorv1.configuracion.ConfiguracionRed;
import com.bodega.monitorv1.configuracion.DatosEntrada;
import com.bodega.monitorv1.configuracion.ManejoTemporales;
import com.bodega.monitorv1.persintencia.DispositivoRepositoryBD;
import com.bodega.monitorv1.repository.DispositivoRepository;
import com.bodega.monitorv1.servicio.Scaneos;
import com.bodega.monitorv1.servicio.ScannerIpServicio;
import com.bodega.monitorv1.servicio.ScannerMacServicio;
import java.io.IOException;

/**
 *
 * @author Yordin
 */

public class Monitorv1 {

    public static void main(String[] args) throws IOException, Exception {
       
        System.out.println("Hello World!");

        DatosEntrada verficador = new DatosEntrada();
        ConfiguracionRed config = new ConfiguracionRed();
        DispositivoRepository repo_map = new DispositivoRepository();
        CambiosPendientes repo_cambios = new CambiosPendientes();

        DispositivoRepositoryBD repo_bd = new DispositivoRepositoryBD(repo_map, repo_cambios);
        ConfiguracionFlujoDatos flujocon = new ConfiguracionFlujoDatos(repo_map, repo_bd);

        ScannerIpServicio servicioip = new ScannerIpServicio(repo_map, config);
        ScannerMacServicio servicioMac = new ScannerMacServicio(repo_map, config);
        ManejoTemporales Manejoregistros = new ManejoTemporales(repo_map, repo_cambios);

        Scaneos scan = new Scaneos();

        ScannerMacControlador MacScanner = new ScannerMacControlador(servicioMac);
        EscanerIpControlador ipscanner = new EscanerIpControlador(servicioip);

        boolean respuesta_menu = true;
        boolean respuestam1 = false;
        boolean respuestam2 = false;
        do {
            System.out.println("-----configuracin de conexion de base de datos ---------");
            System.out.println(" 1 : hacer coneccion con la base de datos  ");
            System.out.println(" 2 : no hacer coneccion con la base de datos  ");
            System.out.println(" 0 : salir de la aplicacion ");
            System.out.println(" DEBE  SELECCIONAR UNA OPCION PARA PODER CONTINUAR ");
            System.out.println("diguita una respuesta");
            int answ = verficador.manejoErrores();

            switch (answ) {

                case 1 -> {
                    if (flujocon.flujocarga()) {

                        System.out.println("los datos han sido cargados correctamente de la base de datos");
                    } else {
                        System.out.println("no se han encontardo datos en la base de datos");
                    }

                    respuesta_menu = false;
                    respuestam1 = true;
                    break;
                }
                case 2 -> {

                    respuesta_menu = false;
                    respuestam2 = true;
                    break;
                }

                case 0 -> {
                    respuesta_menu = false;
                    break;
                }

                default -> {

                    System.out.println(" la opcion selecionada no esta disponible ");
                    break;
                }

            }

        } while (respuesta_menu);

        /* menu del porgrama con coenccion a la base de datos */
        while (respuestam1) {

            System.out.println("-----menu----------");
            System.out.println(" 1 : Scaneo de ip  ");
            System.out.println(" 2 : scaneo de mac ");
            System.out.println(" 3 : mostrar el scaneo de mac ");
            System.out.println(" 4 : mostrar el scaneo de ip");
            System.out.println(" 5 : eliminar todos registros de la base datos  ");
            System.out.println(" 6 : actulizar cambios en la base de datos ");
            System.out.println(" 0 : salir ");

            System.out.println("diguita una respuesta");
            int answ = verficador.manejoErrores();

            switch (answ) {

                case 1 -> {

                    ipscanner.redEscaneoip();

                    break;
                }
                case 2 -> {
                    if (ipscanner.isEscaneoIpRealizado()) {
                        MacScanner.RedScaneoMac();
                        Manejoregistros.manejo_temporales();
                        Manejoregistros.ManejarGuardar();
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
                case 5 -> {
                    repo_bd.borrarTodos();
                    repo_bd.borrarTodos();
                    repo_map.obtenertodos().clear();
                    repo_map.limpiarMacs();
                    repo_cambios.limpiar();
                    System.out.println("Registros eliminados de la base de datos y de la memoria");
                    break;
                }
                case 6 -> {

                    if (ipscanner.isEscaneoIpRealizado() && MacScanner.isMacscannerRealizado()) {
                        repo_bd.sincronizar();

                    }

                    break;
                }

                case 0 -> {
                    respuestam1 = false;
                    break;
                }

                default -> {

                    System.out.println(" la opcion selecionada no esta disponible ");
                    break;
                }

            }

        }
        /* menu del porgrama sin coenccion a la base de datos */

        while (respuestam2) {

            System.out.println("-----menu----------");
            System.out.println(" 1 : Scaneo de ip  ");
            System.out.println(" 2 : scaneo de mac ");
            System.out.println(" 3 : mostrar el scaneo de mac ");
            System.out.println(" 4 : mostrar el scaneo de ip");
            System.out.println(" 5 : mostara todos los datos ");
            System.out.println(" 0 : salir ");

            System.out.println("diguita una respuesta");
            int answ = verficador.manejoErrores();

            switch (answ) {

                case 1 -> {

                    ipscanner.redEscaneoip();

                    break;
                }
                case 2 -> {
                    if (ipscanner.isEscaneoIpRealizado()) {
                        MacScanner.RedScaneoMac();
                        if (ipscanner.isEscaneoIpRealizado() && MacScanner.isMacscannerRealizado()) {
                            Manejoregistros.manejo_temporales();
                            repo_cambios.limpiar();
                        }
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
                case 5 -> {
                    ipscanner.mostrarDispositivos();
                    break;
                }

                case 0 -> {
                    respuestam2 = false;
                    break;
                }

                default -> {

                    System.out.println(" la opcion selecionada no esta disponible ");
                    break;
                }

            }

        }

    }
}
