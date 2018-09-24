
/*
 mage("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");* The podcastproject package contains classes that are part of a podcast client
 *
 * All code in main(String[] args) methods are for testing purposes only.
 *
 * ListProjectController provides methods to inteface between the podcastproject
 * Model classes, and the fxml UI.
 *
 */
package podcastproject;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import javafx.scene.Node;

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
import javafx.scene.web.WebView;

public class ListProjectController {
    
    Podcatcher podcatcher = new Podcatcher();
    Podcast podcast = new Podcast();

    List<Episode> epList;
    List<String> titleList = new ArrayList<>();
    List<Podcast> showList;
    List<String> shTitleList = new ArrayList<>();
    
    /*https://stackoverflow.com/questions/30829164/how-to-display-html-in-javafx-application*/
    @FXML private WebView renderFormattedText;


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
        
        listview.setCellFactory(new Callback<ListView<Podcast>, ListCell<Podcast>>() {
            @Override
            public ListCell<Podcast> call(ListView<Podcast> listview) {
                return new imageCell();
            }
        });

        assert listview != null : "fx:id=\"listview\" was not injected: check your FXML file 'Untitled'.";

    }

    public void populateListView(List<Episode> list) {
        ObservableList<Episode> epContentList = FXCollections.observableList(list);
        listview1.setItems(epContentList);
    }

    public void populateTextArea(Episode ep) {
        bottomTextArea.setText(ep.getGuid() + "\n" + ep.getTitle() + "\n" + ep.getDescription() + "\n" + ep.getLink() + "\n" + ep.getEnclosureUrl() + "\n" + ep.getPubDate());
    }

    public void setWebViewArea(Episode ep) {
        renderFormattedText.getEngine().loadContent(ep.getGuid() + "\n" + ep.getTitle() + "<p>" + ep.getDescription() +  "</p><p>" + ep.getLink() + "</p><p><a href=" + ep.getEnclosureUrl() + ">Download</a></p><p>" + ep.getPubDate() + "</p>");
    }
    
    @FXML
    public void episodeListPopulator(MouseEvent me) {
        Episode currentEpisode = listview1.getSelectionModel().getSelectedItem();
//        populateTextArea(currentEpisode);
        setWebViewArea(currentEpisode);
    }

    @FXML
    public void onSelection(MouseEvent me) {
        Podcast currentlySelected = listview.getSelectionModel().getSelectedItem();
        podcatcher.createEpisodeList(currentlySelected);

        epList = currentlySelected.getEpisodeList();

        ObservableList<Episode> el = FXCollections.observableList(epList);
        listview1.setItems(el);
    }

//docs.oracle.com/javafx/2/ui_controls/list-view.htm#CEGGEDBF
static class imageCell extends ListCell<Podcast> {
//    @Override
        public void updateItem(Podcast p, boolean empty) {
            super.updateItem(p, empty);
            if (empty || p.getImageUrl() == null) {
                setText(null);
                setGraphic(null);
            }
            else {
            
                Image im = new Image(p.getImageUrl(), 100, 100, false, true, true);
                ImageView imageView = new ImageView();
                imageView.setImage(im);
                setGraphic(imageView);
            }
        }
}
}

