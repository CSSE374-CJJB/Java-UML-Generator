package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class Method extends AbstractJavaElement {
	public List<AbstractJavaStructure> arguments;

	public Method(String name, IAccessModifier access, List<IModifier> modifiers, AbstractJavaStructure type,
			List<AbstractJavaStructure> arguments) {
		super(name, access, modifiers, type);
		this.arguments = arguments;
	}
	
	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visit(this);
	}
	
}
