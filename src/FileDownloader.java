import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class FileDownloader {

    private List<Descarga> descargas;

    public FileDownloader() {
        descargas = new LinkedList<>();
        File f = new File("downloads");
        boolean b = f.mkdir();
        if(!b) {
            System.out.println("La carpeta de descargas ya existe.");
        }
    }

    public void process(String downloadsFileURL) {
        // Bajar downloads.txt
        downloadFile("descargas.txt", downloadsFileURL, "");
        // Parsearlo
        descargas = new DownloadParser().ParseDownloadFile();
        // Por cada fichero
        for(Descarga e : descargas) {
            for(Map.Entry<String, String> k : e.getArchivos().entrySet()) {
                downloadFile(k.getKey(), k.getValue(), "downloads/");
            }
        }
            // Creo una cola con sus partes y una barrera
            // Bajo cada parte
            // La barrera acaba uniendo los archivos
        //
    }

    private void downloadFile(String nombreArchivo, String url, String lugar) {
        URL website = null;
        try {
            website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = null;
            fos = new FileOutputStream(lugar + nombreArchivo);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            rbc.close();
            fos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String downloadFile = "https://github.com/jesussanchezoro/PracticaPC/raw/master/descargas.txt";
        FileDownloader fd = new FileDownloader();
        fd.process(downloadFile);
    }
}
