package Entidades;


/**
 * Esta clase representa los videojuegos, sus atributos son:
 * ID único, título, género, año y el ID de la desarrolladora como
 * clave foránea que conecta ambas entidades.
 *
 * Gestiona la entidad Videojuego en el CRUD.
 * Se guarda y carga desde un archivo CSV separado.
 *
 */

public class Videojuego {

    private final int id;
    private String titulo;
    private String genero;
    private int anio;
    private int idDesarrolladora;

    public Videojuego(int id, String titulo, String genero, int anio, int idDesarrolladora) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.idDesarrolladora = idDesarrolladora;
    }

    //region getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnio() {
        return anio;
    }

    public int getIdDesarrolladora() {
        return idDesarrolladora;
    }
    //endregion

    // setters con validaciones para asegurar que los atributos no sean nulos o estén vacíos
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío.");
        }
        this.genero = genero;
    }

    public void setAnio(int anio) {
        int anioActual = java.time.Year.now().getValue();
        if (anio < 1950 || anio > anioActual + 1) {
            throw new IllegalArgumentException("El año debe estar entre 1950 y " + (anioActual + 1) + ".");
        }
        this.anio = anio;
    }

    public void setIdDesarrolladora(int idDesarrolladora) {
        if (idDesarrolladora <= 0) {
            throw new IllegalArgumentException("El ID de la desarrolladora debe ser un número positivo.");
        }
        this.idDesarrolladora = idDesarrolladora;
    }


    @Override
    public String toString() {
        return "ID: " + id + " | Título: '" + titulo + "' | Género: " + genero + " | Año: " + anio;
    }

    //  metodo para convertir el objeto a una línea de texto para guardar en el archivo
    public String aTextoCSV() {
        return id + "," + titulo + "," + genero + "," + anio + "," + idDesarrolladora;
    }

    // para crear un objeto videojuego a partir de la línea del archivo
    public static Videojuego desdeTextoCSV(String linea) {
        String[] campos = linea.split(",");
        if (campos.length != 5) {
            throw new IllegalArgumentException("La línea CSV no tiene el formato correcto para un videojuego.");
        }
        int id = Integer.parseInt(campos[0]);
        String titulo = campos[1];
        String genero = campos[2];
        int anio = Integer.parseInt(campos[3]);
        int idDesarrolladora = Integer.parseInt(campos[4]);
        // por último se crea y devuelve el objeto
        return new Videojuego(id, titulo, genero, anio, idDesarrolladora);
    }
}