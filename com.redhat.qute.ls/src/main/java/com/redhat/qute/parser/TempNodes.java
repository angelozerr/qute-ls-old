package com.redhat.qute.parser;

import java.util.function.Function;

import qute.Node;

public class TempNodes {

	public static Node findNodeAt(Node node, int line, int column) {
		int idx = findFirst(node, c -> isStartNodeIsAfter(node, line, column)) - 1;
		if (idx >= 0) {
			Node child = node.getChild(idx);
			if (isIncluded(child, line, column)) {
				return findNodeAt(child, line, column);
			}
		}
		return node;
	}

	private static boolean isStartNodeIsAfter(Node node, int line, int column) {
		return isAfter(node.getBeginLine(), node.getBeginColumn(), line, column);
	}

	private static boolean isAfter(int line1, int column1, int line2, int column2) {
		if (line1 > line2) {
			return false;
		}
		if (line1 == line2) {
			return column1 <= column2;
		}
		return (line1 < line2);
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
		return isAfter(beginLine, beginColumn, line, column) && !isAfter(endLine, endColumn, line, column);
	}

	public static boolean isIncluded(int start, int end, int offset) {
		return offset >= start && offset <= end;
	}

	/**
	 * Takes a sorted array and a function p. The array is sorted in such a way that
	 * all elements where p(x) is false are located before all elements where p(x)
	 * is true.
	 * 
	 * @returns the least x for which p(x) is true or array.length if no element
	 *          full fills the given function.
	 */
	private static int findFirst(Node array, Function<Node, Boolean> p) {
		int low = 0, high = array.getChildCount();
		if (high == 0) {
			return 0; // no children
		}
		while (low < high) {
			int mid = (int) Math.floor((low + high) / 2);
			if (p.apply(array.getChild(mid))) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

}
