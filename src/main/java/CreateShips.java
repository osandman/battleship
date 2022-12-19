import enums.MyFiles;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateShips {
    private List<String> shipNames = new ArrayList<>();
    private final List<Ship> newShips = new ArrayList<>();
    private int countOfShipCells;

    public CreateShips(MyFiles fNames) {
        readNames(fNames);
        create();
    }

    private void readNames(MyFiles fileName) {
        File file = new File(fileName.toString());
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
                shipNames.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            shipNames = DefaultShipNames.getShipNames(fileName);
            System.out.println("Не найден файл, взяты корабли по умолчанию");
        }
    }

    private void create() {
        if (shipNames == null || shipNames.isEmpty()) {
            throw new RuntimeException("Не могу заполнить список кораблей");
        }
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
