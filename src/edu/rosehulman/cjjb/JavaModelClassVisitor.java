package edu.rosehulman.cjjb;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import edu.rosehulman.cjjb.asm.ClassDeclarationVisitor;
import edu.rosehulman.cjjb.asm.ClassFieldVisitor;
import edu.rosehulman.cjjb.asm.ClassMethodVisitor;
import edu.rosehulman.cjjb.asm.ClassSequnceClassVisitor;
import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.asm.SequenceStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.checks.IPatternCheck;
import edu.rosehulman.cjjb.javaModel.checks.PatternFindingFactory;
import edu.rosehulman.cjjb.javaModel.visitor.IStructureVisitor;

public class JavaModelClassVisitor {

	public Set<String> classes;

	private JavaModel model;
	private String classSearch;
	private QualifiedMethod methodSearch;
	private int depth;
	

	public JavaModelClassVisitor(Set<String> classes) {
		this(classes, null, null, 0);
	}

	public JavaModelClassVisitor(String classSearch, QualifiedMethod methodSearch, int depth) {
		this(null, classSearch, methodSearch, depth);
	}
	
	public JavaModelClassVisitor(Set<String> classes, String classSearch, QualifiedMethod methodSearch, int depth) {
		this.classes = classes;

		this.model = new JavaModel(classes);

		this.classSearch = classSearch;
		this.methodSearch = methodSearch;
		this.depth = depth;
	}

	public void buildUMLModelDefault() throws IOException{
		for (String className : this.classes) {
			try {
				ClassReader reader = new ClassReader(className); 
				ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, className, model);
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, className, model);
				
				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			} catch (IOException e) {
				System.out.println("Class not Found: " + className);
				System.exit(1);
			}
		}
		model.finalize(PatternFindingFactory.getPatternChecks(), PatternFindingFactory.getStructureVisitors());
	}
	
	public void buildUMLModelOnly() {
		System.out.println("Building UML Model");
		for (String className : this.classes) {
			try {
				ClassReader reader = new ClassReader(className); 
				ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, className, model);
				ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, className, model);
				
				reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			} catch (IOException e) {
				System.out.println("Class not Found: " + className);
				System.exit(1);
			}
		}
	}
	
	public void runPatternDetection(List<IPatternCheck> patterns, List<IStructureVisitor> visitors) {
		model.finalize(patterns, visitors);
	}
	
	public void buildSeqModelDefault() throws IOException {
		if (depth != 0) {
			ClassReader reader = new ClassReader(classSearch);
			
			SequenceStructure seqStructure = new SequenceStructure();
			ClassVisitor decVisitor = new ClassSequnceClassVisitor(Opcodes.ASM5, model, classSearch, methodSearch, depth, seqStructure);
			reader.accept(decVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		model.finalize(PatternFindingFactory.getPatternChecks(), PatternFindingFactory.getStructureVisitors());
	}
	
	public JavaModel getModel() {
		return this.model;
	}
}
