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

import qute.ParsingProblem;

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
		//if (validationSettings.isEnabled()) {
			List<ParsingProblem> problems = template.getProblems();
			if (problems != null) {
				problems.forEach(p -> {
					Range range = QutePositionUtility.toRange(p.getNode());
					Diagnostic diagnostic = new Diagnostic(range, p.getDescription(), DiagnosticSeverity.Error, "qute",
							null);
					diagnostics.add(diagnostic);
				});
			}
		//}
		return diagnostics;
	}

}
