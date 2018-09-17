package podcastproject;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ShowList {
    private List<Show> showList= new ArrayList<>();
    Podcast podcast;
    Podcatcher podcatcher = new Podcatcher();

    public List ShowListCreator() {
        Scanner keyboard = new Scanner(System.in);

        String input = "";

        while (true) {
            System.out.print("Enter url: ");

            input = keyboard.nextLine();

            if (input.equals("0")) {
                break;
            }
            else {
                podcast = podcatcher.createShow(input);
                showList.add(podcast);
            }
        }

        return showList;
    }
}
