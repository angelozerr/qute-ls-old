/*******************************************************************************
* Copyright (c) 2020 Red Hat Inc. and others.
* All rights reserved. This program and the accompanying materials
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*
* Contributors:
*     Red Hat Inc. - initial API and implementation
*******************************************************************************/
package com.redhat.qute.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import com.redhat.qute.ls.commons.BadLocationException;
import com.redhat.qute.ls.commons.TextDocument;
import com.redhat.qute.parser.TempNodes;
import com.redhat.qute.parser.Template;
import com.redhat.qute.utils.QutePositionUtility;

import qute.END_SECTION;
import qute.Node;
import qute.START_SECTION;

/**
 * Qute definition support.
 *
 */
class QuteDefinition {

	private static final Logger LOGGER = Logger.getLogger(QuteDefinition.class.getName());

	public List<? extends LocationLink> findDefinition(Template template, TextDocument document, Position position,
			CancelChecker cancelChecker) {
		try {
			int offset = document.offsetAt(position);
			Node node = TempNodes.findNodeAt(template.getRoot(), position.getLine(), position.getCharacter());
			if (node == null) {
				return Collections.emptyList();
			}
			List<LocationLink> locations = new ArrayList<>();
			// Start end tag definition
			findStartEndTagDefinition(node, template, document, offset, locations);
			return locations;
		} catch (BadLocationException e) {
			LOGGER.log(Level.SEVERE, "In QuteDefinition the client provided Position is at a BadLocation", e);
			return Collections.emptyList();
		}

	}

	/**
	 * Find start end tag definition.
	 * 
	 * @param document
	 * @param template
	 * 
	 * @param request   the definition request
	 * @param locations the locations
	 * @throws BadLocationException
	 */
	private static void findStartEndTagDefinition(Node node, Template template, TextDocument document, int offset,
			List<LocationLink> locations) throws BadLocationException {
		Node originNode = null;
		Node targetNode = null;
		if (node instanceof START_SECTION) {
			originNode = node;
			Node section = originNode.getParent();
			targetNode = section.getChild(section.getChildCount() - 1);
		} else if (node instanceof END_SECTION) {
			originNode = node;
			Node section = originNode.getParent();
			targetNode = section.getChild(0);
		}
		if (originNode != null && targetNode != null) {
			Range originRange = QutePositionUtility.toRange(originNode);
			Range targetRange = QutePositionUtility.toRange(targetNode);
			locations.add(new LocationLink(template.getId(), targetRange, targetRange, originRange));
		}
	}

}
