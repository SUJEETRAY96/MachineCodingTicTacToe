package org.example;

import org.example.controllers.GameController;
import org.example.models.*;
import org.example.strategies.winningStrategies.OrderOneColumnWinningStrategy;
import org.example.strategies.winningStrategies.OrderOneDiagonalWinningStrategy;
import org.example.strategies.winningStrategies.OrderOneRowWinningStrategy;

import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {

        GameController gameController = new GameController();
        List<Player> players = List.of(new Player(new Symbol('A'),"ram", PlayerType.HUMAN),
                new Bot(new Symbol('B'),"shyam",BotDifficultyLevel.EASY));
        Game game = gameController.createGame(3,players,
                List.of(new OrderOneRowWinningStrategy(3,players),
                        new OrderOneColumnWinningStrategy(3,players),
                        new OrderOneDiagonalWinningStrategy(players)));
        Scanner sc = new Scanner(System.in);
        while(game.getGameStatus().equals(GameStatus.INPROGRESS)){
            gameController.displayBoard(game);
            System.out.println("Do you want to undo? y/n");
            String input  = sc.next();
            if(input.equalsIgnoreCase("Y")){
                gameController.undo(game);
            }else{
                gameController.makeMove(game);
            }
        }
        gameController.printResult(game);
    }
}
