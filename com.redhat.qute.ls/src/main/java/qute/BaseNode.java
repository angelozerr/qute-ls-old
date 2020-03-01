/* Generated by: JavaCC 21 Parser Generator. Do not edit. BaseNode.java */
package qute;

@SuppressWarnings("rawtypes")
public class BaseNode implements Node {
    static private Class listClass=java.util.ArrayList.class;
    static public void setListClass(Class<?extends java.util.List>listClass) {
        BaseNode.listClass=listClass;
    }

    @SuppressWarnings("unchecked")
    private java.util.List<Node>newList() {
        try {
            return(java.util.List<Node>) listClass.newInstance();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Node parent;
    protected java.util.List<Node>children=newList();
    private int beginLine,beginColumn,endLine,endColumn;
    private String inputSource;
    private java.util.Map<String,Object>attributes;
    public void open() {
    }

    public void close() {
    }

    @Override public boolean hasChildNodes() {
        return getChildCount()>0;
    }

    @Override public Node getFirstChild() {
        return Nodes.getFirstChild(this);
    }

    @Override public Node getLastChild() {
        return Nodes.getLastChild(this);
    }

    public void setParent(Node n) {
        parent=n;
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node n) {
        children.add(n);
        n.setParent(this);
    }

    public void addChild(int i,Node n) {
        children.add(i,n);
        n.setParent(this);
    }

    public Node getChild(int i) {
        return children.get(i);
    }

    public void setChild(int i,Node n) {
        children.set(i,n);
        n.setParent(this);
    }

    public Node removeChild(int i) {
        return children.remove(i);
    }

    public boolean removeChild(Node n) {
        return children.remove(n);
    }

    public int indexOf(Node n) {
        return children.indexOf(n);
    }

    public void clearChildren() {
        children.clear();
    }

    public int getChildCount() {
        return children.size();
    }

    public Object getAttribute(String name) {
        return attributes==null?null:
        attributes.get(name);
    }

    public void setAttribute(String name,Object value) {
        if (attributes==null) {
            attributes=new java.util.HashMap<String,Object>();
        }
        attributes.put(name,value);
    }

    public boolean hasAttribute(String name) {
        return attributes==null?false:
        attributes.containsKey(name);
    }

    public java.util.Set<String>getAttributeNames() {
        if (attributes==null) return java.util.Collections.emptySet();
        return attributes.keySet();
    }

    public void setInputSource(String inputSource) {
        this.inputSource=inputSource;
    }

    public String getInputSource() {
        return inputSource;
    }

    public int getBeginLine() {
        if (beginLine<=0) {
            if (!children.isEmpty()) {
                beginLine=children.get(0).getBeginLine();
                beginColumn=children.get(0).getBeginColumn();
            }
        }
        return beginLine;
    }

    public int getEndLine() {
        if (endLine<=0) {
            if (!children.isEmpty()) {
                Node last=children.get(children.size()-1);
                endLine=last.getEndLine();
                endColumn=last.getEndColumn();
            }
        }
        return endLine;
    }

    public int getBeginColumn() {
        if (beginColumn<=0) {
            if (!children.isEmpty()) {
                beginLine=children.get(0).getBeginLine();
                beginColumn=children.get(0).getBeginColumn();
            }
        }
        return beginColumn;
    }

    public int getEndColumn() {
        if (endColumn<=0) {
            if (!children.isEmpty()) {
                Node last=children.get(children.size()-1);
                endLine=last.getEndLine();
                endColumn=last.getEndColumn();
            }
        }
        return endColumn;
    }

    public void setBeginLine(int beginLine) {
        this.beginLine=beginLine;
    }

    public void setEndLine(int endLine) {
        this.endLine=endLine;
    }

    public void setBeginColumn(int beginColumn) {
        this.beginColumn=beginColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn=endColumn;
    }

    public String toString() {
        StringBuilder buf=new StringBuilder();
        for(Token t : Nodes.getRealTokens(this)) {
            buf.append(t);
        }
        return buf.toString();
    }

    @Override public Node findNodeAt(int line,int column) {
        return Nodes.findNodeAt(this,line,column);
    }

}
