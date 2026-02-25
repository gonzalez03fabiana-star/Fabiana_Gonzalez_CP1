/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fabiana_gonzalez_cp1;

/**
 *
 * @author gonza
 */
public class NodoPila {

    int dato;              // Dato almacenado en el nodo
    NodoPila siguiente;        // Referencia al siguiente nodo en la pila

    public NodoPila(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }

}
