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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractJavaThing other = (AbstractJavaThing) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
