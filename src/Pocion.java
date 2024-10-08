// Pocion.java

/**
 * Clase que representa una poción que puede ser usada por el jugador para restaurar salud.
 * Extiende la clase Objeto.
 */
public class Pocion extends Objeto {
    private final int restauracion;

    /**
     * Constructor de la clase Pocion.
     * 
     * @param nombre       Nombre de la poción.
     * @param restauracion Cantidad de salud que restaura la poción.
     */
    public Pocion(String nombre, int restauracion) {
        super(nombre);
        this.restauracion = restauracion;
    }

    /**
     * Usa la poción en el jugador, restaurando una cantidad fija de salud.
     * 
     * @param jugador El jugador que usa la poción.
     */
    @Override
    public void usar(Jugador jugador) {
        jugador.aumentarSalud(restauracion);
        mostrarMensajeUso();
    }

    // Muestra un mensaje en consola indicando que la poción ha sido usada
    private void mostrarMensajeUso() {
        System.out.println("Has usado una " + nombre + " y has restaurado " + restauracion + " puntos de salud.");
    }
}
