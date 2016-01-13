package edu.rosehulman.cjjb.javaModel;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class AbstractJavaThing {
	public String name;
	public IAccessModifier access;
	public List<IModifier> modifiers;
	
	public AbstractJavaThing(String name, IAccessModifier access, List<IModifier> modifiers) {
		this.name = name;
		this.access = access;
		this.modifiers = modifiers;
	}
	
	
}
