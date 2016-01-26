package edu.rosehulman.cjjb.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaField;
import edu.rosehulman.cjjb.javaModel.JavaModel;

public class ClassFieldVisitor extends ClassVisitor {

	private String className;
	private JavaModel model;

	public ClassFieldVisitor(int api, ClassVisitor decorated, String className, JavaModel model) {
		super(api, decorated);
		this.className = className;
		this.model = model;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();

		AbstractJavaStructure structure = model.getStructure(Utils.getCleanName(this.className));

		if (signature == null) {
			JavaField typeClass = new JavaField(Utils.getInstanceOrJavaStructure(model, className), name, Utils.getAccessModifier(access), Utils.getModifiers(access),
					Utils.getInstanceOrJavaStructure(model, type));

			structure.addSubElement(typeClass);
		} else {
			for (String s : Utils.getGenericsPart(signature)) {
				JavaField typeClass = new JavaField(Utils.getInstanceOrJavaStructure(model, className), name, Utils.getAccessModifier(access), Utils.getModifiers(access),
						Utils.getInstanceOrJavaStructure(model, s));
				structure.addSubElement(typeClass);
			}
		}
		return toDecorate;
	}
}