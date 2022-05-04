package application;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Contains all of the code for the UI, Painting and collecting the user's inputs.
 * 
 * @author Circle Onyx
 * @version 1.5.1
 */
public class inputManager extends Application
{
    public static final int BrushSize = 10;
    static int tCanWidthNext = BrushSize*(Game.getColNext());
    static int tCanWidth = BrushSize*(Game.getCol());
    static int tCanHeight = BrushSize*(Game.getRow());

    public static Canvas tetrisCan;
    public static Canvas HoldCan;
    public static GraphicsContext g;
    public static GraphicsContext gHold;
    public static Label currentScore;
    public static Label highScore;
    public static Label bonusText;
    public static Label levelText;
    public static boolean b = false;
    @Override
    public void start(Stage stage)
    {
        GridPane pane = new GridPane();
        try{Game.Load();}catch(Exception e){
            Canvas except = new Canvas(200,200);
            GraphicsContext gexcept = except.getGraphicsContext2D();
            gexcept.setFill(Color.BLACK);
            gexcept.fillRect(0,0,200,200);
            gexcept.setFill(Color.RED);
            gexcept.fillText("LOADING ERROR",50,50);
            pane.add(except,0,0);
        }
        Scene scene = new Scene(pane, 600,400);
        stage.setTitle("INPUT READER");
        stage.setScene(scene);
        TetrisScore.loadScore();
        HoldCan = new Canvas(8*BrushSize,tCanHeight);
        tetrisCan = new Canvas(tCanWidthNext,tCanHeight);
        g = tetrisCan.getGraphicsContext2D();
        gHold = HoldCan.getGraphicsContext2D();
        currentScore = new Label("Current Score: " + TetrisScore.getCScore());
        highScore = new Label("High Score: " + TetrisScore.getHScore());
        levelText = new Label("Level " + 1);
        bonusText = new Label();
        bonusText.setWrapText(true);
        Button reset = new Button("Reset");
        Button pause = new Button("Pause");
        Button remNext = new Button("Next Piece: ON");
        reset.setFocusTraversable(false);
        pause.setFocusTraversable(false);
        remNext.setFocusTraversable(false);
        pane.add(currentScore,2,2);
        pane.add(highScore,2,3);
        pane.add(levelText,2,4);
        pane.add(bonusText,2,5);
        pane.add(HoldCan,1,1);
        pane.add(tetrisCan,2,1);
        pane.add(reset,3,1);
        pane.add(pause,4,1);
        pane.add(remNext,5,1);
        Game.gameThread.start();

        scene.setOnKeyPressed(event -> {
                switch(event.getCode()){
                    case UP: Game.upBtn = true;break;
                    case RIGHT: Game.rightBtn = true;break;
                    case LEFT: Game.leftBtn = true;break;
                    case DOWN: Game.downBtn = true;break;
                    case X: Game.rorBtn = true;break;
                    case Z: Game.rolBtn = true;break;
                    case ESCAPE: Pause(Game.paused);break;
                    case A: Game.holdBtn = true;break;
				default:
					break;
                }
            });
        scene.setOnKeyReleased(event -> {
                switch(event.getCode()){
                    case UP: Game.upBtn = false;break;
                    case RIGHT: Game.rightBtn = false;break;
                    case LEFT: Game.leftBtn = false;break;
                    case DOWN: Game.downBtn = false;break;
                    case X: Game.rorBtn = false;break;
                    case Z: Game.rolBtn = false;break;
                    case A: Game.holdBtn = false;break;
				default:
					break;
                } 
                Game.held = false;
            });
        reset.setOnAction(event -> {
                if(Game.game)
                    Game.game = false;
                else{
                    Game.game = true;
                    Game.paused = true;
                    Pause(Game.paused);
                }
            });
        pause.setOnAction(event->{
                if(Game.game)
                    Pause(Game.paused);
            });
        remNext.setOnAction(event->{
                g.setFill(Color.BLACK);
                if(Game.EditNext()){
                    Game.playArea.PrintField(Game.current[Game.curIndex]);
                    remNext.setText("NextPiece: ON");
                }
                else{
                    remNext.setText("NextPiece: OFF");
                    g.fillRect(tCanWidth,0,tCanWidthNext-tCanWidth,tCanHeight);
                }
            });

        // Show the Stage (window)
        stage.setOnCloseRequest(event -> {Game.game = false; Game.resetFlag = false; Game.playing =false; System.exit(0);});
        stage.show();
    }

    @SuppressWarnings("removal")
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

    public static void PaintHoldCan(String s){
        Platform.runLater(new Runnable(){
                public void run(){
                    int x = 0;
                    int y = 0;
                    for(int i = 0; i < s.length(); i++){
                        switch(s.charAt(i)){
                            case ',':gHold.setFill(Color.WHITE);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'I':gHold.setFill(Color.BLACK);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'X':gHold.setFill(Color.BLACK);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'J':gHold.setFill(Color.BLUE);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'L':gHold.setFill(Color.ORANGE);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'O':gHold.setFill(Color.YELLOW);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'T':gHold.setFill(Color.PURPLE);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'Z':gHold.setFill(Color.RED);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'S':gHold.setFill(Color.GREEN);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                            case '\n':y+=BrushSize; x = -BrushSize;break;
                            default:gHold.setFill(Color.WHITE);gHold.fillRect(x,y,BrushSize,BrushSize);break;
                        }
                        x+=BrushSize;
                    }
                }});
    }

    public static void PaintCanvas(String s){
        Platform.runLater(new Runnable(){
                public void run(){
                    int x = 0;
                    int y = 0;
                    for(int i = 0; i < s.length(); i++){
                        switch(s.charAt(i)){
                            case ',':g.setFill(Color.WHITE);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'I':g.setFill(Color.BLACK);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'X':g.setFill(Color.BLACK);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'J':g.setFill(Color.BLUE);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'L':g.setFill(Color.ORANGE);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'O':g.setFill(Color.YELLOW);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'T':g.setFill(Color.PURPLE);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'Z':g.setFill(Color.RED);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case 'S':g.setFill(Color.GREEN);g.fillRect(x,y,BrushSize,BrushSize);break;
                            case '\n':y+=BrushSize; x = -BrushSize;break;
                            default:g.setFill(Color.WHITE);g.fillRect(x,y,BrushSize,BrushSize);
                        }
                        x+=BrushSize;
                    }
                }});
    }
}
