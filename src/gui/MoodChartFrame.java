package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import controller.Controller;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * MoodChartFrame displays a bar chart with the information based on the mood entries of the journal.
 * It updates whenever a mood entry is added, deleted or edited.
 * 
 * @author Fernando
 *
 */
public class MoodChartFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private double excited;
	private double happy;
	private double meh;
	private double sad;
	private double cry;
	private double angry;
	private Controller control;
    
	/**
     * Constructor for the MoodChartFrame
     *
     * @param title  the frame title.
     * @param controller 
     */
    public MoodChartFrame(final String title, double[] values, Controller controller) {
    	
        super("");
        
    	control = controller;
        excited = values[0];
    	happy = values[1];
    	meh = values[2];
    	sad = values[3];
    	cry = values[4];
    	angry = values[5];
    	
    	this.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  control.removeActiveView("moodChart");
		      }
		});
    	
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void addValues(double[] values) {
    	excited = values[0];
    	happy = values[1];
    	meh = values[2];
    	sad = values[3];
    	cry = values[4];
    	angry = values[5];
    	
    	this.getContentPane().removeAll();
    	final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
        this.pack();
    }

    /**
     * Returns the dataset to create the chart bars
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset() {
        
        // row keys...
    	JButton button = new JButton();
    	button.setIcon(new ImageIcon(getResourceURL("/images/emoticons/emote0.png"),"Angry"));
        final String series1 = "Excited";
        final String series2 = "Happy";
        final String series3 = "Meh";
        final String series4 = "Sad";
        final String series5 = "Crying";
        final String series6 = "Angry";

        // column keys...
        final String category1 = "Excited";
        final String category2 = "Happy";
        final String category3 = "Meh";
        final String category4 = "Sad";
        final String category5 = "Crying";
        final String category6 = "Angry";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(excited, series1, category1);
        dataset.addValue(happy, series2, category2);
        dataset.addValue(meh, series3, category3);
        dataset.addValue(sad, series4, category4);
        dataset.addValue(cry, series5, category5);
        dataset.addValue(angry, series6, category6);
        
        return dataset;
        
    }
    
    public URL getResourceURL(String path){
    	return this.getClass().getResource(path);
    }
    
    /**
     * Creates the chart with the dataset information.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            control.getMoodLabel() + " Chart",         // chart title
            control.getMoodLabel() + "s",               // domain axis label
            "Days",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
        // set the background color for the chart...
        chart.setBackgroundPaint(new Color (240, 240, 240));

        // get a reference to the plot for further customization...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        return chart;
    }
    
    /**
     * Updates the chart with the given values
     * 
     * @param values An array of doubles containing the new values for the chart bars
     */
    public void updateView(double[] values){
    	excited = values[0];
    	happy = values[1];
    	meh = values[2];
    	sad = values[3];
    	cry = values[4];
    	angry = values[5];
    	
    	this.getContentPane().removeAll();
    	final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
        this.pack();
    }
}