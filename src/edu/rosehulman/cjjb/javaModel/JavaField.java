package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class JavaField extends AbstractJavaElement {

	public JavaField(AbstractJavaStructure owner, String name, IAccessModifier access, List<IModifier> modifiers, AbstractJavaStructure type) {
		super(owner, name, access, modifiers, type);
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visit(this);
	}
}
