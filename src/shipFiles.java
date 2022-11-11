public enum shipFiles {
    MY("myships"),
    COMP("compships");
    private final String fname;

    shipFiles(String fname) {
        this.fname = fname;
    }

    @Override
    public String toString() {
        return fname;
    }
}
