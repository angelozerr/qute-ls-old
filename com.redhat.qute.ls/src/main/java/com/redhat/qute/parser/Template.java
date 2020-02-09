package com.redhat.qute.parser;

import java.util.List;

import qute.Node;

public class Template {

	private final String templateId;

	private Node root;
	
	private List<Problem> problems;

	public Template(String templateId) {
		this.templateId = templateId;
	}

	public String getId() {
		return templateId;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}

	public List<Problem> getProblems() {
		return problems;
	}
	
	public void setRoot(Node root) {
		this.root = root;
	}
	
	public Node getRoot() {
		return root;
	}
}
