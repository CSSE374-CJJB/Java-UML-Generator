package edu.rosehulman.cjjb.javaModel.pattern;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Relation;
import edu.rosehulman.cjjb.javaModel.checks.IPattern;

public class CompositePattern implements IPattern {

	public AbstractJavaStructure component;
	List<AbstractJavaStructure> composites;
	List<AbstractJavaStructure> leafs;
	
	public static final String COMPONENT = "Component";
	public static final String LEAF = "Leaf";
	public static final String COMPOSITE = "Composite";
			
	
	@Override
	public String getStereotype(AbstractJavaStructure struct) {
		if(component.equals(struct))
			return COMPONENT;
		if(composites.contains(struct))
			return COMPOSITE;
		if(leafs.contains(struct))
			return LEAF;
		
		return null;
	}

	@Override
	public List<AbstractJavaStructure> getInvolvedStructes() {
		List<AbstractJavaStructure> toReturn = new LinkedList<AbstractJavaStructure>();
		
		toReturn.addAll(leafs);
		toReturn.addAll(composites);
		toReturn.add(component);
		
		return toReturn;
	}

	@Override
	public Color getDefaultColor() {
		return Color.YELLOW;
	}

	@Override
	public List<Relation> getTopLevelRelations() {
		List<Relation> toReturn = new LinkedList<Relation>();
		return toReturn;
	}

	@Override
	public String getRelationName() {
		return "";
	}

	
}
