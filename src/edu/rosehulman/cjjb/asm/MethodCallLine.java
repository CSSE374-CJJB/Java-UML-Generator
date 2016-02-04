package edu.rosehulman.cjjb.asm;

public class MethodCallLine {

	public String classOf;
	public QualifiedMethod method;
	public String returnType;
	
	public MethodCallLine(String classOf, QualifiedMethod method, String returnType) {
		this.classOf = classOf;
		this.method = method;
		this.returnType = returnType;
	}
}
