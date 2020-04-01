/* Generated by: JavaCC 21 Parser Generator. InvalidToken.java */
package qute;

/**
 * Token subclass to represent lexically invalid input
 */
public class InvalidToken extends Token {
    public InvalidToken(String image) {
        super(QUTEConstants.INVALID,image);
    }

    public InvalidToken() {
        super(QUTEConstants.INVALID);
    }

    public String getNormalizedText() {
        return"Lexically Invalid Input:"+image;
    }

    public boolean isDirty() {
        return true;
    }

}