package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public abstract class AbstractJavaStructure extends AbstractJavaThing {
	public List<AbstractJavaElement> subElements;
	public List<AbstractJavaStructure> implement;

	public AbstractJavaStructure(String name, IAccessModifier access, List<IModifier> modifiers,
			List<AbstractJavaElement> subElements, List<AbstractJavaStructure> implement) {
		super(name, access, modifiers);
		this.subElements = subElements;
		this.implement = implement;
	}

	public AbstractJavaStructure(String cleanName) {
		super(cleanName);
		this.subElements = new LinkedList<AbstractJavaElement>();
	}

	public void addSubElement(AbstractJavaElement element) {
		this.subElements.add(element);
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {

		for (AbstractJavaElement element : subElements) {
			if (element instanceof JavaField)
				v.visit((JavaField) element);
		}

		v.visitEndFields();

		for (AbstractJavaElement element : subElements) {
			if (element instanceof JavaMethod)
				v.visit((JavaMethod) element);
		}

		v.visitEndStructure();
	}
	
	public List<AbstractJavaElement> getElementByName(String name) {
		List<AbstractJavaElement> toReturn = new LinkedList<AbstractJavaElement>();
		for(AbstractJavaElement element: this.subElements) {
			if(element.name.equals(name)){
				toReturn.add(element);
			}
		}
		return toReturn;
	}
	
	public JavaMethod getMethodByQualifiedName(QualifiedMethod meth, JavaModel model) {
		for(AbstractJavaElement element: this.subElements) {
			if (element instanceof JavaMethod) {
				if(element.name.equals(meth.methodName)){
					List<String> list = Utils.getListOfArgs(meth.methodDesc);
					JavaMethod method = (JavaMethod)element;
					if (list.size() != method.arguments.size()) {
						continue;
					}
					boolean t = true;
					for (int i = 0; i < list.size(); i++) {
						AbstractJavaStructure struct = Utils.getInstanceOrJavaStructure(model, list.get(i));
						if (!struct.equals(method.arguments.get(i))){
							t = false;
							break;
						}
					}
					if (!t) {
						continue;
					}
					return method;
				}
			}
		}
		
		return null;
	}
	
	public List<JavaMethod> getConstructors() {
		List<JavaMethod> toReturn = new LinkedList<JavaMethod>();
		for(AbstractJavaElement ele: subElements) {
			if(ele instanceof JavaMethod) {
				if(((JavaMethod)ele).isConstructor) {
					toReturn.add((JavaMethod)ele);
				}
			}
		}
		return toReturn;
	}
	
	public boolean isCastableTo(AbstractJavaStructure struct) {
		if(this.equals(struct)) {
			return true;
		}
		else {
			if(this.implement != null)
				for(AbstractJavaStructure imp: this.implement) {
					if(imp.isCastableTo(struct))
						return true;
				}
		}
		
		return false;
	}
	
	public Set<AbstractJavaStructure> getSuperClasses() {
		Set<AbstractJavaStructure> toReturn = new HashSet<AbstractJavaStructure>();
		getSuperClasses(toReturn);
		return toReturn;
	}
	
	protected void getSuperClasses(Set<AbstractJavaStructure> set) {
		set.add(this);
		if(this.implement != null)
			for (AbstractJavaStructure struct: this.implement) {
				struct.getSuperClasses(set);
			}
	}
}
