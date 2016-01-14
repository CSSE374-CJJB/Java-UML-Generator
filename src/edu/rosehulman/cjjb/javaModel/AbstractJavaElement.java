package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public abstract class AbstractJavaElement extends AbstractJavaThing {
	public AbstractJavaStructure type;

	public AbstractJavaElement(String name, IAccessModifier access, List<IModifier> modifiers,
			AbstractJavaStructure type) {
		super(name, access, modifiers);
		this.type = type;
	}
}
