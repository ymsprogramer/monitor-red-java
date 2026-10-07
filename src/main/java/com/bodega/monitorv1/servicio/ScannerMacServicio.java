/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.servicio;

import com.bodega.monitorv1.configuracion.ConfiguracionRed;
import com.bodega.monitorv1.modelos.Dispositivo;
import com.bodega.monitorv1.repository.DispositivoRepository;
import java.net.InetAddress;
import java.util.Map;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.LinkLayerAddress;
import org.pcap4j.util.MacAddress;

/**
 *
 * @author yordin
 */
public class ScannerMacServicio {

    private DispositivoRepository repo;
    private ConfiguracionRed config;

    public ScannerMacServicio(DispositivoRepository repo, ConfiguracionRed config) {
        this.repo = repo;
        this.config = config;
    }

    public boolean escanearMacs() throws Exception {

        PcapNetworkInterface interfaz = config.obtenerInterfaz();
      
        MacAddress macLocal = null;

        for (LinkLayerAddress direccion : interfaz.getLinkLayerAddresses()) {
            macLocal = MacAddress.getByAddress(direccion.getAddress());
            break;

        }

        PcapHandle handle = interfaz.openLive(
                65536,
                PcapNetworkInterface.PromiscuousMode.PROMISCUOUS,
                100
        );

        Map<Integer, Dispositivo> dispositivos = repo.obtenertodos();

        for (Map.Entry<Integer, Dispositivo> entrada : dispositivos.entrySet()) {

            String ipObjetivo = entrada.getValue().getIp();
            Dispositivo dispositivo = entrada.getValue();

            InetAddress direccionObjetivo = InetAddress.getByName(ipObjetivo);

            InetAddress direccionLocal
                    = InetAddress.getByName(config.getIpInterface());

            MacAddress broadcast
                    = MacAddress.getByName("FF:FF:FF:FF:FF:FF");

            MacAddress macObjetivo
                    = MacAddress.getByName("00:00:00:00:00:00");

            ArpPacket arp = new ArpPacket.Builder()
                    .hardwareType(ArpHardwareType.ETHERNET)
                    .protocolType(EtherType.IPV4)
                    .hardwareAddrLength((byte) 6)
                    .protocolAddrLength((byte) 4)
                    .operation(ArpOperation.REQUEST)
                    .srcHardwareAddr(macLocal)
                    .srcProtocolAddr(direccionLocal)
                    .dstHardwareAddr(macObjetivo)
                    .dstProtocolAddr(direccionObjetivo)
                    .build();

            EthernetPacket ethernet = new EthernetPacket.Builder()
                    .dstAddr(broadcast)
                    .srcAddr(macLocal)
                    .type(EtherType.ARP)
                    .payloadBuilder(arp.getBuilder())
                    .paddingAtBuild(true)
                    .build();

            handle.sendPacket(ethernet);

            boolean encontrado = false;
            long inicio = System.currentTimeMillis();

            while (!encontrado) {

                Packet paquete = handle.getNextPacket();

                if (paquete != null) {

                    ArpPacket respuesta = paquete.get(ArpPacket.class);

                    if (respuesta != null) {

                        InetAddress ipRespuesta
                                = respuesta.getHeader().getSrcProtocolAddr();

                        if (ipRespuesta.getHostAddress().equals(ipObjetivo)) {

                            MacAddress macEncontrada
                                    = respuesta.getHeader().getSrcHardwareAddr();

                            String mac_encontrada = macEncontrada.toString();

                            dispositivo.setMac(mac_encontrada);

                            repo.guardarMacs(dispositivo);

                            encontrado = true;
                        }
                    }
                }

                if (System.currentTimeMillis() - inicio >= 2000) {

                    encontrado = true;

                }
            }

        }
        return true;
    }

    public Map<Integer, Dispositivo> obtenerDispositivos() {
        return repo.obtenertodos();
    }
}
