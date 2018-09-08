package podcastproject;

import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.Scanner;

public class Downloader {
    private URL fileUrl;
    private String destFilename;
    public static String filetype;

    public Downloader(String urlToDownload) {

        try {
            this.fileUrl = new URL(urlToDownload);
            URLConnection identifier = this.fileUrl.openConnection();
            this.filetype = identifier.getContentType();
            String urlPath = fileUrl.getPath();
            System.out.println("\n\n\n" + urlPath + "\n\n\n");
            destFilename = urlToDownload.substring(urlPath.lastIndexOf('/') + 1, urlPath.length()); 
            System.out.println("\n\n\n" + destFilename + "\n\n\n");
        }
        catch (MalformedURLException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public String getFilename() {
        return destFilename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void download() {
        String suffix = "";
        try {

            ReadableByteChannel rbc = Channels.newChannel(fileUrl.openStream());
            System.out.println(filetype);
            if (filetype.contains("mpeg") && !destFilename.endsWith(".mpeg") && !destFilename.endsWith(".mp3")){
                suffix = ".mp3";
            }
            else if (filetype.contains("jpeg") && !destFilename.contains("jpeg") && !destFilename.contains("jpg")) {
                suffix = ".jpg";
            }
            else if (filetype.contains("xml") && !destFilename.contains("xml")) {
                suffix = ".xml";
            }
            

            FileOutputStream fos = new FileOutputStream(destFilename + suffix);
            
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void main (String[] args) {
        Scanner keyboard = new Scanner(System.in);

        String site = keyboard.nextLine();
        Downloader fd = new Downloader(site);
        System.out.println(filetype);
        fd.download();
    }
}
