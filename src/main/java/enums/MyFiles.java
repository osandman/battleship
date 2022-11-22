package enums;

public enum MyFiles {
    PLAYER1_SHIPS("player1_ships"),
    PLAYER2_SHIPS("player2_ships"),
    RESULTS("results");
    private final String fileName;

    MyFiles(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
