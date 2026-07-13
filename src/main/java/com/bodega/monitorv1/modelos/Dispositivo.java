/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.modelos;

/**
 *
 * @author Yordin
 */
public class Dispositivo {
    private String ip ;

    private int id ;

    public Dispositivo() {
        this.ip = ip;
        this.id = id ;
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

    @Override
    public String toString() {
        return "Dispositivo{" + "ip=" + ip + ", id=" + id + '}';
    }
    
    
}
