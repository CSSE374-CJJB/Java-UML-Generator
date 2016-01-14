package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IVisitor;

public class Class extends AbstractJavaStructure {
	public AbstractJavaStructure superClass;

	public Class(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements,
			List<AbstractJavaStructure> implement, AbstractJavaStructure superClass) {
		super(name, access, modifiers, subElements, implement);
		this.superClass = superClass;
	}

	public Class(String cleanName) {
		super(cleanName);
		
		this.superClass = null;
	}

	@Override
	public void accept(IVisitor v) throws IOException {
		v.visit(this);
		
		super.accept(v);
	}
	
	
}
