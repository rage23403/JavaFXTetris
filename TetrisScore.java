
/**
 * Write a description of class TetrisScore here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TetrisScore
{
    private static int cScore = 0;
    private static int hScore = 10000;
    public static boolean tSpinBonus = false;
    public static void scorePoints(int lines, int level){
        int temp = 0;
        switch(lines){
            case 1: temp = 100; break;
            case 2: temp = 250; break;
            case 3: temp = 400; break;
            case 4: temp = 1000; break;
            default: temp = 1; break;
        }
        if(level < 0){
            level = 1;
        }
        temp *= level;
        cScore += temp;
        checkHScore();
        inputManager.SetScoreText(Integer.toString(cScore));
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
