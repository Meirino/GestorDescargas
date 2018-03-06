import java.util.HashMap;
import java.util.Map;

public class Descarga {
    private String nombre;
    private Map<String, String> archivos;

    public Descarga(String nombre) {
        this.nombre = nombre;
        this.archivos = new HashMap<>();
    }

    public void addArchivo(String filename, String url) {
        this.archivos.putIfAbsent(filename, url);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String, String> getArchivos() {
        return archivos;
    }

    public void setArchivos(Map<String, String> archivos) {
        this.archivos = archivos;
    }

    public void mostrarDescarga() {
        System.out.println(nombre + ": ");
        for (Map.Entry<String, String> e : archivos.entrySet()) {
            System.out.println(e.getKey() + " - " + e.getValue());
        }
    }
}
