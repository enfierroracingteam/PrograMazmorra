// Jugador.java
import java.util.ArrayList;

/**
 * Clase que representa a un jugador con atributos como salud, ataque y posición en el mapa.
 * El jugador puede moverse, atacar, recibir daño y gestionar su inventario de objetos.
 */
public class Jugador {
    private int salud;
    private int ataque;
    private final ArrayList<Objeto> inventario;
    private int x; // Coordenada X del jugador en el mapa
    private int y; // Coordenada Y del jugador en el mapa

    public Jugador(int salud, int ataque, int x, int y) {
        this.salud = salud;
        this.ataque = ataque;
        this.x = x;
        this.y = y;
        this.inventario = new ArrayList<>();
    }

    public int getSalud() {
        return salud;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Mueve al jugador en las coordenadas del mapa
    public void mover(int dx, int dy) {
        x += dx;
        y += dy;
    }

    // El jugador recibe daño, reduciendo su salud
    public void recibirDano(int dano) {
        modificarSalud(-dano, "Has recibido");
    }

    // El jugador ataca a un enemigo, causando daño
    public void atacar(Enemigo enemigo) {
        enemigo.recibirDano(ataque);
        mostrarMensajeAccion("Has atacado a", enemigo.getNombre(), ataque);
    }

    // Verifica si el jugador sigue vivo
    public boolean estaVivo() {
        return salud > 0;
    }

    // Agrega un objeto al inventario del jugador
    public void agregarObjeto(Objeto objeto) {
        inventario.add(objeto);
        System.out.println("Has agregado " + objeto.getNombre() + " a tu inventario.");
    }

    // Muestra los objetos actuales en el inventario
    public void mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("Tu inventario está vacío.");
        } else {
            System.out.println("Objetos en tu inventario:");
            for (int i = 0; i < inventario.size(); i++) {
                System.out.println((i + 1) + ". " + inventario.get(i).getNombre());
            }
        }
    }

    // Usa un objeto del inventario y lo elimina después
    public void usarObjeto(int indice) {
        if (esIndiceValido(indice)) {
            Objeto objeto = inventario.get(indice);
            objeto.usar(this);
            inventario.remove(indice);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    // Aumenta la salud del jugador
    public void aumentarSalud(int cantidad) {
        modificarSalud(cantidad, "Has recuperado");
    }

    // Aumenta el ataque del jugador
    public void aumentarAtaque(int cantidad) {
        ataque += cantidad;
    }

    // Obtiene el tamaño del inventario del jugador
    public int getInventarioSize() {
        return inventario.size();
    }

    // Modifica la salud del jugador y muestra un mensaje
    private void modificarSalud(int cantidad, String mensaje) {
        salud += cantidad;
        System.out.println(mensaje + " " + Math.abs(cantidad) + " puntos de salud. Salud actual: " + salud);
    }

    // Verifica si el índice de inventario es válido
    private boolean esIndiceValido(int indice) {
        return indice >= 0 && indice < inventario.size();
    }

    // Muestra un mensaje cuando el jugador realiza una acción (ataque, etc.)
    private void mostrarMensajeAccion(String accion, String objetivo, int cantidad) {
        System.out.println(accion + " " + objetivo + " y le has causado " + cantidad + " puntos de daño.");
    }
}
