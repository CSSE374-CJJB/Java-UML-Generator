package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;

public class ClassMethodVisitor extends ClassVisitor {
	
	private String className;
	private JavaModel model;
	
	public ClassMethodVisitor(int api, ClassVisitor decorated, String className, JavaModel model) {
		super(api, decorated);
		this.className = className;
		this.model = model;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		AbstractJavaStructure structure =  model.getStructure(this.className);
		
		AbstractJavaStructure returnType = Utils.getInstanceOrJavaStructure(model, Utils.getReturnType(desc));
		List<AbstractJavaStructure> arguments = Utils.getInstanceOrJavaStructures(model, Utils.getListOfArgs(desc).toArray(new String[0]));
		
		structure.addSubElement(new Method(Utils.getCleanName(name), Utils.getAccessModifier(access), Utils.getModifiers(access),
				returnType, arguments));
		
		return toDecorate;
	}
}