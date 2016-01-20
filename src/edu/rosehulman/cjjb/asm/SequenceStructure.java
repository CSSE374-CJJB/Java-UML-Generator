package edu.rosehulman.cjjb.asm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SequenceStructure {
	
	private Map<String,Set<String>> classMethod;
	private Map<String,Set<String>> visitedMethods;
	
	public SequenceStructure() {
		this.classMethod = new HashMap<String,Set<String>>();
		this.visitedMethods = new HashMap<String,Set<String>>();
	}
	
	public void addMethod(String clazz, String method) {
		if(visitedMethod(clazz, method))
			return;
		
		Set<String> methods = classMethod.get(clazz);
		
		if(methods == null) {
			methods = new HashSet<String>();
			classMethod.put(clazz, methods);
		}
		
		methods.add(method);
	}
	
	public Map<String,Set<String>> getClassMethods() {
		return classMethod;
	}
	
	public void vistedAll() {
		for(String s: classMethod.keySet()) {
			Set<String> methods = visitedMethods.get(s);
			
			if(methods == null) {
				visitedMethods.put(s, classMethod.get(s));
			} else {
				methods.addAll(classMethod.get(s));
			}
		}
		this.classMethod = new HashMap<String, Set<String>>();
	}
	
	public boolean visitedMethod(String clazz, String method) {
		Set<String> methods = visitedMethods.get(clazz);
		
		if(methods == null) {
			return false;
		} else {
			return methods.contains(method);
		}
		
		
	}
	
}
