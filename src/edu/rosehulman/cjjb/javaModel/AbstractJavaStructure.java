package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IVisitor;

public abstract class AbstractJavaStructure extends AbstractJavaThing {
	public List<AbstractJavaElement> subElements;
	public List<AbstractJavaStructure> implement;
	
	public AbstractJavaStructure(String name, IAccessModifier access, List<IModifier> modifiers, List<AbstractJavaElement> subElements, List<AbstractJavaStructure> implement) {
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
	public void accept(IVisitor v) throws IOException {
		
		for(AbstractJavaElement element: subElements) {
			if(element instanceof Field)
				v.visit((Field)element);			
		}
		
		v.visitEndFields();
		
		for(AbstractJavaElement element: subElements) {
			if(element instanceof Method)
				v.visit((Method)element);			
		}
		
		v.visitEndStructure();
	}
	
}
