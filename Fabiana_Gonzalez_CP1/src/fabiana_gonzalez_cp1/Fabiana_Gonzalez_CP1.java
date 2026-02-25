/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fabiana_gonzalez_cp1;

import javax.swing.JOptionPane;

/**
 *
 * @author gonza
 */
public class Fabiana_Gonzalez_CP1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SistemaMeseros sistema = new SistemaMeseros();
        int opcion = -1;

        while (opcion != 5) {
            String menu = ""
                    + "Menu de entrega de platos\n"
                    + "1) Agregar platos limpios\n"
                    + "2) Registrar llegada de mesero\n"
                    + "3) Atender siguiente mesero\n"
                    + "4) Mostrar estado del sistema\n"
                    + "5) Salir\n\n"
                    + "Digite una opción:";

            opcion = leerEntero(menu);

            switch (opcion) {
                case 1 ->
                    sistema.agregarPlatos();
                case 2 ->
                    sistema.registrarMesero();
                case 3 ->
                    sistema.atenderMesero();
                case 4 ->
                    sistema.mostrarEstado();
                case 5 ->
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema");
                default ->
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, mensaje);
            if (input == null) {
                return -1;
            }

            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puede estar vacio");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor ingresar un numero valido ");
            }
        }
    }

}
