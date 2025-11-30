
package Menus;

import Excepciones.VideojuegoNoEncontradoException;
import Gestion.GestorDatos;
import Entidades.Desarrolladora;
import Entidades.Videojuego;
import java.util.*;

/**
 * Menú interactivo para gestionar videojuegos.
 *
 * Incluye búsquedas parametrizadas y la búsqueda combinada por desarrolladora.
 *
 * Todas las búsquedas muestran todos los resultados y permiten volver atrás.
 */

public class MenuVideojuegos {

    private final GestorDatos gestor;
    private final Scanner scanner;

    public MenuVideojuegos(GestorDatos gestor, Scanner scanner) {
        this.gestor = gestor;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n" + "*".repeat(50));
            System.out.println("                MENU VIDEOJUEGOS");
            System.out.println("*".repeat(50));
            System.out.println("1. Listar todos los videojuegos");
            System.out.println("2. Añadir un nuevo videojuego");
            System.out.println("3. Buscar videojuegos por título");
            System.out.println("4. Buscar videojuegos por género");
            System.out.println("5. Buscar videojuegos por nombre de desarrolladora");
            System.out.println("6. Buscar videojuego por ID");
            System.out.println("7. Modificar un videojuego");
            System.out.println("8. Eliminar un videojuego");
            System.out.println("0. Volver al menú principal");
            System.out.println("*".repeat(50));
            opcion = leerEntero("Seleccione una opción 0-8: ");

            switch (opcion) {
                case 1:
                    listarTodos();
                    break;
                case 2:
                    añadirNuevo();
                    break;
                case 3:
                    buscarPorTitulo();
                    break;
                case 4:
                    buscarPorGenero();
                    break;
                case 5:
                    buscarPorDesarrolladora();
                    break;
                case 6:
                    buscarPorId();
                    break;
                case 7:
                    modificar();
                    break;
                case 8:
                    eliminar();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void listarTodos() {
        List<Videojuego> lista = gestor.listarTodosLosVideojuegos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay ningún videojuego registrado.");
        } else {
            System.out.println("\nVideojuegos disponibles:");
            System.out.println("-".repeat(126));
            for (Videojuego v : lista) {
                String nombreDesa = gestor.obtenerNombreDesarrolladora(v.getIdDesarrolladora());
                System.out.println(v + " | Desarrolladora: " + nombreDesa);
            }
            System.out.println("-".repeat(126));
        }
        esperarEnter();
    }

    private void añadirNuevo() {
        System.out.println("\n--- Añadir nuevo videojuego ---");
        System.out.println("--------------------------------------------------");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("--------------------------------------------------");
        System.out.println("Título: ");
        String titulo = leerString();
        if (esVolver(titulo)) return;

        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("Género: ");
        String genero = leerString();
        if (esVolver(genero)) return;

        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        int anio = 0;
        boolean anioValido = false;
        int anioActual = java.time.Year.now().getValue();
        int anioMin = 1950;
        int anioMax = anioActual + 1;

        while (!anioValido) {
            System.out.print("Año de lanzamiento: ");
            try {
                String entrada = scanner.nextLine();
                if (entrada.isEmpty()) {
                    System.out.println("Entrada vacía. Por favor, introduzca un año.");
                    continue;
                }
                anio = Integer.parseInt(entrada);
                if (anio == 0) {
                    return;
                }
                if (anio < anioMin || anio > anioMax) {
                    System.out.println("Error: El año debe estar entre " + anioMin + " y " + anioMax + ".");
                    continue;
                }
                anioValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe introducir un número entero válido para el año.");
            }
        }


        List<Desarrolladora> listaDesarrolladoras = gestor.listarTodasLasDesarrolladoras();
        if (listaDesarrolladoras.isEmpty()) {
            System.out.println("No hay desarrolladoras registradas. Debe crear una primero.");
            esperarEnter();
            return;
        }
        System.out.println("\nDesarrolladoras disponibles:");
        System.out.println("-".repeat(50));
        for (Desarrolladora d : listaDesarrolladoras) {
            System.out.println("ID: " + d.getId() + " | " + d.getNombre());
        }
        System.out.println("-".repeat(50));

        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        int idDesarrolladora = leerEntero("ID de la desarrolladora: ");
        if (idDesarrolladora == 0) return;

        try {
            gestor.agregarVideojuego(titulo, genero, anio, idDesarrolladora);
            System.out.println("Videojuego añadido correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        esperarEnter();
    }

    private void buscarPorTitulo() {
        System.out.println("\n--- Buscar videojuegos por título ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("Introduzca parte del título,\nse buscarán coincidencias: ");
        String busqueda = leerString();
        if (esVolver(busqueda)) return;

        List<Videojuego> resultados = gestor.buscarVideojuegosPorTitulo(busqueda);
        mostrarResultadosVideojuegos(resultados);
        esperarEnter();
    }

    private void buscarPorGenero() {
        System.out.println("\n--- Buscar videojuegos por género ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("Introduzca parte del género,\nse buscarán coincidencias: ");
        String busqueda = leerString();
        if (esVolver(busqueda)) return;

        List<Videojuego> resultados = gestor.buscarVideojuegosPorGenero(busqueda);
        mostrarResultadosVideojuegos(resultados);
        esperarEnter();
    }

    private void buscarPorDesarrolladora() {
        System.out.println("\n--- Buscar videojuegos por nombre de desarrolladora ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("Introduzca parte del nombre,\nse buscarán coincidencias: ");
        String busqueda = leerString();
        if (esVolver(busqueda)) return;

        List<Videojuego> resultados = gestor.buscarVideojuegosPorDesarrolladora(busqueda);
        mostrarResultadosVideojuegos(resultados);
        esperarEnter();
    }

    private void buscarPorId() {
        System.out.println("\n--- Buscar videojuego por ID ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        int id = leerEntero("Introduzca el ID del videojuego: ");
        if (id == 0) return;

        try {
            Videojuego v = gestor.obtenerVideojuegoPorId(id);
            List<Videojuego> resultados = new ArrayList<>();
            resultados.add(v);
            mostrarResultadosVideojuegos(resultados);
        } catch (VideojuegoNoEncontradoException e) {
            System.out.println("No se encontró ningún videojuego con ID: " + id);
        }
        esperarEnter();
    }

    private void modificar() {
        System.out.println("\n        --- Modificar videojuego ---");
        System.out.println("--------------------------------------------------");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("---------------------------------------------------");
        int id = leerEntero("ID del videojuego a modificar: ");
        if (id == 0) return;

        try {
            Videojuego actual = gestor.obtenerVideojuegoPorId(id);
            String nombreDesaActual = gestor.obtenerNombreDesarrolladora(actual.getIdDesarrolladora());
            System.out.println("-------------------------------------------------");
            System.out.println("Videojuego actual: " + actual + " | Desarrolladora: " + nombreDesaActual);
            System.out.println("--------------------------------------------------");
            System.out.println("Para volver al menú anterior, introduzca 0 ---->");
            System.out.println("---------------------------------------------------");
            System.out.println("Nuevo título: ");
            System.out.println("Si quiere mantener el mismo, deje el campo vacío");
            String titulo = leerString();
            if (esVolver(titulo)) return;
            if (titulo.isEmpty()) titulo = actual.getTitulo();

            System.out.println("---------------------------------------------------");
            System.out.println("Para volver al menú anterior, introduzca 0 ---->");
            System.out.println("---------------------------------------------------");
            System.out.println("Nuevo género: ");
            System.out.println("Si quiere mantener el mismo, deje el campo vacío");
            String genero = leerString();
            if (esVolver(genero)) return;
            if (genero.isEmpty()) genero = actual.getGenero();

            System.out.println("---------------------------------------------------");
            System.out.println("Para volver al menú anterior, introduzca 0 ---->");
            System.out.println("---------------------------------------------------");

            int anio = 0;
            boolean anioValido = false;
            int anioActual = java.time.Year.now().getValue();
            int anioMin = 1950;
            int anioMax = anioActual + 1;

            while (!anioValido) {
                System.out.println("Nuevo año: ");
                System.out.println("Si quiere mantener el mismo, deje el campo vacío");
                try {
                    String entrada = scanner.nextLine();
                    if (entrada.isEmpty()) {
                        anio = actual.getAnio();
                        anioValido = true;
                        break;
                    }
                    anio = Integer.parseInt(entrada);
                    if (anio == 0) {
                        anio = actual.getAnio();
                        anioValido = true;
                        break;
                    }
                    if (anio < anioMin || anio > anioMax) {
                        System.out.println("Error: El año debe estar entre " + anioMin + " y " + anioMax + ".");
                        continue;
                    }
                    anioValido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debe introducir un número entero válido para el año.");
                }
            }
            // Mostrar desarrolladoras disponibles
            List<Desarrolladora> listaDesarrolladoras = gestor.listarTodasLasDesarrolladoras();
            System.out.println("\nDesarrolladoras disponibles:");
            System.out.println("-".repeat(50));
            for (Desarrolladora d : listaDesarrolladoras) {
                System.out.println("ID: " + d.getId() + " | " + d.getNombre());
            }
            System.out.println("-".repeat(50));

            System.out.println("---------------------------------------------------");
            System.out.println("Para volver al menú anterior, introduzca 0 ---->");
            System.out.println("---------------------------------------------------");
            System.out.println("Nuevo ID de desarrolladora: ");
            System.out.println("Si quiere mantener el mismo, deje el campo vacío");
            int idDesarrolladora = leerEntero("Nuevo ID de desarrolladora: ");
            if (idDesarrolladora == 0) {
                idDesarrolladora = actual.getIdDesarrolladora();
            }

            gestor.modificarVideojuego(id, titulo, genero, anio, idDesarrolladora);
            System.out.println("Videojuego modificado correctamente.");
        } catch (VideojuegoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        esperarEnter();
    }

    private void eliminar() {
        System.out.println("\n--- Eliminar videojuego ---");
        System.out.println("---------------------------------------------------");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("---------------------------------------------------");
        int id = leerEntero("ID del videojuego a eliminar: ");
        if (id == 0) return;

        try {
            Videojuego v = gestor.obtenerVideojuegoPorId(id);
            String nombreDesa = gestor.obtenerNombreDesarrolladora(v.getIdDesarrolladora());
            System.out.println("Videojuego a eliminar: " + v + " | Desarrolladora: " + nombreDesa);
            System.out.print("¿Está seguro? Introduzca --> si / no: ");
            String confirmacion = scanner.nextLine().toLowerCase();
            if (confirmacion.equals("si")) {
                gestor.eliminarVideojuego(id);
                System.out.println("Videojuego eliminado correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (VideojuegoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
        esperarEnter();
    }

    private void mostrarResultadosVideojuegos(List<Videojuego> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron videojuegos que coincidan con su búsqueda.");
        } else {
            System.out.println("\nResultados encontrados (" + resultados.size() + "):");
            System.out.println("-".repeat(70));
            for (Videojuego v : resultados) {
                String nombreDesa = gestor.obtenerNombreDesarrolladora(v.getIdDesarrolladora());
                System.out.println(v + " | Desarrolladora: " + nombreDesa);
            }
            System.out.println("-".repeat(70));
        }
    }

    private void esperarEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    private boolean esVolver(String entrada) {
        return entrada != null && entrada.equals("0");
    }

    public int leerEntero(String mensaje) {
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

    private String leerString() {
        while (true) {
            String entrada = scanner.nextLine();
            if (entrada.equals("0")) {
                return "0";
            }
            if (entrada.matches("\\d+")) {
                System.out.println("Error!! Debe introducir texto, no solo números.");
                continue;
            }
            return entrada;
        }
    }
}