import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ListProjectController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listView1;
    
    ObservableList<String> list = FXCollections.observableArrayList ("hello", "bon jour", "guten tag", "hola", "aloha");
    @FXML
    private ListView<?> listView2;

    @FXML
    void initialize() {
        listView1.setItems(list);
        //assert listView1 != null : "fx:id=\"listView1\" was not injected: check your FXML file 'Untitled'.";
        //assert listView2 != null : "fx:id=\"listView2\" was not injected: check your FXML file 'Untitled'.";
    }
}
