/*******************************************************************************
* Copyright (c) 2019 Red Hat Inc. and others.
* All rights reserved. This program and the accompanying materials
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*
* Contributors:
*     Red Hat Inc. - initial API and implementation
*******************************************************************************/
package com.redhat.qute.services;

import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import com.redhat.qute.ls.commons.TextDocument;
import com.redhat.qute.parser.Template;
import com.redhat.qute.settings.QuteValidationSettings;

/**
 * The Qute language service.
 * 
 * @author Angelo ZERR
 *
 */
public class QuteLanguageService {
	private final QuteHighlighting highlighting;
	private final QuteDefinition definition;
	private final QuteSymbolsProvider symbolsProvider;
	private final QuteDiagnostics diagnostics;

	public QuteLanguageService() {
		this.highlighting = new QuteHighlighting();
		this.definition = new QuteDefinition();
		this.symbolsProvider = new QuteSymbolsProvider();
		this.diagnostics = new QuteDiagnostics();
	}

	public List<DocumentHighlight> findDocumentHighlights(Template template, Position position,
			CancelChecker cancelChecker) {
		return highlighting.findDocumentHighlights(template, position, cancelChecker);
	}

	public List<? extends LocationLink> findDefinition(Template template, Position position,
			CancelChecker cancelChecker) {
		return definition.findDefinition(template, position, cancelChecker);
	}

	public List<DocumentSymbol> findDocumentSymbols(Template template, CancelChecker cancelChecker) {
		return symbolsProvider.findDocumentSymbols(template, cancelChecker);
	}

	public List<SymbolInformation> findSymbolInformations(Template template, CancelChecker cancelChecker) {
		return symbolsProvider.findSymbolInformations(template, cancelChecker);
	}

	/**
	 * Validate the given Qute <code>template</code>.
	 * 
	 * @param template           the Qute template.
	 * @param document
	 * @param validationSettings the validation settings.
	 * @param cancelChecker      the cancel checker.
	 * @return the result of the validation.
	 */
	public List<Diagnostic> doDiagnostics(Template template, TextDocument document,
			QuteValidationSettings validationSettings, CancelChecker cancelChecker) {
		return diagnostics.doDiagnostics(template, document, validationSettings, cancelChecker);
	}

}
