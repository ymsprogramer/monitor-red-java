/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.bodega.monitorv1.modelos;
import java.time.LocalDateTime;

/**
 *
 * @author Yordin
 */
public class Dispositivo {

    private String ip;
    private String mac;
    private int id;
    private LocalDateTime fechaDeteccion;
    private String sobrenombre ;

    public Dispositivo() {
    }

 

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public LocalDateTime getFechaDeteccion() {
        return fechaDeteccion;
    }

    public void setFechaDeteccion(LocalDateTime fechaDeteccion) {
        this.fechaDeteccion = fechaDeteccion;
    }

    public String getSobrenombre() {
        return sobrenombre;
    }

    public void setSobrenombre(String sobrenombre) {
        this.sobrenombre = sobrenombre;
    }

    @Override
    public String toString() {
        return "Dispositivo{" + "ip=" + ip + ", mac=" + mac + ", id=" + id + ", fechaDeteccion=" + fechaDeteccion + ", sobrenombre=" + sobrenombre + '}';
    }
    

  

}
