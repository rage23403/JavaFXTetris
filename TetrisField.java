
import java.util.Arrays;
/**
 * Write a description of class TetrisField here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TetrisField
{
    char[][] field;
    char[][] nextPiece;
    char[] oneRow;
    int rows, columns;
    Insets fieldBorder;
    public TetrisField(int rows, int columns, Insets border, String[] next){
        this.rows = rows;
        this.columns = columns;
        fieldBorder = border;
        reset();
        addNext(next, null);
    }

    public void reset(){
        field = new char[rows+fieldBorder.getBottom()+fieldBorder.getTop()+1][columns+fieldBorder.getLeft()+fieldBorder.getRight()+3];
        for(int i = 0; i < fieldBorder.getTop(); i++){
            for(int j = 0; j < field[0].length-1; j++){
                field[i][j] = '□';
            }
            field[i][field[0].length-1] = '\n';
        }
        for(int i = fieldBorder.getTop(); i < field.length-fieldBorder.getBottom()-1; i++){
            for(int j = 0; j < fieldBorder.getLeft(); j++){
                field[i][j] = '□';
            }
            field[i][fieldBorder.getLeft()] = 'X';
            for(int j = fieldBorder.getLeft()+1; j < field[0].length-fieldBorder.getRight()-2; j++){
                field[i][j] = '□';
            }
            field[i][field[0].length-fieldBorder.getRight()-2] = 'X';
            for(int j = field[0].length-fieldBorder.getRight()-1; j < field[0].length-1; j++){
                field[i][j] = '□';
            }
            field[i][field[0].length-1] = '\n';
        }
        for(int j = 0; j < fieldBorder.getLeft(); j++){
            field[field.length-fieldBorder.getBottom()-1][j] = '□';
        }
        for(int j = fieldBorder.getLeft(); j < field[0].length-fieldBorder.getRight(); j++){
            field[field.length-fieldBorder.getBottom()-1][j] = 'X';
        }
        for(int j = field[0].length-fieldBorder.getRight()-1; j < field[0].length; j++){
            field[field.length-fieldBorder.getBottom()-1][j] = '□';
        }
        field[field.length-fieldBorder.getBottom()-1][field[0].length-1] = '\n';
        for(int i = field.length-fieldBorder.getBottom(); i < field.length; i++){
            for(int j = 0; j < field[0].length; j++){
                field[i][j] = '□';
            }
            field[i][field[0].length-1] = '\n';
        }
    }

    void addNext(String[] s, TetrisPiece t){
        nextPiece = new char[field.length][12];
        if(t == null){
            for(int i = 0; i < s.length; i++){
                for(int j = 0; j < s[i].length(); j++){
                    nextPiece[i][j] = s[i].charAt(j);
                }
            }
        }
        else{
            for(int i = 0; i < s.length; i++){
                for(int j = 0; j < s[i].length(); j++){
                    nextPiece[i][j] = s[i].charAt(j) == ' ' ? '□' : 'X';
                }
            }
            for(int i = 2; i < s.length-2; i++){
                for(int j = 2; j < s[i].length()-2; j++){
                    if(t.piece[i-2][j-2] != ' '){
                        nextPiece[i][j] = t.piece[i-2][j-2];
                    }
                }
            }
        }
    }

    void PrintField(TetrisPiece current){
        StringBuilder build = new StringBuilder();
        int tempy = 0;
        if(current == null){
            for(int i = 0; i < field.length; i++){
                for (int j = 0; j < field[i].length+nextPiece[i].length; j++){
                    if(j == field[i].length+nextPiece[i].length-1){}
                    else if(j >= field[i].length-1){
                        build.append(nextPiece[i][j-field[i].length+1]);
                    }
                    else{
                        build.append(field[i][j]);
                    }
                }
                build.append(field[i][field[i].length-1]);
            }
        }
        else{
            for(int i = 0; i < field.length; i++){
                if(i-fieldBorder.getTop() >= current.y && i-fieldBorder.getTop() < current.y+4){
                    tempy++;
                }
                else{tempy = 0;}
                for(int j = 0; j < field[i].length+nextPiece[i].length; j++){
                    if(j == field[i].length+nextPiece[i].length-1){}
                    else if(j >= field[i].length-1){
                        build.append(nextPiece[i][j-field[i].length+1]);
                    }
                    else if(j-fieldBorder.getLeft()-current.x == 0 && j-fieldBorder.getLeft()-current.x < 4 && tempy != 0 && tempy < 5){  
                        for(int PJ = 0; PJ <  current.piece[tempy-1].length; PJ++){
                            if(current.piece[tempy-1][PJ] == current.pCharacter){
                                build.append(current.piece[tempy-1][PJ]);
                            }
                            else{build.append(field[i][j]);}
                            j++;
                        } 
                        build.append(field[i][j]);
                    }
                    else{
                        build.append(field[i][j]);
                    }
                }
                build.append(field[i][field[i].length-1]);
            }
        }
        String s = "\n\n\n\n" + build.toString();
        System.out.println(s);
        try{Thread.sleep(1);}catch(Exception e){}
    }

    void ConnectPiece(TetrisPiece piece){
        for(int i = 0; i < piece.size(); i++){
            for(int j = 0; j < piece.size(); j++){
                if(piece.piece[i][j] == 'X'){
                    field[piece.y+fieldBorder.getLeft()+i][piece.x+fieldBorder.getTop()+j] = piece.piece[i][j];
                }
            }
        }
        PrintField(null);
        int x = 0;
    }

    int checkLines(){
        char[][] temp = new char[field.length][field[0].length];
        int lines = 0;
        int linecheck = 0;
        int count = 0;
        for(int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                if(field[i][j] == 'X' && i < field.length-fieldBorder.getBottom()-1){
                    count++;
                    if(count == 1){
                        temp[i][j] = 'X';
                    }
                    else if (1 < count && count < columns+2) {
                        temp[i][j] = '-';
                    }
                    if(count > columns+1){
                        temp[i][j] = 'X';
                        lines++;
                        count = 0;
                    }
                }
                else
                {
                    temp[i][j] = field[i][j];
                    count = 0;
                }
            }
            if(charArrContains(temp[i], '-')){
                linecheck++;
                if(lines != linecheck){
                    for(int j = 0; j < field[i].length; j++){
                        if(temp[i][j] == '-'){
                            temp[i][j] = 'X';
                        }
                    }
                    linecheck--;
                }
                else{
                }
            }
        }
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[i].length; j++){
                field[i][j] = temp[i][j];
            }
        }
        return lines;
    }

    void BreakLines(int lines){
        int linesDone = 0;
        for(int i = field.length-1-fieldBorder.getBottom(); i >= fieldBorder.getTop(); i--){
            if(charArrContains(field[i], '-')){
                linesDone++;
            }
            else{
                char[] temp = field[i + linesDone];
                field[i+linesDone] = field[i];
                field[i] = temp;
            }
        }
        for(int i = fieldBorder.getTop(); i < fieldBorder.getTop()+linesDone; i++){
            for(int j = 0; j < fieldBorder.getLeft(); j++){
                field[i][j] = '□';//fill border
            }
            field[i][fieldBorder.getLeft()] = 'X';
            for(int j = fieldBorder.getLeft()+1; j < field[0].length-fieldBorder.getRight()-2; j++){
                field[i][j] = '□';//fill columns
            }
            field[i][field[0].length-fieldBorder.getRight()-2] = 'X';
            for(int j = field[0].length-fieldBorder.getRight()-1; j < field[0].length-1; j++){
                field[i][j] = '□';//fill border
            }
            field[i][field[0].length-1] = '\n';
        }
        for(int i = 0; i < field.length; i++){for(int j = 0; j < field[i].length; j++){
                System.out.print(field[i][j]);
            }
        }
    }

    boolean IsValidMove(TetrisPiece piece, int x, int y){
        if(piece == null){return true;}
        TetrisPiece p = new TetrisPiece(piece.piece);
        p.x = piece.x + x;
        p.y = piece.y + y;
        for(int i = 0; i < p.size(); i++){
            for(int j = 0; j < p.size(); j++){
                if(p.piece[i][j] != ' ' && field[p.y+fieldBorder.getLeft()+i][p.x+fieldBorder.getTop()+j] != '□'){
                    return false;
                }
            }
        }
        return true;
    }

    boolean charArrContains(char[] arr, char ch){
        for(char i : arr){
            if(i == ch){
                return true;
            }
        }
        return false;
    }
}
