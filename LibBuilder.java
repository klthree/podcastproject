package podcastproject;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.Scanner;

public class LibBuilder {
    private String feedUrl;

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

    public static void main (String[] args) {
        Scanner keyboard = new Scanner (System.in);
        System.out.print("Enter url: ");
        String input = keyboard.nextLine();
        LibBuilder lb = new LibBuilder(input);
        lb.download();
    }
}
