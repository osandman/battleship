import enums.InputVar;
import enums.ShipFiles;

public class Game {
    private final String finishString = "Игра окончена, все корабли потоплены!";
    Player player1;
    Player player2;

    public Game() {
        player1 = new Player(ShipFiles.PLAYER1_SHIPS, getPlayersName(1));
        player2 = new Player(ShipFiles.PLAYER2_SHIPS, getPlayersName(2));
        Board.printBoards("Корабли успешно расставлены в автоматическом режиме", player1.testBoard, player2.testBoard);
    }

    private String getPlayersName(int playerNumber) {
        return Features.getUserInput("Введите имя игрока №" + playerNumber, InputVar.NAME_INPUT);
    }

    //игра продолжается пока не подбиты все корабли или не нажата q
    public void startPlaying() {
        //System.out.println("Начинаем игру (\"q\" - для выхода)");
        Player currentPlayer = player1;
        Player anotherPlayer = player2;
        while (player1.countOfHitsAll < player1.countOfCells || player2.countOfHitsAll < player2.countOfCells) {
            String playerGuess = anotherPlayer.guessFromAnotherPlayer(currentPlayer);
            Board.printBoards("", player1.board, player2.board);
            switch (playerGuess) {
                case ("q"): {
                    for (Ship ship : player1.ships) {
                        player1.board.setShipOnBoard(ship);
                    }
                    for (Ship ship : player2.ships) {
                        player2.board.setShipOnBoard(ship);
                    }
                    finishGame("Игрок " + currentPlayer.getName() + " прервал игру", player1, player2);
                }
                case ("false"): {
                    if (currentPlayer == player1) {
                        currentPlayer = player2;
                        anotherPlayer = player1;
                    } else {
                        currentPlayer = player1;
                        anotherPlayer = player2;
                    }
                    break;
                }
                case ("true"): {
                    break;
                }
            }
        }
        finishGame(finishString, player1, player2);
    }

    public void finishGame(String finishString, Player... players) {
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
        System.exit(0);
    }
}

