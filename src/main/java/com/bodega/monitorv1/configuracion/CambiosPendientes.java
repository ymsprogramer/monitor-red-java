/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.configuracion;

import com.bodega.monitorv1.modelos.Dispositivo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author yordi
 */
public class CambiosPendientes {

    private Map<Integer, Dispositivo> dispositivosParaActualizar;
    private Map<Integer, Dispositivo> dispositivosParaGuardar;
    private Set<Integer> idsParaEliminar;

    public CambiosPendientes() {

        this.idsParaEliminar = new HashSet<>();
        this.dispositivosParaActualizar = new HashMap<>();
        this.dispositivosParaGuardar = new HashMap<>();
    }

    public void agregarEliminacion(Integer id) {
        idsParaEliminar.add(id);
    }

    public void agregarActualizacion(Dispositivo dispositivo) {
        dispositivosParaActualizar.put(dispositivo.getId(), dispositivo);
    }

    public void agregarGuardar(Dispositivo dispositivo) {
        dispositivosParaGuardar.put(dispositivo.getId(), dispositivo);
    }

    public Set<Integer> obtenerIdsParaEliminar() {
        return idsParaEliminar;
    }

    public Map<Integer, Dispositivo> obtenerDispositivosParaActualizar() {
        return dispositivosParaActualizar;
    }

    public Map<Integer, Dispositivo> obtenerDispositivosParaGuardar() {
        return dispositivosParaGuardar;
    }
    
    public void EliminarGuardar(int id ){
     
        dispositivosParaGuardar.remove(id);
    
    }

    public void limpiar() {
        idsParaEliminar.clear();
        dispositivosParaActualizar.clear();
        dispositivosParaGuardar.clear();
    }


}
