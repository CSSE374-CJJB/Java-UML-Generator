package edu.rosehulman.cjjb.javaModel.visitor;

import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.JavaInterface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.checks.IPattern;
import edu.rosehulman.cjjb.javaModel.JavaMethod;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.StaticModifier;
import edu.rosehulman.cjjb.javaModel.pattern.SingletonPattern;

public class SingletonVisitor implements IStructureVisitor {

	@Override
	public List<IPattern> visit(JavaModel model, AbstractJavaStructure struct) {
		List<IPattern> toReturn = new LinkedList<IPattern>();
		
		if(struct instanceof JavaInterface) {
			// Do nothing
		}
		else if(checkForStaticInstance(struct)){
			toReturn.add(new SingletonPattern((JavaClass)struct));
		}else if(checkForGetInstance(model, struct)) {
			toReturn.add(new SingletonPattern((JavaClass)struct));
		}
		
		return toReturn;
	}
	
	private boolean checkForStaticInstance(AbstractJavaStructure structure) {
		for(AbstractJavaElement element: structure.subElements) {
			if(element.name.equalsIgnoreCase("instance"))
				if(checkForModifier(element.modifiers, StaticModifier.class))
					return true;
		}
		return false;
	}
	
	private boolean checkForModifier(List<IModifier> modifiers, Class<?> c) {
		for(IModifier mod: modifiers)
			if(c.isInstance(mod))
				return true;
		
		return false;
	}

	private boolean checkForGetInstance(JavaModel model, AbstractJavaStructure structure) {
		JavaMethod method = structure.getMethodByQualifiedName(new QualifiedMethod("getInstance", "()" + Utils.getAsmName(structure.name)), model);
		
		if(method == null)
			method = structure.getMethodByQualifiedName(new QualifiedMethod("get" + Utils.shortName(structure.name), "()" + Utils.getAsmName(structure.name)), model);
		
		if(method == null)
			return false;
		
		if(method.arguments != null && method.arguments.size() != 0)
			return false;
		
		if(!checkForModifier(method.modifiers, StaticModifier.class))
			return false;
		
		if(method.type.equals(structure))
			return true;

		return false;			
	}
}
