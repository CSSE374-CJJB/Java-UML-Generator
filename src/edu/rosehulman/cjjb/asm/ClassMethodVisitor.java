package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Method;

public class ClassMethodVisitor extends ClassVisitor {
	
	private OutputStream out;
	
	private boolean firstMethod;
	private String className;
	private HashMap<String, AbstractJavaStructure> map;
	
	public ClassMethodVisitor(int api, ClassVisitor decorated, OutputStream out, String className, HashMap<String, AbstractJavaStructure> map) {
		super(api, decorated);
		this.out = out;
		this.className = className;
		this.map = map;
		
		firstMethod = true;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		try {
			map.get(this.className).subElements.add(new Method(Utils.getCleanName(name), Utils.getAccessModifier(access), Utils.getModifiers(access),
					new AbstractJavaStructure(Utils.getReturnType(desc), null, null, null, null), Utils.getListOfArgs(desc)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/*if(!name.contains("<")) {
			try {
				if(firstMethod) {
					out.write("|".getBytes());
					firstMethod = false;
				}
				addAccessLevel(access);
				
				out.write(name.getBytes());
				out.write("(".getBytes());
				addArguments(desc);
				out.write(")".getBytes());
				
				addReturnType(desc);
				
				out.write("\\l".getBytes());
			} catch (IOException e) {
				
			} 
		}*/
		return toDecorate;
	}

	/*void addAccessLevel(int access) throws IOException {
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
		out.write(level.getBytes());
	}

	void addReturnType(String desc) throws IOException {
		if(desc == null)
			return;
		
		String returnType = Type.getReturnType(desc).getClassName();
		addUsesRelations(new String[] {returnType});
		out.write((" : " + returnType).getBytes());
	}

	void addArguments(String desc) throws IOException {
		Type[] args = Type.getArgumentTypes(desc);
		String[] argNames = new String[args.length];
		
		for (int i = 0; i < args.length; i++) {
			argNames[i] = args[i].getClassName();
		}
		
		addUsesRelations(argNames);
		
		out.write(String.join(", ", argNames).getBytes());;
	}
	
	void addUsesRelations(String[] types) {
		for(String s: types) {
			this.relations.addUsesRelations(this.className, s);
		}
	}*/
}