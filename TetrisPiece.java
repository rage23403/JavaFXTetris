
/**
 * Write a description of class TetrisPiece here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
        piece = temp;
    }

    public void ROR(){
        char[][] temp = new char[piece.length][piece[0].length];
        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                temp[i][j] = piece[j][temp.length-i-1];
            }
        }
        piece = temp;
    }

    public int size(){return piece.length;}
}
