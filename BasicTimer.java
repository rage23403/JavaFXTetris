package application;
import javafx.animation.AnimationTimer;

/**
 * This is a simple file used to help make certain functions repeat
 * after a certain time interval in frames. handle is called once every frame.
 * if you have something check if Timing >= 5 it will repeat while the case is
 * true every 5 frames.
 * 
 * This helps implement the ability for holding down the "right" button to
 * keep moving the piece to the right rather than having to 
 * re-press the button multiple times to continue moving to the right.
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
