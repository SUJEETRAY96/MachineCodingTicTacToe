package org.example.models;

import org.example.strategies.winningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private List<Move> moves;
    private Board board;
    private List<Player> players;
    private int currentMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;
    private GameStatus gameStatus;
    private Player winner;

    public static Builder getBuilder(){
        return new Builder();
    }

    private Game(int dimension, List<Player> players,List<WinningStrategy> winningStrategies){
        this.moves = new ArrayList<>();
        this.board = new Board(dimension);
        this.players=players;
        this.currentMovePlayerIndex=0;
        this.winningStrategies = winningStrategies;
        this.gameStatus = GameStatus.INPROGRESS;


    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentMovePlayerIndex() {
        return currentMovePlayerIndex;
    }

    public void setCurrentMovePlayerIndex(int currentMovePlayerIndex) {
        this.currentMovePlayerIndex = currentMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategyList() {
        return winningStrategies;
    }

    public void setWinningStrategyList(List<WinningStrategy> winningStrategyList) {
        this.winningStrategies = winningStrategyList;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void printBoard(){
        this.board.print();
    }


    public void printResult() {
        if(gameStatus.equals(GameStatus.ENDED)){
            System.out.println("Game has ended");
            System.out.println(winner.getName()+" is winner");
        }else{
            System.out.println("Game is draw");
        }
    }
    
    private boolean validateMove(Cell cell){
        int row = cell.getRow();
        int col = cell.getColumn();
        if(row<0||col<0||row >= board.getSize() || col>=board.getSize()){
            return false;
        }
        return board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY);
         
    }
    public void makeMove(){
        Player currentPlayer = players.get(currentMovePlayerIndex);
        System.out.println("It is "+currentPlayer.getName()+"'s turn.");
        Cell proposedCell = currentPlayer.makeMove(board);
        System.out.println("Move made at row : "+proposedCell.getRow()+" & col : "+proposedCell.getColumn());
        if(!validateMove(proposedCell)){
            System.out.println("Invalid Move, Please try again");
            return;
        }
        Cell cellInBoard = board.getBoard().get(proposedCell.getRow()).get(proposedCell.getColumn());
        cellInBoard.setCellState(CellState.FILLED);
        cellInBoard.setPlayer(currentPlayer);
        
        Move move = new Move(currentPlayer,cellInBoard);
        moves.add(move);

        if (checkGameWon(currentPlayer, move)) return;
        if(moves.size() == board.getSize()* board.getSize()){
            gameStatus = GameStatus.DRAW;
            return;
        }
        currentMovePlayerIndex +=1;
        currentMovePlayerIndex %= players.size();  
    }

    private boolean checkGameWon(Player currentPlayer, Move move) {
        for(WinningStrategy winningStrategy: winningStrategies){
            if(winningStrategy.checkWinner(board, move)){
                gameStatus = GameStatus.ENDED;
                winner= currentPlayer;
                return true;
            }
        }
        return false;
    }

    public void undo() {
        if(moves.size() ==0){
            System.out.println("No move, Can't undo");
            return;
        }
        Move lastMove = moves.get(moves.size()-1);
        for(WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(board,lastMove);
        }
        Cell cellInBoard = lastMove.getCell();
        cellInBoard.setCellState(CellState.EMPTY);
        cellInBoard.setPlayer(null);

        moves.remove(lastMove);


        currentMovePlayerIndex -=1;
        currentMovePlayerIndex += players.size();
        currentMovePlayerIndex %= players.size();
    }

    public static class Builder{
        private List<Player> players;
        private int dimension;
        private List<WinningStrategy> winningStrategyList;

        private Builder(){}

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setWinningStrategyList(List<WinningStrategy> winningStrategyList) {
            this.winningStrategyList = winningStrategyList;
            return this;
        }
        private boolean valid(){
            if(this.players.size()<2){
                return false;
            }
            if(this.players.size() != this.dimension-1){
                return false;
            }
            int botCount = 0;
            for(Player player:this.players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount += 1;
                }
            }
            if(botCount>=2){
                return false;
            }
            Set<Character> existingSymbols = new HashSet<>();
            for(Player player:this.players){
                if(existingSymbols.contains(player.getSymbol().getaChar())){
                    return false;
                }
                existingSymbols.add(player.getSymbol().getaChar());
            }

            return true;

        }
        public Game build(){
            if(!valid()){
                throw new RuntimeException("Invalid param for games");
            }else {
                return new Game(dimension,players,winningStrategyList);
            }
        }
    }
}
