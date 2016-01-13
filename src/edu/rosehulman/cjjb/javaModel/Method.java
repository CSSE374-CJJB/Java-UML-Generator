package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class Method extends AbstractJavaElement {
	public List<AbstractJavaStructure> arguments;

	public Method(String name, IAccessModifier access, List<IModifier> modifiers, AbstractJavaStructure type,
			List<AbstractJavaStructure> arguments) {
		super(name, access, modifiers, type);
		this.arguments = arguments;
	}
}
