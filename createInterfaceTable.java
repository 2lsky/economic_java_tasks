import javax.swing.*;
import javax.swing.border.Border;

public class createInterfaceTable {
    private JTable getJTable(String[] colNames, String[][] data){
        return new JTable(data, colNames);
    }
    private JScrollPane getJScrollPane(String[] colNames, String[][] data){
        JTable table = this.getJTable(colNames, data);
        return new JScrollPane(table);
    }
    public JPanel getJpanel(String[] colNames, String[][] data, String header){
        Border blackline = BorderFactory.createTitledBorder(header);
        JScrollPane pane = this.getJScrollPane(colNames, data);
        JPanel panel = new JPanel();
        panel.setBorder(blackline);
        panel.add(pane);
        return panel;
    }

}
