package com.redhat.qute.utils;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import com.redhat.qute.parser.TempNodes;
import com.redhat.qute.parser.Template;

import qute.Node;

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
		return TempNodes.findNodeAt(template.getRoot(), position.getLine() + 1, position.getCharacter() + 1);
	}
}
