package it.unipr.scarpenti.ai.ann.data;

import java.math.BigDecimal;

public class Application {

	//static String FOLDER = "D:/Google Drive/unipr/03 AI 17-18/machine learning/esercitazioni/ex2/es1b/dataset/";
	static String FOLDER = "C:\\Users\\Omi087\\Google Drive\\unipr\\03 AI 17-18\\machine learning\\esercitazioni\\ex2\\es1b\\dataset\\";

	public static void main(String[] args) throws Exception {

		System.out.println("START");

		writeDatSet(new BigDecimal("0.2"),  "poor.arff");
		writeDatSet(new BigDecimal("0.1"),  "medium.arff");
		writeDatSet(new BigDecimal("0.05"), "rich.arff");
		writeDatSet(new BigDecimal("0.025"), "veryRich.arff");
		

		System.out.println("END");
	}

	private static void writeDatSet(BigDecimal samplingStep, String fileName) throws Exception {
		try (MathSinFunctionArffFile arffFile = new MathSinFunctionArffFile(FOLDER + fileName)) {
			System.out.println("write file with sampling step:" + samplingStep);
			arffFile.writeHead();

			for (BigDecimal x = new BigDecimal(-1); lessOrEqualThan(x, 1); x = x.add(samplingStep)) {
				for (BigDecimal y = new BigDecimal(-1); lessOrEqualThan(y, 1); y = y.add(samplingStep)) {
					double result = func(x.doubleValue(), y.doubleValue());
					arffFile.writeRow(x, y, result);
				}
			}

		}
	}

	private static boolean lessOrEqualThan(BigDecimal x, int i) {
		return x.compareTo(new BigDecimal(i)) <= 0;
	}

	private static double func(double x, double y) {
		return Math.sin(Math.PI * (x * x + y * y));
	}

}
