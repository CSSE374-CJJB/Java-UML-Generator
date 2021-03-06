package edu.rosehulman.cjjb.asm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SequenceStructure {
	
	private Map<String,Set<QualifiedMethod>> classMethod;
	private Map<String,Set<QualifiedMethod>> visitedMethods;
	
	public SequenceStructure() {
		this.classMethod = new HashMap<String,Set<QualifiedMethod>>();
		this.visitedMethods = new HashMap<String,Set<QualifiedMethod>>();
	}
	
	public void addMethod(String clazz, QualifiedMethod method) {
		if(visitedMethod(clazz, method))
			return;
		
		Set<QualifiedMethod> methods = classMethod.get(clazz);
		
		if(methods == null) {
			methods = new HashSet<QualifiedMethod>();
			classMethod.put(clazz, methods);
		}
		
		methods.add(method);
	}
	
	public Map<String,Set<QualifiedMethod>> getClassMethods() {
		return classMethod;
	}
	
	public void vistedAll() {
		for(String s: classMethod.keySet()) {
			Set<QualifiedMethod> methods = visitedMethods.get(s);
			
			if(methods == null) {
				visitedMethods.put(s, classMethod.get(s));
			} else {
				methods.addAll(classMethod.get(s));
			}
		}
		this.classMethod = new HashMap<String, Set<QualifiedMethod>>();
	}
	
	public boolean visitedMethod(String clazz, QualifiedMethod method) {
		Set<QualifiedMethod> methods = visitedMethods.get(clazz);
		
		if(methods == null) {
			return false;
		} else {
			return methods.contains(method);
		}
		
		
	}
	
}
