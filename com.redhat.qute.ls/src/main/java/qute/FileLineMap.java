/* Generated by: JavaCC 21 Parser Generator. FileLineMap.java */
package qute;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
/**
 * Rather bloody-minded implementation of a class to read in a file 
 * and store the contents in a String, and keep track of where the 
 * lines are.
 */
public class FileLineMap {
    private static FileLineMap singleton;
    private static Map<String, FileLineMap>lookup=Collections.synchronizedMap(new HashMap<>());
    private static final int[] EMPTY_INT=new int[0];
    // Munged content, possibly replace unicode escapes, tabs, or CRLF with LF.
    private final CharSequence content;
    // Typically a filename, I suppose.
    private final String inputSource;
    // A list of offsets of the beginning of lines
    private final int[] lineOffsets;
    private int startingLine, startingColumn;
    private int bufferPosition, tokenBeginOffset, tokenBeginColumn, tokenBeginLine, line, column;
    private final List<Token>tokenList;
    public FileLineMap(Reader reader, int startingLine, int startingColumn) {
        this("", readToEnd(reader), startingLine, startingColumn);
    }

    public FileLineMap(String inputSource, CharSequence content) {
        this(inputSource, content, 1, 1);
    }

    public FileLineMap(String inputSource, CharSequence content, int startingLine, int startingColumn) {
        this.inputSource=inputSource;
        this.content=mungeContent(content, 0, true, false);
        this.lineOffsets=createLineOffsetsTable(this.content);
        this.tokenList=new LinkedList<>();
        this.setStartPosition(startingLine, startingColumn);
        if (inputSource!=null&&inputSource.length()>0) {
            lookup.put(inputSource, this);
            if (lookup.size()==1) {
                singleton=this;
            }
            else {
                singleton=null;
            }
        }
    }

    static FileLineMap getFileLineMap(String inputSource) {
        if (singleton!=null) {
            //KLUDGE! 
            // If there is only one FileLineMap object, just return it regardless of 
            // what the key passed in is! REVISIT later probably... 
            return singleton;
        }
        return lookup.get(inputSource);
    }

    static void clearLineMapLoookup() {
        singleton=null;
        lookup.clear();
    }

    // START API methods
    // Now some methods to fulfill the functionality that used to be in that
    // SimpleCharStream class
    // This backup() method is dead simple by design and does not handle any of the messiness
    // with column numbers relating to tabs or unicode escapes. 
    public void backup(int amount) {
        for (int i=0; i<amount; i++) {
            --bufferPosition;
            if (column==1) {
                --line;
                column=getLineLength(line);
            }
            else {
                --column;
            }
        }
    }

    void forward(int amount) {
        for (int i=0; i<amount; i++) {
            ++bufferPosition;
            if (column<getLineLength(line)) {
                column++;
            }
            else {
                ++line;
                column=1;
            }
        }
    }

    int readChar() {
        if (bufferPosition>=content.length()) {
            return-1;
        }
        int ch=content.charAt(bufferPosition++);
        if (ch=='\n') {
            ++line;
            column=1;
        }
        else {
            ++column;
        }
        return ch;
    }

    String getImage() {
        return content.subSequence(tokenBeginOffset, bufferPosition).toString();
    }

    String getSuffix(final int len) {
        int startPos=bufferPosition-len+1;
        return content.subSequence(startPos, bufferPosition).toString();
    }

    int beginToken() {
        tokenBeginOffset=bufferPosition;
        tokenBeginColumn=column;
        tokenBeginLine=line;
        return readChar();
    }

    int getBeginColumn() {
        return tokenBeginColumn;
    }

    int getBeginLine() {
        return tokenBeginLine;
    }

    int getEndColumn() {
        if (column==1) {
            if (line==tokenBeginLine) {
                return 1;
            }
            return getLineLength(line-1);
        }
        return column-1;
    }

    int getEndLine() {
        if (column==1&&line>tokenBeginLine) return line-1;
        return line;
    }

    void addToken(Token token) {
        tokenList.add(token);
    }

    // But there is no goto in Java!!!
    void goTo(int line, int column) {
        this.bufferPosition=getOffset(line, column);
        this.line=line;
        this.column=column;
    }

    // END API methods
    private int getLineLength(int lineNumber) {
        int startOffset=getLineStartOffset(lineNumber);
        int endOffset=getLineEndOffset(lineNumber);
        return endOffset-startOffset;
    }

    private int getLineStartOffset(int lineNumber) {
        int realLineNumber=lineNumber-startingLine;
        return lineOffsets[realLineNumber];
    }

    private int getLineEndOffset(int lineNumber) {
        int realLineNumber=lineNumber-startingLine;
        int endOffset=(realLineNumber+1==lineOffsets.length)?content.length():
        lineOffsets[realLineNumber+1];
        return endOffset;
    }

    private void setStartPosition(int line, int column) {
        this.startingLine=line;
        this.startingColumn=column;
        this.line=line;
        this.column=column;
    }

    private int getOffset(int line, int column) {
        int columnAdjustment=(line==startingLine)?startingColumn:
        1;
        return lineOffsets[line-startingLine]+column-columnAdjustment;
    }

    // ------------- private utilities method
    // Icky method to handle annoying stuff. Might make this public later if it is
    // needed elsewhere
    private static CharSequence mungeContent(CharSequence content, int tabsToSpaces, boolean preserveLines, boolean javaUnicodeEscape) {
        if (tabsToSpaces<=0&&preserveLines&&!javaUnicodeEscape) return content;
        StringBuilder buf=new StringBuilder();
        int index=0;
        int col=0;
        // This is just to handle spaces to tabs. If you don't have that setting set, it
        // is really unused.
        while (index<content.length()) {
            char ch=content.charAt(index++);
            if (ch=='\\'&&javaUnicodeEscape&&index<content.length()) {
                ch=content.charAt(index++);
                if (ch!='u') {
                    buf.append((char)'\\');
                    buf.append(ch);
                    if (ch=='\n') col=0;
                    else col+=2;
                }
                else {
                    while (content.charAt(index)=='u') {
                        index++;
                        // col++;
                    }
                    String hex=content.subSequence(index, index+=4).toString();
                    buf.append((char) Integer.parseInt(hex, 16));
                    // col +=6;
                    ++col;
                    // We're not going to be trying to track line/column information relative to the original content
                    // with tabs or unicode escape, so we just increment 1, not 6
                }
            }
            else if (ch=='\r'&&!preserveLines) {
                buf.append((char)'\n');
                if (index<content.length()) {
                    ch=content.charAt(index++);
                    if (ch!='\n') {
                        buf.append(ch);
                        ++col;
                    }
                    else col=0;
                }
            }
            else if (ch=='\t'&&tabsToSpaces>0) {
                int spacesToAdd=tabsToSpaces-col%tabsToSpaces;
                for (int i=0; i<spacesToAdd; i++) {
                    buf.append((char)' ');
                    col++;
                }
            }
            else {
                buf.append((char) ch);
                if (ch=='\n') {
                    col=0;
                }
                else col++;
            }
        }
        return buf.toString();
    }

    private static int[] createLineOffsetsTable(CharSequence content) {
        if (content.length()==0) {
            return EMPTY_INT;
        }
        int lineCount=0;
        char ch;
        int length=content.length();
        for (int i=0; i<length; i++) {
            ch=content.charAt(i);
            if (ch=='\n') {
                lineCount++;
            }
        }
        if (content.charAt(length-1)!='\n') {
            lineCount++;
        }
        int[] lineOffsets=new int[lineCount];
        lineOffsets[0]=0;
        int index=1;
        for (int i=0; i<length; i++) {
            ch=content.charAt(i);
            if (ch=='\n') {
                if (i+1==length) break;
                lineOffsets[index++]=i+1;
            }
        }
        return lineOffsets;
    }

    // ------------- TODO: unused method for the moment....
    private String getInputSource() {
        return inputSource;
    }

    private int getLineCount() {
        return lineOffsets.length;
    }

    String getText(int beginLine, int beginColumn, int endLine, int endColumn) {
        int startOffset=getOffset(beginLine, beginColumn);
        int endOffset=getOffset(endLine, endColumn);
        return getText(startOffset, endOffset);
    }

    private String getText(int startOffset, int endOffset) {
        return content.subSequence(startOffset, endOffset).toString();
    }

    private String getLine(int lineNumber) {
        int realLineNumber=lineNumber-startingLine;
        int startOffset=lineOffsets[realLineNumber];
        int endOffset=(realLineNumber+1==lineOffsets.length)?content.length():
        lineOffsets[realLineNumber+1];
        return getText(startOffset, endOffset);
    }

    private int getLineNumber(int offset) {
        int i=0;
        while (offset>=lineOffsets[i++]) {
            if (i==lineOffsets.length) break;
        }
        return i+startingLine-1;
    }

    static private int BUF_SIZE=0x10000;
    // Annoying kludge really...
    static private String readToEnd(Reader reader) {
        try {
            return readFully(reader);
        }
        catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    static String readFully(Reader reader) throws IOException {
        char[] block=new char[BUF_SIZE];
        int charsRead=reader.read(block);
        if (charsRead<0) {
            throw new IOException("No input");
        }
        else if (charsRead<BUF_SIZE) {
            char[] result=new char[charsRead];
            System.arraycopy(block, 0, result, 0, charsRead);
            reader.close();
            return new String(block, 0, charsRead);
        }
        StringBuilder buf=new StringBuilder();
        buf.append(block);
        do {
            charsRead=reader.read(block);
            if (charsRead>0) {
                buf.append(block, 0, charsRead);
            }
        }
        while (charsRead==BUF_SIZE);
        reader.close();
        return buf.toString();
    }

}
