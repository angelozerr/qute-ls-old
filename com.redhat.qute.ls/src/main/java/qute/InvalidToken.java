/* Generated by: JavaCC 21 Parser Generator. InvalidToken.java */
package qute;

/**
 * Token subclass to represent lexically invalid input
 */
public class InvalidToken extends Token {
    public InvalidToken(String image) {
        super(TokenType.INVALID, image, "input");
    }

    public InvalidToken() {
        super(TokenType.INVALID, null, "input");
    }

    public String getNormalizedText() {
        return"Lexically Invalid Input:"+getImage();
    }

    public boolean isDirty() {
        return true;
    }

}
