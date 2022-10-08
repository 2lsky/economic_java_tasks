import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

    public  LineChart_AWT(String chartTitle, String yAxisTitile, String xAxisTitle,
                                        String[][] dataAnn,
                                        String [][] dataDiff,
                                        String nameCol,
                                        String[] nameCols) {
        super(chartTitle);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                xAxisTitle, yAxisTitile,
                this.createDataset(dataDiff, dataAnn, nameCol, nameCols),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 700 ) );
        setContentPane(chartPanel);
        pack();
        setLocationRelativeTo(null);
        validate();
        setVisible(true);
    }

    private XYDataset createDataset(String[][] dataDiff, String[][] dataAnn,  String nameCol, String[] nameCols) {
        XYSeries datasetDiff = new XYSeries(nameCol + " " + "Diff");
        XYSeries datasetAnn = new XYSeries(nameCol + " " + "Ann");
        int indexCol = this.getNumCol(nameCol, nameCols);
        for (int i = 0; i < dataDiff.length; i ++){
            datasetDiff.add(Double.parseDouble(dataDiff[i][0]),Double.parseDouble(dataDiff[i][indexCol]));
            datasetAnn.add(Double.parseDouble(dataAnn[i][0]), Double.parseDouble(dataAnn[i][indexCol]));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(datasetAnn);
        dataset.addSeries(datasetDiff);
        return dataset;
    }
    private int getNumCol(String nameCol, String[] nameCols){
        int intKey = 0;
        for (int j = 0; j < nameCols.length; j ++){
            if (nameCols[j] == nameCol){
                intKey = j;
            }
        }
        return intKey;
    }
}
