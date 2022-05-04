package application;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
/**
 * Class containing game loop and some logic
 *
 * @author Circle Onyx
 * @version 1.5.1
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
    static final int TetrisRow = 20;
    static final int NextCol = NEXT[0].length();
    static Insets TetrisBorder = new Insets(3);

    static boolean game = true;
    static boolean paused = false;
    static boolean playing = true;
    static boolean resetFlag = false;

    static long cycleTime;
    static long start;
    static int level;

    static boolean upBtn = false;
    static boolean rightBtn = false;
    static boolean downBtn = false;
    static boolean leftBtn = false;
    static boolean rorBtn = false;
    static boolean rolBtn = false;
    static boolean holdBtn = false;
    static boolean held = false;
    static BasicTimer time;

    public static void Load(){
        holdPiece = null;
        curIndex = 0;
        level = 1;
        TetrisScore.resetScore();
        playArea = new TetrisField(TetrisRow, TetrisCol, TetrisBorder, NEXT);        
    }
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
        time = new BasicTimer();
        time.start();
        holdPiece = null;
        boolean combo = false;
        curIndex = 0;
        level = 1;
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
                if(time.Timing > 5){
                    if(rightBtn && (time.Timing > 10 || !held)){
                        MoveRight(current[curIndex]);
                        held = true;
                        time.Timing = 0;
                    }
                    if(leftBtn &&  (time.Timing > 10 || !held)){
                        MoveLeft(current[curIndex]);
                        held = true;
                        time.Timing = 0;
                    }
                    if(rorBtn && !held){
                        ROR(current[curIndex]);
                        held = true;
                    }
                    if(rolBtn && !held){
                        ROL(current[curIndex]);
                        held = true;
                    }
                    if(holdBtn && !held){
                        Hold();
                        held = true;
                    }
                    if(upBtn && !held){
                        held = true;
                        start = System.currentTimeMillis();
                        cycleTime = 0;
                        while(current[curIndex] != null){
                            if(!MoveDown(current[curIndex])){current[curIndex] = null;}
                        }
                        time.Timing = 0;
                    }
                    if(downBtn){
                        start = System.currentTimeMillis();
                        cycleTime = 0;
                        if(!MoveDown(current[curIndex])){current[curIndex] = null;}
                        time.Timing = 0;
                    }
                }
            }
            else{
                int i = playArea.checkLines();
                if(i > 0){
                    playArea.BreakLines(i);
                    TetrisScore.cleared = playArea.cleared();
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
        TetrisScore.stored = false;
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
        if(!kickChecker.check(t)){
            t.ROR();
            TetrisScore.tSpinBonus = false;
            TetrisScore.tMiniBonus = false;
            return false;
        }else if(t.TPiece){
            int corners = 0;
            Game.TSpinCheck1.x = t.x;
            Game.TSpinCheck2.x = t.x;
            Game.TSpinCheck3.x = t.x;
            Game.TSpinCheck4.x = t.x;
            Game.TSpinCheck1.y = t.y;
            Game.TSpinCheck2.y = t.y;
            Game.TSpinCheck3.y = t.y;
            Game.TSpinCheck4.y = t.y;

            corners += Game.playArea.IsValidMove(Game.TSpinCheck1,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck2,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck3,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck4,0,0) ? 0 : 1;

            if(corners > 2){
                TetrisScore.tSpinBonus = true;
            }else{TetrisScore.tSpinBonus = false;}
            if(kickChecker.SRS){
                TetrisScore.tMiniBonus = true;
            }else{TetrisScore.tMiniBonus = false;}
        }
        else{TetrisScore.tSpinBonus = false; TetrisScore.tMiniBonus = false;}
        return true;
    }

    public static boolean ROR(TetrisPiece t){
        t.ROR();
        if(!kickChecker.check(t)){
            t.ROL();
            TetrisScore.tSpinBonus = false;
            TetrisScore.tMiniBonus = false;
            return false;
        }else if(t.TPiece){
            int corners = 0;
            Game.TSpinCheck1.x = t.x;
            Game.TSpinCheck2.x = t.x;
            Game.TSpinCheck3.x = t.x;
            Game.TSpinCheck4.x = t.x;
            Game.TSpinCheck1.y = t.y;
            Game.TSpinCheck2.y = t.y;
            Game.TSpinCheck3.y = t.y;
            Game.TSpinCheck4.y = t.y;

            corners += Game.playArea.IsValidMove(Game.TSpinCheck1,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck2,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck3,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck4,0,0) ? 0 : 1;

            if(corners > 2){
                TetrisScore.tSpinBonus = true;
            }else{TetrisScore.tSpinBonus = false;}
            if(kickChecker.SRS){
                TetrisScore.tMiniBonus = true;
            }else{TetrisScore.tMiniBonus = false;}
        }
        else{TetrisScore.tSpinBonus = false; TetrisScore.tMiniBonus = false;}
        return true;
    }

    public static void Hold(){
        if(holdPiece == null){
            holdPiece = current[curIndex];
            holdPiece.x = TetrisCol/2;
            holdPiece.y = 0;
            current[curIndex] = null;
            playArea.addHold(NEXT, holdPiece);
        }
        else if(playArea.IsValidMove(holdPiece,0,0)){
            TetrisPiece temp = new TetrisPiece(current[curIndex].piece, current[curIndex].TPiece, current[curIndex].IPiece);
            temp.currentOrient = current[curIndex].currentOrient;
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
                current[i] = new TetrisPiece(next[i].piece, next[i].TPiece, next[i].IPiece);
                current[i].currentOrient = next[i].currentOrient;
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
            " T ",
            "TTT",
            "   "};
    static char[][] Tc = {
            {' ', 'T', ' '},
            {'T', 'T', 'T'},
            {' ', ' ', ' '}};
    static String[] J = {
            "J  ",
            "JJJ",
            "   "};
    static String[] L = {
            "  L",
            "LLL", 
            "   "};
    static String[] Z = {
            "ZZ ",
            " ZZ",
            "   "};
    static String[] S = {
            " SS",
            "SS ",
            "   "};
    static String[] I = {
            "    ",
            "IIII",
            "    ",
            "    "};
    static char[][] Ic = {
            {' ', ' ', ' ', ' '},
            {'I', 'I', 'I', 'I'},
            {' ', ' ', ' ', ' '}};
    static String[] O = {
            "OO",
            "OO"};

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
