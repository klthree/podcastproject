package podcastproject;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

public class LibBuilder {
    private String feedUrl;
    private String libraryFile = "feedLib";

    public LibBuilder (String feedUrl) {
        this.feedUrl = feedUrl + "\n";
    }

    public void download() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("feedLib", true));
            bw.write(feedUrl);
            bw.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Episode> showListBuilder () throws Exception {
        
        BufferedReader br = new BufferedReader(new FileReader(libraryFile));
        
        //while ((String url = br.readLine()) != null) {
            // scan url for show information using xml streamreader. do i pass
            // that to a i
            // do it one way online
            // do it another way offline
        //}
        return null;
    }

    public static void main (String[] args) {
        Scanner keyboard = new Scanner (System.in);
        System.out.print("Enter RSS feed url: ");
        String input = keyboard.nextLine();
        LibBuilder lb = new LibBuilder(input);
        lb.download();
    }
}
