package edu.rosehulman.cjjb.asm;

import java.io.OutputStream;
import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Class;
import edu.rosehulman.cjjb.javaModel.JavaModel;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private JavaModel model;
	
	public ClassDeclarationVisitor(int api, JavaModel model) {
		super(api);
		
		this.model = model;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		String cleanName = Utils.getCleanName(name);
		
		Class clazz;
		if(model.containsStructure(cleanName)) {
			clazz = (Class) model.getStructure(cleanName);
		} else {
			clazz = new Class(cleanName);
		}
		clazz.access = Utils.getAccessModifier(access);
		clazz.modifiers = Utils.getModifiers(access);
		clazz.implement = Utils.getInstanceOrJavaStructures(model, Utils.getCleanNames(interfaces));
		clazz.superClass = model.getStructure(Utils.getCleanName(superName));
	
		model.putStructure(cleanName, clazz);
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}