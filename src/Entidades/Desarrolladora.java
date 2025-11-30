
package Entidades;


/**
 * Esta clase representa una empresa desarrolladora de videojuegos, sus atributos son:
 * ID único, nombre de la empresa y país.
 *
 * Gestiona la entidad Desarrolladora en el CRUD.
 * Se guarda y carga desde un archivo CSV separado.
 */

public class Desarrolladora {

    private final int id;
    private String nombre;
    private String pais;


    public Desarrolladora(int id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    // region getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }
    //endregion


    // setters con validaciones para asegurar que los atributos no sean nulos o estén vacíos
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public void setPais(String pais) {
        if (pais == null || pais.isEmpty()) {
            throw new IllegalArgumentException("El país no puede estar vacío.");
        }
        this.pais = pais;
    }

    // toString del texto a mostrar en consola
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: '" + nombre + "' | País: " + pais;
    }

    // metodo para convertir el objeto a una línea de texto para guardar en el archivo
    public String aTextoCSV() {
        return id + "," + nombre + "," + pais;
    }

    // para crear un objeto Desarrolladora a partir de la línea del archivo
    public static Desarrolladora desdeTextoCSV(String linea) {
        String[] campos = linea.split(",");
        // verificamos que haya exactamente los 3 campos que existen en mi csv
        if (campos.length != 3) {
            throw new IllegalArgumentException("La línea CSV no contiene el formato correcto para una desarrolladora.");
        }
        // hay que parsear el ID ya que es el único int
        int id = Integer.parseInt(campos[0]);
        //  nombre y país ya se trimmean en el constructor!
        String nombre = campos[1];
        String pais = campos[2];
        // por último se crea y devuelve la desarrolladora
        return new Desarrolladora(id, nombre, pais);
    }
}