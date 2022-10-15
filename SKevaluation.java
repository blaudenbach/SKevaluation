
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

                if(c == 'S'){
                    copy += S.evaluate(expression.substring(i));
                }
                else if(c == 'K'){
                    copy += K.evaluate(expression.substring(i));
                }
                else{
                    copy += Character.toString(c);
                }
            }

            expression = copy;
        }

        System.out.println("Evaluation: " + expression);

        reader.close();
    }

    boolean containsSK(String str){
        return (str.contains("S") || str.contains("K"));
    }
}
