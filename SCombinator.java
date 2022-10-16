
public class SCombinator {

    public SCombinator(){
    }

    public String evaluate(String s){
        String copyS = s;
        int numOpen = copyS.length() - copyS.replace("(", "").length();
        copyS = s;
        int numClosed = copyS.length() - copyS.replace(")", "").length();
        int extraClosed = numClosed- numOpen;
        String[] params = getParameters(s);
        int paramSize = params[0].length() + params[1].length() + params[2].length();
        String rest = s.substring(paramSize + extraClosed);
        System.out.println("S eval -- rest:" + rest);

        return params[0] + params[2] + '(' + params[1] + params[2] + ')' + rest;
    }

    public String[] getParameters(String s){
        String[] params = new String [3];
        int count = 0;

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            System.out.println("S -- char:" + c);

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
                    System.out.println("S while -- char:" + p);

                    if((p == '(') && (pos == i)){
                        obj += p;
                        pos++;
                        System.out.println("S -- obj, pos, open, closed: " + obj + Integer.toString(pos-1) + Integer.toString(open) + Integer.toString(closed));
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
                    System.out.println("S -- obj, pos, open, closed: " + obj + Integer.toString(pos-1) + Integer.toString(open) + Integer.toString(closed));
                }

                params[count] = obj;
                count++;
                i += obj.length() - 1;

                System.out.println("S -- param, count: " + params[count-1] + Integer.toString(count));
            }
            else{
                params[count] = Character.toString(c);
                count++;
                System.out.println("S -- param, count: " + params[count-1] + Integer.toString(count));
            }

            if(count == 3){
                break;
            }
        }

        return params;
    }
}
