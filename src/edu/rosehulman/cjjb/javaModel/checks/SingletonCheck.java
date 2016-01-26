package edu.rosehulman.cjjb.javaModel.checks;

import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.JavaInterface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.JavaMethod;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.StaticModifier;

public class SingletonCheck implements IPatternCheck {

	@Override
	public List<IPattern> check(JavaModel model) {
		List<IPattern> toReturn = new LinkedList<IPattern>();
		for (AbstractJavaStructure struct: model.getStructures()) {
			// Only classes can be singletons
			if(struct instanceof JavaInterface)
				continue;
			
			if(checkForStaticInstance(struct)){
				toReturn.add(new SingletonPattern((JavaClass)struct));
				continue;
			}
			
			if(checkForGetInstance(model, struct)) {
				toReturn.add(new SingletonPattern((JavaClass)struct));
				continue;
			}
		}
		return toReturn;
	}
	
	private boolean checkForStaticInstance(AbstractJavaStructure structure) {
		for(AbstractJavaElement element: structure.subElements) {
			System.out.println(element.name);
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
