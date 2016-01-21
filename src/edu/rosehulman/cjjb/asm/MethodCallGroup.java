package edu.rosehulman.cjjb.asm;

import java.util.LinkedList;
import java.util.List;

public class MethodCallGroup {
	
	public String classCaller;
	public QualifiedMethod method;
	public List<MethodCallLine> lines;
	
	public MethodCallGroup(String classCaller, QualifiedMethod method) {
		this.classCaller = classCaller;
		this.method = method;
		lines = new LinkedList<MethodCallLine>();
		this.name = name;
	}
	
	public void addLine(MethodCallLine line) {
		lines.add(line);
	}
}
