package edu.rosehulman.cjjb.javaModel.pattern;


import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.Relation;
import edu.rosehulman.cjjb.javaModel.checks.IPattern;

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

	@Override
	public List<AbstractJavaStructure> getInvolvedStructes() {
		List<AbstractJavaStructure> toReturn = new ArrayList<AbstractJavaStructure>(1);
		toReturn.add(struct);
		return toReturn;
	}

	@Override
	public Color getDefaultColor() {
		return Color.BLUE;
	}

	@Override
	public List<Relation> getTopLevelRelations() {
		return new LinkedList<Relation>();
	}

	@Override
	public String getRelationName() {
		return "";
	}

}
