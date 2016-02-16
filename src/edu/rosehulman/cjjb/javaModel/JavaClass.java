package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class JavaClass extends AbstractJavaStructure {
	public AbstractJavaStructure superClass;

	public JavaClass(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements,
			List<AbstractJavaStructure> implement, AbstractJavaStructure superClass) {
		super(name, access, modifiers, subElements, implement);
		this.superClass = superClass;
	}

	public JavaClass(String cleanName) {
		super(cleanName);

		this.superClass = null;
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visit(this);

		super.accept(v);
	}
	
	@Override
	public boolean isCastableTo(AbstractJavaStructure struct) {
		if(super.isCastableTo(struct))
			return true;
		
		if(superClass != null && superClass.isCastableTo(struct))
			return true;
		
		return false;
	}
	
	@Override
	protected void getSuperClasses(Set<AbstractJavaStructure> set) {
		if(this.superClass != null)
			this.superClass.getSuperClasses(set);
		super.getSuperClasses(set);
	}
	
	@Override
	public List<AbstractJavaElement> getElementByName(String name) {
		List<AbstractJavaElement> element = super.getElementByName(name);
		
		if(superClass != null)
			element.addAll(superClass.getElementByName(name));
		
		return element;
	}
	
	@Override
	protected <T extends AbstractJavaElement> void getElementsOfType(List<T> list, Class<T> clazz) {
		super.getElementsOfType(list, clazz);
		
		if(superClass != null) {
			superClass.getElementsOfType(list, clazz);
		}
	}
}
