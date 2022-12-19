package enums;

public enum AboutGame {
    ABOUT ("This is the Battleship game."),
    AUTHOR ("Author: Ponomarev Oleg"),
    VERSION ("Version: 0.1.0-alpha");

    @Override
    public String toString() {
        return variable;
    }

    private String variable;
    AboutGame(String variable) {
        this.variable = variable;
    }
}
