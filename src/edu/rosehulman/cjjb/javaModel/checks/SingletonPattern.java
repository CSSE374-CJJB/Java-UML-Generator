package edu.rosehulman.cjjb.javaModel.checks;


import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;

public class SingletonPattern implements IPattern {

	JavaClass struct;
	public static final String STEREOTYPE = "singleton";
	
	public SingletonPattern(JavaClass struct) {
		this.struct = struct;
	}
	
	@Override
	public String getStereotype(AbstractJavaStructure struct) {
		if (this.struct.equals(struct)) {
			return STEREOTYPE;
		}
		return null;
	}

}
