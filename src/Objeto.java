// Objeto.java

/**
 * Clase abstracta que representa un objeto en el juego.
 * Los objetos tienen un nombre y pueden ser usados por un jugador.
 */
public abstract class Objeto {
    protected final String nombre;

    /**
     * Constructor de la clase Objeto.
     *
     * @param nombre Nombre del objeto.
     */
    public Objeto(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del objeto.
     *
     * @return El nombre del objeto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método abstracto que define cómo un objeto es usado por un jugador.
     * Debe ser implementado por las clases que extiendan Objeto.
     *
     * @param jugador El jugador que usará el objeto.
     */
    public abstract void usar(Jugador jugador);
}
