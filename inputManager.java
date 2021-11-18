
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
 * LAUNCH THIS APPLICATION TO RUN PROGRAM
 *
 * @author Circle Onyx
 * @version 1.1
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
                    case RIGHT: Game.right = true;break;
                    case LEFT: Game.left = true;break;
                    case DOWN: Game.down = true;break;
                    case X: Game.ror = true;break;
                    case Z: Game.rol = true;break;
                }
            });
        scene.setOnKeyReleased(event -> {
                switch(event.getCode()){
                    case RIGHT: Game.right = false;break;
                    case LEFT: Game.left = false;break;
                    case DOWN: Game.down = false;break;
                    case X: Game.ror = false;break;
                    case Z: Game.rol = false;break;
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
