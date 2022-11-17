public enum ShipFiles {
    MY("myships"),
    COMP("compships");
    private final String fname;

    ShipFiles(String fname) {
        this.fname = fname;
    }

    @Override
    public String toString() {
        return fname;
    }
}
