/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.monitorv1.configuracion;

/**
 *
 * @author yordi
 */
import java.util.Scanner;

public class DatosEntrada {

    private final Scanner sc = new Scanner(System.in);

    public DatosEntrada() {
    }
    

    public int manejoErrores() {
      

        while (true) {
            try {
                System.out.print("Digite un numero entero: ");
                String entrada = sc.nextLine();
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe digitar un numero entero.");
            }
        }
    }

}
