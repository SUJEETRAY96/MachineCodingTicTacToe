package org.example.strategies.winningStrategies;

import org.example.models.Board;
import org.example.models.Move;

public class OrderOneColumnWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Board board, Move move) {
        return false;
    }
}
