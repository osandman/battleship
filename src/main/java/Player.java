import java.util.List;
import java.util.Map;

public class Player {
    String name;
    int countOfGuess; //количество попыток выстрелов
    int countOfHitsAll; //количество попаданий по разным кораблям
    int countOfCells; //количество ячеек которые занимают корабли
    boolean currentShipIsAlive = false;
    Board board; //поле которое отображается в процессе игры
    Board testBoard; //игровое поле на котором видны все корабли
    List<Ship> ships;

    public Player(ShipFiles fname, String name) {
        this.name = name;
        CreateShips createShips = new CreateShips(fname);
        ships = createShips.getNewShips();
        countOfCells = createShips.countOfShipCells;
        testBoard = new Board("Test " + name);
        testBoard.setShipsOnBoard(ships);
        board = new Board(name);
    }
    public String guessFromAnotherPlayer(Player anotherPlayer) {
        String playerGuess = Features.getUserInput("Ход " + anotherPlayer.name + ". Введите координату выстрела:");
        if (playerGuess.equals("q")) {
            return playerGuess;
        }
        BoardCoords coords = new BoardCoords(playerGuess);
        boolean isHit = checkUserGuess(coords, anotherPlayer);
        System.out.printf("После выстрела %s: попадание = %b, подбит корабль = %b, " +
                        "всего выстрелов = %d, попаданий = %d, всего ячеек кораблей = %d",
                anotherPlayer.name, isHit, !currentShipIsAlive, anotherPlayer.countOfGuess, anotherPlayer.countOfHitsAll, countOfCells);
        if (!isHit) {
            board.setCell(coords.y, coords.x, Board.blankCell); // в случае промаха ставим "молоко"
            return "false";
        }
        return "true";
    }
//todo все действия и переменные на другого игрока
    private boolean checkUserGuess(BoardCoords coords, Player anotherPlayer) {
        anotherPlayer.countOfGuess++;
        String guessStr = String.valueOf(coords.y).concat(String.valueOf(coords.x));
        //System.out.println(guessStr);
        currentShipIsAlive = true;
        for (Ship ship : ships) {
            for (Map.Entry<String, String> entry : ship.getCells().entrySet()) {
                if (guessStr.equals(entry.getKey())) {
                    if (ship.setHitCell(guessStr, Ship.hitCell)) {
                        board.setCell(coords.y, coords.x, ship.getCell(guessStr));
                        anotherPlayer.countOfHitsAll++;
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

    /*
Класс задает координаты ячейки поля в виде индексов массива x - номер строки, y - номер столбца
Входной параметр конструктора - строка координата ячейки вида "A5" в соответствие с отображением на экране
 */
    class BoardCoords {
        private int x; //координата x - номер столбца в массиве
        private int y; //координата y - номер строки в массиве
        private String strCoords;
        public BoardCoords(String strCoords) {
            this.strCoords = strCoords;
            this.x = Board.ALPHABET.indexOf(strCoords.substring(0, 1));
            this.y = Integer.parseInt(strCoords.substring(1)) - 1;
        }
    }
}
