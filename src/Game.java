import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class Game {
    public static int countOfGuess; //количество попыток выстрелов
    public static int countOfHitsAll; //количество попаданий по разным кораблям
    public static int countOfCells; //количество ячеек которые занимают корабли
    private boolean currentShipIsAlive = false;
    private String finishString;
    private final List<Ship> ships = new CreateShips().getNewShips();
    Board testBoard = new Board(); //игровое поле на котором видны все корабли
    Board board = new Board(); //поле которое отображается в процессе игры

    public static void main(String[] args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Game game = new Game();
        game.setShipsOnBoard();
        game.startPlaying();
        game.finishGame();
    }
//установка кораблей на игровое поле
    private void setShipsOnBoard() {
        // board.printBoard("Чистое поле"); //голое поле
        int countTry = 0;
        label:
        for (Ship ship : ships) {
            do {
                countTry++;
                /*if (countTry > 50) {
                    System.out.println("Расставляем заново, попыток: " + countTry);
                    countTry = 0;
                    testBoard.cleanBoard();
                    //ships = createShips.create();
                    continue label;
                }*/
                ship.setStartCell();
            } while (testBoard.getNeighbors(ship));
            //загружаем корабли на поле и передаем этому кораблю индексы нахождения на поле
            ship.setCells(testBoard.setShipOnBoard(ship));
            System.out.println(ship.getName() + " " + ship);
            countOfCells += ship.length;
        }
        System.out.println("Количество итераций расстановки кораблей = " + countTry);
        testBoard.printBoard("Корабли расставлены");
        //board.printBoard("Начальное поле");
    }

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
    private void startPlaying() {
        System.out.println("Начинаем игру (\"q\" - для выхода)");
        finishString = "Игра окончена, все корабли потоплены!";
        while (countOfHitsAll < countOfCells) {
            String userGuess = Features.getUserInput("Введите координату выстрела:");
            if (userGuess.equals("q")) {
                for (Ship ship : ships) {
                    board.setShipOnBoard(ship);
                }
                board.printBoard("Открыты все корабли");
                finishString = "Вы прервали игру";
                break;
            }
            BoardCoords coords = new BoardCoords(userGuess);
            boolean isHit = checkUserGuess(coords);
            if (!isHit) {
                board.setCell(coords.y, coords.x, Board.blankCell); // в случае промаха ставим "молоко"
            }
            board.printBoard(format("После выстрела: попадание = %b, подбит корабль = %b, " +
                            "всего выстрелов = %d, попаданий = %d, всего ячеек кораблей = %d",
                    isHit, !currentShipIsAlive, countOfGuess, countOfHitsAll, countOfCells));

        }
    }

    private boolean checkUserGuess(BoardCoords coords) {
        countOfGuess++;
        String guessStr = String.valueOf(coords.y).concat(String.valueOf(coords.x));
        //System.out.println(guessStr);
        currentShipIsAlive = true;
        for (Ship ship : ships) {
            for (Map.Entry<String, String> entry : ship.getCells().entrySet()) {
                if (guessStr.equals(entry.getKey())) {
                    if (ship.setHitCell(guessStr, Ship.hitCell)) {
                        board.setCell(coords.y, coords.x, ship.getCell(guessStr));
                        countOfHitsAll++;
                    }
                    currentShipIsAlive = ship.isAlive;
                    if (!ship.isAlive) {
                        board.setShipOnBoard(ship);
                        board.fillAreaCells(ship, ".", Ship.deadShipCell);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void finishGame() {
        System.out.println(finishString);
        System.out.printf("Количество выстрелов = %d, попаданий = %d\n", countOfGuess, countOfHitsAll);
        System.out.println("Корабли (всего " + ships.size() + "):");
        for (Ship ship : ships) {
            System.out.println(ship.getName() + " " + ship);
        }
    }
}

