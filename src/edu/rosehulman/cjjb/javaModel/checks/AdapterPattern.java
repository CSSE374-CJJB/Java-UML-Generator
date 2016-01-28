package edu.rosehulman.cjjb.javaModel.checks;


import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.Relation;

public class AdapterPattern implements IPattern {
	
	AbstractJavaStructure from;
	AbstractJavaStructure to;
	JavaClass adapter;
	public static final String TARGET = "target";
	public static final String ADAPTEE = "adaptee";
	public static final String ADAPTER = "adapter";
	public static final String RELATION_NAME = "adapts";
	
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

	@Override
	public List<AbstractJavaStructure> getInvolvedStructes() {
		List<AbstractJavaStructure> toReturn = new LinkedList<AbstractJavaStructure>();
		
		toReturn.add(from);
		toReturn.add(to);
		toReturn.add(adapter);
		
		return toReturn;
	}

	@Override
	public Color getDefaultColor() {
		return Color.RED;
	}

	@Override
	public List<Relation> getTopLevelRelations() {
		List<Relation> toReturn = new LinkedList<Relation>();
		
		toReturn.add(new Relation(adapter, from));
		
		return toReturn;
	}
	
	@Override
	public String getRelationName() {
		return this.RELATION_NAME;
	}


}
