package machine_learning;


import java.util.ArrayList;

import arrays.AH;
import weka.classifiers.functions.Logistic;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class Weka {

    public static void main(String[] args) throws Exception {
        testLogit();
    }

    private static void testLogit() throws Exception {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>(3);
        attributes.add(new Attribute("Buffer"));
        attributes.add(new Attribute("Training"));

        ArrayList<String> classValues = new ArrayList<String>(2);
        classValues.add("train");
        classValues.add("not");
        attributes.add(new Attribute("@@class@@", classValues));

        Instances data = new Instances("TestInstances", attributes, 0);
        data.setClassIndex(data.numAttributes() - 1); // set target attribute for classification

        {
            double[] instanceValue = new double[data.numAttributes()];
            instanceValue[0] = 5;
            instanceValue[1] = 1;
            instanceValue[2] = 0; // index von train
            data.add(new DenseInstance(1.0, instanceValue));
        }
        {
            double[] instanceValue = new double[data.numAttributes()];
            instanceValue[0] = 5;
            instanceValue[1] = 1;
            instanceValue[2] = 0; // index von train
            data.add(new DenseInstance(1.0, instanceValue));
        }
        {
            double[] instanceValue = new double[data.numAttributes()];
            instanceValue[0] = 1;
            instanceValue[1] = 2;
            instanceValue[2] = 1; // index von train
            data.add(new DenseInstance(1.0, instanceValue));
        }

        System.out.println(data);

        Logistic logit = new Logistic();
        logit.buildClassifier(data);

        double[][] coeffs = logit.coefficients();
        System.out.println(AH.toString(coeffs, "", "\n", "", "", ",", ""));
    }
}
