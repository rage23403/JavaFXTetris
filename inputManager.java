
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Contains all of the code for the UI and collects user inputs.
 * 
 * @author Circle Onyx
 * @version 1.1.5
 */
public class inputManager extends Application
{
    public static Label currentScore;
    public static Label highScore;
    public static Label bonusText;
    public static Label levelText;
    public static boolean b = false;
    public static Button pause;
    @Override
    public void start(Stage stage)
    {
        GridPane pane = new GridPane();
        pane.setMinSize(300, 300);
        Scene scene = new Scene(pane, 300,600);
        stage.setTitle("INPUT READER");
        stage.setScene(scene);
        currentScore = new Label("Current Score: " + TetrisScore.getCScore());
        TetrisScore.loadScore();
        highScore = new Label("High Score: " + TetrisScore.getHScore());
        levelText = new Label("Level " + 1);
        bonusText = new Label();
        Button reset = new Button("Reset");
        pause = new Button("Pause");
        reset.setFocusTraversable(false);
        pause.setFocusTraversable(false);
        pane.add(currentScore,0,1);
        pane.add(highScore,0,2);
        pane.add(levelText,0,3);
        pane.add(bonusText,0,4);
        pane.add(reset,0,0);
        pane.add(pause,1,0);
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
        reset.setOnAction(event -> {
                Game.game = false;
                Game.paused = false;
            });
        pause.setOnAction(event->{
                Pause(Game.paused);
            });

        // Show the Stage (window)
        stage.setOnCloseRequest(event -> {Game.game = false; Game.resetFlag = false; Game.playing =false;});
        stage.show();
    }

    public static void Pause(boolean b){
        if(!b){
            Game.paused = true;
            Game.gameThread.suspend();
            Platform.runLater(new Runnable(){
                public void run()
                {
                    bonusText.setText("Paused");
                }
            });
        }
        else{
            Game.paused = false;
            Game.gameThread.resume();
            Platform.runLater(new Runnable(){
                public void run()
                {
                    bonusText.setText("");
                }
            });
        }
    }

    public static void Main(String[] args){
        launch(args);
    }

    public static void SetBonusText(String s){
        Platform.runLater(new Runnable(){
                public void run(){
                    bonusText.setText(s);
                }
            });
    }

    public static void SetScoreText(String s){
        Platform.runLater(new Runnable(){
                public void run(){
                    currentScore.setText("Current Score: " + s);
                    highScore.setText("High Score: " + TetrisScore.getHScore());
                }
            });
    }
    
    public static void SetLevelText(String s){
        Platform.runLater(new Runnable(){
                public void run(){
                    levelText.setText("Level " + s);
                }
            });        
    }
}
