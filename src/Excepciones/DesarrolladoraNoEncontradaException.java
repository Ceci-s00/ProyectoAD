
package Excepciones;

/**
 * Excepción para cuando no se encuentra desarrolladora.
 */

public class DesarrolladoraNoEncontradaException extends Exception {
    public DesarrolladoraNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
