package alexu.edu.eg.fetts.app;

import java.awt.Color;

        import javax.swing.JFrame;

        import org.jfree.chart.ChartFactory;
        import org.jfree.chart.ChartPanel;
        import org.jfree.chart.JFreeChart;
        import org.jfree.chart.plot.XYPlot;
        import org.jfree.data.xy.XYDataset;
        import org.jfree.data.xy.XYSeries;
        import org.jfree.data.xy.XYSeriesCollection;

public class PlotterInvoker extends JFrame {
    public PlotterInvoker(String title) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                title,
                "ID", "Time Token(ms)", dataset);

        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));


        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("The scattered data");

        for(int i = 0; i < DataCollector.getSize(); ++i) {
            series1.add((double)DataCollector.difficultyIndex.get(i), (double)DataCollector.time_elapsed.get(i));
        }

        dataset.addSeries(series1);
        return dataset;
    }
}