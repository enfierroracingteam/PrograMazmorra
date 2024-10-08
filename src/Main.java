// Main.java

/**
 * Clase principal del juego "Aventura en la Mazmorra".
 * Maneja el ciclo del juego, el menú principal, y la interacción entre el jugador y la mazmorra.
 * 
 * Funcionalidades principales:
 * - Iniciar un nuevo juego.
 * - Ver instrucciones del juego.
 * - Salir del juego.
 * 
 * Maneja posibles interrupciones de entrada y errores durante la ejecución.
 */
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        // Bucle principal del menú del juego
        while (!salir) {
            mostrarMenu();
            String opcion;

            // Captura la interrupción de entrada en el menú principal
            try {
                opcion = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("\nEntrada interrumpida. Cerrando el juego...");
                break;  // Rompe el bucle principal para cerrar el juego
            }

            // Opciones del menú principal
            switch (opcion) {
                case "1":
                    iniciarJuego();
                    break;
                case "2":
                    mostrarInstrucciones();
                    break;
                case "3":
                    salir = true;
                    System.out.println("¡Gracias por jugar!");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }
    }

    // Muestra el menú principal del juego
    private static void mostrarMenu() {
        System.out.println("=== Aventura en la Mazmorra ===");
        System.out.println("1. Iniciar nuevo juego");
        System.out.println("2. Ver instrucciones");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }

    // Inicia el ciclo de juego principal
    private static void iniciarJuego() {
        Jugador jugador = new Jugador(100, 10, 0, 0);
        Mazmorra mazmorra = new Mazmorra(50, jugador, true);

        // Bucle principal del juego dentro de la mazmorra
        while (!mazmorra.juegoTerminado()) {
            mostrarEstadoJuego(jugador, mazmorra);
            String accion = obtenerAccion();

            if (accion.equalsIgnoreCase("i")) {
                gestionarInventario(jugador);
            } else {
                mazmorra.moverJugador(accion);
            }
        }

        mostrarResultadoJuego(jugador);
    }

    // Muestra el estado actual del juego, incluyendo la salud del jugador y el mapa
    private static void mostrarEstadoJuego(Jugador jugador, Mazmorra mazmorra) {
        mazmorra.mostrarMapa();
        System.out.println("Salud: " + jugador.getSalud() + " | Ataque: " + jugador.getAtaque());
    }

    // Captura la acción del jugador (moverse o acceder al inventario)
    private static String obtenerAccion() {
        System.out.print("¿A dónde quieres moverte? (n/s/e/o) o 'i' para inventario: ");
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("\nEntrada interrumpida. Cerrando el juego...");
            System.exit(0);  // Forzar el cierre del juego de forma controlada
            return "";  // Esta línea es requerida por el compilador pero nunca se ejecutará
        }
    }

    // Gestiona el inventario del jugador y permite usar objetos
    private static void gestionarInventario(Jugador jugador) {
        jugador.mostrarInventario();

        int indice = -1;
        while (indice < 0) {
            System.out.print("Selecciona el número del objeto para usarlo o '0' para volver: ");
            String input = scanner.nextLine();

            try {
                indice = Integer.parseInt(input);

                if (indice == 0) {
                    return;  // Volver sin usar nada
                } else if (indice < 1 || indice > jugador.getInventarioSize()) {
                    System.out.println("Índice inválido. Intenta de nuevo.");
                    indice = -1;  // Reiniciar el ciclo si el índice no es válido
                } else {
                    jugador.usarObjeto(indice - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
            }
        }
    }

    // Muestra el resultado del juego al finalizar
    private static void mostrarResultadoJuego(Jugador jugador) {
        if (jugador.estaVivo()) {
            System.out.println("¡Felicidades! Has completado la mazmorra.");
        } else {
            System.out.println("Has muerto en la mazmorra. Fin del juego.");
        }
    }

    // Muestra las instrucciones del juego
    private static void mostrarInstrucciones() {
        System.out.println("=== Instrucciones ===");
        System.out.println("Mueve a tu personaje usando los comandos:");
        System.out.println("'n' - Norte");
        System.out.println("'s' - Sur");
        System.out.println("'e' - Este");
        System.out.println("'o' - Oeste");
        System.out.println("Encuentra la salida 'S' en el mapa para ganar.");
        System.out.println("Evita los enemigos 'E' o lucha contra ellos.");
        System.out.println("Recoge objetos 'O' para ayudarte en tu aventura.");
        System.out.println("Usa 'i' para acceder a tu inventario y usar objetos.");
        System.out.println("=====================");
    }
}
