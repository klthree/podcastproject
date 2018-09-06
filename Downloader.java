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

    public Downloader(String fileUrl) {

        try {
            this.fileUrl = new URL(fileUrl);
            URLConnection identifier = this.fileUrl.openConnection();
            this.filetype = identifier.getContentType();
            destFilename = fileUrl.substring(fileUrl.lastIndexOf('/') + 1, fileUrl.length());        
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
            if (filetype.contains("mpeg") /*&& !destFilename.contains("mpeg") && !destFilename.contains("mp3")*/){
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
