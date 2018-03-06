import sun.security.krb5.internal.crypto.Des;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Files;

public class DownloadParser {

    private List<Descarga> descargas;
    private Descarga currentDescarga;

    public DownloadParser() {
        descargas = new LinkedList<>();
        currentDescarga = null;
    }

    public List<Descarga> ParseDownloadFile() {
        String path = "descargas.txt";
        try (Scanner s = new Scanner(new File(path))) {
            while(s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.contains("Fichero:")) {
                    if(currentDescarga != null) descargas.add(currentDescarga);
                    currentDescarga = new Descarga(linea.replace("Fichero: ", ""));
                } else {
                    String[] split = linea.split("/");
                    currentDescarga.addArchivo(split[split.length - 1], linea);
                    if(!s.hasNextLine()) descargas.add(currentDescarga);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return descargas;
    }
}
