package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class Field extends AbstractJavaElement {

	public Field(String name, IAccessModifier access, List<IModifier> modifiers, AbstractJavaStructure type) {
		super(name, access, modifiers, type);
	}
	
}
