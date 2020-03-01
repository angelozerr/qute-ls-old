/*******************************************************************************
* Copyright (c) 2020 Red Hat Inc. and others.
* All rights reserved. This program and the accompanying materials
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*
* SPDX-License-Identifier: EPL-2.0
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
import com.redhat.qute.parser.Template;
import com.redhat.qute.utils.QutePositionUtility;

import qute.ENDIF;
import qute.END_SECTION;
import qute.IF;
import qute.Node;
import qute.START_SECTION;

/**
 * Qute definition support.
 *
 */
class QuteDefinition {

	private static final Logger LOGGER = Logger.getLogger(QuteDefinition.class.getName());

	public List<? extends LocationLink> findDefinition(Template template, Position position,
			CancelChecker cancelChecker) {
		try {
			Node node = QutePositionUtility.findNodeAt(template, position);
			if (node == null) {
				return Collections.emptyList();
			}
			List<LocationLink> locations = new ArrayList<>();
			// Start end tag definition
			findStartEndTagDefinition(node, template, locations);
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
	private static void findStartEndTagDefinition(Node node, Template template, List<LocationLink> locations)
			throws BadLocationException {
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
			Range originRange = QutePositionUtility.toRange(originNode);
			Range targetRange = QutePositionUtility.toRange(targetNode);
			locations.add(new LocationLink(template.getId(), targetRange, targetRange, originRange));
		}
	}

}
