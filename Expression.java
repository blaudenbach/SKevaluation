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
}
