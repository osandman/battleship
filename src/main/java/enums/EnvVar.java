package enums;

public enum EnvVar {
    ABOUT ("This is the Battleship game."),
    AUTHOR ("Author: Ponomarev Oleg"),
    VERSION ("Version: 0.1.0-alpha");

    @Override
    public String toString() {
        return variable;
    }

    private String variable;
    EnvVar(String variable) {
        this.variable = variable;
    }
}
