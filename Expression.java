public class Expression {
    public String expression;

    Expression(){
        expression = "";
    }

    public void setExpression(String expr){
        expression = expr;
    }

    public String getExpression(){
        return expression;
    }

    public boolean containsSK(){
        return (expression.contains("S") || expression.contains("K"));
    }

    public void finalize(){
        int index = expression.length() - 1;

        while(index >= 0){
            index = expression.lastIndexOf("(", index);
            if(index >= 0){
                int numOpen = 0;
                int numClosed = 0;
                for(int i = index; i <= expression.length() - 1; i++){
                    if(expression.charAt(i) == '('){
                        numOpen++;
                    }
                    else if(expression.charAt(i) == ')'){
                        numClosed++;
                    }
                }
                if(numOpen > numClosed){
                    StringBuilder sb = new StringBuilder(expression);
                    sb.deleteCharAt(index);
                    expression = sb.toString();
                }
                index -= 1;
            }
        }

    }

    public String getSecond(){
        String second = "";

        //Iterate through expression and grab last argument
        //Is either 1 character or characters inside a pair of parentheses
        for(int i = expression.length() - 1; i >= 0; i--){
            char c = expression.charAt(i);

            if(c == '('){
                continue;
            }
            //Copy everything inside parentheses
            else if(c == ')'){
                int open = 0;
                int closed = 1;
                String obj = "";
                int pos = i;

                while(open < closed){
                    char p = expression.charAt(pos);

                    if(p == ')' && pos == i){
                        obj += p;
                        pos--;
                        continue;
                    }
                    else if(p == ')'){
                        closed++;
                    }
                    else if(p =='('){
                        open++;
                    }

                    obj += p;
                    pos--;
                }

                //Correct object to not be reversed
                String copy = "";
                for(int j = obj.length() - 1; j >= 0; j--){
                    copy += obj.charAt(j);
                }

                return copy;
            }
            else{
                return Character.toString(c);
            }
        }

        return second;
    }

    public String getFirst(){
        //Get's last argument
        String second = this.getSecond();
        //System.out.println("getFirst() -- second = " + second);
        //Gets location of last argument
        int secondLoc = expression.lastIndexOf(second);
        //System.out.println("getFirst() -- secondLoc = " + Integer.toString(secondLoc));
        //Forms substring which represents the rest of the expression
        String first = expression.substring(0, secondLoc);
        //System.out.println("getFirst() -- first = " + first);
        
        return first;
    }

    public int getNumParentheses(){
        int numParentheses = 0;

        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '(' || expression.charAt(i) == ')'){
                numParentheses++;
            }
        }

        return numParentheses;
    }

    public void deparenthesize(){
        int checkedParentheses = 0;
        while(this.getNumParentheses() != checkedParentheses){
            checkedParentheses = 0;
            System.out.println(this.getExpression());
            for(int i = 0; i < expression.length(); i++){
                char c = expression.charAt(i);
                if(c == '(' || c == ')'){
                    if(c == '('){
                        int t = Character.getNumericValue(expression.charAt(i+1));
                        if(t >= 1 && t <= 9){
                            checkedParentheses++;
                            continue;
                        }
                    }
                    if(c == ')'){
                        int t;
                        try{
                            t = Character.getNumericValue(expression.charAt(i-2));
                            System.out.println(expression.charAt(i-2));
                        }
                        catch(Exception e){
                            t = -1;
                            System.out.println("Invalid index");
                        }
                        if(t >= 1 && t<= 9){
                            checkedParentheses++;
                            continue;
                        }
                    }
                    StringBuilder sb = new StringBuilder(expression);
                    sb.deleteCharAt(i);
                    expression = sb.toString();
                    break;
                }
            }
        }
        System.out.println(this.getNumParentheses() + "" + checkedParentheses);
    }

}
