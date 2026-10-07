/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.configuracion;

import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.DispositivoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yordin
 */
public class ManejoTemporales {

    private DispositivoRepository repo;
    private CambiosPendientes cambios;

    public ManejoTemporales(DispositivoRepository repo, CambiosPendientes cambios) {
        this.cambios = cambios;
        this.repo = repo;
    }

    public boolean manejo_temporales() {

        Map<Integer, Dispositivo> dispositivos = repo.obtenertodos();
        for (var dispositivo : dispositivos.entrySet()) {

            String mac = dispositivo.getValue().getMac();

            List<Integer> macs = repo.obtenerIdsPorMac(mac);

            if (macs == null || macs.size() < 2) {
                continue;
            }

            for (int i = 0; i < macs.size() - 1; i++) {

                for (int j = i + 1; j < macs.size(); j++) {

                    Integer id1 = macs.get(i);
                    Integer id2 = macs.get(j);

                    if (id1.equals(id2)) {
                        continue;
                    }

                    Dispositivo d1 = dispositivos.get(id1);
                    Dispositivo d2 = dispositivos.get(id2);

                    if (d1 == null || d2 == null) {
                        continue;
                    }

                    LocalDateTime fecha1 = d1.getFechaDeteccion();
                    LocalDateTime fecha2 = d2.getFechaDeteccion();

                    if (fecha1.isBefore(fecha2)) {

                        Dispositivo oficial = d1;
                        Dispositivo temporal = d2;

                        oficial = Actualizar(oficial, temporal);

                        cambios.agregarEliminacion(temporal.getId());
                        cambios.agregarActualizacion(oficial);
                        cambios.agregarGuardar(oficial);

                    }

                    if (fecha2.isBefore(fecha1)) {

                        Dispositivo oficial = d2;
                        Dispositivo temporal = d1;

                        oficial = Actualizar(oficial, temporal);
                        cambios.agregarEliminacion(temporal.getId());
                        cambios.agregarActualizacion(oficial);
                        cambios.agregarGuardar(oficial);

                    }

                }

            }

        }

        for (Integer id : cambios.obtenerIdsParaEliminar()) {
            repo.BorrarId(id);
        }

        Map<Integer, Dispositivo> registro = cambios.obtenerDispositivosParaActualizar();

        for (var registros : registro.entrySet()) {

            int id = registros.getValue().getId();

            Dispositivo oficial = registros.getValue();

            Dispositivo d = repo.obtenerDispositivoId(id);

            d.setIp(oficial.getIp());
            d.setFechaDeteccion(oficial.getFechaDeteccion());

        }

        return true;
    }

    private Dispositivo Actualizar(Dispositivo oficial, Dispositivo Temporal) {

        oficial.setIp(Temporal.getIp());
        oficial.setFechaDeteccion(Temporal.getFechaDeteccion());

        return oficial;
    }

    public void ManejarGuardar() {

        Map<Integer, Dispositivo> dispositivos = repo.obtenertodos();

        for (Dispositivo d : dispositivos.values()) {

            cambios.agregarGuardar(d);

        }

    }

}
