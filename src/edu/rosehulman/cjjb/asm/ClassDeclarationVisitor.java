package edu.rosehulman.cjjb.asm;

import org.objectweb.asm.ClassVisitor;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
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

		AbstractJavaStructure structure;
		if (model.containsStructure(cleanName)) {
			structure = model.getStructure(cleanName);
		} else {
			structure = Utils.getInstanceOrJavaStructure(model, cleanName);
		}
		
		structure.access = Utils.getAccessModifier(access);
		structure.modifiers = Utils.getModifiers(access);
		structure.implement = Utils.getInstanceOrJavaStructures(model, Utils.getCleanNames(interfaces));

		if (structure instanceof JavaClass && superName != null) {
			((JavaClass) structure).superClass = Utils.getInstanceOrJavaStructure(model, Utils.getCleanName(superName));
		}

		model.putStructure(cleanName, structure);

		super.visit(version, access, name, signature, superName, interfaces);
	}
}