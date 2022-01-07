import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
/**
 * Class containing game loop
 *
 * @author Circle Onyx
 * @version 1.2.5
 */
public class Game
{
    static Random ran = new Random();
    static int curIndex = 0;
    static TetrisField playArea;
    static TetrisPiece[] next = new TetrisPiece[7];
    static TetrisPiece[] current = new TetrisPiece[7];
    static TetrisPiece holdPiece = null;
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
    static final int TetrisCol = 10;
    static final int TetrisRow = 12;
    static final int NextCol = NEXT[0].length();
    static Insets TetrisBorder = new Insets(3);

    static boolean game = true;
    static boolean paused = false;
    static boolean playing = true;
    static boolean resetFlag = false;

    static long cycleTime;
    static long start;

    static boolean rightBtn = false;
    static boolean downBtn = false;
    static boolean leftBtn = false;
    static boolean rorBtn = false;
    static boolean rolBtn = false;
    static boolean holdBtn = false;
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
        holdPiece = null;
        boolean combo = false;
        curIndex = 0;
        int level = 1;
        TetrisScore.resetScore();
        playArea = new TetrisField(TetrisRow, TetrisCol, TetrisBorder, NEXT);
        GenerateNext();
        start = System.currentTimeMillis();
        GenerateNext();
        playArea.addNext(NEXT, current[1]);
        while(game){

            if(cycleTime > 50000/level){
                start = System.currentTimeMillis();
                cycleTime = 0;
                if(!MoveDown(current[curIndex])){current[curIndex] = null;}
            }
            if(current[curIndex] != null){
                if(rightBtn && !held){
                    MoveRight(current[curIndex]);
                    held = true;
                }else if(leftBtn && !held){
                    MoveLeft(current[curIndex]);
                    held = true;
                } else if(downBtn && !held){
                    start = System.currentTimeMillis();
                    cycleTime = 0;
                    if(!MoveDown(current[curIndex])){current[curIndex] = null;}
                    held = true;
                } else if(rorBtn  && !held){
                    ROR(current[curIndex]);
                    held = true;
                } else if(rolBtn  && !held){
                    ROL(current[curIndex]);
                    held = true;
                } else if(holdBtn && !held){
                    Hold();
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
                if(curIndex < 6){
                    curIndex++;
                    playArea.addNext(NEXT, (curIndex == 6) ? next[0] : current[curIndex+1]);
                }
                else{
                    curIndex = 0;
                    GenerateNext();
                    playArea.addNext(NEXT, current[curIndex+1]);
                }
                if(!playArea.IsValidMove((curIndex == 6) ? next[0] : current[curIndex+1],0,0)){
                    game = false;
                }
            }
            playArea.PrintField(current[curIndex]);
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
    
    public static void Hold(){
        if(holdPiece == null){
            holdPiece = current[curIndex];
            current[curIndex] = null;
            playArea.addHold(NEXT, holdPiece);
        }
        else if(playArea.IsValidMove(holdPiece,0,0)){
            TetrisPiece temp = new TetrisPiece(current[curIndex].piece, current[curIndex].TPiece);
            current[curIndex] = holdPiece;
            temp.x = TetrisCol/2;
            temp.y = 0;
            holdPiece = temp;
            playArea.addHold(NEXT, holdPiece);
        }
    }

    public static TetrisPiece GeneratePiece(String[] s){
        TetrisPiece piece = new TetrisPiece(s);
        return piece;
    }

    public static void GenerateNext(){
        if(next[0] == null){}
        else{
            for(int i = 0; i < current.length; i++){
                current[i] = new TetrisPiece(next[i].piece, next[i].TPiece);
                current[i].x = TetrisCol/2;
                current[i].y = 0;
            }
        }
        Integer[] temp = {0,1,2,3,4,5,6};
        List<Integer> tempL = Arrays.asList(temp);
        Collections.shuffle(tempL);
        tempL.toArray(temp); 
        for(int i = 0; i < next.length; i++){
            switch(temp[i]){
                case 0:next[i] = GeneratePiece(O);break;
                case 1:next[i] = GeneratePiece(T);break;
                case 2:next[i] = GeneratePiece(I);break;
                case 3:next[i] = GeneratePiece(J);break;
                case 4:next[i] = GeneratePiece(L);break;
                case 5:next[i] = GeneratePiece(S);break;
                case 6:next[i] = GeneratePiece(Z);break;
                default:next[i] = GeneratePiece(O);break;
            }
        }      
        next[0].x = TetrisCol/2;
        next[0].y = 0;
    }

    public static int getColNext(){
        int tCanWidth = 2+TetrisCol+NextCol+TetrisBorder.getLeft()+TetrisBorder.getRight();
        return tCanWidth;
    }

    public static int getCol(){
        int tCanWidth = 2+TetrisCol+TetrisBorder.getLeft()+TetrisBorder.getRight();
        return tCanWidth;
    }

    public static int getRow(){
        int tCanHeight = 1+TetrisRow+TetrisBorder.getTop()+TetrisBorder.getBottom();
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
    static char[][] Tc = {
            {' ', ' ', ' '},
            {'X', 'X', 'X'},
            {' ', 'X', ' '}};
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
