import java.util.*;

public class UserInterface {
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        SKevaluator sk = new SKevaluator();

        System.out.println("Enter your choice:");
        System.out.println("1 - Evaluate an expresion");
        System.out.println("2 - Form a combinator expression");
        System.out.println("0 - Exit");
        int choice = Integer.parseInt(reader.nextLine());
        while((choice == 1) || (choice == 2)){

            if(choice == 1){
                System.out.println("Enter the expression:");
                String expression = reader.nextLine();

                System.out.println("Evaluation: " + sk.evaluate(expression));
            }
            else if(choice == 2){
                System.out.println("Enter input string:");
                String input = reader.nextLine();
                System.out.println("Enter the desired output:");
                String output = reader.nextLine();

                System.out.println("Combinator expression: " + sk.findCombinator(input, output));
            }

            System.out.println("Enter your choice:");
            System.out.println("1 - Evaluate an expresion");
            System.out.println("2 - Form a combinator expression");
            System.out.println("0 - Exit");
            choice = Integer.parseInt(reader.nextLine());
        }

        reader.close();
    }
}
