package org.example.strategies.winningStrategies;

import org.example.models.Board;
import org.example.models.Move;
import org.example.models.Player;
import org.example.models.Symbol;

import java.util.HashMap;
import java.util.List;

public class OrderOneDiagonalWinningStrategy implements WinningStrategy{
    private HashMap<Symbol,Integer> leftDiagonalMap;
    private HashMap<Symbol,Integer> rightDiagonalMap;

    public OrderOneDiagonalWinningStrategy( List<Player> players){
        leftDiagonalMap = new HashMap<>();
        rightDiagonalMap = new HashMap<>();
        for(Player player : players){
            leftDiagonalMap.put(player.getSymbol(),0);
            rightDiagonalMap.put(player.getSymbol(),0);
        }

    }
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        if(row==col){
            leftDiagonalMap.put(symbol,leftDiagonalMap.get(symbol)+1);
        }
        if((row+col)== board.getSize()-1){
            rightDiagonalMap.put(symbol,rightDiagonalMap.get(symbol)+1);
        }
        if(row==col){
            if(leftDiagonalMap.get(symbol)==board.getSize()){
                return true;
            }
        }
        if((row+col)== board.getSize()-1){
            if(rightDiagonalMap.get(symbol)==board.getSize()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleUndo(Board board ,Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();
        if(row==col){
            leftDiagonalMap.put(symbol,leftDiagonalMap.get(symbol)-1);
        }
        if((row+col)== board.getSize()-1){
            rightDiagonalMap.put(symbol,rightDiagonalMap.get(symbol)-1);
        }
    }
}
