
package Menus;

import Excepciones.DesarrolladoraNoEncontradaException;
import Gestion.GestorDatos;
import Entidades.Desarrolladora;
import java.util.*;

/**
 * Menú para gestionar las desarrolladoras.
 *
 * Usa métodos estáticos de la clase GestorDatos
 *
 * Incluye búsqueda por ID y permite volver atrás en todas las interacciones.
 */

public class MenuDesarrolladoras {

    private final GestorDatos gestor;
    private final Scanner scanner;

    public MenuDesarrolladoras(GestorDatos gestor, Scanner scanner) {
        this.gestor = gestor;
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n" + "*".repeat(50));
            System.out.println("                MENÚ DESARROLLADORAS");
            System.out.println("*".repeat(50));
            System.out.println("1. Listar todas las desarrolladoras");
            System.out.println("2. Añadir una nueva desarrolladora");
            System.out.println("3. Buscar desarrolladoras por nombre");
            System.out.println("4. Buscar desarrolladora por ID");
            System.out.println("5. Modificar una desarrolladora");
            System.out.println("6. Eliminar una desarrolladora");
            System.out.println("0. Volver al menú principal");
            System.out.println("*".repeat(50));
            opcion = leerEntero("Seleccione una opción 0-6: ");

            switch (opcion) {
                case 1:
                    listarTodas();
                    break;
                case 2:
                    añadirNueva();
                    break;
                case 3:
                    buscarPorNombre();
                    break;
                case 4:
                    buscarPorId();
                    break;
                case 5:
                    modificar();
                    break;
                case 6:
                    eliminar();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Introduzca una opción 1 - 6.");
            }
        } while (opcion != 0);
    }

    private void listarTodas() {
        List<Desarrolladora> lista = gestor.listarTodasLasDesarrolladoras();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay ninguna desarrolladora registrada.");
        } else {
            System.out.println("\nDesarrolladoras disponibles:");
            System.out.println("-".repeat(62));
            for (Desarrolladora desarrolladora : lista) {
                System.out.println(desarrolladora);
            }
            System.out.println("-".repeat(62));
        }
        esperarEnter();
    }

    private void añadirNueva() {
        System.out.println("\n--- Añadir nueva desarrolladora ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("Nombre: " );
        String nombre = leerString();
        if (esVolver(nombre)) return;

        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("País: ");
        String pais = leerString();
        if (esVolver(pais)) return;

        if (nombre.isEmpty() || pais.isEmpty()) {
            System.out.println("Error!!! El nombre y el país no pueden estar vacíos.");
        } else {
            gestor.agregarDesarrolladora(nombre, pais);
            System.out.println("Desarrolladora añadida correctamente.");
            esperarEnter();
        }
    }

    private void buscarPorNombre() {
        System.out.println("\n--- Buscar desarrolladoras por nombre ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("Introduzca parte del nombre: ");
        String busqueda = leerString();
        if (esVolver(busqueda)) return;

        List<Desarrolladora> resultados = gestor.buscarDesarrolladorasPorNombre(busqueda);
        mostrarResultadosDesarrolladoras(resultados);
        esperarEnter();
    }

    private void buscarPorId() {
        System.out.println("\n--- Buscar desarrolladora por ID ---");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        int id = leerEntero("Introduzca el ID: ");
        if (id == 0) return;

        try {
            Desarrolladora d = gestor.obtenerDesarrolladoraPorId(id);
            List<Desarrolladora> resultados = new ArrayList<>();
            resultados.add(d);
            mostrarResultadosDesarrolladoras(resultados);
        } catch (DesarrolladoraNoEncontradaException e) {
            System.out.println("No se encontró ninguna desarrolladora con ID: " + id);
        }
        esperarEnter();
    }

    private void modificar() {
        System.out.println("\n        --- Modificar desarrolladora ---");
        System.out.println("--------------------------------------------------");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("---------------------------------------------------");
        int id = leerEntero("ID de la desarrolladora a modificar: ");
        if (id == 0) return;

        try {
            Desarrolladora actual = gestor.obtenerDesarrolladoraPorId(id);
            System.out.println("-------------------------------------------------");
            System.out.println("Desarrolladora actual: " + actual);
            System.out.println("--------------------------------------------------");
            System.out.println("Para volver al menú anterior, introduzca 0 ---->");
            System.out.println("---------------------------------------------------");
            System.out.println("Nuevo nombre: ");
            System.out.println("Si quiere mantener el mismo, deje el campo vacío");
            String nombre = leerString();
            if (esVolver(nombre)) return;
            if (nombre.isEmpty()) nombre = actual.getNombre();

            System.out.println("---------------------------------------------------");
            System.out.println("Para volver al menú anterior, introduzca 0 ---->");
            System.out.println("---------------------------------------------------");
            System.out.println("Nuevo país: ");
            System.out.println("Si quiere mantener el mismo, deje el campo vacío");
            String pais = leerString();
            if (esVolver(pais)) return;
            if (pais.isEmpty()) pais = actual.getPais();

            gestor.modificarDesarrolladora(id, nombre, pais);
            System.out.println("Desarrolladora modificada correctamente.");
        } catch (DesarrolladoraNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
        esperarEnter();
    }

    private void eliminar() {
        System.out.println("\n--- Eliminar desarrolladora ---");
        System.out.println("---------------------------------------------------");
        System.out.println("Para volver al menú anterior, introduzca 0 ---->");
        System.out.println("---------------------------------------------------");
        int id = leerEntero("ID de la desarrolladora a eliminar: ");
        if (id == 0) return;

        try {
            Desarrolladora d = gestor.obtenerDesarrolladoraPorId(id);
            System.out.println("Desarrolladora a eliminar: " + d);
            System.out.print("¿Está seguro? Introduzca si / no: ");
            String confirmacion = scanner.nextLine().toLowerCase();
            if (confirmacion.equals("si")) {
                gestor.eliminarDesarrolladora(id);
                System.out.println("Desarrolladora eliminada correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (DesarrolladoraNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
        esperarEnter();
    }

    private void mostrarResultadosDesarrolladoras(List<Desarrolladora> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron desarrolladoras que coincidan con su búsqueda.");
        } else {
            System.out.println("\nResultados encontrados (" + resultados.size() + "):");
            System.out.println("-".repeat(50));
            for (Desarrolladora resultado : resultados) {
                System.out.println(resultado);
            }
            System.out.println("-".repeat(50));
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