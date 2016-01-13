package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;

public class ClassFieldVisitor extends ClassVisitor {
	
	private OutputStream out;
	private String className;
	private HashMap<String, AbstractJavaStructure> map;
	
	public ClassFieldVisitor(int api, OutputStream out) {
		super(api);
		this.out = out;
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, OutputStream out, String className, HashMap<String, AbstractJavaStructure> map) {
		super(api, decorated);
		this.out = out;
		this.className = className;
		this.map = map;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		
		if(signature == null) {
			relations.addAssociationRelations(this.className, type);			
		} else {
			String[] genericsPart = getGenericsPart(signature);
			type += "<" + String.join(", ", genericsPart) + ">";
			for(String s: genericsPart){
				relations.addAssociationRelations(this.className, s);
			}
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append(getAccessLevel(access));
		buf.append(name + " : " + type + "\\l");
		try {
			out.write(buf.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toDecorate;
	};

	public String getAccessLevel(int access) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "+ ";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "# ";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "- ";
		} else {
			level = "";
		}
		return level;
	}
	
	public String[] getGenericsPart(String signiture) {
		String[] split = signiture.split("<");
		
		ArrayList<String> toReturn = new ArrayList<String>();
		
		for(String s: split[1].split(";")) {
			if(s.equals(">"))
				break;
			toReturn.add(Type.getType(s + ";").getClassName());
		}
		
		return toReturn.toArray(new String[toReturn.size()]);
	}
}