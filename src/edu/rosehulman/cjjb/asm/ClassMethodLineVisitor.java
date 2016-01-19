package edu.rosehulman.cjjb.asm;

import edu.rosehulman.cjjb.javaModel.JavaModel;

import org.objectweb.asm.MethodVisitor;

public class ClassMethodLineVisitor extends MethodVisitor {
	
	private MethodCallGroup method;
	
	public ClassMethodLineVisitor(int api, MethodVisitor cv, MethodCallGroup method, JavaModel model) {
		super(api, cv);
		
		this.method = method;
		
		model.addMethodCallGroup(method);
	}

	
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		
		method.addLine(new MethodCallLine(Utils.getCleanName(owner), name, Utils.getReturnType(desc), Utils.getListOfArgs(desc)));
		
		System.out.println(String.format("Line: %s %s %s %s %s", opcode, owner, name, desc, itf));
	}
	
}
