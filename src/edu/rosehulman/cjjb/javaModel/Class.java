package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class Class extends AbstractJavaStructure {
	public AbstractJavaStructure superClass;

	public Class(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements,
			List<Interface> implement, AbstractJavaStructure superClass) {
		super(name, access, modifiers, subElements, implement);
		this.superClass = superClass;
	}
	
	
}
