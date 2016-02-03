package edu.rosehulman.cjjb.javaModel.checks;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaMethod;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.pattern.CompositePattern;

public class CompositeCheck implements IPatternCheck {

	Set<AbstractJavaStructure> set = new HashSet<AbstractJavaStructure>();

	@Override
	public List<IPattern> check(JavaModel model) {
		List<IPattern> toReturn = new LinkedList<IPattern>();
		
		List<AbstractJavaStructure> leafPossibility = new LinkedList<AbstractJavaStructure>();
		
		for(AbstractJavaStructure struct: model.getStructures()) {
			set.clear();
			if(hasAddRemove(struct)) {
				for(AbstractJavaStructure component: set) {
					CompositePattern pattern = containsPattern(toReturn, component);
					if(pattern != null) {
						pattern.addComposite(struct);
					} else {
						pattern = new CompositePattern(component);
						pattern.addComposite(struct);
						
						for(AbstractJavaStructure leaf: leafPossibility) {
							if(leaf.isCastableTo(component)) {
								pattern.addComposite(leaf);
							}
						}
					}
				}
			} else {
				for(IPattern pattern: toReturn) {
					AbstractJavaStructure comp = ((CompositePattern)pattern).component;
					if(struct.isCastableTo(comp)) {
						((CompositePattern)pattern).addLeaf(struct);
					}
				}
				leafPossibility.add(struct);				
			}
		}
		
		return toReturn;
	}
	
	public boolean hasAddRemove(AbstractJavaStructure struct) {
		boolean hasMethods = false;
		
		for(AbstractJavaElement ele: struct.subElements) {
			if(!(ele instanceof JavaMethod)) {
				continue;
			}
			JavaMethod meth = (JavaMethod) ele;
			
			if(meth.name.equalsIgnoreCase("add") && meth.arguments.size() == 1) {				
				AbstractJavaStructure arg = meth.arguments.get(0);
				if (struct.isCastableTo(arg)) {
					for(AbstractJavaElement eleIn: struct.subElements) {
						if(!(ele instanceof JavaMethod)) {
							continue;
						}
						JavaMethod methIn = (JavaMethod) eleIn;
						if(methIn.name.equalsIgnoreCase("remove")  && methIn.arguments.size() == 1) {
							AbstractJavaStructure argIn = methIn.arguments.get(0);
							
							if(arg.equals(argIn)){
								set.add(arg);
								hasMethods = true;
							}
						}
					}
				}
			}
			
		}
		
		return hasMethods;
	}
	
	public AbstractJavaStructure findComponent(AbstractJavaStructure struct) {
		for(AbstractJavaStructure interfase: struct.implement) {
			if(hasAddRemove(interfase)) {
				AbstractJavaStructure comp = findComponent(interfase);
			}
		}
		
		return struct;
	}

	public CompositePattern containsPattern(List<IPattern> patterns, AbstractJavaStructure to) {
		for(IPattern pattern: patterns) {
			CompositePattern dPattern = (CompositePattern)pattern;
			if(dPattern.component.equals(to)) {
				return dPattern;
			}
		}
		return null;
	}
}
