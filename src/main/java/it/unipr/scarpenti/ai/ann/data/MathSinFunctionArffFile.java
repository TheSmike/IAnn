package it.unipr.scarpenti.ai.ann.data;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class MathSinFunctionArffFile implements AutoCloseable {

	private Path file;
	private boolean isHeadWritten;
	private PrintWriter pwriter;
	
	
	public MathSinFunctionArffFile(String path) throws Exception  {
		isHeadWritten = false;
		file = Paths.get(path);
		pwriter = new PrintWriter(new FileOutputStream(file.toFile(), true));
	}
	
	public void writeHead() {
		pwriter.println("@RELATION function_sin \r\n" + 
				"\r\n" + 
				"@ATTRIBUTE x-val	real\r\n" + 
				"@ATTRIBUTE y-val 	real\r\n" + 
				"@ATTRIBUTE class 	real\r\n" + 
				"\r\n" +
				"\r\n" +
				"@DATA");
		
		isHeadWritten = true;
	}
	

	public void close() throws Exception {
		pwriter.close();
		
	}

	private static DecimalFormat df;
	
	static {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df = new DecimalFormat("0.00##", dfs);
	}
	
	private static DecimalFormat dfDouble;
	
	static {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfDouble = new DecimalFormat("0.00#############", dfs);
	}
	
	
	public void writeRow(BigDecimal x, BigDecimal y, double result) throws Exception {
		if (!isHeadWritten)
			throw new Exception("manca l'intestazione del file arff");
		
		pwriter.println(String.format("%s %s %s", df.format(x), df.format(y), dfDouble.format(result)));
		
	}
	

}
