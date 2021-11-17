
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Write a description of JavaFX class inputManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class inputManager extends Application
{
    @Override
    public void start(Stage stage)
    {
        GridPane pane = new GridPane();
        pane.setMinSize(300, 300);
        Scene scene = new Scene(pane, 200,0);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        Game.gameThread.start();
        scene.setOnKeyPressed(event -> {
                switch(event.getCode()){
                    case RIGHT: Game.right = true;
                    case LEFT: Game.left = true;
                    case DOWN: Game.down = true;
                    case X: Game.ror = true;
                    case Z: Game.rol = true;
                }
            });
        scene.setOnKeyReleased(event -> {
                switch(event.getCode()){
                    case RIGHT: Game.right = false;
                    case LEFT: Game.left = false;
                    case DOWN: Game.down = false;
                    case X: Game.ror = false;
                    case Z: Game.rol = false;
                } 
                Game.held = false;
            });

        // Show the Stage (window)
        stage.show();
    }

    public static void Main(String[] args){
        launch(args);
    }
}
