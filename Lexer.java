import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class Lexer {
    private String acc = "";
    private int position = 0;
    private boolean waitForSuccess = true;
    private Lexeme currentLexeme = null;

    List<Token> parse(String input) {
        List<Token> tokens = new ArrayList<>();

        if (input.length() != 0) {
            while (position < input.length()) {
                acc += input.charAt(position++);
                if (!find()) {
                    if (!waitForSuccess) {
                        waitForSuccess = true;
                        Token token = new Token(currentLexeme, format(acc));
                        tokens.add(token);
                        acc = "";
                        back();
                    } else {
                        waitForSuccess = true;
                        System.err.println('\n' + "Can't recognize input '" + acc + "' at position:" + position + "!");
                        System.exit(2);
                    }
                } else {
                    waitForSuccess = false;
                }
            }
            tokens.add(new Token(currentLexeme, acc));
        }else {
            System.err.println('\n' + "Error: Null input!");
            System.exit(1);
        }
        return tokens;
    }

    private void back() {
        position--;
    }

    private boolean find() {
        for (Lexeme lexeme : Lexeme.values()) {
            Matcher matcher = lexeme.getPattern().matcher(acc);
            if (matcher.matches()) {
                currentLexeme = lexeme;
                return true;
            }
        }
        return false;
    }

    private String format(String acc) {
        return acc.substring(0, acc.length() - 1);
    }
}