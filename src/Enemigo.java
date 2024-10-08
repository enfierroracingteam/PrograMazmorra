// Enemigo.java

/**
 * Clase que representa a un enemigo con nombre, salud y ataque.
 * Los enemigos pueden recibir daño y atacar a jugadores.
 */
public class Enemigo {
    private final String nombre;
    private int salud;
    private final int ataque;

    public Enemigo(String nombre, int salud, int ataque) {
        this.nombre = nombre;
        this.salud = salud;
        this.ataque = ataque;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    // Verifica si el enemigo sigue vivo
    public boolean estaVivo() {
        return salud > 0;
    }

    // Aplica daño al enemigo y reduce su salud
    public void recibirDano(int dano) {
        salud -= dano;
        mostrarMensaje("ha recibido", dano);
    }

    // El enemigo ataca al jugador, infligiéndole daño
    public void atacar(Jugador jugador) {
        jugador.recibirDano(ataque);
        mostrarMensaje("te ha atacado y causado", ataque);
    }

    // Muestra un mensaje en consola con la acción realizada y la cantidad de daño
    private void mostrarMensaje(String accion, int cantidad) {
        System.out.println(nombre + " " + accion + " " + cantidad + " puntos de daño.");
    }
}
