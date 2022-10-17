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
}
