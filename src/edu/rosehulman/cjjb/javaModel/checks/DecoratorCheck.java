package edu.rosehulman.cjjb.javaModel.checks;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaField;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.JavaMethod;

public class DecoratorCheck implements IPatternCheck{

	@Override
	public List<IPattern> check(JavaModel model) {
		List<IPattern> toReturn = new LinkedList<IPattern>();
		for(AbstractJavaStructure struct: model.getStructures()){
			List<JavaMethod> constructs = struct.getConstructors();
			Set<AbstractJavaStructure> superClasses = struct.getSuperClasses();
			
			for(JavaMethod meth: constructs) {
				boolean cont = false;
				for(AbstractJavaStructure arg: meth.arguments) {
					if(struct.isCastableTo(arg)) {
						for (AbstractJavaElement ele: struct.subElements) {
							if (ele instanceof JavaField) {
								if (ele.type.isCastableTo(arg)) {
									cont = true;
								}
							}
						}
					}
				}
				if(!cont)
					return toReturn; //false
			}
		}
		return null;
	}
}
