package edu.rosehulman.cjjb.javaModel.checks;

import java.awt.Color;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Relation;

public interface IPattern {
	public String getStereotype(AbstractJavaStructure struct);
	public List<AbstractJavaStructure> getInvolvedStructes();
	public Color getDefaultColor();
	public List<Relation> getTopLevelRelations();
	String getRelationName();
}
