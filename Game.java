import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.Event;
/**
 * Class containing game loop
 *
 * @author Circle Onyx
 * @version 1.2
 */
public class Game
{
    static Random ran = new Random();
    static TetrisField playArea;
    static TetrisPiece next;
    static TetrisPiece current;
    static String[] NEXT = {
            "XXXXXXXX",
            "X      X",
            "X      X",
            "X      X",
            "X      X",
            "X      X",
            "X      X",
            "XXXXXXXX"
        };
    static final int TetrisCol = 12;
    static final int TetrisRow = 10;
    static final int NextCol = NEXT[0].length();
    static Insets TetrisBorder = new Insets(3);
    
    static boolean game = true;
    static boolean paused = false;
    static boolean playing = true;
    static boolean resetFlag = false;
    
    static long cycleTime;
    static long start;
    
    static boolean right = false;
    static boolean down = false;
    static boolean left = false;
    static boolean ror = false;
    static boolean rol = false;
    static boolean held = false;
    
    static Thread gameThread = new Thread(){
            public void run(){
                while(playing){
                    GameLoop();
                }
                /**
                playArea = new TetrisField(12, 10, new Insets(3), NEXT);
                TetrisPiece current = GeneratePiece(T);
                current.x = playArea.columns/2;
                current.y = 0;
                //start = System.currentTimeMillis();
                GenerateNext();
                playArea.PrintField(current);
                 */
            }
        };
        
    public static void Garbo(){System.gc();}

    public static void GameLoop(){
        boolean combo = false;
        int level = 1;
        TetrisScore.resetScore();
        playArea = new TetrisField(TetrisCol, TetrisRow, TetrisBorder, NEXT);
        GenerateNext();
        current = next;
        current.x = playArea.columns/2;
        current.y = 0;
        start = System.currentTimeMillis();
        GenerateNext();
        while(game){

            if(cycleTime > 50000/level){
                start = System.currentTimeMillis();
                cycleTime = 0;
                if(!MoveDown(current)){current = null;}
            }
            if(current != null){
                if(right && !held){
                    MoveRight(current);
                    held = true;
                }else if(left && !held){
                    MoveLeft(current);
                    held = true;
                } else if(down && !held){
                    start = System.currentTimeMillis();
                    cycleTime = 0;
                    if(!MoveDown(current)){current = null;}
                    held = true;
                } else if(ror && !held){
                    ROR(current);
                    held = true;
                } else if(rol && !held){
                    ROL(current);
                    held = true;
                }
            }
            else{
                int i = playArea.checkLines();
                if(i > 0){
                    playArea.BreakLines(i);
                    if(level < TetrisScore.scorePoints(i,level,combo)){
                        level++;
                        inputManager.SetLevelText(Integer.toString(level));
                    }
                    combo = true;
                }
                else{inputManager.SetBonusText(""); combo = false;}
                if(!playArea.IsValidMove(next,0,0)){
                    game = false;
                }
                else{
                    current = next;
                    GenerateNext();
                }
            }
            playArea.PrintField(current);
            cycleTime += System.currentTimeMillis()-start;
        }
        inputManager.SetBonusText("GAME OVER!!");
        TetrisScore.saveScore();
        inputManager.Pause(paused);
    }

    public static boolean MoveDown(TetrisPiece t){
        if(t == null){return true;}
        if(!playArea.IsValidMove(t,0,1)){
            playArea.ConnectPiece(t);
            return false;
        }
        else{
            t.y+=1;
            return true;
        }
    }

    public static boolean MoveLeft(TetrisPiece t){
        if(!playArea.IsValidMove(t,-1,0)){
            return false;
        }
        else{
            t.x-=1;
            return true;
        }
    }

    public static boolean MoveRight(TetrisPiece t){
        if(!playArea.IsValidMove(t,1,0)){
            return false;
        }
        else{
            t.x+=1;
            return true;
        }
    }

    public static boolean ROL(TetrisPiece t){
        t.ROL();
        if(!playArea.IsValidMove(t,0,0)){
            t.ROR();
            TetrisScore.tSpinBonus = false;
            return false;
        }
        return true;
    }

    public static boolean ROR(TetrisPiece t){
        t.ROR();
        if(!playArea.IsValidMove(t,0,0)){
            t.ROL();
            TetrisScore.tSpinBonus = false;
            return false;
        }
        return true;
    }

    public static TetrisPiece GeneratePiece(String[] s){
        TetrisPiece current = new TetrisPiece(s);
        current.x = playArea.columns/2;
        current.y = 0;
        return current;
    }

    public static void GenerateNext(){
        int nextPiece = ran.nextInt(7);
        switch(nextPiece){
            case 0:next = GeneratePiece(O);break;
            case 1:next = GeneratePiece(T);break;
            case 2:next = GeneratePiece(I);break;
            case 3:next = GeneratePiece(J);break;
            case 4:next = GeneratePiece(L);break;
            case 5:next = GeneratePiece(S);break;
            case 6:next = GeneratePiece(Z);break;
            default:next = GeneratePiece(O);break;
        }
        playArea.addNext(NEXT,next);      
    }

    public static int getColNext(){
        int tCanWidth = TetrisCol+NextCol+TetrisBorder.getLeft()+TetrisBorder.getRight();
        return tCanWidth;
    }

    public static int getCol(){
        int tCanWidth = TetrisCol+TetrisBorder.getLeft()+TetrisBorder.getRight();
        return tCanWidth;
    }

    public static int getRow(){
        int tCanHeight = TetrisRow+TetrisBorder.getTop()+TetrisBorder.getBottom();
        return tCanHeight;
    }

    public static boolean EditNext(){
        playArea.nextOn = !playArea.nextOn;
        return playArea.nextOn;
    }
    static String[] T = {
            "   ",
            "XXX",
            " X "};
    static String[] J = {
            "   ",
            "X  ",
            "XXX"};
    static String[] L = {
            "   ",
            "  X",
            "XXX"};
    static String[] Z = {
            "   ",
            "XX ",
            " XX"};
    static String[] S = {
            "   ",
            " XX",
            "XX "};
    static String[] I = {
            "    ",
            "XXXX",
            "    ",
            "    "};
    static String[] O = {
            "XX",
            "XX"};

    static TetrisPiece TSpinCheck1 = new TetrisPiece(new String[]{
                "X  ",
                "   ",
                "   "});
    static TetrisPiece TSpinCheck2 = new TetrisPiece(new String[]{
                "   ",
                "   ",
                "  X"});
    static TetrisPiece TSpinCheck3 = new TetrisPiece(new String[]{
                "   ",
                "   ",
                "X  "});
    static TetrisPiece TSpinCheck4 = new TetrisPiece(new String[]{
                "  X",
                "   ",
                "   "});
}
