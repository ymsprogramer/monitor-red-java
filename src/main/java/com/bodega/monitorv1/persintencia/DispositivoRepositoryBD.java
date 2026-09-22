/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.persintencia;

/**
 *
 * @author yordin
 */
import com.bodega.monitorv1.configuracion.ConexionBD;
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.DispositivoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DispositivoRepositoryBD {

    private DispositivoRepository repo;

    public DispositivoRepositoryBD(DispositivoRepository repo) {
        this.repo = repo;
    }

    public void guardar() throws SQLException {
        Map<Integer, Dispositivo> dispositivos = repo.obtenertodos();

        String sql = "INSERT INTO dispositivos (id, ip, mac) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            for (Map.Entry<Integer, Dispositivo> dispositivo : dispositivos.entrySet()) {
                if (!existeId(dispositivo.getKey())) {
                    sentencia.setInt(1, dispositivo.getValue().getId());
                    sentencia.setString(2, dispositivo.getValue().getIp());
                    sentencia.setString(3, dispositivo.getValue().getMac());
                    sentencia.executeUpdate();
                }
            }
        }

    }

    public void borrarTodos() throws SQLException {
        String sql = "DELETE FROM dispositivos";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.executeUpdate();
        }
    }

    public void cargar() throws SQLException {

        String sql = "SELECT id, ip, mac FROM dispositivos";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String ip = resultado.getString("ip");
                String mac = resultado.getString("mac");

                Dispositivo dispositivo = new Dispositivo();
                dispositivo.setId(id);
                dispositivo.setIp(ip);
                dispositivo.setMac(mac);

                repo.guardarmap(id, dispositivo);
            }
        }
    }

    public boolean existeId(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dispositivos WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            ResultSet resultado = sentencia.executeQuery();
            resultado.next();

            int cantidad = resultado.getInt(1);

            return cantidad > 0;
        }
    }

    public boolean hayDispositivos() throws SQLException {
        String sql = "SELECT COUNT(*)  FROM dispositivos";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            resultado.next();

            int cantidad = resultado.getInt(1);

            return cantidad > 0;
        }
    }

    public Map<Integer, Dispositivo> obtenerDispositivos() {
        return repo.obtenertodos();
    }

}
