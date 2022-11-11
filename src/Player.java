import java.util.List;

public class Player {
    String name;
    public int countOfGuess; //количество попыток выстрелов
    public int countOfHitsAll; //количество попаданий по разным кораблям
    public int countOfCells; //количество ячеек которые занимают корабли
    public boolean currentShipIsAlive = false;
    Board board; //поле которое отображается в процессе игры
    Board testBoard; //игровое поле на котором видны все корабли
    List<Ship> ships;

    public Player(shipFiles fname, String name) {
        this.name = name;
        CreateShips createShips = new CreateShips(fname);
        ships = createShips.getNewShips();
        countOfCells = createShips.countOfShipCells;
        testBoard = new Board();
        testBoard.setShipsOnBoard(ships);
        board = new Board();
    }

}
