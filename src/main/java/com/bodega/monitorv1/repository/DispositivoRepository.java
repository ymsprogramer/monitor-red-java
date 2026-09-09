package com.bodega.monitorv1.repository;

import com.bodega.monitorv1.modelos.Dispositivo;
import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yordin
 */
public class DispositivoRepository {
    
    private Map<Integer, Dispositivo> dispositivos ;
    
    public DispositivoRepository() {
        this.dispositivos = new HashMap<>();
    }

    

    public void guardarmap (Integer clave , Dispositivo valor  ){
        
        dispositivos.put(clave, valor);
        
    }
    
    
    public Map<Integer, Dispositivo> obtenertodos(){ 

    return dispositivos;
    }

   
    
    
}
