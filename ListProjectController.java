
/*
 * The podcastproject package contains classes that are part of a podcast client
 *
 * All code in main(String[] args) methods are for testing purposes only.
 *
 * ListProjectController provides methods to inteface between the podcastproject
 * Model classes, and the fxml UI.
 *
 */
package podcastproject;

import javafx.scene.input.MouseEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
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
    List<Podcast> showList;
    List<String> shTitleList = new ArrayList<>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML private ListView<Podcast> listview; // Value injected by FXMLLoader
    
    @FXML private ListView<Episode> listview1;

    @FXML private TextArea bottomTextArea;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        showList = podcatcher.createShowListFromFile("feedLib");

        ObservableList<Podcast> el = FXCollections.observableList(showList);
        listview.setItems(el);
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        assert listview != null : "fx:id=\"listview\" was not injected: check your FXML file 'Untitled'.";

    }

    public void populateListView(List<Episode> list) {
        ObservableList<Episode> epContentList = FXCollections.observableList(list);
        listview1.setItems(epContentList);
    }

    public void populateTextArea(Episode ep) {
        bottomTextArea.setText(ep.getGuid() + "\n" + ep.getTitle() + "\n" + ep.getDescription() + "\n" + ep.getLink() + "\n" + ep.getEnclosureUrl() + "\n" + ep.getPubDate());
    }
    
    @FXML
    public void onSelection(MouseEvent me) {
        Podcast currentlySelected = listview.getSelectionModel().getSelectedItem();
        podcatcher.createEpisodeList(currentlySelected);

        epList = currentlySelected.getEpisodeList();

        ObservableList<Episode> el = FXCollections.observableList(epList);
        listview1.setItems(el);

        Episode currentEpisode = listview1.getSelectionModel().getSelectedItem();
        populateTextArea(currentEpisode);
    }

}
