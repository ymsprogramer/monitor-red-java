/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.repository;

import com.bodega.monitorv1.modelos.Dispositivo;
import java.util.HashMap;

/**
 *
 * @author Yordin
 */
public class Repository {

    public HashMap<Integer, Dispositivo> dispositivos;
   

    public Repository() {
        this.dispositivos = new HashMap<>();
    }
    
}
