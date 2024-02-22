package oak.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oak.controller.OakController;

// @@author SherisseTJW-reused
// The contents of this file is mainly reused, with minor modifications from the JavaFX Tutorial as provided here
// https://se-education.org/guides/tutorials/javaFxPart4.html

public class Main extends Application {
    private OakController oak = new OakController();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            VBox ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setOak(oak);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
