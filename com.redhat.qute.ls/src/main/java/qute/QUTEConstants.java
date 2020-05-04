/* Generated by: JavaCC 21 Parser Generator. Do not edit. QUTEConstants.java */
package qute;

/**
 * Token literal values and constants.
 */
public interface QUTEConstants {
    public enum TokenType {
        EOF, COMMA, IN, AS, OR, OR2, AND, AND2, SIMPLE_EQUALS, EQUALS, EQUALS2, EQUALS3, GT, ALT_GT, GE, ALT_GE, LT, ALT_LT, LE, ALT_LE, DOT_DOT, PLUS, MINUS, TIMES, DIVIDE, DOT, EXCLAM, OPEN_BRACKET, CLOSE_BRACKET, NULL, TRUE, FALSE, INTEGER, DECIMAL, STRING_LITERAL, RAW_STRING, _TOKEN_36, ASCII_LETTER, ASCII_DIGIT, C_IDENTIFIER, ESCAPE1, ESCAPE2, REGULAR_CHAR, JSON_STRING, ZERO, NON_ZERO, FRACTION, EXPONENT, NUMBER, OPEN_PAREN, CLOSE_PAREN, NO_OPEN_CURLY, ESCAPED_OPEN_CURLY, WS, TEXT, OPEN_CURLY, ABBREVIATED_END, EACH, ENDEACH, FOR, ENDFOR, IF, ELSEIF, ELSE, ENDIF, QUTE_INCLUDE, ENDINCLUDE, INSERT, ENDINSERT, WITH, ENDWITH, START_SECTION, END_SECTION, START_PARAMETER_DECL, OPEN_COMMENT, _TOKEN_75, CLOSE_COMMENT, CLOSE_CURLY, CLOSE_EMPTY, INVALID
    }
    /**
   * Lexical States
   */
    public enum LexicalState {
        QUTE_TEXT, QUTE_EXPRESSION, IN_COMMENT, 
    }
    String[] tokenImage={"<EOF>", "\",\"", "\"in\"", "\"as\"", "\"||\"", "\"or\"", "\"&&\"", "\"and\"", "\"=\"", "\"==\"", "\"eq\"", "\"is\"", "\">\"", "\"gt\"", "\">=\"", "\"ge\"", "\"<\"", "\"lt\"", "\"<=\"", "\"le\"", "\"..\"", "\"+\"", "\"-\"", "\"*\"", "\"/\"", "\".\"", "\"!\"", "\"[\"", "\"]\"", "\"null\"", "\"true\"", "\"false\"", "<INTEGER>", "<DECIMAL>", "<STRING_LITERAL>", "<RAW_STRING>", "<_TOKEN_36>", "<ASCII_LETTER>", "<ASCII_DIGIT>", "<C_IDENTIFIER>", "<ESCAPE1>", "<ESCAPE2>", "<REGULAR_CHAR>", "<JSON_STRING>", "\"0\"", "<NON_ZERO>", "<FRACTION>", "<EXPONENT>", "<NUMBER>", "\"(\"", "\")\"", "<NO_OPEN_CURLY>", "\"\\\\{\"", "<WS>", "<TEXT>", "\"{\"", "\"{/}\"", "<EACH>", "\"{/each}\"", "<FOR>", "\"{/for}\"", "<IF>", "<ELSEIF>", "<ELSE>", "\"{/if}\"", "<QUTE_INCLUDE>", "\"{/include}\"", "<INSERT>", "\"{/insert}\"", "<WITH>", "\"{/with}\"", "<START_SECTION>", "<END_SECTION>", "<START_PARAMETER_DECL>", "\"{!\"", "<_TOKEN_75>", "\"!}\"", "\"}\"", "\"/}\"", };
}
