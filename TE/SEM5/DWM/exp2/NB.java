import java.util.HashMap;
import java.util.Map;

public class NB {
    public static void main(String[] args) {
        // Training data: attributes and class labels
        String[][] trainingData = {
            {"<=30", "high", "no", "fair", "no"},
            {"<=30", "high", "no", "excellent", "no"},
            {"31...40", "high", "no", "fair", "yes"},
            {">40", "medium", "no", "fair", "yes"},
            {">40", "low", "yes", "fair", "yes"},
            {">40", "low", "yes", "excellent", "no"},
            {"31...40", "low", "yes", "excellent", "yes"},
            {"<=30", "medium", "no", "fair", "no"},
            {"<=30", "low", "yes", "fair", "yes"},
            {">40", "medium", "yes", "fair", "yes"},
            {"<=30", "medium", "yes", "excellent", "yes"},
            {"31...40", "medium", "no", "excellent", "yes"},
            {"31...40", "high", "yes", "fair", "yes"},
            {">40", "medium", "no", "excellent", "no"}
        };

        // Test data
        String[] testData = {"<=30", "medium", "yes", "fair"};

        // Calculate prior probabilities
        double pYes = 9.0 / 14.0;
        double pNo = 5.0 / 14.0;

        // Calculate likelihoods
        Map<String, Double> likelihoodsYes = new HashMap<>();
        Map<String, Double> likelihoodsNo = new HashMap<>();

        likelihoodsYes.put("age<=30", 2.0 / 9.0);
        likelihoodsNo.put("age<=30", 3.0 / 5.0);

        likelihoodsYes.put("income=medium", 4.0 / 9.0);
        likelihoodsNo.put("income=medium", 2.0 / 5.0);

        likelihoodsYes.put("student=yes", 6.0 / 9.0);
        likelihoodsNo.put("student=yes", 1.0 / 5.0);

        likelihoodsYes.put("credit_rating=fair", 6.0 / 9.0);
        likelihoodsNo.put("credit_rating=fair", 2.0 / 5.0);

        // Calculate P(X|Ci)
        double pXYes = likelihoodsYes.get("age<=30") * likelihoodsYes.get("income=medium")
                * likelihoodsYes.get("student=yes") * likelihoodsYes.get("credit_rating=fair");
        double pXNo = likelihoodsNo.get("age<=30") * likelihoodsNo.get("income=medium")
                * likelihoodsNo.get("student=yes") * likelihoodsNo.get("credit_rating=fair");

        // Print step 3 computation
        System.out.println("Step 3 Computation:");
        System.out.println("P(X|buys_computer=yes) = " + pXYes);
        System.out.println("P(X|buys_computer=no) = " + pXNo);

        // Calculate P(X|Ci) * P(Ci)
        double pXYesTimesPYes = pXYes * pYes;
        double pXNoTimesPNo = pXNo * pNo;

        // Print step 4 computation
        System.out.println("Step 4 Computation:");
        System.out.println("P(X|buys_computer=yes) * P(buys_computer=yes) = " + pXYesTimesPYes);
        System.out.println("P(X|buys_computer=no) * P(buys_computer=no) = " + pXNoTimesPNo);

        // Determine the class
        if (pXYesTimesPYes > pXNoTimesPNo) {
            System.out.println("X belongs to class 'buys_computer=yes'");
        } else {
            System.out.println("X belongs to class 'buys_computer=no'");
        }
    }
}
