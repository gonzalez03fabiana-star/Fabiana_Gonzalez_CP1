/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fabiana_gonzalez_cp1;

/**
 *
 * @author gonza
 */
public class NodoTorre {
    PilaDinamica torre;
    int cantidadPlatos;     
    NodoTorre siguiente;    

    public NodoTorre() {
        this.torre = new PilaDinamica();
        this.cantidadPlatos = 0;
        this.siguiente = null;
    }
     
}
