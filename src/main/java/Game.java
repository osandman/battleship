public class Game {
    private String finishString;

    //игра продолжается пока не подбиты все корабли или не нажата q
    public void startPlaying(Player player1, Player player2) {
        System.out.println("Начинаем игру (\"q\" - для выхода)");
        finishString = "Игра окончена, все корабли потоплены!";
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
                    finishGame("Игрок " + currentPlayer.name + " прервал игру", player1, player2);
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
            System.out.println("Итого " + player.name + ":");
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

