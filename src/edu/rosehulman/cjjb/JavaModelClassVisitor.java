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
import edu.rosehulman.cjjb.javaModel.JavaModel;

public class JavaModelClassVisitor {

	public Set<String> classes;
	public OutputStream out;
	public static final String boilerPlate = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]";

	private JavaModel model;

	public JavaModelClassVisitor(Set<String> classes, OutputStream out) {
		this.classes = classes;
		this.out = out;

		this.model = new JavaModel(classes);
	}

	public void buildModel() throws IOException{
		for (String className : this.classes) {

			ClassReader reader = new ClassReader(className);
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, model);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, className, model);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, className, model);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		
		model.convertMethodCallLinesToStructure();
	}
	
	public JavaModel getModel() {
		return this.model;
	}
}
