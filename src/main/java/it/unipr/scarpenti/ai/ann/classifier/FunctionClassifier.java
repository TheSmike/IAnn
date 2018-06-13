package it.unipr.scarpenti.ai.ann.classifier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class FunctionClassifier {

	MultilayerPerceptron classifier;
	private Instances data;

	public FunctionClassifier(String modelPath, String datasetPath) throws Exception {
		InputStream is = new FileInputStream(modelPath);
		classifier = (MultilayerPerceptron) SerializationHelper.read(is);

		BufferedReader reader = new BufferedReader(new FileReader(datasetPath));
		data = new Instances(reader);
		reader.close();
		// setting class attribute
		data.setClassIndex(data.numAttributes() - 1);
	}

	public double[][] classifyDataset() throws Exception {
		double[][] distributionForInstance = classifier.distributionsForInstances(data);
		return distributionForInstance;
	}

	public double getX(int j) {
		Instance instance = data.get(j);
		return instance.value(0);		
	}

	public double getY(int j) {
		Instance instance = data.get(j);
		return instance.value(1);
	}

}
