package edu.rosehulman.cjjb.javaModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.rosehulman.cjjb.asm.MethodCallGroup;
import edu.rosehulman.cjjb.asm.MethodCallLine;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.checks.IPattern;
import edu.rosehulman.cjjb.javaModel.checks.IPatternCheck;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PublicModifier;
import edu.rosehulman.cjjb.javaModel.visitor.ISequenceVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.ISquenceTraverser;
import edu.rosehulman.cjjb.javaModel.visitor.IStructureTraverser;
import edu.rosehulman.cjjb.javaModel.visitor.IStructureVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLTraverser;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;

public class JavaModel implements IUMLTraverser, ISquenceTraverser, IStructureTraverser {

	HashMap<String, AbstractJavaStructure> map;
	List<MethodCallGroup> methodGroups;
	Set<String> includedClasses;
	
	List<IPattern> patterns;

	public JavaModel(Set<String> includedClasses) {
		this.map = new HashMap<String, AbstractJavaStructure>();

		this.includedClasses = includedClasses;
		
		this.methodGroups = new LinkedList<MethodCallGroup>();
		
		this.patterns = new LinkedList<IPattern>();
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
			if (struct instanceof JavaClass) {
				if (includedClasses.contains(struct.name)) {
					JavaClass clazz = (JavaClass) struct;
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
					if (other instanceof JavaMethod) {
						if (includedClasses.contains(other.type.name)) {
							relations.add(new Relation(struct, other.type));
						}

						for (AbstractJavaStructure arg : ((JavaMethod) other).arguments) {
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
						if (other instanceof JavaField) {
							relations.add(new Relation(struct, other.type));
						}
					}
				}
			}
		}
		return new ArrayList<Relation>(relations);
	}
	
	public Collection<AbstractJavaStructure> getStructures() {
		return map.values();
	}

	public void addMethodCallGroup(MethodCallGroup group) {
		this.methodGroups.add(group);
	}
	
	private void convertMethodCallLinesToStructure() {
		for(MethodCallGroup group: this.methodGroups) {
			AbstractJavaStructure caller = Utils.getInstanceOrJavaStructure(this, group.classCaller);
			for(MethodCallLine line: group.lines) {
				AbstractJavaStructure other = Utils.getInstanceOrJavaStructure(this, line.classOf);
				
				JavaMethod method = (JavaMethod) caller.getMethodByQualifiedName(group.method, this);
				JavaMethod otherMethod = (JavaMethod) other.getMethodByQualifiedName(line.method, this);
				
				if (otherMethod == null) {
					boolean isConstuctor = other.name.equals(line.method.methodName);
					
					otherMethod = new JavaMethod(other, line.method.methodName,  new PublicModifier(), new LinkedList<IModifier>(), Utils.getInstanceOrJavaStructure(this, line.returnType), Utils.getInstanceOrJavaStructures(this, Utils.getListOfArgs(line.method.methodDesc).toArray(new String[0])), isConstuctor);
					other.addSubElement(otherMethod);
				}
				
				method.addMethodCall(otherMethod);
			}
		}
	}
	
	public void finalize(List<IPatternCheck> patterns, List<IStructureVisitor> structVisitors) {
		convertMethodCallLinesToStructure();
		checkForPatterns(patterns);
		
		runStructVisitors(structVisitors);
	}
	
	private void runStructVisitors(List<IStructureVisitor> structVisitors) {
		for(IStructureVisitor v: structVisitors) {
			this.accept(v);
		}
	}
	
	private void checkForPatterns(List<IPatternCheck> patterns) {		
		for(IPatternCheck check: patterns) {
			List<IPattern> list = check.check(this);
			this.patterns.addAll(list);
		}
	}

	@Override
	public void accept(IUMLVisitor v) throws IOException {
		v.visitStart();

		for (String name : map.keySet()) {
			if (includedClasses.contains(name))
				map.get(name).accept(v);
		}
		v.visitRelations(this);
		v.visitPatterns(this);

		v.visitEnd();
	}
	
	@Override
	public void accept(ISequenceVisitor v) throws IOException {
		v.visit(this);
	}
	
	@Override
	public void accept(IStructureVisitor v) {
		for(AbstractJavaStructure s: this.map.values()) {
			List<IPattern> list = v.visit(this, s);
			this.patterns.addAll(list);
		}
	}
	
	public List<String> getStereotypes(AbstractJavaStructure struct) {
		List<String> steros = new LinkedList<String>();
		
		for(IPattern p: patterns) {
			String s = p.getStereotype(struct);
			if(s != null)
				steros.add(s);
		}
		
		return steros;
	}

	public List<IPattern> getPatterns() {
		return patterns;
	}
}
