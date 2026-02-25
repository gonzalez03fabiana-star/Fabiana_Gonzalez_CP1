/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fabiana_gonzalez_cp1;

import javax.swing.JOptionPane;

/**
 *
 * @author gonza
 */
public class SistemaMeseros {

    private int maxPlatos = 10;

    private NodoTorre cimaTorres;
    private Cola colaMeseros;
    private int idMesero;

    public SistemaMeseros() {
        cimaTorres = null;
        colaMeseros = new Cola();
        idMesero = 1;
    }

    public void agregarPlatos() {
        int cantidad = leerEntero("Cuántos platos desea agregar?");
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un numero valido: ");
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            apilarUnPlato();
        }

        JOptionPane.showMessageDialog(null, "Se agregaron " + cantidad + " platos");
    }

    public void registrarMesero() {
        int platosSolicitados = leerEntero("Cuantos platos necesita el mesero?");
        if (platosSolicitados <= 0 || platosSolicitados > 10) {
            JOptionPane.showMessageDialog(null, "Cantidad invalida.");
            return;
        }

        int id = idMesero++;
        int codigo = codificarMesero(id, platosSolicitados);

        colaMeseros.encolar(codigo);

        JOptionPane.showMessageDialog(null,"Mesero #" + id + " en espera solicitando " + platosSolicitados + " platos");
    }

    public void atenderMesero() {
        if (colaMeseros.estaVacia()) {
            JOptionPane.showMessageDialog(null, "No hay meseros esperando");
            return;
        }

        int codigo;
        try {
            codigo = colaMeseros.desencolar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return;
        }

        int id = obtenerIdMesero(codigo);
        int solicitados = obtenerPlatosSolicitados(codigo);

        int disponibles = contarPlatosDisponibles();

        if (disponibles < solicitados) {
            manejarInventarioInsuficiente(codigo, id, solicitados, disponibles);
            return;
        }

        entregarPlatos(solicitados);

        JOptionPane.showMessageDialog(null,"Mesero #" + id + " atendido. \nSe entregaron " + solicitados + " platos.");
    }

    public void mostrarEstado() {
        String estado = "Estado actual del sistema\n";

        int torres = contarTorres();
        estado += "Torres existentes: " + torres + "\n";

        if (cimaTorres == null) {
            estado += "No hay torres\n";
        } else {
            // Mostrar desde la más reciente hacia abajo
            NodoTorre actual = cimaTorres;
            int num = torres;
            while (actual != null) {
                estado += "Torre " + num + actual.cantidadPlatos + " platos\n";
                actual = actual.siguiente;
                num--;
            }
        }

        int totalPlatos = contarPlatosDisponibles();
        estado += "\nPlatos disponibles: " + totalPlatos;

        int enEspera = 0;
        try {
            enEspera = colaMeseros.contarRec(colaMeseros);
        } catch (Exception e) {
            enEspera = 0;
        }
        estado += "\nMeseros en espera: " + enEspera;

        if (!colaMeseros.estaVacia()) {
            estado += "\nDetalle de meseros:\n";
            estado += construirListadoMeseros();
        } else {
            estado += "\nNo hay meseros en espera.\n";
        }

        JOptionPane.showMessageDialog(null, estado);
    }

    
    private void apilarUnPlato() {
        
        if (cimaTorres == null) {
            cimaTorres = new NodoTorre();
        }

        
        if (cimaTorres.cantidadPlatos == maxPlatos) {
            NodoTorre nueva = new NodoTorre();
            nueva.siguiente = cimaTorres;
            cimaTorres = nueva;
        }

        
        cimaTorres.torre.push(1);
        cimaTorres.cantidadPlatos++;
    }

    private void entregarPlatos(int cantidad) {
        int porRetirar = cantidad;

        while (porRetirar > 0) {
            
            if (cimaTorres == null) {
                
                break;
            }

            cimaTorres.torre.pop();
            cimaTorres.cantidadPlatos--;
            porRetirar--;

            
            if (cimaTorres.cantidadPlatos == 0) {
                cimaTorres = cimaTorres.siguiente;
            }
        }
    }

    private int contarPlatosDisponibles() {
        int total = 0;
        NodoTorre actual = cimaTorres;
        while (actual != null) {
            total += actual.cantidadPlatos;
            actual = actual.siguiente;
        }
        return total;
    }

    private int contarTorres() {
        int total = 0;
        NodoTorre actual = cimaTorres;
        while (actual != null) {
            total++;
            actual = actual.siguiente;
        }
        return total;
    }

    private void manejarInventarioInsuficiente(int codigo, int id, int solicitados, int disponibles) {
        String msg = ""
                + "No hay suficientes platos.\n"
                + "Mesero #" + id + " solicito: " + solicitados + "\n"
                + "Disponibles: " + disponibles + "\n"
                + "Desea que el mesero permanezca en espera?";

        int decision = JOptionPane.showConfirmDialog(null, msg, "Inventario insuficiente",JOptionPane.YES_NO_OPTION);

        if (decision == JOptionPane.YES_OPTION) {
            colaMeseros.encolar(codigo);
            JOptionPane.showMessageDialog(null, "Mesero #" + id + " permanece en espera.");
        } else {
            JOptionPane.showMessageDialog(null, "Mesero #" + id + " se retiró sin ser atendido.");
        }
    }

    private String construirListadoMeseros() {
        String texto = "";
        Cola auxiliar = new Cola();

        try {
            while (!colaMeseros.estaVacia()) {
                int codigo = colaMeseros.desencolar();
                int id = obtenerIdMesero(codigo);
                int solicitados = obtenerPlatosSolicitados(codigo);

                texto += "Mesero #" + id + " solicita " + solicitados + " platos";
                auxiliar.encolar(codigo);
            }

            while (!auxiliar.estaVacia()) {
                colaMeseros.encolar(auxiliar.desencolar());
            }

        } catch (Exception e) {
            texto += "Error al leer la cola";
        }

        return texto;
    }

    
    private int codificarMesero(int id, int platos) {
        return (id * 1000) + platos;
    }

    private int obtenerIdMesero(int codigo) {
        return codigo / 1000;
    }

    private int obtenerPlatosSolicitados(int codigo) {
        return codigo % 1000;
    }

    private int leerEntero(String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, mensaje);
            if (input == null) {
                return -1;
            }

            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No puede estar vacío");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número valido");
            }
        }
    }
}
