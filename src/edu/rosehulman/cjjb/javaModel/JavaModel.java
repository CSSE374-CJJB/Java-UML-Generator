package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.asm.MethodCallGroup;
import edu.rosehulman.cjjb.asm.MethodCallLine;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.visitor.ITraverser;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class JavaModel implements ITraverser {

	HashMap<String, AbstractJavaStructure> map;
	
	List<MethodCallGroup> methodGroups;

	Set<String> includedClasses;

	public JavaModel(Set<String> includedClasses) {
		this.map = new HashMap<String, AbstractJavaStructure>();

		this.includedClasses = includedClasses;
		
		this.methodGroups = new LinkedList<MethodCallGroup>();
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visitStart();

		for (String name : map.keySet()) {
			if (includedClasses.contains(name))
				map.get(name).accept(v);
		}
		v.visitRelations(this);

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

	public List<Relation> getChildParrentIncludedRelations() {
		List<Relation> relations = new LinkedList<Relation>();
		for (AbstractJavaStructure struct : map.values()) {
			if (struct instanceof Class) {
				if (includedClasses.contains(struct.name)) {
					Class clazz = (Class) struct;
					if (clazz.superClass != null) {
						if (includedClasses.contains(clazz.superClass.name)) {
							relations.add(new Relation(clazz, clazz.superClass));
						}
					}
				}
			}
		}
		return relations;
	}

	public List<Relation> getIncludedInterfaceRelations() {
		List<Relation> relations = new LinkedList<Relation>();
		for (AbstractJavaStructure struct : map.values()) {
			if (includedClasses.contains(struct.name)) {
				for (AbstractJavaStructure other : struct.implement) {
					if (includedClasses.contains(other.name)) {
						relations.add(new Relation(struct, other));
					}
				}
			}
		}
		return relations;
	}

	public List<Relation> getIncludedUsesRelations() {
		Set<Relation> relations = new HashSet<Relation>();
		for (AbstractJavaStructure struct : map.values()) {
			if (includedClasses.contains(struct.name)) {
				for (AbstractJavaElement other : struct.subElements) {
					if (other instanceof Method) {
						if (includedClasses.contains(other.type.name)) {
							relations.add(new Relation(struct, other.type));
						}

						for (AbstractJavaStructure arg : ((Method) other).arguments) {
							if (includedClasses.contains(arg.name)) {
								relations.add(new Relation(struct, arg));
							}
						}
					}
				}
			}

		}
		return new ArrayList<Relation>(relations);
	}

	public List<Relation> getIncludedAssociationReltiations() {
		Set<Relation> relations = new HashSet<Relation>();
		for (AbstractJavaStructure struct : map.values()) {
			if (includedClasses.contains(struct.name)) {
				for (AbstractJavaElement other : struct.subElements) {
					if (includedClasses.contains(other.name)) {
						if (other instanceof Field) {
							relations.add(new Relation(struct, other.type));
						}
					}
				}
			}
		}
		return new ArrayList<Relation>(relations);
	}

	
	public void addMethodCallGroup(MethodCallGroup group) {
		this.methodGroups.add(group);
	}
	
	public void convertMethodCallLinesToStructure() {
		for(MethodCallGroup group: this.methodGroups) {
			AbstractJavaStructure caller = Utils.getInstanceOrJavaStructure(this, group.classCaller);
			for(MethodCallLine line: group.lines) {
				AbstractJavaStructure other = Utils.getInstanceOrJavaStructure(this, line.classOf);
				
				Method method = (Method) caller.getElementByName(group.name);
				Method otherMethod = (Method) other.getElementByName(line.name);
				
				method.addMethodCall(otherMethod);
			}
		}
	}
}
