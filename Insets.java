
/**
 * Write a description of class Insets here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Insets
{
    private int topI, bottomI, leftI, rightI;
    public Insets(int top, int left){
        topI = top;
        leftI = left;
        bottomI = 0;
        rightI = 0;
    }
    public Insets(int top, int bottom, int left, int right){
        topI = top;
        bottomI = bottom;
        leftI = left;
        rightI = right;
    }
    public Insets(int border){
        topI = border;
        bottomI = border;
        leftI = border;
        rightI = border;
    }
    
    int getTop(){
        return topI;
    }
    int getBottom(){
        return bottomI;
    }
    int getLeft(){
        return leftI;
    }
    int getRight(){
        return rightI;
    }
}
