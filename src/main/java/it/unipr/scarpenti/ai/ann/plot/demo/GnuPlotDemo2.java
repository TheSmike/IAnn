package it.unipr.scarpenti.ai.ann.plot.demo;

import java.math.BigDecimal;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.ArrayDataSet;
import com.panayotis.gnuplot.dataset.DataSet;
import com.panayotis.gnuplot.dataset.GenericDataSet;
import com.panayotis.gnuplot.dataset.Point;
import com.panayotis.gnuplot.dataset.PointDataSet;
import com.panayotis.gnuplot.plot.Graph;

public class GnuPlotDemo2 {

	public static void main(String[] args) throws Exception {
		
		System.out.println("START PLOT");
		JavaPlot p = new JavaPlot();
		p.addPlot("[x=-1:+1] [y=-1:1] sin(pi*(x*x+y*y))");

		DataSet ds = getDatSet(new BigDecimal("0.1"));
		p.addPlot(ds);

		p.plot();
		
		System.out.println("END PLOT");
	}

	private static DataSet getDatSet(BigDecimal samplingStep) throws Exception {

		PointDataSet<Double> ds = new PointDataSet<Double>();
		
		
		for (BigDecimal x = new BigDecimal(-1); lessOrEqualThan(x, 1); x = x.add(samplingStep)) {
			for (BigDecimal y = new BigDecimal(-1); lessOrEqualThan(y, 1); y = y.add(samplingStep)) {
				double result = func(x.doubleValue(), y.doubleValue());
				ds.add(new Point<Double>(x.doubleValue(), y.doubleValue(), result));
			}
		}
		
		return ds;
	}

	private static boolean lessOrEqualThan(BigDecimal x, int i) {
		return x.compareTo(new BigDecimal(i)) <= 0;
	}

	private static double func(double x, double y) {
		return Math.sin(Math.PI * (x * x + y * y));
	}

}
