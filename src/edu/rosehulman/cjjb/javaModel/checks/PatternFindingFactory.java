package edu.rosehulman.cjjb.javaModel.checks;

import java.util.LinkedList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.visitor.IStructureVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.SingletonVisitor;

public class PatternFindingFactory {

	public static List<IPatternCheck> getPatternChecks() {
		List<IPatternCheck> toReturn = new LinkedList<IPatternCheck>();

		toReturn.add(new AdapterCheck());
		toReturn.add(new DecoratorCheck());
		toReturn.add(new CompositeCheck());
		
		return toReturn;
	}
	
	public static List<IStructureVisitor> getStructureVisitors() {
		List<IStructureVisitor> toReturn = new LinkedList<IStructureVisitor>();
		
		toReturn.add(new SingletonVisitor());
		
		return toReturn;
	}
}
