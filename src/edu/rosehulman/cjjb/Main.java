package edu.rosehulman.cjjb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import edu.rosehulman.cjjb.asm.ClassDeclarationVisitor;
import edu.rosehulman.cjjb.asm.ClassFieldVisitor;
import edu.rosehulman.cjjb.asm.ClassMethodVisitor;

public class Main {

	public static final String[] CLASSES = {
			"../../../Lab1-1/src/problem"
		};
	
	public static void main(String[] args) throws IOException {
		for (String className : CLASSES) {
			
			ClassReader reader = new ClassReader(className);
			OutputStream out = new FileOutputStream("output.txt");
			ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, out);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, out);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, out);
			// TODO: add more DECORATORS here in later milestones to accomplish
			// specific tasks
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			out.write("}\n]".getBytes());
		}
	}

}
