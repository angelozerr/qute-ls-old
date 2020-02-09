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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import com.redhat.qute.ls.commons.TextDocument;
import com.redhat.qute.parser.Problem;
import com.redhat.qute.parser.Template;
import com.redhat.qute.settings.QuteValidationSettings;
import com.redhat.qute.utils.QutePositionUtility;

import io.quarkus.qute.Engine;
import qute.Node;

/**
 * Qute diagnostics support.
 *
 */
class QuteDiagnostics {

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
		if (validationSettings == null) {
			validationSettings = QuteValidationSettings.DEFAULT;
		}
		List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();
		if (validationSettings.isEnabled()) {
			// validateWithOfficialQuteParser(document, diagnostics);
			List<Problem> problems = template.getProblems();
			if (problems != null) {
				problems.forEach(p -> {
					Range range = QutePositionUtility.toRange(p.getNode());
					Diagnostic diagnostic = new Diagnostic(range, p.getMessage(), DiagnosticSeverity.Error, "qute",
							"X");
					diagnostics.add(diagnostic);
				});
			}
		}
		return diagnostics;
	}

	private void validateWithOfficialQuteParser(TextDocument document, List<Diagnostic> diagnostics) {
		Engine engine = Engine.builder().addDefaults().build();
		try {
			engine.parse(document.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void validate(Node node, TextDocument document, List<Diagnostic> diagnostics,
			CancelChecker cancelChecker) {
		/*
		 * if (!node.isClosed()) { try { Position start =
		 * document.positionAt(node.getStart()); Position end =
		 * document.positionAt(node.getEnd()); Diagnostic diagnostic = new
		 * Diagnostic(new Range(start, end), "not closed", DiagnosticSeverity.Error,
		 * "qute", "NotClosed"); diagnostics.add(diagnostic); } catch (Exception e) {
		 * e.printStackTrace(); } } for (Node child : node.getChildren()) {
		 * validate(child, document, diagnostics, cancelChecker); }
		 */
	}

}
