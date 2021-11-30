
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    public static Label currentScore;
    public static Label highScore;
    public static Thread changeLabelText;
    public static Task<Void> task;
    public static boolean b = false;
    @Override
    public void start(Stage stage)
    {
        GridPane pane = new GridPane();
        pane.setMinSize(300, 300);
        Scene scene = new Scene(pane, 300,600);
        stage.setTitle("INPUT READER");
        stage.setScene(scene);
        currentScore = new Label("Current Score: 0");
        highScore = new Label("High Score: ");
        Button b = new Button("reset");
        b.setFocusTraversable(false);
        pane.add(currentScore,0,1);
        pane.add(highScore,0,2);
        pane.add(b,0,0);
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
        b.setOnAction(event -> {
            Game.game = false;
            TetrisScore.resetScore();
        });

        // Show the Stage (window)
        stage.setOnCloseRequest(event -> {Game.game = false; Game.resetFlag = false; Game.playing =false;});
        stage.show();
    }

    public static void Main(String[] args){
        launch(args);
    }

    public static void SetScoreText(String s){
        Platform.runLater(new Runnable(){
                        public void run(){
                            currentScore.setText("Current Score: " + s);
                            highScore.setText("High Score: " + TetrisScore.getHScore());
                        }
                    });
    }
}
