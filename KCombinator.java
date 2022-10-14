package MATH485;

public class KCombinator {

    public KCombinator(){
    }

    public String evaluate(String s){
        String[] params = getParameters(s);
        int paramSize = params[0].length() + params[1].length();
        String rest = s.substring(paramSize);

        return params[0] + rest;
    }

    public String[] getParameters(String s){
        String[] params = new String [2];
        int count = 0;

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            if(count == 2){
                break;
            }

            if(c == ')'){
                continue;
            }
            else if(c == '('){
                int open = 1;
                int closed = 0;
                String obj = "";
                int pos = i;

                while(closed < open){
                    char p = s.charAt(pos);

                    if(p == ')'){
                        closed++;
                    }

                    obj += p;
                }

                params[count] = obj;
                count++;
            }
            else{
                params[count] = Character.toString(c);
                count++;
            }
        }

        return params;
    }
}
