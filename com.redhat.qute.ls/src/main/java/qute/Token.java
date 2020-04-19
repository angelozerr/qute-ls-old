/* Generated by: JavaCC 21 Parser Generator. Token.java */
package qute;

import java.util.*;
/**
 * Describes the input token stream.
 */
public class Token implements QUTEConstants,Node {
    // The token does not correspond to actual characters in the input.
    // It was typically inserted to (tolerantly) complete some grammatical production.
    private boolean virtual;
    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual=virtual;
    }

    private String inputSource="";
    /**
     * An integer that describes the kind of this token.  This numbering
     * system is determined by JavaCCParser, and a table of these numbers is
     * stored in the file ...Constants.java.
     */
    //    int kind;
    private TokenType type;
    public TokenType getType() {
        return type;
    }

    void setType(TokenType type) {
        this.type=type;
        //        this.kind = type.ordinal();
    }

    /**
     * beginLine and beginColumn describe the position of the first character
     * of this token; endLine and endColumn describe the position of the
     * last character of this token.
     */
    int beginLine,beginColumn,endLine,endColumn;
    /**
     * The string image of the token.
     */
    String image;
    private Token next;
    Token getNext() {
        return next;
    }

    void setNext(Token next) {
        this.next=next;
    }

    /**
     * This field is used to access special tokens that occur prior to this
     * token, but after the immediately preceding regular (non-special) token.
     * If there are no such special tokens, this field is set to null.
     * When there are more than one such special token, this field refers
     * to the last of these special tokens, which in turn refers to the next
     * previous special token through its specialToken field, and so on
     * until the first special token (whose specialToken field is null).
     * The next fields of special tokens refer to other special tokens that
     * immediately follow it (without an intervening regular token).  If there
     * is no such token, this field is null.
     */
    Token specialToken;
    boolean unparsed;
    public Token() {
    }

    public Token(TokenType type,String image) {
        this.type=type;
        //        this.kind = type.ordinal();
        this.image=image;
    }

    public boolean isUnparsed() {
        return unparsed;
    }

    public void setUnparsed(boolean unparsed) {
        this.unparsed=unparsed;
    }

    public void clearChildren() {
    }

    public String getNormalizedText() {
        if (virtual) {
            return"Virtual Token of type "+getType();
        }
        return image;
    }

    public String getRawText() {
        return image;
    }

    public String toString() {
        return getNormalizedText();
    }

    public static Token newToken(TokenType type,String image) {
        switch(type) {
            case COMMA:
            return new COMMA(TokenType.COMMA,image);
            case IN:
            return new IN(TokenType.IN,image);
            case AS:
            return new AS(TokenType.AS,image);
            case OR:
            return new OR(TokenType.OR,image);
            case OR2:
            return new OR2(TokenType.OR2,image);
            case AND:
            return new AND(TokenType.AND,image);
            case AND2:
            return new AND2(TokenType.AND2,image);
            case SIMPLE_EQUALS:
            return new SIMPLE_EQUALS(TokenType.SIMPLE_EQUALS,image);
            case EQUALS:
            return new EQUALS(TokenType.EQUALS,image);
            case EQUALS2:
            return new EQUALS2(TokenType.EQUALS2,image);
            case EQUALS3:
            return new EQUALS3(TokenType.EQUALS3,image);
            case GT:
            return new GT(TokenType.GT,image);
            case ALT_GT:
            return new ALT_GT(TokenType.ALT_GT,image);
            case GE:
            return new GE(TokenType.GE,image);
            case ALT_GE:
            return new ALT_GE(TokenType.ALT_GE,image);
            case LT:
            return new LT(TokenType.LT,image);
            case ALT_LT:
            return new ALT_LT(TokenType.ALT_LT,image);
            case LE:
            return new LE(TokenType.LE,image);
            case ALT_LE:
            return new ALT_LE(TokenType.ALT_LE,image);
            case DOT_DOT:
            return new DOT_DOT(TokenType.DOT_DOT,image);
            case PLUS:
            return new PLUS(TokenType.PLUS,image);
            case MINUS:
            return new MINUS(TokenType.MINUS,image);
            case TIMES:
            return new TIMES(TokenType.TIMES,image);
            case DIVIDE:
            return new DIVIDE(TokenType.DIVIDE,image);
            case DOT:
            return new DOT(TokenType.DOT,image);
            case EXCLAM:
            return new EXCLAM(TokenType.EXCLAM,image);
            case OPEN_BRACKET:
            return new OPEN_BRACKET(TokenType.OPEN_BRACKET,image);
            case CLOSE_BRACKET:
            return new CLOSE_BRACKET(TokenType.CLOSE_BRACKET,image);
            case NULL:
            return new NULL(TokenType.NULL,image);
            case TRUE:
            return new TRUE(TokenType.TRUE,image);
            case FALSE:
            return new FALSE(TokenType.FALSE,image);
            case INTEGER:
            return new INTEGER(TokenType.INTEGER,image);
            case DECIMAL:
            return new DECIMAL(TokenType.DECIMAL,image);
            case STRING_LITERAL:
            return new STRING_LITERAL(TokenType.STRING_LITERAL,image);
            case RAW_STRING:
            return new RAW_STRING(TokenType.RAW_STRING,image);
            case C_IDENTIFIER:
            return new C_IDENTIFIER(TokenType.C_IDENTIFIER,image);
            case JSON_STRING:
            return new JSONString(TokenType.JSON_STRING,image);
            case NUMBER:
            return new NumberLiteral2(TokenType.NUMBER,image);
            case OPEN_PAREN:
            return new Delimiter(TokenType.OPEN_PAREN,image);
            case CLOSE_PAREN:
            return new Delimiter(TokenType.CLOSE_PAREN,image);
            case TEXT:
            return new Text(TokenType.TEXT,image);
            case OPEN_CURLY:
            return new OPEN_CURLY(TokenType.OPEN_CURLY,image);
            case ABBREVIATED_END:
            return new ABBREVIATED_END(TokenType.ABBREVIATED_END,image);
            case EACH:
            return new EACH(TokenType.EACH,image);
            case ENDEACH:
            return new ENDEACH(TokenType.ENDEACH,image);
            case FOR:
            return new FOR(TokenType.FOR,image);
            case ENDFOR:
            return new ENDFOR(TokenType.ENDFOR,image);
            case IF:
            return new IF(TokenType.IF,image);
            case ELSEIF:
            return new ELSEIF(TokenType.ELSEIF,image);
            case ELSE:
            return new ELSE(TokenType.ELSE,image);
            case ENDIF:
            return new ENDIF(TokenType.ENDIF,image);
            case QUTE_INCLUDE:
            return new QUTE_INCLUDE(TokenType.QUTE_INCLUDE,image);
            case ENDINCLUDE:
            return new ENDINCLUDE(TokenType.ENDINCLUDE,image);
            case INSERT:
            return new INSERT(TokenType.INSERT,image);
            case ENDINSERT:
            return new ENDINSERT(TokenType.ENDINSERT,image);
            case WITH:
            return new WITH(TokenType.WITH,image);
            case ENDWITH:
            return new ENDWITH(TokenType.ENDWITH,image);
            case START_SECTION:
            return new START_SECTION(TokenType.START_SECTION,image);
            case END_SECTION:
            return new END_SECTION(TokenType.END_SECTION,image);
            case START_PARAMETER_DECL:
            return new START_PARAMETER_DECL(TokenType.START_PARAMETER_DECL,image);
            case OPEN_COMMENT:
            return new OPEN_COMMENT(TokenType.OPEN_COMMENT,image);
            case CLOSE_COMMENT:
            return new Comment(TokenType.CLOSE_COMMENT,image);
            case CLOSE_CURLY:
            return new CLOSE_CURLY(TokenType.CLOSE_CURLY,image);
            case CLOSE_EMPTY:
            return new CLOSE_EMPTY(TokenType.CLOSE_EMPTY,image);
        }
        return new Token(type,image);
    }

    public void setInputSource(String inputSource) {
        this.inputSource=inputSource;
    }

    public String getInputSource() {
        return inputSource;
    }

    public void setBeginColumn(int beginColumn) {
        this.beginColumn=beginColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn=endColumn;
    }

    public void setBeginLine(int beginLine) {
        this.beginLine=beginLine;
    }

    public void setEndLine(int endLine) {
        this.endLine=endLine;
    }

    public int getBeginLine() {
        return beginLine;
    }

    public int getBeginColumn() {
        return beginColumn;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    private Node parent;
    private Map<String,Object>attributes;
    public void setChild(int i,Node n) {
        throw new UnsupportedOperationException();
    }

    public void addChild(Node n) {
        throw new UnsupportedOperationException();
    }

    public void addChild(int i,Node n) {
        throw new UnsupportedOperationException();
    }

    public Node removeChild(int i) {
        throw new UnsupportedOperationException();
    }

    public boolean removeChild(Node n) {
        return false;
    }

    public int indexOf(Node n) {
        return-1;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent=parent;
    }

    public int getChildCount() {
        return 0;
    }

    public Node getChild(int i) {
        return null;
    }

    public List<Node>children() {
        return Collections.emptyList();
    }

    public void open() {
    }

    public void close() {
    }

    public Object getAttribute(String name) {
        return attributes==null?null:
        attributes.get(name);
    }

    public void setAttribute(String name,Object value) {
        if (attributes==null) {
            attributes=new HashMap<String,Object>();
        }
        attributes.put(name,value);
    }

    public boolean hasAttribute(String name) {
        return attributes==null?false:
        attributes.containsKey(name);
    }

    public Set<String>getAttributeNames() {
        if (attributes==null) return Collections.emptySet();
        return attributes.keySet();
    }

}
