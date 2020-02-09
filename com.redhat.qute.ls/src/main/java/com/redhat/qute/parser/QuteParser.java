package com.redhat.qute.parser;

import java.io.StringReader;
import java.util.Arrays;

import org.eclipse.lsp4j.jsonrpc.CancelChecker;

import qute.Node;
import qute.ParseException;
import qute.QUTEParser;
import qute.Token;

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
		parser.setBuildTree(true);
		try {
			parser.Root();
			
			// At the end of the children Root, there is a Token node
			// which encloses the all Root which causes some trouble with findNodeAt
			// we remove it
			Node root = parser.rootNode(); //.getChild(0);
			int count = root.getChildCount();
			if (count > 1) {
				Node n = root.getChild(count - 1);
				if (n instanceof Token) {
					root.removeChild(n);
				}
			}
			template.setRoot(root);
		} catch (ParseException e) {
			template.setProblems(Arrays.asList(new Problem(e.currentToken, e.getMessage())));
		}
		return template;
	}
}
