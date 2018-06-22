import java.util.*;
/*
lang - expr
expr - (init | ifElseLoop)
init - TYPE assign
assign - assignOp SEM
assignOp - VAR ASSIGN_OP value
value - val (opValue)*
val - VAR | DIGIT | breakValue
opValue - OP val
breakValue - L_R_SQU value R_R_SQU
ifElseLoop - IF ifElseExpr ifElseBody ELSE ifElseBody
ifElseExpr - L_R_SQU logExpr R_R_SQU
logExpr - (assignOp | value) LOG_OP (assignOp | value)
ifElseBody - L_F_SQU (ifElseForm)* R_F_SQU
ifElseForm - (init | assign)
VAR - [a-z]+
DIGIT - 0|[1-9][0-9]*
OP - +|-|*|/
LOG_OP - <|>|<=|>=|!=|==
TYPE - int
ASSIGN_OP - =
L_R_SQU - (
R_R_SQU - )
L_F_SQU - {
R_F_SQU - }
*/
public class Pars {
    Map<String, Integer> tableOfVariables = new HashMap<>();
    List<String> tokensPolis = new ArrayList<>();
    private Stack<String> stack = new Stack<>();
    private MyLinkedList<Token> tokens = new MyLinkedList<>();
    private int position = 0;
    private int p1;
    private int p2;

    boolean lang(List<Token> tokens) {
        boolean lang = false;
        for (Token token : tokens) {
            if (token.getLexeme() != Lexeme.WS) {
                this.tokens.addLast(token);
            }
        }
        while (this.tokens.size() != position) {
            if (!expr()) {
                System.err.println(" Error: Syntax mistake ");
                System.exit(4);
            } else
                lang = true;
        }
        System.out.println(tokensPolis);
        return lang;
    }

    private boolean expr() {
        boolean expr = false;

        if (init() || ifElseLoop()) {
            expr = true;
        }
        return expr;
    }

    private boolean init() {
        boolean init = false;
        int oldPosition = position;

        if (getTokenInc() == Lexeme.TYPE) {
            if (assign()) {
                init = true;
            }
        }
        position = init ? position : oldPosition;
        return init;
    }

    private boolean assign() {
        boolean assign = false;
        int oldPosition = position;

        if (assignOp()) {
            if (getTokenInc() == Lexeme.SEM) {
                assign = true;
            }
        }
        position = assign ? position : oldPosition;
        return assign;
    }

    private boolean ifElseLoop() {
        boolean ifElseLoop = false;
        int oldPosition = position;

        if (getTokenInc() == Lexeme.IF) {
            if (ifElseExpr()) {
                if (ifElseBody()) {
                    if (getTokenInc() == Lexeme.ELSE){
                        p2 = tokensPolis.size();
                        tokensPolis.add("p2");
                        tokensPolis.add("!");
                        tokensPolis.set(p1,String.valueOf(tokensPolis.size()));
                        if (ifElseBody()){
                            ifElseLoop = true;
                            tokensPolis.set(p2,String.valueOf((tokensPolis).size()));
                        }
                    }
                }
            }
        }
        position = ifElseLoop ? position : oldPosition;
        return ifElseLoop;
    }

    private boolean ifElseExpr() {
        boolean ifElseExpr = false;
        int oldPosition = position;
        if (getTokenInc() == Lexeme.L_R_SQU) {
            if (logExpr()) {
                if (getTokenInc() == Lexeme.R_R_SQU) {
                    ifElseExpr = true;
                }
            }
        }
        position =ifElseExpr ?position :oldPosition;
        return ifElseExpr;
    }

    private boolean logExpr() {
        boolean logExpr = false;
        int oldPosition = position;

        if (assignOp() || value()) {
            if (getTokenInc() == Lexeme.LOG_OP) {
                String logOp = getLastTokenValue();
                if (assignOp() || value()) {
                    logExpr = true;
                    tokensPolis.add(logOp);
                    p1 = tokensPolis.size();
                    tokensPolis.add("p1");
                    tokensPolis.add("!F");
                }
            }
        }
        position = logExpr ? position : oldPosition;
        return logExpr;
    }

    private boolean ifElseBody() {
        boolean ifElseBody = false;
        int oldPosition = position;

        if (getTokenInc() == Lexeme.L_F_SQU) {
            while (ifElseForm()) {
            }
            if (getTokenInc() == Lexeme.R_F_SQU) {
                ifElseBody = true;
            }
        }
        position = ifElseBody ? position : oldPosition;
        return ifElseBody;
    }

    private boolean ifElseForm() {
        boolean ifElseForm  = false;

        if (init() || assign()) {
            ifElseForm = true;
        }
        return ifElseForm;
    }

    private boolean assignOp() {
        boolean assignOp = false;
        int oldPosition = position;
        boolean add = false;
        String var = null;

        if (getTokenInc() == Lexeme.VAR) {
            add = tokensPolis.add(getLastTokenValue());
            var = getLastTokenValue();
            if (getTokenInc() == Lexeme.ASSIGN_OP) {
                stack.push(getLastTokenValue());
                if (value()) {
                    assignOp = true;
                    tableOfVariables.put(var, 0);
                }
            }
        }
        if (add && !assignOp) {
            tokensPolis.remove(tokensPolis.size()-1);
        }
        if (assignOp) {
            while (!stack.empty()) {
                tokensPolis.add(stack.pop());
            }
        }
        position = assignOp ? position : oldPosition;
        return assignOp;
    }

    private boolean value() {
        boolean value = false;

        if (val()) {
            while (opValue()) {
            }
            value = true;
        }
        return value;
    }

    private boolean opValue() {
        boolean opValue = false;
        int oldPosition = position;

        if (getTokenInc() == Lexeme.OP) {
            String arthOp = getLastTokenValue();
            while (getPriority(arthOp) <= getPriority(stack.peek())) {
                tokensPolis.add(stack.pop());
            }
            stack.push(arthOp);
            if (val()) {
                opValue = true;
            }
        }
        position = opValue ? position : oldPosition;
        return opValue;
    }

    private boolean val() {
        boolean val = false;

        if (getTokenInc() == Lexeme.VAR) {
            tokensPolis.add(getLastTokenValue());
            return true;
        } else {
            position--;
        }
        if (getTokenInc() == Lexeme.DIGIT) {
            tokensPolis.add(getLastTokenValue());
            return true;
        } else {
            position--;
        }
        if (breakValue()) {
            return true;
        }
        return val;
    }

    private boolean breakValue() {
        boolean breakValue = false;
        int oldPosition = position;

        if (getTokenInc() == Lexeme.L_R_SQU) {
            stack.push(getLastTokenValue());
            if (value()) {
                if (getTokenInc() == Lexeme.R_R_SQU) {
                    while (!stack.peek().equals("(")) {
                        tokensPolis.add(stack.pop());
                    }
                    stack.pop();

                    breakValue = true;
                }
            }
        }
        position = breakValue ? position : oldPosition;
        return breakValue;
    }

    private Lexeme getTokenInc() {
        return tokens.get(position++).getLexeme();
    }

    private String getLastTokenValue() {
        return tokens.get(position-1).getValue();
    }

    private int getPriority(String str) {
        switch (str) {
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 2;
            case "^":
                return 2;
            case "/":
                return 2;
            case "%":
                return 2;
            case "=":
                return 0;
            case "(":
                return 0;
            default:
                System.err.println("Error: In symbol " + str);
                System.exit(5);
                return 0;
        }
    }
}
