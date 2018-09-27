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
import java.io.IOException;

public class LibBuilder {
    private String feedUrl;
    private String libraryFile = "podcastproject/feedLib";
    Scanner keyboard = new Scanner(System.in);

    public void writeToFile(String showUrl) {
        try {
            FileWriter fw = new FileWriter(libraryFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
    
//            while (true) {
//                System.out.print("Enter url to add to library: ");
//    
//                url = keyboard.nextLine();
//    
//                if (url.equals("0")) {
//                    break;
//                }
//    
//                bw.write(url + "\n");
//            }
            
            bw.write(showUrl);

            bw.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main (String[] args) {
//        LibBuilder lb = new LibBuilder();
//       lb.writeToFile();
    }
}
