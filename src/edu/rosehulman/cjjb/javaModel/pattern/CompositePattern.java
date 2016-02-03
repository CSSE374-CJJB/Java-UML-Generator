package edu.rosehulman.cjjb.javaModel.pattern;

import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Relation;
import edu.rosehulman.cjjb.javaModel.checks.IPattern;

public class CompositePattern implements IPattern {

	public AbstractJavaStructure component;
	Set<AbstractJavaStructure> composites;
	Set<AbstractJavaStructure> leaves;
	
	public static final String COMPONENT = "Component";
	public static final String LEAF = "Leaf";
	public static final String COMPOSITE = "Composite";
			
	public CompositePattern(AbstractJavaStructure component) {
		this.component = component;
		this.composites = new HashSet<AbstractJavaStructure>();
		this.leaves = new HashSet<AbstractJavaStructure>();
	}
	
	@Override
	public String getStereotype(AbstractJavaStructure struct) {
		if(component.equals(struct))
			return COMPONENT;
		if(composites.contains(struct))
			return COMPOSITE;
		if(leaves.contains(struct))
			return LEAF;
		
		return null;
	}

	@Override
	public List<AbstractJavaStructure> getInvolvedStructes() {
		List<AbstractJavaStructure> toReturn = new LinkedList<AbstractJavaStructure>();
		
		toReturn.addAll(leaves);
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

	public void addLeaf(AbstractJavaStructure struct) {
		this.leaves.add(struct);
	}
	
	public void addComposite(AbstractJavaStructure struct) {
		this.composites.add(struct);
	}
	
}
