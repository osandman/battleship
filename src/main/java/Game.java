import enums.MyFiles;
import enums.ReturnStr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {
    private final String finishString = "Игра окончена, все корабли потоплены!";
    private Player player1;
    private Player player2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
//        Board.printBoards("Корабли успешно расставлены в автоматическом режиме", player1.testBoard, player2.testBoard);
        Board.printBoards("Корабли успешно расставлены в автоматическом режиме", player1.board, player2.board);
    }



    //игра продолжается пока не подбиты все корабли или не нажата q
    public void startPlaying() {
        //System.out.println("Начинаем игру (\"q\" - для выхода)");
        Player currentPlayer = player1;
        Player anotherPlayer = player2;
        while (player1.countOfHitsAll < player1.countOfCells || player2.countOfHitsAll < player2.countOfCells) {
            ReturnStr playerGuess = anotherPlayer.guessFromAnotherPlayer(currentPlayer);
            switch (playerGuess) {
                case QUIT: {
                    for (Ship ship : player1.ships) {
                        player1.board.setShipOnBoard(ship);
                    }
                    for (Ship ship : player2.ships) {
                        player2.board.setShipOnBoard(ship);
                    }
                    finishGame("Игрок " + currentPlayer.getName() + " прервал игру", player1, player2);
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
            Board.printBoards("", player1.board, player2.board);
        }
        finishGame(finishString, player1, player2);
    }

    private void finishGame(String finishString, Player... players) {
        System.out.println(finishString);
        Board.printBoards("Открыты все корабли", players[0].board, players[1].board);
        for (Player player : players) {
            System.out.println("Итого " + player.getName() + ":");
            System.out.printf("Количество выстрелов = %d, попаданий = %d\n", player.countOfGuess, player.countOfHitsAll);
            System.out.println("Корабли (всего " + player.ships.size() + "):");
            for (Ship ship : player.ships) {
                System.out.print(ship.getName() + " " + ship + ", ");
            }
            System.out.println();
        }
        File resFile = new File(MyFiles.RESULTS.toString());
        Date dateNow = new Date();
        SimpleDateFormat formatDateNow = new SimpleDateFormat(" dd.MM.yyyy (E)', время' HH.mm");
        try (PrintWriter printWriter = new PrintWriter(resFile)) {
            printWriter.println("Игра от " + formatDateNow.format(dateNow));
            printWriter.printf("Игрок %s - %d попаданий\n", player1.getName(), player1.countOfHitsAll);
            printWriter.printf("Игрок %s - %d попаданий", player2.getName(), player2.countOfHitsAll);
            System.out.println("Результаты сохранены в файл");
        } catch (IOException e) {
            System.out.println("не найден файл для записи");
        }

        System.exit(0);
    }
}

