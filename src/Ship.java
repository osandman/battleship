import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Ship {
    /*
        public static final String RESET = "\033[0m";
        public static final String RED = "\033[0;31m";
        public static final String YELLOW = "\033[0;33m";
        public static final String BLUE = "\033[0;34m";
     */
    public static final String RESET = "";
    public static final String RED = "";
    public static final String YELLOW = "";
    public static final String BLUE = "";
    public static final String hitCell = "■";
    public static final String deadShipCell = "¤";
    //public static String hitCell = "⚡";
    //public static String hitCell = "✨";
    //public static String hitCell = "\uD83D\uDCA5"; // взрыв
    //public static String hitCell = "\uD83D\uDD25"; //огонь
    public final int length; // длина корабля
    public boolean isAlive = true;
    public boolean isHorizontal = true;
    int startX; //начальная координата х - по горизонтали, от 0
    int startY; //начальная координата y - по вертикали, от 0

    private int countOfHits = 0;
    private final String name;

    public String getName() {
        return name;
    }

    private Map<String, String> shipCells = new TreeMap<>();

    public Ship(String name) {
        this.name = BLUE + name + RESET;
        String[] cellArray = name.split("");
        for (int i = 0; i < cellArray.length; i++) {
            shipCells.put(String.valueOf(i), BLUE + cellArray[i] + RESET);
        }
        length = shipCells.size();
    }

    @Override
    public String toString() {
        return List.of(shipCells).toString();
    }

    public Map<String, String> getCells() {
        return shipCells;
    }

    public String getCell(String key) {
        return shipCells.get(key);
    }

    public void setCells(Map<String, String> shipCells) {
        this.shipCells = shipCells;
    }

    public boolean setHitCell(String key, String cell) {
        //если ячейка корабля не содержит символа подбит или убит
        if (!shipCells.get(key).contains(hitCell) && !shipCells.get(key).contains(deadShipCell)) {
            shipCells.put(key, YELLOW + cell + RESET);
            countOfHits++;
            if (countOfHits >= length) {
                isAlive = false;
                for (String keyHit : shipCells.keySet()) {
                    shipCells.put(keyHit, RED + deadShipCell + RESET);
                }
            }
            return true;
        }
        return false;
    }

    public void setStartCell(int startX, int startY, boolean isHorizontal) {
        this.startX = startX;
        this.startY = startY;
        this.isHorizontal = isHorizontal;
    }

    // задаем начальные координаты корабля рандомом, после чего пытаемся установить его на поле
    public void setStartCell() {
        Random rand = new Random();
        int minX = 0, minY = 0;
        int maxX = Board.WIDTH - length;
        int maxY = Board.HEIGHT - length;
        isHorizontal = rand.nextBoolean();
        if (isHorizontal) {
            startX = minX + rand.nextInt(maxX + 1);
            startY = minY + rand.nextInt(Board.HEIGHT);
        } else {
            startX = minX + rand.nextInt(Board.WIDTH);
            startY = minY + rand.nextInt(maxY + 1);
        }
    }
}
