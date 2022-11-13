//import java.util.*;
import java.io.*;

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
            //System.out.println("eval starting exp -- " + expression.getExpression());
            String copy = "";

            for(int i = 0; i < expression.getExpression().length(); i++){
                char c = expression.getExpression().charAt(i);
                //System.out.println(c);

                if(c == 'S'){
                    try{
                        copy += S.evaluate(expression.getExpression().substring(i+1));
                    }
                    catch(NullPointerException e){
                        expression.finalize();
                        //System.out.println("Initial expression " + expr + " evalutated as: " + expression.getExpression());
                        return expression.getExpression();
                    }

                    //System.out.println("eval -- " + copy);
                    break;
                }
                else if(c == 'K'){
                    try{
                        copy += K.evaluate(expression.getExpression().substring(i+1));
                    }
                    catch(NullPointerException e){
                        expression.finalize();
                        //System.out.println("Initial expression " + expr + " evalutated as: " + expression.getExpression());
                        return expression.getExpression();
                    }

                    //System.out.println("eval -- " + copy);
                    break;
                }
                else{
                    copy += Character.toString(c);
                    //System.out.println("eval -- " + copy);
                }
            }

            expression.setExpression(copy);

            if(expression.getExpression().equals(expr)){
                expression.finalize();
                return expression.getExpression();
            }
        }

        expression.finalize();

        //System.out.println("Initial expression " + expr + " evaluated as: " + expression.getExpression());

        return expression.getExpression();
    }

    public String findCombinator(String in, String out){
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

                
                i = pos - 1;
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

        int outCount = 0;

        //Find number of arguments in "in"
        //Iterate through string and evaluate
        for(int i = 0; i < out.length(); i++){
            //Get character at position
            char c = out.charAt(i);
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
                    char p = out.charAt(pos);

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

                i = pos - 1;
                outCount++;
            }
            //If anything else, increment count
            else{
                outCount++;
            }
        }

        //System.out.println("Number of arguments in output: " + Integer.toString(outCount));

        //We now know how many arguments, so fill an array with them
        //Similar to above step, except an array is filled
        //These represent the x1,x2,...,xn
        //Create and fill array of arguments
        String[] outArgs = new String[outCount];
        outCount = 0;
        for(int i = 0; i < out.length(); i++){
            char c = out.charAt(i);

            if(c == ')'){
                continue;
            }
            else if(c == '('){
                int open = 1;
                int closed = 0;
                String obj = "";
                int pos = i;

                while(closed < open){
                    char p = out.charAt(pos);

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

                outArgs[outCount] = obj;
                outCount++;
                i += obj.length() - 1;
            }
            else{
                outArgs[outCount] = Character.toString(c);
                outCount++;
            }
        }

        //If count is 1, remove unneccessary parentheses
        //For instance, (a) becomes a. Does not do anything to (ab)
        if(count == 1 && args[0].length() == 3){
            if(args[0].charAt(0) == '('){
                args[0] = args[0].substring(1, args[0].length() - 1);
            }
        }

        //If outCount is 1, remove unneccessary parentheses
        //For instance, (a) becomes a. Does not do anything to (ab)
        if(outCount == 1 && outArgs[0].length() == 3){
            if(outArgs[0].charAt(0) == '('){
                outArgs[0] = outArgs[0].substring(1, outArgs[0].length() - 1);
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
        //System.out.println("Number of parameters: " + Integer.toString(count));
        //System.out.println("params Array: " + Arrays.toString(args));
        //Continues until all arguments x1,x2,...,xn have been considered
        //Conditionals to make output shorter (test-cases)
        if(outCount == 1 && count == 2 && outArgs[0].equals(args[0])){
            return "K";
        }
        else if(count == 3 && out.equals(args[0] + args[2] + "(" + args[1] + args[2] + ")")){
            return "S";
        }
        else if(out.equals(in)){
            return "SKK";
        }
        else if(outCount == 2 && count == 1 && outArgs[0].equals(args[0]) && outArgs[1].equals(args[0])){
            return "S(SKK)(SKK)";
        }
        
        while(count > 0){
            if(count > 1){
                System.out.println(count);
            }
            //Set the expression we wish to form
            outExp.setExpression(out);
            //System.out.println("while -- out: " + out);
            //System.out.println("while -- params[count - 1]: " + args[count - 1]);
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
                String second = outExp.getSecond();
                String first = outExp.getFirst();

                //If the rest is empty, first is contained in a pair of parentheses, so we must delete the parentheses and re-evaluate until we have something for first and rest
                while(first.equals("")){
                    out = out.substring(1, out.length() - 1);
                    outExp.setExpression(out);
                    //System.out.println("New out: " + out);
                    second = outExp.getSecond();
                    first = outExp.getFirst();
                }

                //System.out.println("while -- first: " + first);
                //System.out.println("while -- second: " + second);

                //Our combinatory is S(X1)(X2), as explained above
                combExp = "S(" + findCombinator(args[count - 1], first) + ")(" + findCombinator(args[count - 1], second) + ")";
            }

            //Decrease count, so next argument is considered
            count--;
            //System.out.println("In: " + in + ", Out: " + out + ", combExp: " + combExp + ", count: " + Integer.toString(count));

            //Next iteration must form our newly found expression out of the next argument
            //So, we set our out expression to what we just found and the process is repeated
            out = combExp;
        }

        //After all arguments have been considered, we return our complete expression
        return combExp;
    }

    public String readFile(String inName){
        String outName;
        if(inName.equals("positions.txt")){
            outName = "posResults.txt";
        }
        else if(inName.equals("foolsmate.txt")){
            outName = "fmResults.txt";
        }
        else{
            outName = "results.txt";
        }
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader(inName));
            File outFile = new File(outName);
            outFile.createNewFile();
            FileWriter writer = new FileWriter(outFile);

            int lineNum = 1;
            String line = reader.readLine();

            while(line != null){
                //Parse line
                int i1 = line.indexOf('|');
                int i3 = line.lastIndexOf('|');
                int i2 = line.indexOf('|', i1 + 1);
                String pos = line.substring(i1 + 1, i2);
                int prevPosNum;
                
                if(lineNum == 1){
                    prevPosNum = -1;
                }
                else{
                    prevPosNum = Integer.parseInt(line.substring(i2 + 1, i3));
                }

                //Get previous position
                String prevPos = "";
                BufferedReader tempReader;
                if(prevPosNum == -1){
                    prevPos = "-";
                }
                else{
                    tempReader = new BufferedReader(new FileReader(inName));
                    String tempLine = tempReader.readLine();

                    while(tempLine != null){
                        //Parse line
                        int temp1 = tempLine.indexOf('|');
                        int temp2 = tempLine.indexOf('|', temp1 + 1);
                        int tempPosNum = Integer.parseInt(tempLine.substring(0, temp1));
                        String tempPos = tempLine.substring(temp1 + 1, temp2);

                        if(tempPosNum == prevPosNum){
                            prevPos = tempPos;
                            break;
                        }

                        tempLine = tempReader.readLine();
                    }

                    tempReader.close();
                }

                String combinator = this.findCombinator(prevPos, pos);

                Expression evaluation = new Expression();
                evaluation.setExpression(this.evaluate(combinator + prevPos));
                evaluation.deparenthesize();

                boolean works = pos.equals(evaluation.getExpression());

                writer.write(combinator + "|" + prevPos + "|" + pos + "|" + works + "|\n");

                line = reader.readLine();
                lineNum++;
            }

            reader.close();
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return outName;
    }

}
