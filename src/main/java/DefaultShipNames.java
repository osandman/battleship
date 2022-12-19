import enums.MyFiles;

import java.util.Arrays;
import java.util.List;

public class DefaultShipNames {
    static String[] ships1 = {
            "Potato",
            "Onion",
            "Corn",
            "Pea",
            "Yam",
            "Wa",
            "Hi"
    };
    static String[] ships2 = {
            "Orange",
            "Mango",
            "Kiwi",
            "Fig",
            "Fru",
            "Lu",
            "Si"
    };
    static List<String> shipsList;

    static List<String> getShipNames(MyFiles fName) {
        switch (fName) {
            case PLAYER1_SHIPS:
                shipsList = Arrays.asList(ships1);
                break;
            case PLAYER2_SHIPS:
                shipsList = Arrays.asList(ships2);
                break;
        }
        return shipsList;
    }
}
