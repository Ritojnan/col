import java.util.HashMap;
import java.util.Map;

public class NaiveBayes {
    private Map<String, Map<String, Integer>> featureCount;
    private Map<String, Integer> classCount;
    private int totalSamples;

    public NaiveBayes() {
        featureCount = new HashMap<>();
        classCount = new HashMap<>();
        totalSamples = 0;
    }

    public void train(String[] features, String className) {
        for (String feature : features) {
            featureCount.putIfAbsent(feature, new HashMap<>());
            featureCount.get(feature).put(className, featureCount.get(feature).getOrDefault(className, 0) + 1);
        }
        classCount.put(className, classCount.getOrDefault(className, 0) + 1);
        totalSamples++;
    }

    public String classify(String[] features) {
        double maxProbability = -1;
        String bestClass = null;
        System.out.println("Classifying the sample: " + String.join(", ", features));

        for (String className : classCount.keySet()) {
            double priorProbability = (double) classCount.get(className) / totalSamples;
            System.out.println("Prior Probability of class " + className + ": " + priorProbability);

            double featureProbabilityProduct = 1.0;
            for (String feature : features) {
                int featureClassCount = featureCount.getOrDefault(feature, new HashMap<>()).getOrDefault(className, 0);
                double featureProbability = (featureClassCount + 1.0) / (classCount.get(className) + featureCount.size());
                featureProbabilityProduct *= featureProbability;
                System.out.println("P(" + feature + " | " + className + ") = " + featureProbability);
            }

            double totalProbability = priorProbability * featureProbabilityProduct;
            System.out.println("Total Probability of class " + className + ": " + totalProbability);

            if (totalProbability > maxProbability) {
                maxProbability = totalProbability;
                bestClass = className;
            }
        }

        return bestClass;
    }

    public static void main(String[] args) {
        NaiveBayes nb = new NaiveBayes();

        // Training data
        nb.train(new String[]{"<=30", "high", "no", "fair"}, "no");
        nb.train(new String[]{"<=30", "high", "no", "excellent"}, "no");
        nb.train(new String[]{"31-40", "high", "no", "fair"}, "yes");
        nb.train(new String[]{">40", "medium", "no", "fair"}, "yes");
        nb.train(new String[]{">40", "low", "yes", "fair"}, "yes");
        nb.train(new String[]{">40", "low", "yes", "excellent"}, "no");
        nb.train(new String[]{"31-40", "low", "yes", "excellent"}, "yes");
        nb.train(new String[]{"<=30", "medium", "no", "fair"}, "no");
        nb.train(new String[]{"<=30", "low", "yes", "fair"}, "yes");
        nb.train(new String[]{">40", "medium", "yes", "fair"}, "yes");
        nb.train(new String[]{"<=30", "medium", "yes", "excellent"}, "yes");
        nb.train(new String[]{"31-40", "medium", "no", "excellent"}, "yes");
        nb.train(new String[]{"31-40", "high", "yes", "fair"}, "yes");
        nb.train(new String[]{">40", "medium", "no", "excellent"}, "no");

        // Test data
        System.out.println("Ritojnan Mukherjee C23 114");
        String[] testSample = {"<=30", "medium", "yes", "fair"};
        String result = nb.classify(testSample);
        System.out.println("Classified as: " + result);
    }
}
