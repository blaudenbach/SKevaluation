import java.util.*;

public class SKevaluator{
    SCombinator S;
    KCombinator K;
    Expression expression;

    public SKevaluator(){
        S = new SCombinator(this);
        K = new KCombinator();
        expression = new Expression();
    }

    public String evaluate(String expr){
        expression.setExpression(expr);
        while(expression.containsSK()){
            System.out.println("eval starting exp -- " + expression.getExpression());
            String copy = "";

            for(int i = 0; i < expression.getExpression().length(); i++){
                char c = expression.getExpression().charAt(i);
                System.out.println(c);

                if(c == 'S'){
                    try{
                        copy += S.evaluate(expression.getExpression().substring(i+1));
                    }
                    catch(NullPointerException e){
                        expression.finalize();
                        System.out.println("Initial expression " + expr + " evaluated as: " + expression.getExpression());
                        return expression.getExpression();
                    }
                    System.out.println("eval -- " + copy);
                    break;
                }
                else if(c == 'K'){
                    try{
                        copy += K.evaluate(expression.getExpression().substring(i+1));
                    }
                    catch(NullPointerException e){
                        expression.finalize();
                        System.out.println("Initial expression " + expr + " evaluated as: " + expression.getExpression());
                        return expression.getExpression();
                    }
                    System.out.println("eval -- " + copy);
                    break;
                }
                else{
                    copy += Character.toString(c);
                    System.out.println("eval -- " + copy);
                }
            }

            expression.setExpression(copy);
        }

        expression.finalize();

        System.out.println("Initial expression " + expr + " evaluated as: " + expression.getExpression());

        return expression.getExpression();
    }

    String findCombinator(String in, String out){
        int count = 0;
        String combExp = "";

        //Find number of parameters
        for(int i = 0; i < in.length(); i++){
            char c = in.charAt(i);
            if(c == ')'){
                continue;
            }
            else if(c == '('){
                int open = 1;
                int closed = 0;
                int pos = i;

                while(closed < open){
                    char p = in.charAt(pos);

                    if(p == '(' && pos == i){
                        pos++;
                        continue;
                    }
                    else if(p == '('){
                        open++;
                    }
                    else if(p == ')'){
                        closed++;
                    }

                    pos++;
                }

                i = pos;
                count++;
            }
            else{
                count++;
            }
        }

        //System.out.println("Number of parameters in input: " + Integer.toString(count));

        //Create and fill array of parameters
        String[] params = new String[count];
        count = 0;
        for(int i = 0; i < in.length(); i++){
            char c = in.charAt(i);

            if(c == ')'){
                continue;
            }
            else if(c == '('){
                int open = 1;
                int closed = 0;
                String obj = "";
                int pos = i;

                while(closed < open){
                    char p = in.charAt(pos);

                    if((p == '(') && (pos == i)){
                        obj += p;
                        pos++;
                        continue;
                    }
                    else if(p == '('){
                        open++;
                    }
                    else if(p == ')'){
                        closed++;
                    }

                    obj += p;
                    pos++;
                }

                params[count] = obj;
                count++;
                i += obj.length() - 1;
            }
            else{
                params[count] = Character.toString(c);
                count++;
            }
        }

        //If count is 1, remove unneccessary parentheses
        if(count == 1 && params[0].length() == 3){
            if(params[0].charAt(0) == '('){
                params[0] = params[0].substring(1, params[0].length() - 1);
            }
        }

        //Remove unneccessary parentheses from out
        if(out.charAt(0) == '('){
            int open = 1;
            int closed = 0;
            int pos = 0;

            while(closed < open){
                char p = out.charAt(pos);

                if(p == '(' && pos == 0){
                    pos++;
                    continue;
                }
                else if(p == '('){
                    open++;
                }
                else if(p == ')'){
                    closed++;
                }

                pos++;
            }

            if(pos == out.length()){
                out = out.substring(1, pos - 1);
            }

        }

        //Return correct combinator expression
        Expression outExp = new Expression();
        System.out.println("Number of parameters: " + Integer.toString(count));
        System.out.println("params Array: " + Arrays.toString(params));
        while(count > 0){
            outExp.setExpression(out);
            System.out.println("while -- out: " + out);
            System.out.println("while -- params[count - 1]: " + params[count - 1]);
            if(out.equals(params[count - 1])){
                combExp = "SKK";
            }
            else if(!(out.contains(params[count - 1]))){
                combExp =  "K(" + out + ")";
            }
            else{
                String first = outExp.getFirst();
                String rest = outExp.getRest();

                while(rest.equals("")){
                    out = out.substring(1, out.length() - 1);
                    outExp.setExpression(out);
                    System.out.println("New out: " + out);
                    first = outExp.getFirst();
                    rest = outExp.getRest();
                }

                System.out.println("while -- first: " + first);
                System.out.println("while -- rest: " + rest);

                combExp = "S(" + findCombinator(params[count - 1], first) + ")(" + findCombinator(params[count - 1], rest) + ")";
            }
            count--;
            System.out.println("In: " + in + ", Out: " + out + ", combExp: " + combExp + ", count: " + Integer.toString(count));
            out = combExp;
        }

        return combExp;
    }
}
