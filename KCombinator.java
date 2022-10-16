
public class KCombinator {

    public KCombinator(){
    }

    public String evaluate(String s){
        String copyS = s;
        int numOpen = copyS.length() - copyS.replace("(", "").length();
        copyS = s;
        int numClosed = copyS.length() - copyS.replace(")", "").length();
        int extraClosed = numClosed- numOpen;
        String[] params = getParameters(s);
        int paramSize = params[0].length() + params[1].length();
        String rest = s.substring(paramSize + extraClosed);
        System.out.println("string: " + s);
        System.out.print("paramSize, extraClosed: " + Integer.toString(paramSize) + Integer.toString(extraClosed));
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

                System.out.println("K -- param, count: " + params[count-1] + Integer.toString(count));
            }
            else{
                params[count] = Character.toString(c);
                count++;
                System.out.println("K -- param, count: " + params[count-1] + Integer.toString(count));
            }

            if(count == 2){
                break;
            }
        }

        return params;
    }
}
