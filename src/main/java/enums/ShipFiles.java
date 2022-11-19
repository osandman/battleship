package enums;

public enum ShipFiles {
    PLAYER1_SHIPS("player1_ships"),
    PLAYER2_SHIPS("player2_ships");
    private final String fileName;

    ShipFiles(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
