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

    String getFirst(){
        String first = "";

        //Iterate through expression and grab first argument
        //Is either 1 character or characters inside a pair of parentheses
        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);

            if(c == ')'){
                continue;
            }
            //Copy everything inside parentheses
            else if(c == '('){
                int open = 1;
                int closed = 0;
                String obj = "";
                int pos = i;

                while(closed < open){
                    char p = expression.charAt(pos);

                    if(p == '(' && pos == i){
                        obj += p;
                        pos ++;
                        continue;
                    }
                    else if(p == '('){
                        open++;
                    }
                    else if(p ==')'){
                        closed++;
                    }

                    obj += p;
                    pos++;
                }

                return obj;
            }
            else{
                return Character.toString(c);
            }
        }

        return first;
    }

    String getRest(){
        //Get's first argument
        String first = this.getFirst();
        //Gets location of first argument
        int firstLoc = expression.indexOf(first);
        //Uses firstLoc and length of first argument to find location of rest
        int restLoc = firstLoc + first.length();
        //Forms substring which represents the rest of the expression
        String rest = expression.substring(restLoc);
        
        return rest;
    }
}
