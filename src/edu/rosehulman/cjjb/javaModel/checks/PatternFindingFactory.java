package edu.rosehulman.cjjb.javaModel.checks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.rosehulman.cjjb.JsonConfig;
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
	
	public static List<IPatternCheck> getPatternChecks(JsonConfig config) {
		List<IPatternCheck> toReturn = new LinkedList<IPatternCheck>();
		
		List<String> phases = Arrays.asList(config.Phases);

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
	
	public static List<IStructureVisitor> getStructureVisitors(JsonConfig config) {
		List<IStructureVisitor> toReturn = new LinkedList<IStructureVisitor>();
		List<String> phases = Arrays.asList(config.Phases);
		for(String s: visitors.keySet()) {
			if(phases.contains(s)) {
				try {
					IStructureVisitor vis = visitors.get(s).newInstance();
					vis.setSettings(config);
					toReturn.add(vis);
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
