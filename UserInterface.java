import java.util.*;

public class UserInterface {
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        SKevaluator sk = new SKevaluator();

        System.out.println("Enter the expression:");
        String expression = reader.nextLine();

        System.out.println("Evaluation: " + sk.evaluate(expression));

        reader.close();
    }
}
