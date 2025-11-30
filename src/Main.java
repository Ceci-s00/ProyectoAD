
import Gestion.GestorDatos;
import Menus.MenuDesarrolladoras;
import Menus.MenuVideojuegos;
import java.util.Scanner;

/**
 * Clase con el menú de navegación principal que coordina la interacción
 * entre el usuario y los submenús de videojuegos y desarrolladoras.
 */
import java.util.Scanner;

/**
 * Clase principal del programa.
 *
 * Contiene el menú principal y gestiona la ejecución completa del sistema.
 *
 * Al finalizar, guarda los datos en los archivos CSV.
 */

public class Main {

    public static void main(String[] args) {
        GestorDatos gestor = new GestorDatos();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("¡Bienvenidx al Sistema de Videojuegos y Desarrolladoras!");
            System.out.println( "*".repeat(38));
            System.out.println("          MENÚ PRINCIPAL");
            System.out.println("*".repeat(38));
            System.out.println("1. Gestionar Videojuegos");
            System.out.println("2. Gestionar Desarrolladoras");
            System.out.println("0. Salir del programa");
            System.out.println("=".repeat(38));
            opcion = leerEntero(scanner, "Seleccione una opción 0-2: ");

            switch (opcion) {
                case 1:
                    new MenuVideojuegos(gestor, scanner).mostrarMenu();
                    break;
                case 2:
                    new MenuDesarrolladoras(gestor, scanner).mostrarMenu();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción del menú.");
            }
        } while (opcion != 0);

        gestor.guardarDatos();
        scanner.close();
        System.out.println("Datos guardados correctamente. ¡Hasta pronto!");
    }


    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                String entrada = scanner.nextLine();
                if (entrada.isEmpty()) {
                    System.out.println("Entrada vacía. Por favor, introduzca un número.");
                    continue;
                }
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduzca un número entero.");
            }
        }
    }
}