# Monitor de Red V1

Proyecto desarrollado en Java como práctica de programación de redes.

## Descripción

Este proyecto realiza un escaneo de una red local IPv4 ingresada por el usuario, verifica qué direcciones IP están activas utilizando `InetAddress.isReachable()` y almacena los dispositivos encontrados en un `HashMap`.

El objetivo del proyecto es aprender conceptos de programación de redes, estructuras de datos y diseño orientado a objetos en Java.

## Funcionalidades

- Escaneo de una subred IPv4.
- Detección de dispositivos activos mediante ICMP (`isReachable`).
- Almacenamiento de dispositivos en memoria usando `HashMap`.
- Organización del código mediante clases (`Dispositivo`, `Repository`, `ScaneosIp`).

## Tecnologías

- Java
- Maven
- NetBeans
- Java Networking (`java.net`)

## Estructura del proyecto

```
src/
 ├── modelos/
 │    └── Dispositivo.java
 ├── repository/
 │    └── Repository.java
 ├── servicio/
 │    └── ScaneosIp.java
 └── Monitorv1.java
```

## Ejemplo de uso

```
Digite la red a la que se desea escanear:
192.168.80.

Encontrado: 192.168.80.1
Encontrado: 192.168.80.10
Encontrado: 192.168.80.12

ID: 1 | IP: 192.168.80.1
ID: 10 | IP: 192.168.80.10
ID: 12 | IP: 192.168.80.12
```

## Próximas mejoras

- Escaneo concurrente con `ExecutorService`.
- Obtención de la dirección MAC.
- Detección de puertos abiertos.
- Medición de latencia.
- Interfaz gráfica.
- Persistencia en base de datos.
- Monitoreo continuo de la red.

## Autor

Desarrollado por Yordin Puello.
