package pip_player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Application for playing youtube videos in a "Play In Picture" always on top window
 * 
 * @author JGoodHub
 */
public class Player extends Application {
    
    //-----VARIABLES-----
    
    protected static String targetURL = "";
    
    //-----METHODS-----
    
    /**
     * Launch the JavaFX application
     * 
     * @param args arguments passed from the run command
     */
    public static void main(String[] args) {        
        launch(args);
    }
    
    /**
     * Launch the JavaFX application
     * 
     * @param primaryStage The stage for the JavaFX application
     * @throws java.lang.Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        webView.setMinSize(640, 390);
        webView.setMaxSize(640, 390);
        
        loadVideo(webView);  
        
        //All the user to change the video by pressing F5
        Scene scene = new Scene(webView, Color.BLACK);        
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F5) {
                loadVideo(webView);
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Youtube PIP Player (F5 to Change Video)");
        primaryStage.show();
    }
    
    /**
     * Take the users URL input and inject it into the Webview container to load the video
     * 
     * @param youtubePlayer The Webview container for the video player
     */
    private void loadVideo (WebView youtubePlayer) {
        //Blocking call to get the URL
        PopupWindow.getInput();
        
        if (targetURL.equals("") == false) {
            youtubePlayer.getEngine().load(
                    "https://www.youtube.com/embed/" 
                    + extractVideoID(targetURL)
                    + "?autoplay=1");
        } else {
            System.exit(0);
        }
    }
  
    /**
     * Extract just the video ID from the URL, the rest can be disposed of
     * 
     * @param userURL The URL of the youtube video
     * @return The extracted URL
     */
    private String extractVideoID(String userURL) {
        String output = "";
        char stopChar = '&';
        int ptr = 0;
        
        //Determine the stop char based on the tyoe of url
        if (userURL.contains("www.youtube.com/watch?v=")) {
            ptr = userURL.indexOf("h?v=") + 4;
            stopChar = '&';
        } else if (userURL.contains("www.youtube.com/embed/")) {
            ptr = userURL.indexOf("bed/") + 4;
            stopChar = '?';            
        }
        
        //Keep pushing the ID characters onto the output until the stop char is reached
        while (ptr < userURL.length() && userURL.charAt(ptr) != stopChar) {
            output += String.valueOf(userURL.charAt(ptr));
            ptr++;
        }
        
        return output;
    }    

}
