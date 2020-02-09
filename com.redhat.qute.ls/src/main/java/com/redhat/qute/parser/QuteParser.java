package com.redhat.qute.parser;

import java.io.StringReader;
import java.util.Arrays;

import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import qute.LexicalException;
import qute.ParseException;
import qute.QUTEParser;

public class QuteParser {

	private static CancelChecker DEFAULT_CANCEL_CHECKER = () -> {
	};

	public static Template parse(String content) {
		return parse(content, null, DEFAULT_CANCEL_CHECKER);
	}

	public static Template parse(String content, String templateId, CancelChecker cancelChecker) {
		if (cancelChecker == null) {
			cancelChecker = DEFAULT_CANCEL_CHECKER;
		}
		Template template = new Template(templateId);
		QUTEParser parser = new QUTEParser(new StringReader(content));
		try {
			parser.Root();
			template.setRoot(parser.rootNode());
		} catch (ParseException e) {
			template.setProblems(Arrays.asList(new Problem(e.currentToken, e.getMessage())));
		}
		return template;
	}
}
