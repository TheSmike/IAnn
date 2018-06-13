package it.unipr.scarpenti.ai.ann.plot.demo;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.ScatterMultiColor;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Jzy3dDemo {

	public static void main(String[] args) throws Exception {
		
		drawFunction();
		scatterPlot();

	}


	private static void drawFunction() {
		// Define a function to plot
		Mapper mapper = new Mapper() {
		    public double f(double x, double y) {
		        return Math.sin(Math.PI * (Math.pow(x, 2) + Math.pow(y, 2)));
		    }
		};
		

		// Define range and precision for the function to plot
		Range range = new Range(-1, 1);
		int steps = 50;

		// Create a surface drawing that function
		Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(true);
		surface.setWireframeColor(Color.GRAY);

		// Create a chart and add the surface
		Chart chart = new AWTChart(Quality.Advanced);
		chart.add(surface);
		chart.open("Jzy3d Demo", 600, 600);
		chart.addMouseCameraController();
	}
	
	
	private static void scatterPlot() {
		int size = 100000;
		float x;
		float y;
		float z;
		Coord3d[] points = new Coord3d[size];

		// Create scatter points
		for(int i=0; i<size; i++){
		    x = (float)Math.random() - 0.5f;
		    y = (float)Math.random() - 0.5f;
		    z = (float)Math.random() - 0.5f;
		    points[i] = new Coord3d(x, y, z);
		}       

		// Create a drawable scatter with a colormap
		ScatterMultiColor scatter = new ScatterMultiColor( points, new ColorMapper( new ColorMapRainbow(), -0.5f, 0.5f ) );

		// Create a chart and add scatter
		Chart chart = new AWTChart();
		chart.getAxeLayout().setMainColor(Color.WHITE);
		chart.getView().setBackgroundColor(Color.BLACK);
		chart.getScene().add(scatter);
		ChartLauncher.openChart(chart);
		
	}

}
