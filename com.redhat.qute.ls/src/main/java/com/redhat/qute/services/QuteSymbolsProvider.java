/**
 *  Copyright (c) 2018 Angelo ZERR
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package com.redhat.qute.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import com.redhat.qute.parser.Template;
import com.redhat.qute.utils.QutePositionUtility;

import qute.Block;
import qute.Comment;
import qute.IF;
import qute.Interpolation;
import qute.Node;
import qute.Section;
import qute.Text;

/**
 * XML symbol provider.
 *
 */
class QuteSymbolsProvider {

	public List<DocumentSymbol> findDocumentSymbols(Template template, CancelChecker cancelChecker) {
		List<DocumentSymbol> symbols = new ArrayList<>();
		Node root = template.getRoot();
		if (root != null) {
			for (int i = 0; i < root.getChildCount(); i++) {
				Node child = root.getChild(i);
				findDocumentSymbols(child, symbols, template, cancelChecker);
			}
		}
		return symbols;
	}

	private void findDocumentSymbols(Node node, List<DocumentSymbol> symbols, Template template,
			CancelChecker cancelChecker) {
		if (!isNodeSymbol(node)) {
			return;
		}
		cancelChecker.checkCanceled();

		String name = nodeToName(node);
		Range selectionRange = getSymbolRange(node);

		Range range = selectionRange;
		List<DocumentSymbol> children = new ArrayList<>();
		DocumentSymbol symbol = new DocumentSymbol(name, getSymbolKind(node), range, selectionRange, null, children);
		symbols.add(symbol);

		if (node.getChildCount() > 0) {
			for (int i = 0; i < node.getChildCount(); i++) {
				Node child = node.getChild(i);
				findDocumentSymbols(child, children, template, cancelChecker);
			}
		}
	}

	private SymbolKind getSymbolKind(Node node) {
		if (node instanceof Block) {
			return SymbolKind.Module;
		}
		if (node instanceof Text) {
			return SymbolKind.Constant;
		}
		if (node instanceof Interpolation) {
			return SymbolKind.Function;
		}
		if (node instanceof Comment) {
			return SymbolKind.Struct;
		}
		if (node instanceof Section) {
			return SymbolKind.Method;
		}
		if (node instanceof IF) {
			return SymbolKind.Event;
		}
		return SymbolKind.Field;
	}

	private String nodeToName(Node node) {
		return node.getClass().getSimpleName();
	}

	private static Range getSymbolRange(Node node) {
		return QutePositionUtility.toRange(node);
	}

	private boolean isNodeSymbol(Node node) {
		return true;
	}

	public List<SymbolInformation> findSymbolInformations(Template template, CancelChecker cancelChecker) {
		List<SymbolInformation> symbols = new ArrayList<>();
		Node root = template.getRoot();
		if (root != null) {

		}
		return symbols;
	}
}
