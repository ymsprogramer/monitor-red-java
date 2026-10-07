/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.persintencia;

/**
 *
 * @author yordin
 */
import com.bodega.monitorv1.configuracion.CambiosPendientes;
import com.bodega.monitorv1.configuracion.ConexionBD;
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.DispositivoRepository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public class DispositivoRepositoryBD {

    private DispositivoRepository repo;
    private CambiosPendientes cambios;

    public DispositivoRepositoryBD(DispositivoRepository repo, CambiosPendientes cambios) {
        this.repo = repo;
        this.cambios = cambios;
    }

    public void guardar(Dispositivo d) throws SQLException {

        if (existeId(d.getId())) {

            return;
        }

        String sql = """
            INSERT INTO dispositivos (id, ip, mac, fecha_deteccion)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, d.getId());
            ps.setString(2, d.getIp());
            ps.setString(3, d.getMac());
            ps.setObject(4, d.getFechaDeteccion());

            ps.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public void borrarTodos() throws SQLException {
        String sql = "DELETE FROM dispositivos";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.executeUpdate();
        }
    }

    public void cargar() throws SQLException {

        String sql = "SELECT id, ip, mac, fecha_deteccion FROM dispositivos";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String ip = resultado.getString("ip");
                String mac = resultado.getString("mac");
                LocalDateTime fecha = resultado.getObject("fecha_deteccion", LocalDateTime.class);

                Dispositivo dispositivo = new Dispositivo();
                dispositivo.setId(id);
                dispositivo.setIp(ip);
                dispositivo.setMac(mac);
                dispositivo.setFechaDeteccion(fecha);

                repo.guardarmap(id, dispositivo);
                repo.guardarMacs(dispositivo);   
            }
        }
    }
    

    public void actualizar(Dispositivo d) {

        String sql = """
        UPDATE dispositivos
        SET ip = ?, mac = ?, fecha_deteccion = ?
        WHERE id = ?
        """;

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, d.getIp());
            ps.setString(2, d.getMac());
            ps.setObject(3, d.getFechaDeteccion());
            ps.setInt(4, d.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
        }

    }

    public void eliminar(int id) throws SQLException {

        if (!existeId(id)) {
            return;
        }

        String sql = """
        DELETE FROM dispositivos
        WHERE id = ?
        """;

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
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
    
    /*
    public void LimpiarContenidoAguardar(){
    
    Map< Integer ,Dispositivo > dispositivos = repo.obtenertodos();
    
    for (var eliminar : dispositivos.entrySet() ){
    
        if( eliminar.getValue().getMac()== null ){}
                
        int id = eliminar.getValue().getId();
        
        cambios.EliminarGuardar(id);
       
        
    }
    
    
    
    
     LimpiarContenidoAguardar();
    
    }
*/
    public void sincronizar() throws SQLException {
        
       

        System.out.println("Pendientes para guardar: "
                + cambios.obtenerDispositivosParaGuardar().size());

        for (Dispositivo d : cambios.obtenerDispositivosParaGuardar().values()) {
            System.out.println("Intentando guardar ID: " + d.getId());
            guardar(d);
        }

        for (Dispositivo d : cambios.obtenerDispositivosParaActualizar().values()) {
            actualizar(d);
        }

        for (Integer id : cambios.obtenerIdsParaEliminar()) {
            eliminar(id);
        }

        cambios.limpiar();
    }

    public Map<Integer, Dispositivo> obtenerDispositivos() {
        return repo.obtenertodos();
    }

}
