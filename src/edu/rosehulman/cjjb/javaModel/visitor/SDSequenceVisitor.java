package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;
import java.io.OutputStream;

import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;

public class SDSequenceVisitor implements ISequenceVisitor {

	private String className;
	private String methodName;
	private OutputStream out;
	
	public SDSequenceVisitor(String className, String methodName, OutputStream out) {
		this.className = className;
		this.methodName = methodName;
		this.out = out;
	}

	@Override
	public void visit(JavaModel model) throws IOException {
		
		out.write(String.format("%s:%s", "<variable name>", this.className).getBytes());
		
		AbstractJavaStructure struct = model.getStructure(className);
		AbstractJavaElement method = struct.getElementByName(methodName);
		
		if(method == null) {
			return;
		}
		
	}
	
	
}