import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Customer {
    String age;
    String income;
    String student;
    String creditRating;
    String buysComputer;

    public Customer(String age, String income, String student, String creditRating, String buysComputer) {
        this.age = age;
        this.income = income;
        this.student = student;
        this.creditRating = creditRating;
        this.buysComputer = buysComputer;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-6s %-6s %-11s %-13s", age, income, student, creditRating, buysComputer);
    }
}

public class BayesTheorem {
    private ArrayList<Customer> customers;
    private Map<String, Double> priorProbabilities;
    private Map<String, Map<String, Double>> conditionalProbabilities;

    public BayesTheorem(ArrayList<Customer> customers) {
        this.customers = customers;
        this.priorProbabilities = new HashMap<>();
        this.conditionalProbabilities = new HashMap<>();
        calculateProbabilities();
    }

    private void calculateProbabilities() {
        int totalCustomers = customers.size();
        Map<String, Integer> buysComputerCount = new HashMap<>();
        Map<String, Map<String, Integer>> attributeCount = new HashMap<>();

        // Count occurrences
        for (Customer customer : customers) {
            buysComputerCount.put(customer.buysComputer, buysComputerCount.getOrDefault(customer.buysComputer, 0) + 1);

            countAttribute(attributeCount, "age", customer.age, customer.buysComputer);
            countAttribute(attributeCount, "income", customer.income, customer.buysComputer);
            countAttribute(attributeCount, "student", customer.student, customer.buysComputer);
            countAttribute(attributeCount, "creditRating", customer.creditRating, customer.buysComputer);
        }

        // Calculate prior probabilities
        for (Map.Entry<String, Integer> entry : buysComputerCount.entrySet()) {
            priorProbabilities.put(entry.getKey(), (double) entry.getValue() / totalCustomers);
        }

        // Calculate conditional probabilities
        for (Map.Entry<String, Map<String, Integer>> attributeEntry : attributeCount.entrySet()) {
            String attribute = attributeEntry.getKey();
            Map<String, Double> attributeProbabilities = new HashMap<>();

            for (Map.Entry<String, Integer> valueEntry : attributeEntry.getValue().entrySet()) {
                String value = valueEntry.getKey();
                String[] parts = value.split("_");
                String attributeValue = parts[0];
                String buysComputer = parts[1];

                double probability = (double) valueEntry.getValue() / buysComputerCount.get(buysComputer);
                attributeProbabilities.put(value, probability);
            }

            conditionalProbabilities.put(attribute, attributeProbabilities);
        }
    }

    private void countAttribute(Map<String, Map<String, Integer>> attributeCount, String attribute, String value, String buysComputer) {
        attributeCount.putIfAbsent(attribute, new HashMap<>());
        String key = value + "_" + buysComputer;
        attributeCount.get(attribute).put(key, attributeCount.get(attribute).getOrDefault(key, 0) + 1);
    }

    public double calculateProbability(String age, String income, String student, String creditRating, String buysComputer) {
        double priorProbability = priorProbabilities.get(buysComputer);
        double likelihoodAge = conditionalProbabilities.get("age").get(age + "_" + buysComputer);
        double likelihoodIncome = conditionalProbabilities.get("income").get(income + "_" + buysComputer);
        double likelihoodStudent = conditionalProbabilities.get("student").get(student + "_" + buysComputer);
        double likelihoodCreditRating = conditionalProbabilities.get("creditRating").get(creditRating + "_" + buysComputer);

        double numerator = priorProbability * likelihoodAge * likelihoodIncome * likelihoodStudent * likelihoodCreditRating;
        double denominator = calculateEvidenceProbability(age, income, student, creditRating);

        return numerator / denominator;
    }

    private double calculateEvidenceProbability(String age, String income, String student, String creditRating) {
        double total = 0;
        for (String buysComputer : priorProbabilities.keySet()) {
            double priorProbability = priorProbabilities.get(buysComputer);
            double likelihoodAge = conditionalProbabilities.get("age").get(age + "_" + buysComputer);
            double likelihoodIncome = conditionalProbabilities.get("income").get(income + "_" + buysComputer);
            double likelihoodStudent = conditionalProbabilities.get("student").get(student + "_" + buysComputer);
            double likelihoodCreditRating = conditionalProbabilities.get("creditRating").get(creditRating + "_" + buysComputer);

            total += priorProbability * likelihoodAge * likelihoodIncome * likelihoodStudent * likelihoodCreditRating;
        }
        return total;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("<=30", "high", "no", "fair", "no"));
        customers.add(new Customer("<=30", "high", "no", "excellent", "no"));
        customers.add(new Customer("31...40", "high", "no", "fair", "yes"));
        customers.add(new Customer(">40", "medium", "no", "fair", "yes"));
        customers.add(new Customer(">40", "low", "yes", "fair", "no"));
        customers.add(new Customer(">40", "low", "yes", "excellent", "no"));
        customers.add(new Customer("31...40", "low", "yes", "excellent", "yes"));
        customers.add(new Customer("<=30", "medium", "no", "fair", "no"));
        customers.add(new Customer("<=30", "low", "yes", "fair", "yes"));
        customers.add(new Customer(">40", "medium", "yes", "fair", "yes"));
        customers.add(new Customer("<=30", "medium", "yes", "excellent", "yes"));
        customers.add(new Customer("31...40", "medium", "no", "excellent", "yes"));
        customers.add(new Customer("31...40", "high", "yes", "fair", "yes"));
        customers.add(new Customer(">40", "medium", "no", "excellent", "no"));

        // Print the table header
//        System.out.printf("%-6s %-6s %-6s %-11s %-13s\n", "age", "income", "student", "credit_rating", "buys_computer");
//        System.out.println("-----------------------------------------------------------");
        // Print the table rows
//        for (Customer customer : customers) {
//            System.out.println(customer);
//        }

        BayesTheorem bayesTheorem = new BayesTheorem(customers);

        // Example: Calculate the probability of buying a computer for a new customer
//        String age = "<=30";
//        String income = "medium";
//        String student = "yes";
//        String creditRating = "fair";
        System.out.println("Enter Age : ");
        String age = sc.next();
        System.out.println("Enter income : ");
        String income = sc.next();
        System.out.println("Student? : ");
        String student = sc.next();
        System.out.println("Enter credit rating : ");
        String creditRating = sc.next();

        double probYes = bayesTheorem.calculateProbability(age, income, student, creditRating, "yes");
        double probNo = bayesTheorem.calculateProbability(age, income, student, creditRating, "no");

        System.out.println("\nProbability calculation for a new customer:");
        System.out.printf("Age: %s, Income: %s, Student: %s, Credit Rating: %s\n", age, income, student, creditRating);
        System.out.printf("Probability of buying a computer: %.2f%%\n", probYes * 100);
        System.out.printf("Probability of not buying a computer: %.2f%%\n", probNo * 100);
        if((probYes * 100) >= (probNo * 100)){
            System.out.println("Will buy computer");
        }else {
            System.out.println("Will not buy computer");
        }
    }
}