
import java.util.*;

public class SKevaluation {
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        SKevaluation sk = new SKevaluation();
        SCombinator S = new SCombinator();
        KCombinator K = new KCombinator();

        System.out.println("Enter your expression:");
        String expression = reader.nextLine();

        while(sk.containsSK(expression)){
            String copy = "";

            for(int i = 0; i < expression.length(); i++){
                char c = expression.charAt(i);
                System.out.println(c);

                if(c == 'S'){
                    copy += S.evaluate(expression.substring(i+1));
                    System.out.println("SK -- " + copy);
                    break;
                }
                else if(c == 'K'){
                    copy += K.evaluate(expression.substring(i+1));
                    System.out.println("SK -- " + copy);
                    break;
                }
                else{
                    copy += Character.toString(c);
                    System.out.println("SK -- " + copy);
                }
            }

            expression = copy;
        }

        /*String copyExp = expression;
        int numOpen = copyExp.length() - copyExp.replace("(", "").length();
        copyExp = expression;
        int numClosed = copyExp.length() - copyExp.replace(")", "").length();
        while(numOpen != numClosed){
            for(int i = 0; i < expression.length(); i++){
                char c = expression.charAt(i);
                if(c == '('){
                    int lastOpen = i;
                }
            }
        }*/

        System.out.println("Evaluation: " + expression);

        reader.close();
    }

    boolean containsSK(String str){
        return (str.contains("S") || str.contains("K"));
    }
}
