/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.bodega.monitorv1;

import com.bodega.monitorv1.servicio.ScaneosIp;
import java.io.IOException;

/**
 *
 * @author Yordin
 */
public class Monitorv1 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
            
        ScaneosIp scan = new ScaneosIp ();
        
        try {
            scan.Scaneoip();
            scan.mostrar();
        } catch (IOException ex) {
            System.getLogger(Monitorv1.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
