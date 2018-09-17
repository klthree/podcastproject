package podcastproject;

import javafx.scene.control.SelectionMode;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;

public class ListProjectController {
    
    Podcatcher podcatcher = new Podcatcher();
    Podcast podcast = new Podcast();

    List<Episode> epList;
    List<String> titleList = new ArrayList<>();
    List<Show> showList;
    List<String> shTitleList = new ArrayList<>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML private ListView<String> listview; // Value injected by FXMLLoader
    
    @FXML private ListView<String> listview1;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        podcast = podcatcher.createShow("http://feeds.wnyc.org/2dopequeens");
        podcatcher.createEpisodeList();
        epList = podcast.getEpisodeList();

        for (Episode ep: epList) {
            titleList.add(ep.getTitle());
        }


        ObservableList<String> el = FXCollections.observableList(titleList);
        listview.setItems(el);
        listview1.getItems().addAll("Hello", "bon jour", "aloha", "hola");
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        assert listview != null : "fx:id=\"listview\" was not injected: check your FXML file 'Untitled'.";

    }
}
