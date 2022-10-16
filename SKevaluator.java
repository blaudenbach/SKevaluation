public class SKevaluator{
    SCombinator S;
    KCombinator K;
    Expression expression;

    public SKevaluator(){
        S = new SCombinator();
        K = new KCombinator();
        expression = new Expression();
    }

    public String evaluate(String expr){
        expression.setExpression(expr);
        while(expression.containsSK()){
            String copy = "";

            for(int i = 0; i < expression.getExpression().length(); i++){
                char c = expression.getExpression().charAt(i);
                System.out.println(c);

                if(c == 'S'){
                    copy += S.evaluate(expression.getExpression().substring(i+1));
                    System.out.println("SK -- " + copy);
                    break;
                }
                else if(c == 'K'){
                    copy += K.evaluate(expression.getExpression().substring(i+1));
                    System.out.println("SK -- " + copy);
                    break;
                }
                else{
                    copy += Character.toString(c);
                    System.out.println("SK -- " + copy);
                }
            }

            expression.setExpression(copy);
        }
        return expression.getExpression();
    }
}
