package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
			if (element instanceof Field)
				v.visit((Field) element);
		}

		v.visitEndFields();

		for (AbstractJavaElement element : subElements) {
			if (element instanceof Method)
				v.visit((Method) element);
		}

		v.visitEndStructure();
	}
	
	public AbstractJavaElement getElementByName(String name) {
		for(AbstractJavaElement element: this.subElements) {
			if(element.name.equals(name)){
				return element;
			}
		}
		return null;
	}
	
	public Method getMethodByQualifiedName(QualifiedMethod meth, JavaModel model) {
		for(AbstractJavaElement element: this.subElements) {
			if (element instanceof Method) {
				if(element.name.equals(meth.methodName)){
					List<String> list = Utils.getListOfArgs(meth.methodDesc);
					Method method = (Method)element;
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
}
