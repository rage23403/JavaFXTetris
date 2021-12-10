import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static double comboBonus = 1.0;
    public static boolean tSpinBonus = false;
    public static int scorePoints(int lines, int level, boolean combo){
        if(combo){
            comboBonus+=0.125;
        }
        else{
            comboBonus = 1.0;
        }
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
        cScore += temp*comboBonus;
        checkHScore();
        if(combo){s += "\nCombo x" + ((comboBonus-1)*8);}
        if(tSpinBonus){ 
            inputManager.SetBonusText("T Spin " + s);
            tSpinBonus = false;
        }
        else{
            inputManager.SetBonusText(s);
        }
        inputManager.SetScoreText(Integer.toString(cScore));
        if(cScore/level > level*1000){level++;}
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

    public static void saveScore(){
        String text = "High Score: " + Integer.toString(hScore);
        byte sum = 0;
        for(int i = 0; i < text.length(); i++){
            sum += (byte)(text.charAt(i));
        }
        sum += (byte)text.length();
        int i = Byte.toUnsignedInt(sum);
        text += ""+(char)i;
        try{
            FileWriter f = new FileWriter("Score.dat");
            f.write(text);
            f.close();
        }catch(IOException e){}
    }

    public static boolean loadScore(){
        try{
            File file = new File("Score.dat");
            byte[] text = new byte[(int)file.length()];
            FileReader f = new FileReader(file);
            for(int i = 0; i < file.length(); i++){
                text[i] = (byte)f.read();
            }
            f.close();
            int z = Byte.toUnsignedInt(text[text.length-1]);
            int ez = Byte.toUnsignedInt(text[text.length-2]);
            int sum = 0;
            if(ez < 60){
                sum = 0;
                for(int i = 0; i < text.length-1; i++){
                    sum += text[i];
                }
                sum += (text.length-1);
                sum &= 0xFF;
                if(z == sum){
                    StringBuilder b = new StringBuilder();
                    for(int i = 0; i < text.length; i++){
                        b.append((char)text[i]);
                    }
                    String s = b.substring(12,text.length-1);
                    hScore = Integer.parseInt(s);
                    return true;
                }
                else{
                    System.out.println("you cheated not only the game but yourself\nYou didn't grow\nYou didn't improve\nYou took a shortcut and gained nothing!");
                    return false;
                }
            }
            else{
                sum = 0;
                for(int i = 0; i < text.length-2; i++){
                    sum += text[i];
                }
                sum += (text.length-2);
                sum &= 0xFF;
                if(ez == sum){
                    StringBuilder b = new StringBuilder();
                    for(int i = 0; i < text.length; i++){
                        b.append((char)text[i]);
                    }
                    String s = b.substring(12,text.length-2);
                    hScore = Integer.parseInt(s);
                    return true;
                }
                else{
                    System.out.println("you cheated not only the game but yourself\nYou didn't grow\nYou didn't improve\nYou took a shortcut and gained nothing!");
                    return false;
                }
            }
        }catch(IOException e){}
        return false;
    }
}
