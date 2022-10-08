import javax.swing.*;
import java.awt.*;



public class mainFrame {

    public JFrame getMainFrame(String[][] tableDiffString, String[][] tableAnnuityString, String[] colNames) {
        String title = "Сравнение графиков платежей";
        JFrame frame = new JFrame(title);
        constructorInterfaceTable tableDiff = new constructorInterfaceTable(colNames,
                tableDiffString);
        constructorInterfaceTable tableAnnuity = new constructorInterfaceTable(colNames, tableAnnuityString);
        JPanel panelDiff = new createInterfaceTable().getJpanel(tableDiff.getColNames(), tableDiff.getData(), "Diff");
        JPanel panelAnnuity = new createInterfaceTable().getJpanel(tableAnnuity.getColNames(), tableAnnuity.getData(), "Annuity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panelDiff, BorderLayout.EAST);
        frame.add(panelAnnuity, BorderLayout.WEST);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.validate();
        return frame;
    }
}