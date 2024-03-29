import enums.MyFiles;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Board {
    String boardName;
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;
    public static final String ALPHABET = "abcdefghijklmno";
    public static final String FOG_CELL = "░"; // u2591
    public static final String CLEAN_CELL = " ";
    //переменные для начальной и конечной координаты корабля на поле
    private int endX, endY, beginX, beginY;
    private int countOfCells;
    private final String[][] board;
    //String defaultCell = "\u2587"; // = ▇
    //String defaultCell = "▇";
    //String defaultCell = "O";

    public void setCell(int row, int col, String value) {
        board[row][col] = value;
    }

    //в конструкторе создаем двумерный массив строк и заполняем его строкой defaultCell;
    public Board(String boardName) {
        this.boardName = boardName;
        board = new String[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = FOG_CELL;
            }
        }
    }

    //установка кораблей на игровое поле
    public void trySetAllShipsOnBoard(List<Ship> ships) {
        int countTry = 0;
        for (Ship ship : ships) {
            if (ship.length > (HEIGHT - 3)) {
                System.out.println("Не могу расставить корабли, уменьшите размеры кораблей в файлах: " +
                        MyFiles.PLAYER1_SHIPS + ", " + MyFiles.PLAYER2_SHIPS +
                        " до " + (HEIGHT - 3) + " символов");
                System.exit(0);
            }
            do {
                countTry++;
                if (countTry > 500) {
                    System.out.println("Не удается расставить корабли, уменьшите их количество или " +
                            "размеры в файлах: " + MyFiles.PLAYER1_SHIPS + ", " + MyFiles.PLAYER2_SHIPS);
                    System.exit(0);
                }
                ship.setStartCell();
            } while (getNeighbors(ship));
            //загружаем корабли на поле и передаем этому кораблю индексы нахождения на поле
            ship.setCells(setShipOnBoard(ship));
            //System.out.println(ship.getName() + " " + ship);
            countOfCells = ship.length;
        }
        System.out.printf("Количество итераций расстановки кораблей %s = %d\n", this.boardName, countTry);
    }

    public void cleanBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = FOG_CELL;
            }
        }
    }

    //печать игровых полей в консоли с маркировкой осей координат
    public static void printBoards(String info, Board... boards) {
        int numOfBoards = boards.length != 0 ? boards.length : 1;
        String tab = "    ";
        String alpha = ALPHABET.substring(0, WIDTH).replace("", " ").toUpperCase();
        System.out.println(info);

//todo сделать кузяво вывод надписей имен --------------
        String column1Format = "%-27s";
        String formatInfo = tab + column1Format;
        for (int i = 0; i < numOfBoards; i++) {
            System.out.format(formatInfo, boards[i].boardName);
        }
        System.out.println();
        for (int i = 0; i < numOfBoards; i++) {
            System.out.printf("   %s   %s", alpha, tab);
        }
        System.out.println();
        // ---------------------------------

        for (int i = 0; i < HEIGHT; i++) {
            for (int k = 0; k < numOfBoards; k++) {
                System.out.printf(" %2d", i + 1);
                for (int j = 0; j < WIDTH; j++) {
                    System.out.printf(" %s", boards[k].board[i][j]);
                }
                System.out.printf(" %-2d %s", (i + 1), tab);
            }
            System.out.println();
        }
        for (int i = 0; i < numOfBoards; i++) {
            System.out.printf("   %s   %s", alpha, tab);
        }
        System.out.println();
    }

    //установка корабля на игровое поле, возвращает корабль с координатами поля
    public Map<String, String> setShipOnBoard(Ship ship) {
        Map<String, String> resultShip = new TreeMap<>();
        String index;
        int i = 0;
        for (String shipCell : ship.getCells().values()) {
            if (ship.isHorizontal) {
                board[ship.startY][ship.startX + i] = shipCell;
                index = String.valueOf(ship.startY).concat(String.valueOf(ship.startX + i));
            } else {
                board[ship.startY + i][ship.startX] = shipCell;
                index = String.valueOf(ship.startY + i).concat(String.valueOf(ship.startX));
            }
            resultShip.put(index, shipCell);
            i++;
        }
        return resultShip;
    }

    public boolean testShipOnBoard(Ship ship) {
        for (int i = 0; i < ship.length; i++) {
            if (ship.isHorizontal) {
                if (!board[ship.startY][ship.startX + i].equals(FOG_CELL)) {
                    return false;
                }
            } else if (!board[ship.startY + i][ship.startX].equals(FOG_CELL)) {
                return false;
            }
        }
        return true;
    }

    //проверяет наличия кораблей в соседних ячейках
    public boolean getNeighbors(Ship ship) {
        setArea(ship);
        for (int i = beginY; i <= endY; i++) {
            for (int j = beginX; j <= endX; j++) {
                if (board[i][j].equals(FOG_CELL)) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    //устанавливает зону вокруг корабля на + одну ячейку
    private void setArea(Ship ship) {
        beginX = Math.max((ship.startX - 1), 0);
        beginY = Math.max((ship.startY - 1), 0);
        if (ship.isHorizontal) {
            endX = Math.min((ship.startX + ship.length), WIDTH - 1);
            endY = Math.min((ship.startY + 1), HEIGHT - 1);
        } else {
            endX = Math.min((ship.startX + 1), WIDTH - 1);
            endY = Math.min((ship.startY + ship.length), HEIGHT - 1);
        }
    }

    //заполняем зону вокруг корабля если корабль подбит
    public void fillAreaCells(Ship ship, String fill, String notFillCell) {
        setArea(ship);
        for (int i = beginY; i <= endY; i++) {
            for (int j = beginX; j <= endX; j++) {
                //if (board[i][j].equals(check) || board[i][j].matches("\\w")) {
                if (board[i][j].equals(notFillCell)) {
                    continue;
                }
                board[i][j] = fill;
            }
        }
    }
}

