
package Gestion;

import Excepciones.DesarrolladoraNoEncontradaException;
import Excepciones.VideojuegoNoEncontradoException;
import Entidades.Desarrolladora;
import Entidades.Videojuego;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Clase gestora del CRUD para videojuegos y desarrolladoras.
 * Usa listas estáticas y métodos estáticos para gestionar los datos.
 * Los datos se guardan en archivos CSV en la carpeta del proyecto.
 * Genera IDs automáticos y carga/guarda los datos al iniciar y cerrar.
 */

public class GestorDatos {

    private final List<Videojuego> videojuegos;
    private final List<Desarrolladora> desarrolladoras;
    private int siguienteIdVideojuego;
    private int siguienteIdDesarrolladora;

    private static final String csv_videojuegos = "Videojuegos.csv";
    private static final String csv_desarrolladoras = "Desarrolladoras.csv";


    public GestorDatos() {
        this.videojuegos = new ArrayList<>();
        this.desarrolladoras = new ArrayList<>();
        this.siguienteIdVideojuego = 1;
        this.siguienteIdDesarrolladora = 1;
        cargarDatos();
    }


    private void cargarDatos() {
        // Cargamos desarrolladoras primero porque los videojuegos dependen de estas
        Path rutaDesarrolladoras = Paths.get(csv_desarrolladoras);
        if (Files.exists(rutaDesarrolladoras)) {
            try (BufferedReader lector = Files.newBufferedReader(rutaDesarrolladoras)) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    linea = linea;
                    if (!linea.isEmpty()) {
                        try {
                            Desarrolladora d = Desarrolladora.desdeTextoCSV(linea);
                            desarrolladoras.add(d);
                            if (d.getId() >= siguienteIdDesarrolladora) {
                                siguienteIdDesarrolladora = d.getId() + 1;
                            }
                        } catch (Exception e) {
                            System.err.println("Línea ignorada en desarrolladoras.csv: " + linea);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("No se pudo leer el archivo de desarrolladoras.csv");
            }
        }


        // Cargar videojuegos
        Path rutaVideojuegos = Paths.get(csv_videojuegos);
        if (Files.exists(rutaVideojuegos)) {
            try (BufferedReader lector = Files.newBufferedReader(rutaVideojuegos)) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    linea = linea;
                    if (!linea.isEmpty()) {
                        try {
                            Videojuego v = Videojuego.desdeTextoCSV(linea);
                            videojuegos.add(v);
                            if (v.getId() >= siguienteIdVideojuego) {
                                siguienteIdVideojuego = v.getId() + 1;
                            }
                        } catch (Exception e) {
                            System.err.println("Línea ignorada en videojuegos.csv: " + linea);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("No se pudo leer el archivo de videojuegos.csv");
            }
        }
    }

    // Guarda todas las listas en los archivos CSV
    public void guardarDatos() {
        // Guardar desarrolladoras
        try (PrintWriter escritor = new PrintWriter(new OutputStreamWriter(new FileOutputStream(csv_desarrolladoras)))) {
            for (Desarrolladora d : desarrolladoras) {
                escritor.println(d.aTextoCSV());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de desarrolladoras.");
        }

        // Guardar videojuegos
        try (PrintWriter escritor = new PrintWriter(new OutputStreamWriter(new FileOutputStream(csv_videojuegos)))) {
            for (Videojuego v : videojuegos) {
                escritor.println(v.aTextoCSV());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de videojuegos.");
        }
    }

    // ========== MÉTODOS PARA DESARROLLADORAS ==========

    public void agregarDesarrolladora(String nombre, String pais) {
        Desarrolladora d = new Desarrolladora(siguienteIdDesarrolladora, nombre, pais);
        desarrolladoras.add(d);
        siguienteIdDesarrolladora++;
    }

    public Desarrolladora obtenerDesarrolladoraPorId(int id) throws DesarrolladoraNoEncontradaException {
        for (Desarrolladora d : desarrolladoras) {
            if (d.getId() == id) {
                return d;
            }
        }
        throw new DesarrolladoraNoEncontradaException("No se encontró ninguna desarrolladora con ID: " + id);
    }

    public List<Desarrolladora> buscarDesarrolladorasPorNombre(String nombre)  {
        List<Desarrolladora> resultados = new ArrayList<>();
        String busqueda = nombre.toLowerCase();
        for (Desarrolladora d : desarrolladoras) {
            if (d.getNombre().toLowerCase().contains(busqueda)) {
                resultados.add(d);
            }
        }
        return resultados;
    }

    public void modificarDesarrolladora(int id, String nombre, String pais) throws DesarrolladoraNoEncontradaException {
        for (Desarrolladora d : desarrolladoras) {
            if (d.getId() == id) {
                d.setNombre(nombre);
                d.setPais(pais);
                return;
            }
        }
        throw new DesarrolladoraNoEncontradaException("No se encontró la desarrolladora para modificar.");
    }

    public void eliminarDesarrolladora(int id) throws DesarrolladoraNoEncontradaException {
        // Comprobar que no tenga videojuegos asociados
        for (Videojuego v : videojuegos) {
            if (v.getIdDesarrolladora() == id) {
                throw new DesarrolladoraNoEncontradaException("No se puede eliminar, hay videojuegos asociados a esta desarrolladora,\nprimero se debe eliminar el videojuego.");
            }
        }
        // Eliminar la desarrolladora
        for (int i = 0; i < desarrolladoras.size(); i++) {
            if (desarrolladoras.get(i).getId() == id) {
                desarrolladoras.remove(i);
                return;
            }
        }
        throw new DesarrolladoraNoEncontradaException("No se encontró la desarrolladora para eliminar.");
    }

    public List<Desarrolladora> listarTodasLasDesarrolladoras() {
        return new ArrayList<>(desarrolladoras);
    }

    // ========== MÉTODOS PARA VIDEOJUEGOS ==========

    public void agregarVideojuego(String titulo, String genero, int anio, int idDesarrolladora) {
        // validar que la desarrolladora exista
        boolean existe = false;
        for (Desarrolladora desarrolladora : desarrolladoras) {
            if (desarrolladora.getId() == idDesarrolladora) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            throw new IllegalArgumentException("El ID de desarrolladora no existe. Cree la desarrolladora primero.");
        }

        Videojuego v = new Videojuego(siguienteIdVideojuego, titulo, genero, anio, idDesarrolladora);
        videojuegos.add(v);
        siguienteIdVideojuego++;
    }

    public Videojuego obtenerVideojuegoPorId(int id) throws VideojuegoNoEncontradoException {
        for (Videojuego v : videojuegos) {
            if (v.getId() == id) {
                return v;
            }
        }
        throw new VideojuegoNoEncontradoException("No se encontró ningún videojuego con ID: " + id);
    }

    public List<Videojuego> buscarVideojuegosPorTitulo(String titulo) {
        List<Videojuego> resultados = new ArrayList<>();
        String busqueda = titulo.toLowerCase();
        for (Videojuego v : videojuegos) {
            if (v.getTitulo().toLowerCase().contains(busqueda)) {
                resultados.add(v);
            }
        }
        return resultados;
    }

    public List<Videojuego> buscarVideojuegosPorGenero(String genero) {
        List<Videojuego> resultados = new ArrayList<>();
        String busqueda = genero.toLowerCase();
        for (Videojuego v : videojuegos) {
            if (v.getGenero().toLowerCase().contains(busqueda)) {
                resultados.add(v);
            }
        }
        return resultados;
    }

    //  combina videojuegos cuya desarrolladora contiene un nombre
    public List<Videojuego> buscarVideojuegosPorDesarrolladora(String nombreDesarrolladora) {
        List<Videojuego> resultados = new ArrayList<>();
        String busqueda = nombreDesarrolladora.toLowerCase();
        // Primero, encontrar desarrolladoras que coinciden
        for (Desarrolladora d : desarrolladoras) {
            if (d.getNombre().toLowerCase().contains(busqueda)) {
                int idDesa = d.getId();
                // Luego, buscar videojuegos con ese ID
                for (Videojuego v : videojuegos) {
                    if (v.getIdDesarrolladora() == idDesa) {
                        resultados.add(v);
                    }
                }
            }
        }
        return resultados;
    }

    public void modificarVideojuego(int id, String titulo, String genero, int anio, int idDesarrolladora) throws VideojuegoNoEncontradoException {
        // validar que la desarrolladora exista
        boolean existeDesa = false;
        for (Desarrolladora desarrolladora : desarrolladoras) {
            if (desarrolladora.getId() == idDesarrolladora) {
                existeDesa = true;
                break;
            }
        }
        if (!existeDesa) {
            throw new IllegalArgumentException("El ID de desarrolladora no existe.");
        }

        for (Videojuego v : videojuegos) {
            if (v.getId() == id) {
                v.setTitulo(titulo);
                v.setGenero(genero);
                v.setAnio(anio);
                v.setIdDesarrolladora(idDesarrolladora);
                return;
            }
        }
        throw new VideojuegoNoEncontradoException("No se encontró el videojuego para modificar.");
    }

    public void eliminarVideojuego(int id) throws VideojuegoNoEncontradoException {
        for (int i = 0; i < videojuegos.size(); i++) {
            if (videojuegos.get(i).getId() == id) {
                videojuegos.remove(i);
                return;
            }
        }
        throw new VideojuegoNoEncontradoException("No se encontró el videojuego para eliminar.");
    }

    public List<Videojuego> listarTodosLosVideojuegos() {
        return new ArrayList<>(videojuegos);
    }

    //  mostrar el nombre real de la desarrolladora
    public String obtenerNombreDesarrolladora(int idDesarrolladora) {
        for (Desarrolladora d : desarrolladoras) {
            if (d.getId() == idDesarrolladora) {
                return d.getNombre();
            }
        }
        return "Desconocida ID: " + idDesarrolladora;
    }
}