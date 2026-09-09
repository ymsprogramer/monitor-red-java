/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author yordi
 */
package com.bodega.monitorv1.configuracion;

import java.net.Inet4Address;
import java.net.InetAddress;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

public class ConfiguracionRed {

    private String ipInterface;
    private String red;
    private int prefijo;
    private int utilizables;

    public PcapNetworkInterface obtenerInterfaz() throws Exception {

        for (PcapNetworkInterface interfaz : Pcaps.findAllDevs()) {

            if (interfaz.isLoopBack()) {
                continue;
            }

            for (PcapAddress direccion : interfaz.getAddresses()) {

                InetAddress ip = direccion.getAddress();
                InetAddress mascara = direccion.getNetmask();

                if (ip instanceof Inet4Address ipv4
                        && mascara instanceof Inet4Address mascaraIPv4) {

                    String valor = ipv4.getHostAddress();

                    // Ignorar direcciones 169.254.x.x pipe
                    if (valor.startsWith("169.254.")) {
                        continue;
                    }

                    int prefijoCalculado = calcularPrefijo(mascaraIPv4);

                    if (prefijoCalculado == 24) {

                       
                        ipInterface = valor;
                        prefijo = prefijoCalculado;

                        
                        red = calcularRed(ipv4, mascaraIPv4);

                        return interfaz;
                    }
                }
            }
        }

        return null;
    }

    private int calcularPrefijo(Inet4Address mascara) {

        byte[] bytes = mascara.getAddress();

        int prefijo = 0;

        for (byte b : bytes) {

            int valor = b & 0xFF;

            prefijo += Integer.bitCount(valor);
        }

        return prefijo;
    }

    private String calcularRed(Inet4Address ip, Inet4Address mascara) {

        byte[] ipBytes = ip.getAddress();
        byte[] maskBytes = mascara.getAddress();

        byte[] redBytes = new byte[4];

        for (int i = 0; i < 4; i++) {

            redBytes[i] = (byte) (ipBytes[i] & maskBytes[i]);
        }

        return (redBytes[0] & 0xFF) + "."
                + (redBytes[1] & 0xFF) + "."
                + (redBytes[2] & 0xFF) + "."
                + (redBytes[3] & 0xFF);
    }

    public int IpsUtilizables() {

        int bitsHost = 32 - prefijo;

        int temp = (int) Math.pow(2, bitsHost);

        utilizables = temp - 2;

        return utilizables;
    }
    
    public String getBaseEscaneo() {
    String[] partes = red.split("\\.");

    return partes[0] + "."
            + partes[1] + "."
            + partes[2] + ".";
}

    public int getIpsUtilizables() {
        return utilizables;
    }

    public String getIpInterface() {
        return ipInterface;
    }

    public String getRed() {
        return red;
    }

    public int getPrefijo() {
        return prefijo;
    }
}
