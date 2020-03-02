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

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import com.redhat.qute.ls.commons.BadLocationException;
import com.redhat.qute.ls.commons.snippets.ISnippetLineContext;
import com.redhat.qute.ls.commons.snippets.SnippetRegistry;
import com.redhat.qute.parser.Template;
import com.redhat.qute.settings.QuteCompletionSettings;
import com.redhat.qute.settings.QuteFormattingSettings;
import com.redhat.qute.utils.QutePositionUtility;

import qute.Node;
import qute.Text;

/**
 * The Qute completions
 * 
 * @author Angelo ZERR
 *
 */
class QuteCompletions {

	public class LineContext implements ISnippetLineContext {

		private final Node node;

		private final Position position;

		public LineContext(Node node, Position position) {
			this.node = node;
			this.position = position;
		}

		@Override
		public String getLineDelimiter() {
			return System.lineSeparator();
		}

		@Override
		public String getWhitespacesIndent() {
			return "";
		}

		@Override
		public Range getReplaceRange(String expr) throws BadLocationException {
			Position start = position;
			Position end = position;
			return new Range(start, end);
		}

	}

	private static final Logger LOGGER = Logger.getLogger(QuteCompletions.class.getName());
	private boolean snippetsLoaded;

	/**
	 * Returns completion list for the given position
	 * 
	 * @param template           the Qute template
	 * @param position           the position where completion was triggered
	 * @param completionSettings the completion settings.
	 * @param formattingSettings the formatting settings.
	 * @param cancelChecker      the cancel checker
	 * @return completion list for the given position
	 */
	public CompletionList doComplete(Template template, Position position, QuteCompletionSettings completionSettings,
			QuteFormattingSettings formattingSettings, CancelChecker cancelChecker) {
		CompletionList list = new CompletionList();
		Node node = QutePositionUtility.findNodeAt(template, position);
		fillCompletionSnippets(node, position, list);
		if (node != null) {

		}
		return list;
	}

	private void fillCompletionSnippets(Node node, Position position, CompletionList list) {
		if (node == null || node instanceof Text) {
			initSnippets();
			ISnippetLineContext lineContext = new LineContext(node, position);
			Collection<CompletionItem> snippetItems = SnippetRegistry.getInstance().getCompletionItems(lineContext,
					true, context -> {
						if (!"qute".equals(context.getType())) {
							return false;
						}
						return true;
					});
			if (!snippetItems.isEmpty()) {
				list.getItems().addAll(snippetItems);
			}
		}

	}

	private void initSnippets() {
		if (snippetsLoaded) {
			return;
		}
		try {
			try {
				SnippetRegistry.getInstance().load(QuteCompletions.class.getResourceAsStream("qute-snippets.json"));
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Error while loading Qute snippets", e);
			}
		} finally {
			snippetsLoaded = true;
		}

	}

}