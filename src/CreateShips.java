import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateShips {
    private final List<String> shipNames = new ArrayList<>();
    private final List<Ship> newShips = new ArrayList<>();
    public CreateShips() {
        readNamesFromFile();
        create();
    }

    private void readNamesFromFile() {
        String fileName = "ships";
        File file = new File(fileName);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
                shipNames.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void create() {
        for (String name : shipNames) {
            newShips.add(new Ship(name));
        }
    }

    public List<Ship> getNewShips() {
        return newShips;
    }

}
