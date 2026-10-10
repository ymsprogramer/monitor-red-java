package com.bodega.monitorv1.repository;

import com.bodega.monitorv1.modelos.Dispositivo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author yordin
 */
public class DispositivoRepository {

    private final Map<Integer, Dispositivo> dispositivos;
    private final Map<String, List<Integer>> Macs;

    public DispositivoRepository() {
        this.dispositivos = new HashMap<>();
        this.Macs = new HashMap<>();
    }

    public void guardarmap(Integer clave, Dispositivo valor) {

        dispositivos.put(clave, valor);

    }

    public Map<Integer, Dispositivo> obtenertodos() {

        return dispositivos;
    }

    public void guardarMacs(Dispositivo dispositivo) {

        Macs.computeIfAbsent(dispositivo.getMac(), clave -> new ArrayList<>()).add(dispositivo.getId());
    }

    public void BorrarId(int clave) {

        dispositivos.remove(clave);
    }

    public Map<String, List<Integer>> obtenertodosMac() {

        return Macs;
    }

    public List<Integer> obtenerIdsPorMac(String mac) {
        return Macs.getOrDefault(mac, new ArrayList<>());
    }

    public Map< Integer, Dispositivo> obtenerOrdenados() {

        return new TreeMap<>(dispositivos);
    }
    
    public void limpiarMacs(){
        
        Macs.clear();
    
    }
    
    public Dispositivo obtenerDispositivoPorId( int id ){
        
     Dispositivo dispositivo =  dispositivos.get(id);
        
     return dispositivo;
    }

    public int CantidadDispositivos () {
        
        
      return dispositivos.size();
    }
    
    

}
