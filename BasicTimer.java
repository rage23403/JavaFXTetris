import javafx.animation.AnimationTimer;

/**
 * Write a description of class BasicTimer here.
 *
 * @author Circle Onyx
 * @version 1.0
 */
public class BasicTimer extends AnimationTimer
{
    public int Timing = 0;
    public void handle(long time){
        if(Timing < 16){
            Timing++;
        }
    }
}
