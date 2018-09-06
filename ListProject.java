package podcastproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

public class ListProject extends Application {
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("ListProject.fxml"));
        Scene scene = new Scene(parent);

        stage.setTitle("List Project");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
