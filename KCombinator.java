
public class KCombinator {

    public KCombinator(){
    }

    public String evaluate(String s){
        String[] params = getParameters(s);
        String rest = getRest(params, s);
        System.out.println("K eval -- rest:" + rest);

        return params[0] + rest;
    }

    public String[] getParameters(String s){
        String[] params = new String [2];
        int count = 0;

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            System.out.println("K -- char : " + c);

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
                    System.out.println("K while -- char :" + p);

                    if((p == '(') && (pos == i)){
                        obj += p;
                        pos++;
                        System.out.println("K -- obj, pos, open, closed: " + obj + Integer.toString(pos-1) + Integer.toString(open) + Integer.toString(closed));
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
                    System.out.println("K -- obj, pos, open, closed: " + obj + Integer.toString(pos-1) + Integer.toString(open) + Integer.toString(closed));
                }

                params[count] = obj;
                count++;
                i += obj.length() - 1;

                System.out.println("K -- param, count: " + params[count-1] + ", " + Integer.toString(count));
            }
            else{
                params[count] = Character.toString(c);
                count++;
                System.out.println("K -- param, count: " + params[count-1] + ", " + Integer.toString(count));
            }

            if(count == 2){
                break;
            }
        }

        return params;
    }

    public String getRest(String[] p, String s){
        int index;

        //Get index of last parameter
        if(p[1] != p[0]){
            index = s.indexOf(p[1]) + p[1].length();
        }
        else{
            int tempIndex = s.indexOf(p[0]) + p[0].length();
            index = s.indexOf(p[1], tempIndex) + p[1].length();
        }

        if(index >= s.length()){
            return "";
        }

        //Remove extra closed parentheses starting at index
        String rest = s.substring(index);
        StringBuilder sb = new StringBuilder(rest);
        while(sb.toString().charAt(0) == ')'){
            sb.deleteCharAt(0);

            if(sb.toString().length() == 0){
                break;
            }
        }

        return sb.toString();
    }
}
