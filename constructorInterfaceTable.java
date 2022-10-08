public class constructorInterfaceTable {
    private final String[] colNames;
    private final String[][] data;

    public String[] getColNames() {
        return colNames;
    }

    public String[][] getData() {
        return data;
    }

    public constructorInterfaceTable(String[] colNames, String[][] data) {
        this.colNames = colNames;
        this.data = data;
    }
}
