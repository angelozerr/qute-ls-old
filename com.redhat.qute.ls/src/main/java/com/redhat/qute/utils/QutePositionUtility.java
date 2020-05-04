package com.redhat.qute.utils;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import com.redhat.qute.parser.Template;

import qute.Node;
import qute.ast.EACH;
import qute.ast.ENDEACH;
import qute.ast.ENDFOR;
import qute.ast.ENDIF;
import qute.ast.END_SECTION;
import qute.ast.FOR;
import qute.ast.IF;
import qute.ast.START_SECTION;

public class QutePositionUtility {

	public static Location toLocation(LocationLink locationLink) {
		return new Location(locationLink.getTargetUri(), locationLink.getTargetRange());
	}

	public static Range toRange(Node node) {
		Position start = new Position(node.getBeginLine() - 1, node.getBeginColumn() - 1);
		Position end = new Position(node.getEndLine() - 1, node.getEndColumn());
		return new Range(start, end);
	}

	public static Node findNodeAt(Template template, Position position) {
		Node root = template.getRoot();
		if (root == null) {
			return null;
		}
		return root.findNodeAt(position.getLine() + 1, position.getCharacter() + 1);
	}
	
	public static boolean isEndSection(Node node) {
		return node instanceof END_SECTION || node instanceof ENDIF|| node instanceof ENDFOR|| node instanceof ENDEACH;
	}

	public static boolean isStartSection(Node node) {
		return node instanceof START_SECTION || node instanceof IF || node instanceof FOR || node instanceof EACH;
	}

}
