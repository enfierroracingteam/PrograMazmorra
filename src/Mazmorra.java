import java.util.Random;

/**
 * Clase que representa una mazmorra en el juego.
 * La mazmorra contiene un mapa con paredes, enemigos, objetos y una salida.
 * El jugador puede moverse por el mapa, combatir enemigos y recoger objetos.
 * También puede interactuar con la salida para ganar el juego.
 */
public class Mazmorra {
    // Colores ANSI para representar los elementos en el mapa
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[96m";   // Jugador - Cian brillante
    public static final String ANSI_RED = "\u001B[91m";    // Enemigos - Rojo brillante
    public static final String ANSI_GREEN = "\u001B[92m";  // Objetos - Verde brillante
    public static final String ANSI_YELLOW = "\u001B[93m"; // Paredes - Amarillo brillante
    public static final String ANSI_BROWN = "\u001B[33m";  // Salida - Café claro

    private final char[][] mapa;
    private final Jugador jugador;
    private final Enemigo[][] enemigos;
    private final Objeto[][] objetos;
    private final int salidaX;
    private final int salidaY;
    private final int tamaño;
    private final boolean usarColores;

    // Lista de nombres de armas y sus aumentos de ataque correspondientes
    private static final String[] nombresArmas = {
        "Espada de Hierro",
        "Hacha de Batalla",
        "Lanza de Acero",
        "Arco Largo",
        "Daga Envenenada",
        "Martillo de Guerra",
        "Espada Larga",
        "Maza Pesada",
        "Bastón Mágico",
        "Espada Legendaria"
    };

    private static final int[] aumentosAtaque = {
        5, 7, 6, 4, 5, 8, 6, 7, 5, 10
    };

    // Lista de nombres de pociones y sus curaciones correspondientes
    private static final String[] nombresPociones = {
        "Poción de Salud Menor",
        "Poción de Salud",
        "Poción de Salud Mayor",
        "Poción de Salud Superior",
        "Elixir de Vida"
    };

    private static final int[] curacionesPociones = {
        10, 20, 30, 40, 50
    };

    /**
     * Constructor de la mazmorra. Inicializa el mapa y coloca al jugador, enemigos y objetos.
     *
     * @param tamaño       Tamaño del mapa de la mazmorra (NxN).
     * @param jugador      El jugador que se moverá por la mazmorra.
     * @param usarColores  Indica si se deben usar colores para representar el mapa.
     */
    public Mazmorra(int tamaño, Jugador jugador, boolean usarColores) {
        this.tamaño = tamaño;
        this.jugador = jugador;
        this.usarColores = usarColores;
        this.mapa = new char[tamaño][tamaño];
        this.enemigos = new Enemigo[tamaño][tamaño];
        this.objetos = new Objeto[tamaño][tamaño];
        salidaX = tamaño - 1;  // La salida está en la esquina inferior derecha
        salidaY = tamaño - 1;
        inicializarMapa();
    }

    // Inicializa el mapa con paredes, espacios vacíos y la salida
    private void inicializarMapa() {
        Random rand = new Random();

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                // 20% de probabilidad de colocar una pared ('#')
                mapa[i][j] = (rand.nextInt(100) < 20) ? '#' : '.';
            }
        }

        // Coloca la salida en la posición final del mapa
        mapa[salidaX][salidaY] = 'S';
        colocarJugadorEnMapa();
        colocarEnemigosYObjetos(rand);
    }

    // Coloca al jugador en su posición inicial en el mapa
    private void colocarJugadorEnMapa() {
        mapa[jugador.getX()][jugador.getY()] = 'J';
    }

    // Coloca enemigos y objetos de manera aleatoria en el mapa
    private void colocarEnemigosYObjetos(Random rand) {
        for (int i = 0; i < tamaño / 2; i++) {
            colocarEnemigo(rand);
            colocarObjeto(rand);
        }
    }

    // Coloca un enemigo aleatoriamente en una casilla vacía del mapa
    private void colocarEnemigo(Random rand) {
        int x, y;
        do {
            x = rand.nextInt(tamaño);
            y = rand.nextInt(tamaño);
        } while (mapa[x][y] != '.' || enemigos[x][y] != null || objetos[x][y] != null);

        mapa[x][y] = 'E';
        enemigos[x][y] = new Enemigo("Goblin", 20, 5);
    }

    // Coloca un objeto aleatoriamente en una casilla vacía del mapa
    private void colocarObjeto(Random rand) {
        int x, y;
        do {
            x = rand.nextInt(tamaño);
            y = rand.nextInt(tamaño);
        } while (mapa[x][y] != '.' || enemigos[x][y] != null || objetos[x][y] != null);

        mapa[x][y] = 'O';

        // Decide aleatoriamente qué tipo de objeto colocar
        int tipoObjeto = rand.nextInt(2);  // 0 para Pocion, 1 para Arma
        if (tipoObjeto == 0) {
            // Selecciona una poción aleatoria
            int indicePocion = rand.nextInt(nombresPociones.length);
            String nombrePocion = nombresPociones[indicePocion];
            int curacionPocion = curacionesPociones[indicePocion];
            objetos[x][y] = new Pocion(nombrePocion, curacionPocion);
        } else {
            // Selecciona un arma aleatoria de la lista
            int indiceArma = rand.nextInt(nombresArmas.length);
            String nombreArma = nombresArmas[indiceArma];
            int aumentoAtaque = aumentosAtaque[indiceArma];
            objetos[x][y] = new Arma(nombreArma, aumentoAtaque);
        }
    }

    // Resto del código de la clase Mazmorra (mostrarMapa, moverJugador, etc.)
    // ...

    // Muestra el mapa actual con los elementos del jugador, enemigos y objetos
    public void mostrarMapa() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                mostrarCaracter(mapa[i][j], i, j);
            }
            System.out.println();
        }
    }

    // Muestra el carácter del mapa con colores si está activado
    private void mostrarCaracter(char c, int i, int j) {
        if (i == jugador.getX() && j == jugador.getY()) {
            imprimirConColor('J', ANSI_CYAN);  // Jugador
        } else if (i == salidaX && j == salidaY) {
            imprimirConColor('S', ANSI_BROWN);  // Salida
        } else if (c == 'E') {
            imprimirConColor(c, ANSI_RED);  // Enemigo
        } else if (c == 'O') {
            imprimirConColor(c, ANSI_GREEN);  // Objeto
        } else if (c == '#') {
            imprimirConColor(c, ANSI_YELLOW);  // Pared
        } else {
            System.out.print(c + " ");
        }
    }

    // Imprime un carácter con el color correspondiente
    private void imprimirConColor(char c, String color) {
        if (usarColores) {
            System.out.print(color + c + ANSI_RESET + " ");
        } else {
            System.out.print(c + " ");
        }
    }

    // Mueve al jugador en la dirección indicada
    public void moverJugador(String direccion) {
        int[] nuevaPosicion = calcularNuevaPosicion(direccion);
        if (nuevaPosicion == null) {
            System.out.println("Dirección inválida.");
            return;
        }

        int nuevaX = nuevaPosicion[0];
        int nuevaY = nuevaPosicion[1];

        if (!esMovimientoValido(nuevaX, nuevaY)) return;

        manejarInteracciones(nuevaX, nuevaY);

        // Si el jugador no está vivo, termina el movimiento
        if (!jugador.estaVivo()) {
            return;
        }

        // Actualiza el mapa y mueve al jugador
        mapa[jugador.getX()][jugador.getY()] = '.';
        jugador.mover(nuevaX - jugador.getX(), nuevaY - jugador.getY());
        colocarJugadorEnMapa();
    }

    // Calcula la nueva posición del jugador según la dirección
    private int[] calcularNuevaPosicion(String direccion) {
        int nuevaX = jugador.getX();
        int nuevaY = jugador.getY();

        switch (direccion.toLowerCase()) {
            case "n": nuevaX -= 1; break;
            case "s": nuevaX += 1; break;
            case "e": nuevaY += 1; break;
            case "o": nuevaY -= 1; break;
            default: return null;
        }
        return new int[]{nuevaX, nuevaY};
    }

    // Verifica si el movimiento es válido (dentro de los límites y sin colisiones)
    private boolean esMovimientoValido(int nuevaX, int nuevaY) {
        if (nuevaX < 0 || nuevaX >= tamaño || nuevaY < 0 || nuevaY >= tamaño) {
            System.out.println("No puedes moverte fuera del mapa.");
            return false;
        }
        if (mapa[nuevaX][nuevaY] == '#') {
            System.out.println("Hay una pared en esa dirección.");
            return false;
        }
        return true;
    }

    // Maneja las interacciones del jugador con enemigos y objetos en la nueva posición
    private void manejarInteracciones(int nuevaX, int nuevaY) {
        if (enemigos[nuevaX][nuevaY] != null) {
            System.out.println("¡Te has encontrado con un " + enemigos[nuevaX][nuevaY].getNombre() + "!");
            iniciarCombate(enemigos[nuevaX][nuevaY]);
            if (!enemigos[nuevaX][nuevaY].estaVivo()) {
                enemigos[nuevaX][nuevaY] = null;
                mapa[nuevaX][nuevaY] = '.';
            }
        }

        if (objetos[nuevaX][nuevaY] != null) {
            jugador.agregarObjeto(objetos[nuevaX][nuevaY]);
            // Mostrar mensaje de recogida
            System.out.println("Has agregado " + objetos[nuevaX][nuevaY].getNombre() + " a tu inventario.");
            objetos[nuevaX][nuevaY] = null;
            mapa[nuevaX][nuevaY] = '.';
        }

        if (nuevaX == salidaX && nuevaY == salidaY) {
            System.out.println("¡Has encontrado la salida!");
            // Aquí puedes agregar lógica para terminar el juego o pasar al siguiente nivel
        }
    }

    // Inicia un combate entre el jugador y un enemigo
    private void iniciarCombate(Enemigo enemigo) {
        while (jugador.estaVivo() && enemigo.estaVivo()) {
            jugador.atacar(enemigo);
            if (enemigo.estaVivo()) {
                enemigo.atacar(jugador);
            }
        }

        if (!jugador.estaVivo()) {
            System.out.println("Has sido derrotado por " + enemigo.getNombre() + ".");
        } else {
            System.out.println("Has derrotado a " + enemigo.getNombre() + ".");
        }
    }

    // Verifica si el juego ha terminado (jugador muerto o llegada a la salida)
    public boolean juegoTerminado() {
        return (jugador.getX() == salidaX && jugador.getY() == salidaY) || !jugador.estaVivo();
    }
}
