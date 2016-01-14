package edu.rosehulman.cjjb.asm;

import java.util.List;

public class MethodCallLine {

	public String classOf;
	public String name;
	public String returnType;
	public List<String> args;
	
	public MethodCallLine(String classOf, String name, String returnType, List<String> args) {
		this.classOf = classOf;
		this.name = name;
		this.returnType = returnType;
		this.args = args;
	}
}
