package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class Interface extends AbstractJavaStructure {
	
	public Interface(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements, List<Interface> implement) {
		super(name, access, modifiers, subElements, implement);
	}
	
}
