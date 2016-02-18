package edu.rosehulman.cjjb.javaModel.checks;

import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.JsonConfig;
import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.JavaField;
import edu.rosehulman.cjjb.javaModel.JavaMethod;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.pattern.AdapterPattern;

public class AdapterCheck implements IPatternCheck {

	private int adaptedMethodCount;

	@Override
	public List<IPattern> check(JavaModel model) {
		List<IPattern> toReturn = new LinkedList<IPattern>();
		
		for(AbstractJavaStructure struct: model.getStructures()) {
			if( !(struct instanceof JavaClass))
				continue;
			
			AbstractJavaStructure to = checkInheritance((JavaClass)struct);
			if(to == null) {
				continue;
			}
			
			for(JavaMethod meth: struct.getConstructors() ) {
				if(meth.arguments.size() > 1) 
					break;
				for(AbstractJavaStructure arg: meth.arguments) {
					if(hasFieldCatableTo(struct.subElements, arg)) {
						if(checkMethodsForCallTo(struct.subElements, arg) && ! (to.isCastableTo(arg) || arg.isCastableTo(to))) {
							toReturn.add(new AdapterPattern(arg, to, (JavaClass) struct));
						}
					}
				}
			}
		}
		
		return toReturn;
	}
	
	private boolean checkMethodsForCallTo(List<AbstractJavaElement> subElements, AbstractJavaStructure arg) {
		int counter = 0;
		
		for(AbstractJavaElement ele: subElements) {
			if(ele instanceof JavaMethod) {
				if(((JavaMethod) ele).isConstructor)
					continue;
				for(JavaMethod meth :((JavaMethod) ele).methodCalls) {
					if(meth.owner.equals(arg)) {
						counter++;
						break;
					}
				}
			}
		}
		return counter >= this.adaptedMethodCount;
	}

	public boolean hasFieldCatableTo(List<AbstractJavaElement> elements, AbstractJavaStructure to) {
		for(AbstractJavaElement ele: elements) {
			if(ele instanceof JavaField && ele.type.isCastableTo(to)) {
				return true;
			}
		}
		return false;
	}
	
	public AbstractJavaStructure checkInheritance(JavaClass struct) {
		AbstractJavaStructure implments = null;
		AbstractJavaStructure supers = null;
		
		if(struct.implement != null && struct.implement.size() == 1) {
			implments = struct.implement.get(0);
		}
		
		if(struct.superClass != null) {
			if(!struct.superClass.name.equals("java.lang.Object")){
				supers = struct.superClass;				
			}
		}

		if(implments != null && supers != null || implments == null && supers == null) {
			return null;
		}
		
		return supers == null ? implments : supers;
	}

	@Override
	public void setSettings(JsonConfig config) {
		this.adaptedMethodCount = config.Adapter_MethodDelegation;
	}

}
