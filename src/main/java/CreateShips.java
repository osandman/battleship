import enums.ShipFiles;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

public class CreateShips {
    private final List<String> shipNames = new ArrayList<>();
    private final List<Ship> newShips = new ArrayList<>();
    int countOfShipCells;

    public CreateShips(ShipFiles fNames) {
        readNamesFromFile(fNames.toString());
        create();
    }

    private void readNamesFromFile(String fileName) {
        File file = new File(fileName);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
                shipNames.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(format("не найден файл \"%s\"!", fileName));
        }
    }

    private void create() {
        for (String name : shipNames) {
            countOfShipCells += name.length();
            newShips.add(new Ship(name));
        }
    }

    public int getCountOfShipCells() {
        return countOfShipCells;
    }

    public List<Ship> getNewShips() {
        return newShips;
    }

}
