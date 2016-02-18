package edu.rosehulman.cjjb.javaModel.checks;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.JsonConfig;
import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.JavaField;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.pattern.DecoratorPattern;
import edu.rosehulman.cjjb.javaModel.JavaMethod;

public class DecoratorCheck implements IPatternCheck {

	@Override
	public List<IPattern> check(JavaModel model) {
		List<IPattern> toReturn = new LinkedList<IPattern>();
		for(AbstractJavaStructure struct: model.getStructures()){
			AbstractJavaStructure mayBeValid = checkForPotentialDecorator(struct);
			
			if(mayBeValid != null) {
				DecoratorPattern potential = containsPattern(toReturn, mayBeValid);
				if(potential != null) {
					potential.addToDecorators(struct);
				} else {
					Set<AbstractJavaStructure> set = new HashSet<AbstractJavaStructure>();
					
					populateListOfClassesToOther(set, struct, mayBeValid);
					DecoratorPattern pattern = new DecoratorPattern(mayBeValid, set);
					toReturn.add(pattern);
				}
			}
		}
		return toReturn;
	}
	
	public AbstractJavaStructure checkForPotentialDecorator(AbstractJavaStructure struct) {
		for(JavaMethod meth: struct.getConstructors()) {
			for(AbstractJavaStructure arg: meth.arguments) {
				if(!arg.name.equals("java.lang.Object") && struct.isCastableTo(arg)) {
					if(hasField(struct, arg))
						return arg;
				}
			}
		}
		return null;
	}
	
	public void populateListOfClassesToOther(Set<AbstractJavaStructure> set, AbstractJavaStructure struct, AbstractJavaStructure to) {		
		set.add(struct);
		
		if(struct.implement != null)
			for(AbstractJavaStructure imp: struct.implement) {
				if(imp.isCastableTo(to)){
					populateListOfClassesToOther(set, imp, to);
				}
			}
		
		if(struct instanceof JavaClass) {
			AbstractJavaStructure sClass = ((JavaClass)struct).superClass; 
			if(sClass != null && sClass.isCastableTo(to)){
				populateListOfClassesToOther(set, sClass, to);
			}
		}
	}

	public boolean hasField(AbstractJavaStructure struct, AbstractJavaStructure of) {
		
		List<AbstractJavaElement> elements = struct.subElements;
		for(AbstractJavaElement ele: elements) {
			if(ele instanceof JavaField && ele.type.equals(of)) {
				return true;
			}
		}
		
		if(struct instanceof JavaClass) {
			if(((JavaClass)struct).superClass != null )
				return hasField(((JavaClass)struct).superClass, of);
		}
		
		return false;
	}
	
	public boolean hasFieldCatableTo(List<AbstractJavaElement> elements, AbstractJavaStructure to) {
		for(AbstractJavaElement ele: elements) {
			if(ele instanceof JavaField && ele.type.isCastableTo(to)) {
				return true;
			}
		}
		return false;
	}
	
	public DecoratorPattern containsPattern(List<IPattern> patterns, AbstractJavaStructure to) {
		for(IPattern pattern: patterns) {
			DecoratorPattern dPattern = (DecoratorPattern)pattern;
			if(dPattern.struct.equals(to)) {
				return dPattern;
			}
		}
		return null;
	}

	@Override
	public void setSettings(JsonConfig config) {
		// TODO Decorator Settings
	}
}
