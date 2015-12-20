package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {
	
	private OutputStream out;
	
	public ClassFieldVisitor(int api, OutputStream out) {
		super(api);
		this.out = out;
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, OutputStream out) {
		super(api, decorated);
		this.out = out;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		// TODO: delete the line below
		System.out.println(" " + type + " " + name);
		// TODO: add this field to your internal representation of the current
		// class.
		// What is a good way to know what the current class is?
		StringBuffer buf = new StringBuffer();
		buf.append(access);
		buf.append(" " + name + " : " + type + "\\l");
		try {
			out.write(buf.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toDecorate;
	};
}