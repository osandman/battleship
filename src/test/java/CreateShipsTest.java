import enums.MyFiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateShipsTest {
    CreateShips ships1;
    CreateShips ships2;
    @BeforeEach
    void setShips() {
         ships1 = new CreateShips(MyFiles.PLAYER1_SHIPS);
         ships2 = new CreateShips(MyFiles.PLAYER2_SHIPS);
    }

    @Test
    void getCountOfShipCellsTest() {
        assertEquals(25, ships1.getCountOfShipCells());
        assertEquals(25, ships2.getCountOfShipCells());
    }

    @Test
    void getNewShipsTest() {
        assertEquals(DefaultShipNames.getShipNames(MyFiles.PLAYER1_SHIPS).get(4), ships1.getNewShips().get(4).getName());
        assertEquals(DefaultShipNames.getShipNames(MyFiles.PLAYER2_SHIPS).get(4), ships2.getNewShips().get(4).getName());
    }
}