package com.redhat.qute.ls.commons.snippets;

import org.eclipse.lsp4j.Range;

import com.redhat.qute.ls.commons.BadLocationException;

public interface ISnippetLineContext {

	String getLineDelimiter();

	String getWhitespacesIndent();

	Range getReplaceRange(String expr) throws BadLocationException;

}
