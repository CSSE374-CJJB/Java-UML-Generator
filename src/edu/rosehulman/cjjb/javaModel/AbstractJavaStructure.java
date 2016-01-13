package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class AbstractJavaStructure extends AbstractJavaThing {
	public List<AbstractJavaElement> subElements;
	public List<Interface> implement;
	
	public AbstractJavaStructure(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements, List<Interface> implement) {
		super(name, access, modifiers);
		this.subElements = subElements;
		this.implement = implement;
	}
	
	
}
