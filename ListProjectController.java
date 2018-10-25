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

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.Node;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Slider;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.scene.web.WebView;

public class ListProjectController {
    Media toPlay;
    MediaPlayer mediaPlayer;
    private boolean isPlaying;

    Podcatcher podcatcher = new Podcatcher();
    Podcast podcast = new Podcast();
    Podcast currentlySelectedPodcast;
    Episode currentlySelectedEp;

    List<Episode> epList;
    List<String> titleList = new ArrayList<>();
    List<Podcast> showList;
    List<String> shTitleList = new ArrayList<>();
    private ObservableList<Podcast> pl;

    /*https://stackoverflow.com/questions/30829164/how-to-display-html-in-javafx-application*/
    @FXML private WebView renderFormattedText;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML private ListView<Podcast> listview; // Value injected by FXMLLoader
    
    @FXML private ListView<Episode> listview1;

    @FXML private TextArea bottomTextArea;

    @FXML private TextField urlEntry;

    @FXML private Button playButton;
    @FXML private Button stopButton;
    @FXML private Button pauseButton;
    @FXML private Slider volumeController;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        
        volumeController.setMax(1.0);
        volumeController.setMin(0.0);
        volumeController.setValue(0.5);
        volumeController.valueProperty().addListener(new ChangeListener<Number>() {
            
            @Override
            public void changed (ObservableValue<? extends Number> observable, Number oldVal, Number newVal) 
            {
                mediaPlayer.setVolume(newVal.doubleValue());
            }
            
        });


        showList = podcatcher.createShowListFromFile("feedLib");

        pl = FXCollections.observableList(showList);
        listview.setItems(pl);

//        volumeControl.valueProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println("Hello");
//            mediaPlayer.setVolume(newVal.doubleValue());
//            System.out.println("Value changing: " + newVal);
//        });

        listview.setCellFactory(new Callback<ListView<Podcast>, ListCell<Podcast>>() {
            @Override
            public ListCell<Podcast> call(ListView<Podcast> listview) {
                return new imageCell();
            }
        });


        assert listview != null : "fx:id=\"listview\" was not injected: check your FXML file 'Untitled'.";

    }

    /* On selection of a specific podcast, show a list of that podcast's
     * episodes*/
    @FXML
    public void populateEpisodeList(MouseEvent me) {
        currentlySelectedPodcast = listview.getSelectionModel().getSelectedItem();
        podcatcher.createEpisodeList(currentlySelectedPodcast);

        epList = currentlySelectedPodcast.getEpisodeList();

        ObservableList<Episode> el = FXCollections.observableList(epList);
        listview1.setItems(el);
    }
    
    /* Select an episode and display in WebView */
    @FXML
    public void episodeViewer(MouseEvent me) {
        
        currentlySelectedEp = listview1.getSelectionModel().getSelectedItem();
        setWebViewArea();
    }

    /* Renders the html content of episode description using javafx embedded
     * browser */
    public void setWebViewArea() {
        
        renderFormattedText.getEngine().loadContent(currentlySelectedEp.getGuid() + "\n" + currentlySelectedEp.getTitle() + "<p>" + currentlySelectedEp.getDescription() +  "</p><p>" + currentlySelectedEp.getLink() + "</p><p><a href=" + currentlySelectedEp.getEnclosureUrl() + ">Download</a></p><p>" + currentlySelectedEp.getPubDate() + "</p>");
    }



    public void makeNewMediaPlayer () {
            toPlay = new Media(currentlySelectedEp.getEnclosureUrl());
            mediaPlayer = new MediaPlayer(toPlay);
    }

    public void play() {
    
        if (mediaPlayer == null || mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
            makeNewMediaPlayer();
        }
        else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            stop();
            makeNewMediaPlayer();
        }

        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    /* Allows user to add show by entering rss feed url */
    public void showUrlAdder() {
        
        String showUrl = urlEntry.getText();

        for (Podcast p: pl) {
            if (showUrl.equals(p.getRssFeedUrl())) {
                
                Alert alreadyPresent = new Alert(AlertType.INFORMATION);
                alreadyPresent.setTitle("Error");
                alreadyPresent.setContentText("You already have that one");
                alreadyPresent.show();
                return;
            }
        }

        Podcatcher podcatcher = new Podcatcher();
        
        Podcast newlyAdded = podcatcher.createShow(showUrl);
        
        pl.add(newlyAdded);

        new LibBuilder().writeToFile(showUrl + "\n");
    }

    //docs.oracle.com/javafx/2/ui_controls/list-view.htm#CEGGEDBF
    static class imageCell extends ListCell<Podcast> {
        
        private Map<String, Image> imageCache = new LinkedHashMap<>();
        private String title;
        private String imageUrl;
        private Image im;
        private ImageView imageview = new ImageView();

    //    @Override
        public void updateItem(Podcast p, boolean empty) {

            super.updateItem(p, empty);
            
            if (empty) {
                setGraphic(null);
            }
            else {
                title = p.getTitle();
            
    
                if (p.getImageUrl() == null) {
               
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/ac/No_image_available.svg";
                }
                else {
                    imageUrl = p.getImageUrl();
                }
            

                if ((imageCache.get(imageUrl) == null)) {
                  
                    im = new Image(imageUrl, 100, 100, false, true, true);
                    imageCache.put(imageUrl, im);
                }
            

            imageview.setImage(imageCache.get(imageUrl));
            setText(title);
            setGraphic(imageview);
            }
        }
    }
}
