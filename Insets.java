package application;
/**
 * Contains 4 private variables to help signify the border of something
 * inspired by JavaFX Insets
 * 
 * @author Circle Onyx
 * @version 1.0
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
