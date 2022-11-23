package enums;

public enum ReturnStr {
    QUIT("q"),
    SHOW("show"),
    HUMAN_GAME("1"),
    COMP_GAME("2"),
    TRUE("да"),
    FALSE("нет");

    private String inputStr;

    @Override
    public String toString() {
        return inputStr;
    }
    ReturnStr() {
    }
    ReturnStr(String inputStr) {
        this.inputStr = inputStr;
    }
}
