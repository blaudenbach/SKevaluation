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
                        System.out.println("Initial expression " + expr + " evalutated as: " + expression.getExpression());
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
                        System.out.println("Initial expression " + expr + " evalutated as: " + expression.getExpression());
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

            if(expression.getExpression().equals(expr)){
                expression.finalize();
                return expression.getExpression();
            }
        }

        expression.finalize();

        System.out.println("Initial expression " + expr + " evaluated as: " + expression.getExpression());

        return expression.getExpression();
    }

    String findCombinator(String in, String out){
        int count = 0;
        String combExp = "";

        //Find number of arguments in "in"
        //Iterate through string and evaluate
        for(int i = 0; i < in.length(); i++){
            //Get character at position
            char c = in.charAt(i);
            //Ignore closed parentheses
            if(c == ')'){
                continue;
            }
            //If open parenthesis, get everything inside parentheses and increment count
            else if(c == '('){
                int open = 1;
                int closed = 0;
                int pos = i;

                //Get everything until # of closed parentheses equals # of open parentheses
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
            //If anything else, increment count
            else{
                count++;
            }
        }

        //System.out.println("Number of arguments in input: " + Integer.toString(count));

        //We now know how many arguments, so fill an array with them
        //Similar to above step, except an array is filled
        //These represent the x1,x2,...,xn
        //Create and fill array of arguments
        String[] args = new String[count];
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

                args[count] = obj;
                count++;
                i += obj.length() - 1;
            }
            else{
                args[count] = Character.toString(c);
                count++;
            }
        }

        //If count is 1, remove unneccessary parentheses
        //For instance, (a) becomes a. Does not do anything to (ab)
        if(count == 1 && args[0].length() == 3){
            if(args[0].charAt(0) == '('){
                args[0] = args[0].substring(1, args[0].length() - 1);
            }
        }

        //Remove unneccessary parentheses from out
        //If "out" is inside a pair of parentheses, remove them
        //For instance, (a) becomes a - helps if "in" is a, so we can just return SKK.
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

        //Form and return correct combinator expression
        //All System.out.println calls are strictly for debugging
        //Create Expression object, so we can use it's methods
        Expression outExp = new Expression();
        System.out.println("Number of parameters: " + Integer.toString(count));
        System.out.println("params Array: " + Arrays.toString(args));
        //Continues until all arguments x1,x2,...,xn have been considered
        while(count > 0){
            //Set the expression we wish to form
            outExp.setExpression(out);
            System.out.println("while -- out: " + out);
            System.out.println("while -- params[count - 1]: " + args[count - 1]);
            //If out is equal to the argument being considered, return identity
            if(out.equals(args[count - 1])){
                combExp = "SKK";
            }
            //If out does not contain the argument at all, we use the K combinator
            else if(!(out.contains(args[count - 1]))){
                combExp =  "K(" + out + ")";
            }
            //Otherwise, out = AB
            //We create a combinatory X1 such that X1(arg) evaluates to A and X2 such that X2(arg) evaluates to B
            //Then, return S(X1)(X2)
            else{
                //We need a way to be consistent in our choice of what is A and what is B
                //I opted for the first argument in the out expression to be A and what remains to be B
                String first = outExp.getFirst();
                String rest = outExp.getRest();

                //If the rest is empty, first is contained in a pair of parentheses, so we must delete the parentheses and re-evaluate until we have something for first and rest
                while(rest.equals("")){
                    out = out.substring(1, out.length() - 1);
                    outExp.setExpression(out);
                    System.out.println("New out: " + out);
                    first = outExp.getFirst();
                    rest = outExp.getRest();
                }

                System.out.println("while -- first: " + first);
                System.out.println("while -- rest: " + rest);

                //Our combinatory is S(X1)(X2), as explained above
                combExp = "S(" + findCombinator(args[count - 1], first) + ")(" + findCombinator(args[count - 1], rest) + ")";
            }

            //Decrease count, so next argument is considered
            count--;
            System.out.println("In: " + in + ", Out: " + out + ", combExp: " + combExp + ", count: " + Integer.toString(count));

            //Next iteration must form our newly found expression out of the next argument
            //So, we set our out expression to what we just found and the process is repeated
            out = combExp;
        }

        //After all arguments have been considered, we return our complete expression
        return combExp;
    }
}
