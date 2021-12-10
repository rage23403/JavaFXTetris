
/**
 * contains the attributes of a tetris piece and its movement related methods
 * 
 * @author Circle Onyx
 * @version 1.2
 */
public class TetrisPiece
{
    char[][] piece;
    boolean TPiece = false;
    int x, y;
    public TetrisPiece(String[] pieceLayout)
    {
        piece = new char[pieceLayout.length][pieceLayout[0].length()];
        for(int i = 0; i < pieceLayout.length; i++){
            for(int j = 0; j < pieceLayout[i].length(); j++){
                piece[i][j] = pieceLayout[i].charAt(j);
            }
        }
        if(pieceLayout == Game.T){
            TPiece = true;
        }
    }

    public TetrisPiece(char[][] pieceLayout)
    {
        piece = new char[pieceLayout.length][pieceLayout[0].length];
        for(int i = 0; i < pieceLayout.length; i++){
            for(int j = 0; j < pieceLayout[i].length; j++){
                piece[i][j] = pieceLayout[i][j];
            }
        }
    }

    public void moveDown(){y++;}

    public void moveLeft(){x--;}

    public void moveRight(){x++;}

    public void ROL(){
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

    public void ROR(){
        char[][] temp = new char[piece.length][piece[0].length];
        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                temp[i][j] = piece[j][temp.length-i-1];
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

    public boolean equals(TetrisPiece t){
        if(piece == t.piece){return true;}
        return false;
    }

    public int size(){return piece.length;}
}
