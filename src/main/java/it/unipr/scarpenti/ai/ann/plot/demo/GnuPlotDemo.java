package it.unipr.scarpenti.ai.ann.plot.demo;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.ArrayDataSet;
import com.panayotis.gnuplot.dataset.DataSet;
import com.panayotis.gnuplot.plot.Graph;

public class GnuPlotDemo {

	public static void main(String[] args) {
        JavaPlot p = new JavaPlot();
        
        Graph gr = new Graph();
        
        //p.addGraph(gr);
        p.addPlot("sin(x)");
        
        int[][] values = {{2,2}, {3,3}, {6,7}} ;
        DataSet ds = new  ArrayDataSet(values); 
        p.addPlot(ds);
        
        p.plot();
    }

}
