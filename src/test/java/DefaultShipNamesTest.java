import enums.MyFiles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultShipNamesTest {

    @Test
    void getShipNamesTest() {
        assertEquals("Potato", DefaultShipNames.getShipNames(MyFiles.PLAYER1_SHIPS).get(0));
        assertEquals("Corn", DefaultShipNames.getShipNames(MyFiles.PLAYER1_SHIPS).get(2));
        assertEquals("Hi", DefaultShipNames.getShipNames(MyFiles.PLAYER1_SHIPS).
                get(DefaultShipNames.getShipNames(MyFiles.PLAYER1_SHIPS).size()-1));

        assertEquals("Orange", DefaultShipNames.getShipNames(MyFiles.PLAYER2_SHIPS).get(0));
        assertEquals("Fig", DefaultShipNames.getShipNames(MyFiles.PLAYER2_SHIPS).get(3));
        assertEquals("Si", DefaultShipNames.getShipNames(MyFiles.PLAYER2_SHIPS).
                get(DefaultShipNames.getShipNames(MyFiles.PLAYER2_SHIPS).size()-1));
    }
}