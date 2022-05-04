package application;
/**
 * contains the attributes of a tetris piece and its movement related methods
 * 
 * @author Circle Onyx
 * @version 1.5
 */
public class TetrisPiece
{
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[i].length+1; j++){
                if(j == piece[i].length){b.append('\n');}else{b.append(piece[i][j]);}
            }
        }
        return b.toString();
    }
    Orient currentOrient;
    Orient prevOrient;
    char[][] piece;
    boolean TPiece = false;
    boolean IPiece = false;
    
    int x, y;
    public TetrisPiece(String[] pieceLayout)
    {
        currentOrient = Orient.NORMAL;
        piece = new char[pieceLayout.length][pieceLayout[0].length()];
        for(int i = 0; i < pieceLayout.length; i++){
            for(int j = 0; j < pieceLayout[i].length(); j++){
                piece[i][j] = pieceLayout[i].charAt(j);
            }
        }
        if(pieceLayout == Game.T){
            TPiece = true;
        }
        if(pieceLayout == Game.I){
            IPiece = true;
        }
    }

    public TetrisPiece(char[][] pieceLayout, boolean T, boolean I)
    {
        piece = new char[pieceLayout.length][pieceLayout[0].length];
        for(int i = 0; i < pieceLayout.length; i++){
            for(int j = 0; j < pieceLayout[i].length; j++){
                piece[i][j] = pieceLayout[i][j];
            }
        }
        if(T){
            TPiece = true;
        }
        if(I){
            IPiece = true;
        }
    }

    public void moveDown(){y++;}

    public void moveLeft(){x--;}

    public void moveRight(){x++;}

    public void ROR(){
        switch(currentOrient){
            case NORMAL:currentOrient = Orient.RIGHT;prevOrient = Orient.NORMAL;break;
            case LEFT:currentOrient = Orient.NORMAL;prevOrient = Orient.LEFT;break;
            case RIGHT:currentOrient = Orient.FLIPPED;prevOrient = Orient.RIGHT;break;
            case FLIPPED:currentOrient = Orient.LEFT;prevOrient = Orient.FLIPPED;break;
        }
        char[][] temp = new char[piece.length][piece[0].length];
        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                temp[i][j] = piece[temp.length-j-1][i];
            }
        }

        if(TPiece){
            int corners = 0;
            Game.TSpinCheck1.x = x;
            Game.TSpinCheck2.x = x;
            Game.TSpinCheck3.x = x;
            Game.TSpinCheck4.x = x;
            Game.TSpinCheck1.y = y;
            Game.TSpinCheck2.y = y;
            Game.TSpinCheck3.y = y;
            Game.TSpinCheck4.y = y;

            corners += Game.playArea.IsValidMove(Game.TSpinCheck1,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck2,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck3,0,0) ? 0 : 1;
            corners += Game.playArea.IsValidMove(Game.TSpinCheck4,0,0) ? 0 : 1;

            if(corners > 2){
                TetrisScore.tSpinBonus = true;
            }else{TetrisScore.tSpinBonus = false;}
        }
        else{TetrisScore.tSpinBonus = false;}
        piece = temp;
    }

    public void ROL(){
        switch(currentOrient){
            case NORMAL:currentOrient = Orient.RIGHT;prevOrient = Orient.NORMAL;break;
            case LEFT:currentOrient = Orient.NORMAL;prevOrient = Orient.LEFT;break;
            case RIGHT:currentOrient = Orient.FLIPPED;prevOrient = Orient.RIGHT;break;
            case FLIPPED:currentOrient = Orient.LEFT;prevOrient = Orient.FLIPPED;break;
        }
        char[][] temp = new char[piece.length][piece[0].length];
        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                temp[i][j] = piece[j][temp.length-i-1];
            }
        }
        piece = temp;
    }

    public boolean equals(TetrisPiece t){
        if(piece == t.piece){return true;}
        return false;
    }

    public int size(){return piece.length;}
}
/**
 * the orientation of the Tetris Piece. 
 * By default this should be NORMAL.
 * 
   */
enum Orient{
    NORMAL,
    LEFT,
    RIGHT,
    FLIPPED
}
