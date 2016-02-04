package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class JavaInterface extends AbstractJavaStructure {

	public JavaInterface(String name, IAccessModifier access, List<IModifier> modifiers,
			List<AbstractJavaElement> subElements, List<AbstractJavaStructure> implement) {
		super(name, access, modifiers, subElements, implement);
	}

	public JavaInterface(String name) {
		super(name);
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visit(this);

		super.accept(v);
	}
}
