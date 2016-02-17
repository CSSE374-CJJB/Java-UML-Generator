package edu.rosehulman.cjjb.javaModel.checks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.cjjb.javaModel.visitor.IStructureVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.SingletonVisitor;

public class PatternFindingFactory {

	static Map<String, Class<? extends IPatternCheck>> patterns;
	static Map<String, Class<? extends IStructureVisitor>> visitors;
	
	static {
		patterns = new HashMap<String, Class<? extends IPatternCheck>>();
		visitors = new HashMap<String, Class<? extends IStructureVisitor>>();
		
		patterns.put("Decorator-Detection", DecoratorCheck.class);
		patterns.put("Adapter-Detection", AdapterCheck.class);
		patterns.put("Composite-Detection", CompositeCheck.class);
		
		visitors.put("Singleton-Detection", SingletonVisitor.class);
	}
	
	public static List<IPatternCheck> getPatternChecks() {
		List<IPatternCheck> toReturn = new LinkedList<IPatternCheck>();

		toReturn.add(new AdapterCheck());
		toReturn.add(new DecoratorCheck());
		toReturn.add(new CompositeCheck());
		
		return toReturn;
	}
	
	public static List<IPatternCheck> getPatternChecks(String phases) {
		List<IPatternCheck> toReturn = new LinkedList<IPatternCheck>();

		for(String s: patterns.keySet()) {
			if(phases.contains(s)) {
				try {
					toReturn.add(patterns.get(s).newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					System.out.println("Invalid IPatternCheck Class");
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		
		return toReturn;
	}
	
	public static List<IStructureVisitor> getStructureVisitors() {
		List<IStructureVisitor> toReturn = new LinkedList<IStructureVisitor>();
		
		toReturn.add(new SingletonVisitor());
		
		return toReturn;
	}
	
	public static List<IStructureVisitor> getStructureVisitors(String phases) {
		List<IStructureVisitor> toReturn = new LinkedList<IStructureVisitor>();
		
		for(String s: visitors.keySet()) {
			if(phases.contains(s)) {
				try {
					toReturn.add(visitors.get(s).newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					System.out.println("Invalid IPatternCheck Class");
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		
		return toReturn;
	}
}
