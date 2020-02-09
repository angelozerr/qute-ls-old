package com.redhat.qute.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightKind;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import com.redhat.qute.ls.commons.BadLocationException;
import com.redhat.qute.parser.Template;
import com.redhat.qute.utils.QutePositionUtility;

import qute.ENDIF;
import qute.END_SECTION;
import qute.IF;
import qute.Node;
import qute.START_SECTION;

class QuteHighlighting {

	private static final Logger LOGGER = Logger.getLogger(QuteHighlighting.class.getName());

	public List<DocumentHighlight> findDocumentHighlights(Template template, Position position,
			CancelChecker cancelChecker) {
		try {
			Node node = QutePositionUtility.findNodeAt(template, position);
			if (node == null) {
				return Collections.emptyList();
			}
			List<DocumentHighlight> highlights = new ArrayList<>();
			fillWithDefaultHighlights(node, position, highlights, cancelChecker);
			return highlights;
		} catch (BadLocationException e) {
			LOGGER.log(Level.SEVERE, "In QuteHighlighting the client provided Position is at a BadLocation", e);
			return Collections.emptyList();
		}
	}

	private static void fillWithDefaultHighlights(Node node, Position position, List<DocumentHighlight> highlights,
			CancelChecker cancelChecker) throws BadLocationException {
		Node originNode = null;
		Node targetNode = null;
		if (node instanceof START_SECTION || node instanceof IF) {
			originNode = node;
			Node section = originNode.getParent();
			targetNode = section.getChild(section.getChildCount() - 1);
		} else if (node instanceof END_SECTION || node instanceof ENDIF) {
			originNode = node;
			Node section = originNode.getParent();
			targetNode = section.getChild(0);
		}
		if (originNode != null && targetNode != null) {
			Range startTagRange = QutePositionUtility.toRange(originNode);
			Range endTagRange = QutePositionUtility.toRange(targetNode);
			if (doesTagCoverPosition(startTagRange, endTagRange, position)) {
				fillHighlightsList(startTagRange, endTagRange, highlights);
			}
		}
	}

	private static void fillHighlightsList(Range startTagRange, Range endTagRange, List<DocumentHighlight> result) {
		if (startTagRange != null) {
			result.add(new DocumentHighlight(startTagRange, DocumentHighlightKind.Read));
		}
		if (endTagRange != null) {
			result.add(new DocumentHighlight(endTagRange, DocumentHighlightKind.Read));
		}
	}

	public static boolean doesTagCoverPosition(Range startTagRange, Range endTagRange, Position position) {
		return startTagRange != null && covers(startTagRange, position)
				|| endTagRange != null && covers(endTagRange, position);
	}

	public static boolean covers(Range range, Position position) {
		return isBeforeOrEqual(range.getStart(), position) && isBeforeOrEqual(position, range.getEnd());
	}

	public static boolean isBeforeOrEqual(Position pos1, Position pos2) {
		return pos1.getLine() < pos2.getLine()
				|| (pos1.getLine() == pos2.getLine() && pos1.getCharacter() <= pos2.getCharacter());
	}

}
