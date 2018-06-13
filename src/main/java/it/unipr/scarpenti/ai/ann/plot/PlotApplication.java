package it.unipr.scarpenti.ai.ann.plot;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Scale;
import org.jzy3d.plot3d.primitives.ScatterMultiColor;

import it.unipr.scarpenti.ai.ann.classifier.FunctionClassifier;

public class PlotApplication {

	private static final String MODEL_PATH = "D:\\Google Drive\\unipr\\03 AI 17-18\\machine learning\\esercitazioni\\ex2\\es1b\\model\\";
	private static final String DATASET_PATH = "D:\\Google Drive\\unipr\\03 AI 17-18\\machine learning\\esercitazioni\\ex2\\es1b\\dataset\\";

	public static void main(String[] args) throws Exception {
		
		if (args.length < 2)
			throw new Exception("due parametri obbligatori:\n"
					+ " - nome del dataset (e del modello)\n"
					+ " - sampling step");
		
		scatterPlot(Double.parseDouble(args[1]));
		scatterClassifiedPlot(args[0]);
		
	}
	
	private static void scatterPlot(double samplingStep) throws Exception {
		
		
		int size = ((Double)(2/samplingStep)).intValue() + 1;
		size *= size;
		
		System.out.println("step number: " + size);
		
		double z;
		Coord3d[] points = new Coord3d[size];
		
		int i = 0;
		
		for (double x = -1d; x <= 1.0000001d; x+=samplingStep) {
			for (double y = -1d; y <= 1.0000001d; y+=samplingStep) {
				z = f(x, y);
				//z = classifier.classify(x, y);
				points[i++] = new Coord3d(x, y, z);
//				System.out.println(String.format("step %d : f(%f,%f) = %f", i, x,y,z));
			}
		}   

		// Create a drawable scatter with a colormap
		ScatterMultiColor scatter = new ScatterMultiColor( points, new ColorMapper( new ColorMapRainbow(), -1f, 1f ) );
		scatter.setWidth(2f);
		// Create a chart and add scatter
		Chart chart = new AWTChart();
		chart.getAxeLayout().setMainColor(Color.WHITE);
		chart.getView().setBackgroundColor(Color.BLACK);
		chart.getScene().add(scatter);
		ChartLauncher.openChart(chart);
		
	}
	
    public static double f(double x, double y) {
        return Math.sin(Math.PI * (Math.pow(x, 2) + Math.pow(y, 2)));
    }
    
    private static void scatterClassifiedPlot(String fileName) throws Exception {
    	String[] split = fileName.split("_");
    	String model = MODEL_PATH + fileName + ".model";
    	String dataset = DATASET_PATH + split[0] + ".arff";
    	
		FunctionClassifier classifier = new FunctionClassifier(model, dataset);
		
		double[][] results = classifier.classifyDataset();
		Coord3d[] points = new Coord3d[results.length];
		
		for (int j = 0; j < results.length; j++) {
			double x = classifier.getX(j);
			double y = classifier.getY(j);
			double z = results[j][0];
			points[j] = new Coord3d(x, y, z);
		}
		
		// Create a drawable scatter with a colormap
		ScatterMultiColor scatter = new ScatterMultiColor( points, new ColorMapper( new ColorMapRainbow(), -1f, 1f ) );
		scatter.setWidth(2f);
		// Create a chart and add scatter
		Chart chart = new AWTChart();
		chart.getAxeLayout().setMainColor(Color.WHITE);
		chart.getView().setBackgroundColor(Color.BLACK);

		BoundingBox3d bbox = new BoundingBox3d();
		bbox.setXmin(-1f);
        bbox.setXmax(1f);
        bbox.setYmin(-1f);
        bbox.setYmax(1f);
        bbox.setZmin(-1f);
        bbox.setZmax(1f);
        
        chart.getView().setBoundManual(bbox);
        chart.getView().updateBounds();
		
		
		chart.getScene().add(scatter);
		ChartLauncher.openChart(chart);
		
	}
    
}
