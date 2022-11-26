import enums.ReturnStr;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Player player1;
    private final Player player2;
    List<Player> playerList = new ArrayList<>();


    public Game(Player player1, Player player2) {
        playerList.add(this.player1 = player1);
        playerList.add(this.player2 = player2);
    }

    //игра продолжается пока не подбиты все корабли или не нажата q
    public void startPlaying() {
        //System.out.println("Начинаем игру (\"q\" - для выхода)");
        Player currentPlayer = player1;
        Player anotherPlayer = player2;
        while (currentPlayer.countOfHitsAll < anotherPlayer.countOfCells) {
            currentPlayer.board.boardName = currentPlayer.getName();
            anotherPlayer.board.boardName = anotherPlayer.getName() + "  VVV";
            Board.printBoards("", player1.board, player2.board);
            ReturnStr playerGuess = anotherPlayer.guessFromAnotherPlayer(currentPlayer);
            switch (playerGuess) {
                case QUIT: {
                    finishGame("Игрок " + currentPlayer.getName() + " прервал игру");
                }
                case SHOW: {
                    Board.printBoards("Открыты все корабли", player1.testBoard, player2.testBoard);
                    break;
                }
                case FALSE: {
                    if (currentPlayer == player1) {
                        currentPlayer = player2;
                        anotherPlayer = player1;
                    } else {
                        currentPlayer = player1;
                        anotherPlayer = player2;
                    }
                    break;
                }
                case TRUE: {
                    break;
                }
            }
        }
        String finishString = "Игра окончена, корабли противника потоплены!";
        finishGame(finishString + " Победил - " + currentPlayer.getName());
    }

    private void finishGame(String finishString) {
        System.out.println(finishString);
        for (Player player : playerList) {
            for (Ship ship : player.ships) {
                player.board.setShipOnBoard(ship);
            }
        }
        Board.printBoards("Итоги боя", player1.board, player2.board);
        for (Player player : playerList) {
            System.out.println("Итого " + player.getName() + ":");
            System.out.printf("Количество попыток = %d, попаданий = %d\n", player.countOfGuess, player.countOfHitsAll);
            System.out.println("Корабли (всего " + player.ships.size() + "):");
            for (Ship ship : player.ships) {
                System.out.print(ship.getName() + " " + ship + ", ");
            }
            System.out.println();
        }
        Features.writeToFile(player1, player2);
        System.exit(0);
    }
}

