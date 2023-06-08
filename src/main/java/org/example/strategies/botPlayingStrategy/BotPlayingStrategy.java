package org.example.strategies.botPlayingStrategy;

import org.example.models.Board;
import org.example.models.Cell;

public interface BotPlayingStrategy {
    Cell makeMove(Board board);
}
