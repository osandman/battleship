import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class Game {
    private String finishString;


    /*
    Класс задает координаты ячейки поля в виде индексов массива x - номер строки, y - номер столбца
    Входной параметр конструктора - строка координата ячейки вида "A5" в соответствие с отображением на экране
     */
    static class BoardCoords {
        private int x; //координата x - номер столбца в массиве
        private int y; //координата y - номер строки в массиве
        private String strCoords;
        public BoardCoords(String strCoords) {
            this.strCoords = strCoords;
            this.x = Board.ALPHABET.indexOf(strCoords.substring(0, 1));
            this.y = Integer.parseInt(strCoords.substring(1)) - 1;
        }
    }
//игра продолжается пока не подбиты все корабли или не нажата q
    public void startPlaying(Player player) {
        System.out.println("Начинаем игру (\"q\" - для выхода)");
        finishString = "Игра окончена, все корабли потоплены!";
        while (player.countOfHitsAll < player.countOfCells) {
            String userGuess = Features.getUserInput("Введите координату выстрела:");
            if (userGuess.equals("q")) {
                for (Ship ship : player.ships) {
                    player.board.setShipOnBoard(ship);
                }
                Board.printBoards("Открыты все корабли", player.board);
                finishString = "Вы прервали игру";
                break;
            }
            BoardCoords coords = new BoardCoords(userGuess);
            boolean isHit = checkUserGuess(player, coords);
            if (!isHit) {
                player.board.setCell(coords.y, coords.x, Board.blankCell); // в случае промаха ставим "молоко"
            }
            Board.printBoards(format("После выстрела: попадание = %b, подбит корабль = %b, " +
                            "всего выстрелов = %d, попаданий = %d, всего ячеек кораблей = %d",
                    isHit, !player.currentShipIsAlive, player.countOfGuess, player.countOfHitsAll, player.countOfCells), player.board);

        }
    }

    private boolean checkUserGuess(Player player, BoardCoords coords) {
        player.countOfGuess++;
        String guessStr = String.valueOf(coords.y).concat(String.valueOf(coords.x));
        //System.out.println(guessStr);
        player.currentShipIsAlive = true;
        for (Ship ship : player.ships) {
            for (Map.Entry<String, String> entry : ship.getCells().entrySet()) {
                if (guessStr.equals(entry.getKey())) {
                    if (ship.setHitCell(guessStr, Ship.hitCell)) {
                        player.board.setCell(coords.y, coords.x, ship.getCell(guessStr));
                        player.countOfHitsAll++;
                    }
                    player.currentShipIsAlive = ship.isAlive;
                    if (!ship.isAlive) {
                        player.board.setShipOnBoard(ship);
                        player.board.fillAreaCells(ship, ".", Ship.deadShipCell);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void finishGame(Player player) {
        System.out.println(finishString);
        System.out.printf("Количество выстрелов = %d, попаданий = %d\n", player.countOfGuess, player.countOfHitsAll);
        System.out.println("Корабли (всего " + player.ships.size() + "):");
        for (Ship ship : player.ships) {
            System.out.println(ship.getName() + " " + ship);
        }
    }
}

