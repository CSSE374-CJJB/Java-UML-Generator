package edu.rosehulman.cjjb.asm;

import edu.rosehulman.cjjb.javaModel.JavaModel;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;

public class ClassMethodLineVisitor extends MethodVisitor {

	private String className;
	private JavaModel model;
	
	public ClassMethodLineVisitor(int api, MethodVisitor cv, String className, JavaModel model) {
		super(api, cv);
		
		this.className = className;
		this.model = model;
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
		// TODO Auto-generated method stub
		super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		
		System.out.println(name);
		System.out.println(desc);
		System.out.println(bsm);
		System.out.println(bsmArgs);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
		
		System.out.println(String.format("Field: %s %s %s %s", opcode, owner, name, desc));
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		// TODO Auto-generated method stub
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		
		System.out.println(String.format("Method: %s %s %s %s %s", opcode, owner, name, desc, itf));
	}
	
	
	
}
