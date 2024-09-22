import java.util.Scanner;

public class Bayes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Training data
        String[][] data = {
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

        // Class counts
        int yesCount = 0;
        int noCount = 0;
        for (String[] row : data) {
            if (row[4].equals("yes")) {
                yesCount++;
            } else {
                noCount++;
            }
        }

        // Calculate prior probabilities
        double pYes = (double) yesCount / data.length;
        double pNo = (double) noCount / data.length;

        // Prompt user for input
        System.out.println("Enter age (<=30, 31...40, >40): ");
        String age = scanner.nextLine();
        System.out.println("Enter income (high, medium, low): ");
        String income = scanner.nextLine();
        System.out.println("Are you a student? (yes, no): ");
        String student = scanner.nextLine();
        System.out.println("Enter credit rating (fair, excellent): ");
        String creditRating = scanner.nextLine();

        // Calculate likelihoods
        double pAgeGivenYes = calculateProbability(data, age, "yes", 0);
        double pAgeGivenNo = calculateProbability(data, age, "no", 0);
        double pIncomeGivenYes = calculateProbability(data, income, "yes", 1);
        double pIncomeGivenNo = calculateProbability(data, income, "no", 1);
        double pStudentGivenYes = calculateProbability(data, student, "yes", 2);
        double pStudentGivenNo = calculateProbability(data, student, "no", 2);
        double pCreditRatingGivenYes = calculateProbability(data, creditRating, "yes", 3);
        double pCreditRatingGivenNo = calculateProbability(data, creditRating, "no", 3);

        // Calculate posterior probabilities
        double pXGivenYes = pAgeGivenYes * pIncomeGivenYes * pStudentGivenYes * pCreditRatingGivenYes;
        double pXGivenNo = pAgeGivenNo * pIncomeGivenNo * pStudentGivenNo * pCreditRatingGivenNo;
        double pYesGivenX = pXGivenYes * pYes;
        double pNoGivenX = pXGivenNo * pNo;

        // Output classification result
        if (pYesGivenX > pNoGivenX) {
            System.out.println("The classification result is: yes (buys computer)");
        } else {
            System.out.println("The classification result is: no (does not buy computer)");
        }
    }

    // Helper method to calculate probability
    private static double calculateProbability(String[][] data, String value, String classLabel, int column) {
        int count = 0;
        int classCount = 0;
        for (String[] row : data) {
            if (row[column].equals(value) && row[4].equals(classLabel)) {
                count++;
            }
            if (row[4].equals(classLabel)) {
                classCount++;
            }
        }
        return (double) count / classCount;
    }
}
