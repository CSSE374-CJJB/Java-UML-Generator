package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import edu.rosehulman.cjjb.javaModel.visitor.ITraverser;
import edu.rosehulman.cjjb.javaModel.visitor.IVisitor;

public class JavaModel implements ITraverser{

	HashMap<String, AbstractJavaStructure> map;
	
	Set<String> includedClasses;
	
	public JavaModel(Set<String> includedClasses) {
		this.map = new HashMap<String, AbstractJavaStructure>();
		
		this.map.put("void", new Interface("void"));
		this.map.put("int", new Interface("int"));
		this.map.put("boolean", new Interface("boolean"));
		
		this.includedClasses = includedClasses;
	}

	@Override
	public void accept(IVisitor v) throws IOException{
		v.visitStart();
		
		for(AbstractJavaStructure struct: map.values()) {
			if(includedClasses.contains(struct.name))
				struct.accept(v);
		}
		
		v.visitEnd();
	}
	
	public boolean containsStructure(String name) {
		return map.containsKey(name);
	}
	
	public void putStructure(String name, AbstractJavaStructure struct) {
		map.put(name, struct);
	}
	
	public AbstractJavaStructure getStructure(String name) {
		return map.get(name);
	}
	
}
