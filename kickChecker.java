
/**
 * Checks the SRS movement using values from harddrop.com 
 * I piece SRS rotation is using normal values and not Akira.
 * 
 * @author Circle Onyx
 * @version 1.5
 */
public class kickChecker
{
    //LJZST piece SRS calculations
    private static final int[][] NORMALRIGHT = {{0,0},{-1,0},{-1,-1},{0,2},{-1,2}};
    private static final int[][] RIGHTNORMAL = {{0,0},{1,0},{1,1},{0,-2},{1,-2}};
    private static final int[][] RIGHTFLIPPED = {{0,0},{1,0},{1,1},{0,-2},{1,-2}};
    private static final int[][] FLIPPEDRIGHT = {{0,0},{-1,0},{-1,-1},{0,2},{-1,2}};
    private static final int[][] FLIPPEDLEFT = {{0,0},{1,0},{1,-1},{0,2},{1,2}};
    private static final int[][] LEFTFLIPPED = {{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}};
    private static final int[][] LEFTNORMAL = {{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}};
    private static final int[][] NORMALLEFT = {{0,0},{1,0},{1,-1},{0,2},{1,2}};
    
    //I piece SRS calculations
    private static final int[][] INORMALRIGHT = {{0,0},{-2,0},{1,0},{-2,1},{1,-2}};
    private static final int[][] IRIGHTNORMAL = {{0,0},{2,0},{-1,0},{2,-1},{-1,2}};
    private static final int[][] IRIGHTFLIPPED = {{0,0},{-1,0},{2,0},{-1,-2},{2,1}};
    private static final int[][] IFLIPPEDRIGHT = {{0,0},{1,0},{-2,0},{1,2},{-2,-1}};
    private static final int[][] IFLIPPEDLEFT = {{0,0},{2,0},{-1,0},{2,-1},{-1,2}};
    private static final int[][] ILEFTFLIPPED = {{0,0},{-2,0},{1,0},{-2,1},{1,-2}};
    private static final int[][] ILEFTNORMAL = {{0,0},{1,0},{-2,0},{1,2},{-2,-1}};
    private static final int[][] INORMALLEFT = {{0,0},{-1,0},{2,0},{-1,-2},{2,1}};

    /**
     * required input is a tetris piece that is being rotated.
     * 
     * uses the current & previous (or next and current) orientation 
     * of the piece to check the movement spots for the next orientation
     * 0,0 is the default rotation and doesn't move the piece around.
     * -X moves left, +X moves right
     * -Y moves up,   +Y moves down
     * if the method returns true, it will move the piece and rotate it accordingly.
     * if the method returns false, it will not move the piece and not rotate it.
       */
    public static boolean check(TetrisPiece t){
        if(t.IPiece){
            switch(t.prevOrient){
                case NORMAL:
                if(t.currentOrient == Orient.RIGHT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,INORMALRIGHT[i][0],INORMALRIGHT[i][1])){
                            t.x+= INORMALRIGHT[i][0];
                            t.y+= INORMALRIGHT[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.LEFT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,INORMALLEFT[i][0],INORMALLEFT[i][1])){
                            t.x+= INORMALLEFT[i][0];
                            t.y+= INORMALLEFT[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
                case RIGHT:
                if(t.currentOrient == Orient.NORMAL){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,IRIGHTNORMAL[i][0],IRIGHTNORMAL[i][1])){
                            t.x+= IRIGHTNORMAL[i][0];
                            t.y+= IRIGHTNORMAL[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.FLIPPED){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,IRIGHTFLIPPED[i][0],IRIGHTFLIPPED[i][1])){
                            t.x+= IRIGHTFLIPPED[i][0];
                            t.y+= IRIGHTFLIPPED[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
                case FLIPPED:
                if(t.currentOrient == Orient.RIGHT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,IFLIPPEDRIGHT[i][0],IFLIPPEDRIGHT[i][1])){
                            t.x+= IFLIPPEDRIGHT[i][0];
                            t.y+= IFLIPPEDRIGHT[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.LEFT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,IFLIPPEDLEFT[i][0],IFLIPPEDLEFT[i][1])){
                            t.x+= IFLIPPEDLEFT[i][0];
                            t.y+= IFLIPPEDLEFT[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
                case LEFT:
                if(t.currentOrient == Orient.FLIPPED){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,ILEFTFLIPPED[i][0],ILEFTFLIPPED[i][1])){
                            t.x+= ILEFTFLIPPED[i][0];
                            t.y+= ILEFTFLIPPED[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.NORMAL){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,ILEFTNORMAL[i][0],ILEFTNORMAL[i][1])){
                            t.x+= ILEFTNORMAL[i][0];
                            t.y+= ILEFTNORMAL[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
            }
        }else{
            switch(t.prevOrient){
                case NORMAL:
                if(t.currentOrient == Orient.RIGHT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,NORMALRIGHT[i][0],NORMALRIGHT[i][1])){
                            t.x+= NORMALRIGHT[i][0];
                            t.y+= NORMALRIGHT[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.LEFT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,NORMALLEFT[i][0],NORMALLEFT[i][1])){
                            t.x+= NORMALLEFT[i][0];
                            t.y+= NORMALLEFT[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
                case RIGHT:
                if(t.currentOrient == Orient.NORMAL){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,RIGHTNORMAL[i][0],RIGHTNORMAL[i][1])){
                            t.x+= RIGHTNORMAL[i][0];
                            t.y+= RIGHTNORMAL[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.FLIPPED){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,RIGHTFLIPPED[i][0],RIGHTFLIPPED[i][1])){
                            t.x+= RIGHTFLIPPED[i][0];
                            t.y+= RIGHTFLIPPED[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
                case FLIPPED:
                if(t.currentOrient == Orient.RIGHT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,FLIPPEDRIGHT[i][0],FLIPPEDRIGHT[i][1])){
                            t.x+= FLIPPEDRIGHT[i][0];
                            t.y+= FLIPPEDRIGHT[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.LEFT){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,FLIPPEDLEFT[i][0],FLIPPEDLEFT[i][1])){
                            t.x+= FLIPPEDLEFT[i][0];
                            t.y+= FLIPPEDLEFT[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
                case LEFT:
                if(t.currentOrient == Orient.FLIPPED){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,LEFTFLIPPED[i][0],LEFTFLIPPED[i][1])){
                            t.x+= LEFTFLIPPED[i][0];
                            t.y+= LEFTFLIPPED[i][1];
                            return true;
                        }
                    }
                }else if(t.currentOrient == Orient.NORMAL){
                    for(int i = 0; i < 5; i++){
                        if(Game.playArea.IsValidMove(t,LEFTNORMAL[i][0],LEFTNORMAL[i][1])){
                            t.x+= LEFTNORMAL[i][0];
                            t.y+= LEFTNORMAL[i][1];
                            return true;
                        }
                    }
                }else{
                    return false;
                }
                break;
            }
        }
        return false;
    }
}
