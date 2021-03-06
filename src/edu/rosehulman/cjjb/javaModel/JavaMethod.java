package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class JavaMethod extends AbstractJavaElement {
	public List<AbstractJavaStructure> arguments;
	
	public List<JavaMethod> methodCalls;
	
	public boolean isConstructor;

	public JavaMethod(AbstractJavaStructure structure, String name, IAccessModifier access, List<IModifier> modifiers, AbstractJavaStructure type,
			List<AbstractJavaStructure> arguments, boolean isConstructor) {
		super(structure, name, access, modifiers, type);
		this.arguments = arguments;
		this.isConstructor = isConstructor;
		this.methodCalls = new LinkedList<JavaMethod>();
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visit(this);
	}

	public void addMethodCall(JavaMethod method) {
		if(method == null) {
			System.out.println("Null method parm");
			return;
		}
		methodCalls.add(method);
	}
	
	public String argumentsToString() {
		List<String> args = new ArrayList<String>();
		
		for(AbstractJavaStructure s: arguments)
			args.add(s.name);
		
		return String.join(", ", args);
	}
}
