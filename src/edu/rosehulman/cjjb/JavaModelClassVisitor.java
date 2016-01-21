package edu.rosehulman.cjjb;

import java.io.IOException;
import java.io.OutputStream;
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

public class JavaModelClassVisitor {

	public Set<String> classes;
	public static final String boilerPlate = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]";

	private JavaModel model;
	private String classSearch;
	private QualifiedMethod methodSearch;
	private int depth;
	

	public JavaModelClassVisitor(Set<String> classes, OutputStream out) {
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

	public void buildUMLModel() throws IOException{
		for (String className : this.classes) {
			ClassReader reader = new ClassReader(className);
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, className, model);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, className, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		model.convertMethodCallLinesToStructure();
	}
	
	public void buildSeqModel() throws IOException {
		if (depth != 0) {
			ClassReader reader = new ClassReader(classSearch);
			
			SequenceStructure seqStructure = new SequenceStructure();
			ClassVisitor decVisitor = new ClassSequnceClassVisitor(Opcodes.ASM5, model, classSearch, methodSearch, depth, seqStructure);
			reader.accept(decVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		model.convertMethodCallLinesToStructure();
	}
	
	public JavaModel getModel() {
		return this.model;
	}
}
