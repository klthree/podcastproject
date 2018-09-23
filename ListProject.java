
/*
 * The podcastproject package contains classes that are part of a podcast client
 *
 * All code in main(String[] args) methods are for testing purposes only.
 *
 * ListProject launches application
 *
 */
package podcastproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListProject extends Application {
    public void start (Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("ListProject.fxml"));
        Scene scene = new Scene(parent);

        stage.setTitle("List Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
