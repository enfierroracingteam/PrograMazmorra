// Arma.java

/**
 * Clase que representa un arma que aumenta el ataque de un jugador.
 * Al usarla, incrementa el ataque del jugador en una cantidad fija.
 */
public class Arma extends Objeto {
    private final int aumentoAtaque;

    /**
     * Constructor de la clase Arma.
     *
     * @param nombre         Nombre del arma.
     * @param aumentoAtaque  Cantidad en que el arma aumenta el ataque del jugador.
     */
    public Arma(String nombre, int aumentoAtaque) {
        super(nombre);
        this.aumentoAtaque = aumentoAtaque;
    }

    /**
     * Aplica el aumento de ataque al jugador cuando el arma es usada.
     *
     * @param jugador El jugador que usará el arma.
     */
    @Override
    public void usar(Jugador jugador) {
        jugador.aumentarAtaque(aumentoAtaque);
        mostrarMensajeEquipar();
    }

    /**
     * Muestra un mensaje indicando que el arma ha sido equipada.
     */
    private void mostrarMensajeEquipar() {
        System.out.println("Has equipado " + nombre + " y tu ataque aumenta en " + aumentoAtaque + " puntos.");
    }
}
