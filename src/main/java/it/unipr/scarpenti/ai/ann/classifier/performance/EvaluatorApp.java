package it.unipr.scarpenti.ai.ann.classifier.performance;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EvaluatorApp {

	private static final String POOR_PATH = "D:/Google Drive/unipr/03 AI 17-18/machine learning/esercitazioni/ex2/es1b/dataset/poor.arff";
	private static final String MEDIUM_PATH = "D:/Google Drive/unipr/03 AI 17-18/machine learning/esercitazioni/ex2/es1b/dataset/medium.arff";
	private static final String RICH_PATH = "D:/Google Drive/unipr/03 AI 17-18/machine learning/esercitazioni/ex2/es1b/dataset/rich.arff";
	private static final String OUTPUT_PATH = "D:/Google Drive/unipr/03 AI 17-18/machine learning/esercitazioni/ex2/es1b/";

	public static void main(String[] args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		System.setOut(outputFile(OUTPUT_PATH + "_RICH_4_4" + sdf.format(new Date()) + ".txt"));
		
		ClassifierPerformanceEvaluator evaluator;
		double trainStocasticEvaluation;
		System.out.println("START on " + sdf.format(new Date()));

		System.out.println("Rete 4,4");
		evaluator = new ClassifierPerformanceEvaluator(RICH_PATH, "4,4", 0.2, 0.0, 40000, 12, 3);
		trainStocasticEvaluation = evaluator.trainStocasticEvaluation(100);
		System.out.println("media errore = " + trainStocasticEvaluation);

//		System.out.println("Rete 10,4");
//		evaluator = new ClassifierPerformanceEvaluator(RICH_PATH, "10,4", 0.2, 0.0, 40000, 12, 4);
//		trainStocasticEvaluation = evaluator.trainStocasticEvaluation(100);
//		System.out.println("media errore = " + trainStocasticEvaluation);

		System.out.println("END on " + sdf.format(new Date()));

	}
	
	   protected static PrintStream outputFile(String name) throws FileNotFoundException {
	       return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)), true);
	   }
}
