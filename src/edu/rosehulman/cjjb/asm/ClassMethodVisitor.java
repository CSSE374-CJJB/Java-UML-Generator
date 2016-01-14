package edu.rosehulman.cjjb.asm;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;

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
		toDecorate = new ClassMethodLineVisitor(this.api, toDecorate, this.className, this.model);
		
		
		AbstractJavaStructure structure = model.getStructure(this.className);

		AbstractJavaStructure returnType = Utils.getInstanceOrJavaStructure(model, Utils.getReturnType(desc));
		List<AbstractJavaStructure> arguments = Utils.getInstanceOrJavaStructures(model,
				Utils.getListOfArgs(desc).toArray(new String[0]));

		structure.addSubElement(new Method(Utils.getCleanName(name), Utils.getAccessModifier(access),
				Utils.getModifiers(access), returnType, arguments));

		return toDecorate;
	}
}