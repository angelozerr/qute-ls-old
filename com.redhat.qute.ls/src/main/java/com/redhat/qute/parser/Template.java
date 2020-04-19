package com.redhat.qute.parser;

import java.util.List;

import qute.Node;
import qute.ParsingProblem;

public class Template {

	private final String templateId;

	private Node root;

	private List<ParsingProblem> problems;

	public Template(String templateId) {
		this.templateId = templateId;
	}

	public String getId() {
		return templateId;
	}

	public void setParsingProblems(List<ParsingProblem> list) {
		this.problems = list;
	}

	public List<ParsingProblem> getProblems() {
		return problems;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}

}
