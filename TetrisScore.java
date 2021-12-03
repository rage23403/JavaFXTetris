
/**
 * Tracks the current score, high score, and whether there's a t-spin or not.
 * 
 * @author Circle Onyx
 * @version 1.1.5
 */
public class TetrisScore
{
    private static int cScore = 0;
    private static int hScore = 10000;
    public static boolean tSpinBonus = false;
    public static int scorePoints(int lines, int level){
        String s;
        int temp = 0;
        switch(lines){
            case 1: temp = 100; s = "Single"; break;
            case 2: temp = 250; s = "Double"; break;
            case 3: temp = 400; s = "Triple"; break;
            case 4: temp = 1000; s = "TETRIS!"; break;
            default: temp = 1; s = "FAKER!!"; break;
        }
        if(level <= 0){
            level = 1;
        }
        temp *= level * (tSpinBonus ? 2 : 1);
        cScore += temp;
        checkHScore();
        if(tSpinBonus){ 
            inputManager.SetBonusText("T Spin " + s);
        }
        else{
            inputManager.SetBonusText(s);
        }
        inputManager.SetScoreText(Integer.toString(cScore));
        if(cScore/level > 1000){level++;}
        return level;
    }
    public static void checkHScore(){
        if(cScore > hScore){
            hScore = cScore;
        }
    }
    public static void resetScore(){
        cScore = 0;
        inputManager.SetScoreText(Integer.toString(cScore));
    }
    public static int getCScore(){
        return cScore;
    }
    public static int getHScore(){
        return hScore;
    }
}
