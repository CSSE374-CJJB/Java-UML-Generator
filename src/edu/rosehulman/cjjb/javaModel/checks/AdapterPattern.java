package edu.rosehulman.cjjb.javaModel.checks;


import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;

public class AdapterPattern implements IPattern {
	
	AbstractJavaStructure from;
	AbstractJavaStructure to;
	JavaClass adapter;
	public static final String TARGET = "target";
	public static final String ADAPTEE = "adaptee";
	public static final String ADAPTER = "adapter";
	
	public AdapterPattern(AbstractJavaStructure from, AbstractJavaStructure to, JavaClass adapter) {
		this.from = from;
		this.to = to;
		this.adapter = adapter;
	}

	@Override
	public String getStereotype(AbstractJavaStructure struct) {
		if (this.to.equals(struct)) {
			return TARGET;
		}
		if (this.from.equals(struct)) {
			return ADAPTEE;
		}
		if (this.adapter.equals(struct)) {
			return ADAPTER;
		}
		return null;
	}


}
