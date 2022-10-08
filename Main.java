import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String [] colNames = new String[]{"Номер", "Период", "Дата платежа", "Общая сумма платежа",
                "Сумма по процентам", "Сумма по долгу", "Задолженность"};
        programForCredit program = new programForCredit();
        Object[] tables = program.getOutputData();
        String[][] tableAnnuityString = (String[][]) tables[0];
        String[][] tableDiffString = (String[][]) tables[1];
        JFrame mainFrame = new mainFrame().getMainFrame(tableDiffString, tableAnnuityString, colNames);
        LineChart_AWT lineChartMainSum = new LineChart_AWT("Общая сумма платежа",
                "Value", "Number", tableAnnuityString, tableDiffString,
                "Общая сумма платежа",  colNames);
        LineChart_AWT lineChartPercents = new LineChart_AWT("Проценты",
                "Value", "Number", tableAnnuityString, tableDiffString,
                "Сумма по процентам",  colNames);
        LineChart_AWT lineChartDebtSum = new LineChart_AWT("Cумма по долгу",
                "Value", "Number", tableAnnuityString, tableDiffString,
                "Сумма по долгу",  colNames);
        LineChart_AWT lineChartDebt = new LineChart_AWT("Задолженность",
                "Value", "Number", tableAnnuityString, tableDiffString,
                "Задолженность",  colNames);
        mainFrame.setVisible(true);
    }
}