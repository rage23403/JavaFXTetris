
/**
 * Write a description of class TetrisPiece here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TetrisPiece
{
    char[][] piece;
    char pCharacter;
    int x, y;
    Orientation o;
    public TetrisPiece(String[] pieceLayout)
    {
        piece = new char[pieceLayout.length][pieceLayout[0].length()];
        for(int i = 0; i < pieceLayout.length; i++){
            for(int j = 0; j < pieceLayout[i].length(); j++){
                piece[i][j] = pieceLayout[i].charAt(j);
                if(piece[i][j] != ' '){pCharacter = piece[i][j];}
            }
        }
    }
    public TetrisPiece(char[][] pieceLayout)
    {
        piece = new char[pieceLayout.length][pieceLayout[0].length];
        for(int i = 0; i < pieceLayout.length; i++){
            for(int j = 0; j < pieceLayout[i].length; j++){
                piece[i][j] = pieceLayout[i][j];
                if(piece[i][j] != ' '){pCharacter = piece[i][j];}
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
        PrintPiece(temp);
        piece = temp;
    }

    public void ROR(){
        char[][] temp = new char[piece.length][piece[0].length];
        for(int i = 0; i < piece.length; i++){
            for(int j = 0; j < piece[0].length; j++){
                temp[i][j] = piece[j][temp.length-i-1];
            }
        }
        PrintPiece(temp);
        piece = temp;
    }

    public static void PrintPiece(char[][] p){
        for(int i = 0; i < p.length; i++){
            for(int j = 0; j < p[i].length; j++){
                if(p[i][j] != ' '){
                    System.out.print(p[i][j]);
                }
                else{System.out.print(',');}
            }
            System.out.println();
        }
    }

    public int size(){return piece.length;}
}
enum Orientation{
    UP,
    DOWN,
    LEFT,
    RIGHT
}
