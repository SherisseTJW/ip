package oak.javafx;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import oak.controller.OakController;
import oak.feedback.enums.CommandEnum;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private OakController oak;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/oak_dex.png"));
    private Image oakImage = new Image(this.getClass().getResourceAsStream("/images/oak_dex.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setOak(OakController d) {
        oak = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Oak's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        // @@author SherisseTJW-reused
        // Reused from https://stackoverflow.com/a/13602324
        // with minor modifications
        if (input.equals(CommandEnum.BYE.getCommandValue())) {
            Stage stage = (Stage) userInput.getScene().getWindow();
            stage.close();
        }

        String response = this.oak.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getOakDialog(response, oakImage)
        );
        userInput.clear();
    }
}