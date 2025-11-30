
package Excepciones;


/**
 * Excepción para cuando no se encuentra videojuego.
 */

public class VideojuegoNoEncontradoException extends Exception {
    public VideojuegoNoEncontradoException(String mensaje) {

        super(mensaje);
    }
}
