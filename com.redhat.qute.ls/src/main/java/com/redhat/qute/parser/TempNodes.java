package com.redhat.qute.parser;

import qute.Node;

public class TempNodes {

	public static Node findNodeAt(Node node, int line, int column) {
		if (!isIncluded(node, line, column)) {
			return null;
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			Node child = node.getChild(i);
			Node match = findNodeAt(child, line, column);
			if (match != null) {
				return match;
			}
		}
		return node;
	}

	/**
	 * Returns true if the node included the given offset and false otherwise.
	 * 
	 * @param node
	 * @param offset
	 * @return true if the node included the given offset and false otherwise.
	 */
	public static boolean isIncluded(Node node, int line, int column) {
		if (node == null) {
			return false;
		}
		return isIncluded(node.getBeginLine(), node.getBeginColumn(), node.getEndLine(), node.getEndColumn(), line,
				column);
	}

	private static boolean isIncluded(int beginLine, int beginColumn, int endLine, int endColumn, int line,
			int column) {
		return !isAfter(beginLine, beginColumn, line, column) && isAfter(endLine, endColumn, line, column);
	}

	private static boolean isAfter(int line1, int column1, int line2, int column2) {
		if (line1 > line2) {
			return true;
		}
		if (line1 == line2) {
			return column1 >= column2;
		}
		return false;
	}
}
