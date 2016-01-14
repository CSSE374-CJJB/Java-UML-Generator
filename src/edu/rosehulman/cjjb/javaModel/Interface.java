package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IVisitor;

public class Interface extends AbstractJavaStructure {
	
	public Interface(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements, List<AbstractJavaStructure> implement) {
		super(name, access, modifiers, subElements, implement);
	}

	public Interface(String name) {
		super(name);
	}
	
	@Override
	public void accept(IVisitor v) throws IOException {
		v.visit(this);
		
		super.accept(v);
	}
}
