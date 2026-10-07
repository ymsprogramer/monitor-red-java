/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.configuracion;

import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.persintencia.DispositivoRepositoryBD;
import com.bodega.monitorv1.repository.DispositivoRepository;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author yordin
 */
public class ConfiguracionFlujoDatos {

    private final DispositivoRepository repo;
    private final DispositivoRepositoryBD repobd;
   

    public ConfiguracionFlujoDatos(DispositivoRepository repo, DispositivoRepositoryBD repobd) {
        this.repo = repo;
        this.repobd = repobd;
    }

    public boolean flujocarga() throws SQLException {

        Map<Integer, Dispositivo> dispositivos = repo.obtenertodos();
        if (dispositivos.isEmpty()) {
            if (repobd.hayDispositivos()) {
                repobd.cargar();
                return true;
            } else {
                return false;
            }
        }

        return false;

    }
    
    

}
