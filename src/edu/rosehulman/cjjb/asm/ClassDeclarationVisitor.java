package edu.rosehulman.cjjb.asm;

import java.io.OutputStream;
import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private OutputStream out;
	
	public ClassDeclarationVisitor(int api, OutputStream out) {
		super(api);
		this.out = out;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// TODO: delete the line below
		System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		System.out.println("----------------------------");
		// TODO: construct an internal representation of the class for later use
		// by decorators
		super.visit(version, access, name, signature, superName, interfaces);
	}
}