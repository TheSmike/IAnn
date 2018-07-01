package it.unipr.scarpenti.ai.ann.classifier.performance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

public class ClassifierPerformanceEvaluator {

	private Instances data;
	private int seed;
	private String hiddenLayers;
	private double learningRate;
	private double momentum;
	private int epochs;
	private int threadNumber;

	public ClassifierPerformanceEvaluator(String datasetPath, String hiddenLayers, double learningRate, double momentum,
			int epochs, int seed, int threadNumber) throws Exception {
		this.hiddenLayers = hiddenLayers;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.epochs = epochs;
		this.seed = seed;
		this.threadNumber = threadNumber;
		data = getInstances(datasetPath);
	}
	
	private Instances getInstances(String datasetPath) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(datasetPath));
		Instances data = new Instances(reader);
		reader.close();

		// setting class attribute
		data.setClassIndex(data.numAttributes() - 1);

		return data;
	}

	public double trainStocasticEvaluation(int iteration) throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
		List<Double> errorList = new ArrayList<Double>();

		for (int i = 0; i < iteration; i++) {
			WorkerThread thread = new WorkerThread(seed++, errorList);
			executor.execute(thread);
		}
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
		
//		while (!executor.isTerminated()) {
//		}
		System.out.println("Finished all threads");

		double average = 0.0d;
		for (int i = 0; i < errorList.size(); i++) {
			double error = errorList.get(i);
			average += error / errorList.size();
		}
		return average;
	}
	
	
	
	public class WorkerThread implements Runnable {
	    
	    private int workerSeed;
		private List<Double> errorList;

		public WorkerThread(int workerSeed, List<Double> errorList){
			this.workerSeed = workerSeed;
			this.errorList = errorList;
	    }

	    @Override
	    public void run() {
	    	try {
//				System.out.println("start training for seed " + workerSeed);
				
				MultilayerPerceptron classifier;
				classifier = new MultilayerPerceptron();
				classifier.setHiddenLayers(hiddenLayers);
				classifier.setLearningRate(learningRate);
				classifier.setMomentum(momentum);
				classifier.setTrainingTime(epochs);
				classifier.setSeed(workerSeed);
				
				String[] options = classifier.getOptions();
				String console = "Options: ";
				for (int i = 0; i < options.length; i++) {
					console += options[i] + " ";
				}

				classifier.buildClassifier(data); // build classifier

				Evaluation trainEval = new Evaluation(data);
				trainEval.evaluateModel(classifier, data);

				console += "mean absolute errror : " + trainEval.meanAbsoluteError();

				double meanAbsoluteError = trainEval.meanAbsoluteError();
				
				saveMeanError(meanAbsoluteError);
				
				synchronized (this) {
					System.out.println(console);					
				}
				

				// double learningRate = classifier.getLearningRate();
				// classifier.setLearningRate(learningRate - 0.01d);

				// System.out.println("\tcorrelationCoefficient : " +
				// trainEval.correlationCoefficient());

				// System.out.println("end training");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    
	    	}

		private synchronized void saveMeanError(double meanAbsoluteError) {
			errorList.add(meanAbsoluteError);
			
		}

	}
}
