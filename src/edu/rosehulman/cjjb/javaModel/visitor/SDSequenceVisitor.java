package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;

public class SDSequenceVisitor implements ISequenceVisitor {

	private String className;
	private String methodName;
	private int depth;
	private OutputStream out;
	
	public SDSequenceVisitor(String className, String methodName, int depth, OutputStream out) {
		this.className = className;
		this.methodName = methodName;
		this.depth = depth;
		this.out = out;
	}

	@Override
	public void visit(JavaModel model) throws IOException {
		
		out.write(String.format("%s:%s", "<variable name>", this.className).getBytes());
		
		AbstractJavaStructure struct = model.getStructure(className);
		AbstractJavaElement element = struct.getElementByName(methodName);
		
		if(element == null ||  !(element instanceof Method)) {
			return;
		}
		
		Method method = (Method)element;
		
		Set<String> objects = new HashSet<String>();
		List<String> sdCalls = new ArrayList<String>();
		
		
		//objects.add(className.replace(".", "\\.") + "[a]");
		
		addCalls(this.depth, objects, sdCalls, method);
		
		for(String s: objects) {
			out.write(String.format("%s:%s[a]\n", s,s).getBytes());
		}
		out.write("\n".getBytes());
		
		for(String s: sdCalls) {
			out.write(String.format("%s\n", s).getBytes());
		}
	}
	
	public void addCalls(int depth, Set<String> objects, List<String> sdCalls, Method method) {
		if(depth == 0)
			return;
		for(Method call: method.methodCalls) {
			String str = call.owner.name.replace(".", "\\.");
			if(method.owner.name.equals(call.owner.name) && depth == 1) {
				sdCalls.add(String.format("%s:.%s(%s)", method.owner.name.replace(".", "\\."), call.name, call.argumentsToString()));
			} else {
				sdCalls.add(String.format("%s:%s=%s.%s(%s)", method.owner.name.replace(".", "\\."), call.type.name ,call.owner.name.replace(".", "\\."), call.name, call.argumentsToString().replace(".", "\\.")));					
			}
			objects.add(str);
			addCalls(depth - 1, objects, sdCalls, call);
		}
	}
	
	
}