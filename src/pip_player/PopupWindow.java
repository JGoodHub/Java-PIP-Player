package pip_player;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pop-up window used to get the youtube URL from the user
 * 
 * @author JGoodHub
 */
public class PopupWindow {
    
    //-----VARIABLES-----
    
    private static boolean urlValid;
    
    //-----METHODS-----
    
    /**
     * Present the user with a text input field and wait from them to input a correct URL
     * Reject any URL's that don't conform to the required pattern 
     */
    public static void getInput() {
        Stage popupStage = new Stage();
        popupStage.setResizable(false);
        
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER); 

        Label windowTitle = new Label("Youtube Video URL");
        
        Label urlValidationFeedback = new Label("Please enter a valid youtube URL");

        HBox urlBar = new HBox(20);
        urlBar.setPadding(new Insets(0, 20, 0, 20));
        
        TextField urlField  = new TextField();
        urlField.setMinWidth(400);
        urlField.setMaxWidth(400);
        
        //Live check if the URL is valid, setting the appropriate flag
        urlField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (validateURL(newValue) == true) {
                urlValidationFeedback.setText("URL is Valid");
                urlValid = true;
            } else {
                urlValidationFeedback.setText("URL is Not Valid");
                urlValid = false;
            }
        });
        
        //Close the pop-up if the URL is valid, else inform the user
        urlField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (urlValid) {
                    Player.targetURL = urlField.getText();
                    popupStage.close();
                } else {
                   urlValidationFeedback.setText("URL is must be valid to continue"); 
                }
            }
        });
        
        layout.getChildren().addAll(windowTitle, urlField, urlValidationFeedback);
        
        Scene scene = new Scene(layout, 500, 90);           
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(scene);
        popupStage.setTitle("Video URL");       
        popupStage.setResizable(false);
        popupStage.setAlwaysOnTop(true);
        popupStage.showAndWait();        
    }
    
    /**
     * Check if the user's URL is a valid youtube URL
     * 
     * @param userURL The URL provided by the user
     * @return Boolean stating if the URL is valid
     */
    private static boolean validateURL(String userURL) {
        if (userURL.contains("www.youtube.com/watch?v=") || userURL.contains("www.youtube.com/embed/")){
            return true;
        } else {
            return false;
        }
    }
    
}
