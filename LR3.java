import java.util.List;

public class LR3 {
    public static void main(String[] args) {

        Lexer lexer = new Lexer();
        Pars parser = new Pars();
        Stackk stackk = new Stackk();
        String input = "int a=2; int b=5; if(a<b){a=a+b;} else{b=b+a;}";
        List<Token> tokens = lexer.parse(input);

        System.out.println();
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println();
        System.out.println("[ " + input + " ]");
        System.out.println();
        System.out.println(parser.lang(tokens));
        System.out.println();
        System.out.println(stackk.stackk(parser));

        MyLinkedList<String> e = new MyLinkedList();
        e.addLast("First");
        e.addLast("Second");
        e.addLast("Third");
        System.out.println();
        System.out.println(e.get(0));

    }

}


