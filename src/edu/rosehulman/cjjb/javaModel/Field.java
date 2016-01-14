package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class Field extends AbstractJavaElement {

	public Field(String name, IAccessModifier access, List<IModifier> modifiers, AbstractJavaStructure type) {
		super(name, access, modifiers, type);
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visit(this);
	}

}
