import java.util.Map;
import java.util.Stack;

public class Stackk {
    Stack<String> stack = new Stack<>();
    Map stackk(Pars parser) {
        Map<String, Integer> tableOfVar = parser.tableOfVariables;

        System.out.println(tableOfVar);
        System.out.println();
        int a,b,res;

        for (int i =0; i<parser.tokensPolis.size();i++){
            System.out.println((i+1) + ")" +tableOfVar  + " Value: " + parser.tokensPolis.get(i));
            switch (parser.tokensPolis.get(i)) {
                case "=":
                    a = getInt(tableOfVar);
                    tableOfVar.put(stack.pop(), a);
                    break;
                case "+":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    res = a+b;
                    stack.push(String.valueOf(res));
                    break;
                case "-":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    res = b-a;
                    stack.push(String.valueOf(res));
                    break;
                case "/":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    res = b/a;
                    stack.push(String.valueOf(res));
                    break;
                case "%":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    res = b%a;
                    stack.push(String.valueOf(res));
                    break;
                case "^":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    res = (int) Math.pow(a, b);
                    stack.push(String.valueOf(res));
                    break;
                case "*":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    res = a*b;
                    stack.push(String.valueOf(res));
                    break;
                case "!=":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    boolean log = a!=b;
                    stack.push(String.valueOf(log));
                    break;
                case "==":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    log = a==b;
                    stack.push(String.valueOf(log));
                    break;
                case "<":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    log = b<a;
                    stack.push(String.valueOf(log));
                    break;
                case ">":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    log = b>a;
                    stack.push(String.valueOf(log));
                    break;
                case "<=":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    log = b<=a;
                    stack.push(String.valueOf(log));
                    break;
                case ">=":
                    a = getInt(tableOfVar);
                    b = getInt(tableOfVar);
                    log = b>=a;
                    stack.push(String.valueOf(log));
                    break;
                case "!F":
                    a = getInt(tableOfVar)-1;
                    boolean c = stack.pop().equals("true");
                    if (c==false){
                        i=a;
                    }
                    break;
                case "!":
                    i = getInt(tableOfVar) - 1;
                    break;
                default:
                    stack.push(String.valueOf(parser.tokensPolis.get(i)));
                    break;
            }
        }
        System.out.println();
        return tableOfVar;
    }

    private int getInt(Map<String, Integer> table) {
        Lexer lexer = new Lexer();
        switch (lexer.parse(stack.peek()).get(0).getLexeme()) {
            case VAR:
                return table.get(stack.pop());
            case DIGIT:
                return Integer.valueOf(stack.pop());
            default:
                System.err.println();
                System.exit(10);
        }
        return -1;
    }
}