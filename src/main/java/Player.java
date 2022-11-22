import enums.ReturnStr;
import enums.InputVar;
import enums.MyFiles;

import java.util.List;
import java.util.Map;

public abstract class Player {
    private final String name;

    public String getName() {
        return name;
    }

    int countOfGuess; //количество попыток выстрелов
    int countOfHitsAll; //количество попаданий по разным кораблям
    int countOfCells; //количество ячеек которые занимают корабли
    boolean currentShipIsAlive = false;
    Board board; //поле которое отображается в процессе игры
    Board testBoard; //игровое поле на котором видны все корабли
    List<Ship> ships;

    public Player(MyFiles fileName, String name) {
        this.name = name;
        CreateShips createShips = new CreateShips(fileName);
        ships = createShips.getNewShips();
        countOfCells = createShips.getCountOfShipCells();
        testBoard = new Board("Test " + name);
        testBoard.trySetAllShipsOnBoard(ships);
        board = new Board(name);
    }

    public ReturnStr guessFromAnotherPlayer(Player anotherPlayer) {
        String playerGuess = Features.getUserInput("Ходит " + anotherPlayer.name +
                ", введите координату выстрела в формате \"A1\" (или \"q\" для выхода):", InputVar.HIT_GUESS);

        if (playerGuess.equals(ReturnStr.QUIT.toString())){
            return ReturnStr.QUIT;
        }
        if (playerGuess.equals(ReturnStr.SHOW.toString())){
            return ReturnStr.SHOW;
        }
        BoardCoords coords = new BoardCoords(playerGuess);
        boolean isHit = checkPlayerGuess(coords, anotherPlayer);
        System.out.printf("После выстрела %s: попадание = %b, подбит корабль = %b, " +
                        "всего выстрелов = %d, попаданий = %d, всего ячеек кораблей = %d",
                anotherPlayer.name, isHit, !currentShipIsAlive, anotherPlayer.countOfGuess, anotherPlayer.countOfHitsAll, countOfCells);
        if (!isHit) {
            board.setCell(coords.y, coords.x, Board.CLEAN_CELL); // в случае промаха ставим "молоко"
            return ReturnStr.FALSE;
        }
        return ReturnStr.TRUE;
    }

    //todo все действия и переменные на другого игрока
    private boolean checkPlayerGuess(BoardCoords coords, Player anotherPlayer) {
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

    /**
     * Класс задает координаты ячейки поля в виде индексов массива x - номер строки, y - номер столбца
     * Входной параметр конструктора - строка координата ячейки вида "A5" в соответствие с отображением на экране
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
}
