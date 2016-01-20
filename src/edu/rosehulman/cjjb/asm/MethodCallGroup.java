package edu.rosehulman.cjjb.asm;

import java.util.LinkedList;
import java.util.List;

public class MethodCallGroup {
	
	public String classCaller;
	public String name;
	public List<MethodCallLine> lines;
	
	public MethodCallGroup(String classCaller, String name) {
		this.classCaller = classCaller;
		this.name = name;
		lines = new LinkedList<MethodCallLine>();
	}
	
	public void addLine(MethodCallLine line) {
		lines.add(line);
	}
}
