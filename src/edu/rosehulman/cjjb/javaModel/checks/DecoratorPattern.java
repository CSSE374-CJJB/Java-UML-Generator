package edu.rosehulman.cjjb.javaModel.checks;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;

public class DecoratorPattern implements IPattern {
	
	List<AbstractJavaStructure> list;
	AbstractJavaStructure struct;
	public static final String COMPONENT = "compoenent";
	public static final String DECORATOR = "decorator";
	
	public DecoratorPattern(AbstractJavaStructure struct, List<AbstractJavaStructure> list) {
		this.struct = struct;
		this.list = list;
	}
	
	@Override
	public String getStereotype(AbstractJavaStructure struct) {
		if (this.struct.equals(struct)) {
			return COMPONENT;
		}
		if (this.list.contains(struct)) {
			return DECORATOR;
		}
		return null;
	}

}
