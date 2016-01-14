package edu.rosehulman.cjjb.javaModel;

import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLTraverser;

public abstract class AbstractJavaThing implements IUMLTraverser {
	public String name;
	public IAccessModifier access;
	public List<IModifier> modifiers;

	public AbstractJavaThing(String name, IAccessModifier access, List<IModifier> modifiers) {
		this.name = name;
		this.access = access;
		this.modifiers = modifiers;
	}

	public AbstractJavaThing(String cleanName) {
		this(cleanName, null, new LinkedList<IModifier>());
	}

}
