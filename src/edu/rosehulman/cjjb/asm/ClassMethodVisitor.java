package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassVisitor {
	
	private OutputStream out;
	
	private boolean firstMethod;
	
	public ClassMethodVisitor(int api, OutputStream out) {
		super(api);
		this.out = out;
		
		firstMethod = true;
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, OutputStream out) {
		super(api, decorated);
		this.out = out;
		
		firstMethod = true;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		// TODO: create an internal representation of the current method and
		// pass it to the methods below
		
		if(!name.contains("<")) {
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
		}
		return toDecorate;
	}

	void addAccessLevel(int access) throws IOException {
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
		// TODO: delete the next line
		out.write(level.getBytes());
		// TODO: ADD this information to your representation of the current
		// method.
	}

	void addReturnType(String desc) throws IOException {
		if(desc == null)
			return;
		
		String returnType = Type.getReturnType(desc).getClassName();
		// TODO: delete the next line
		out.write((" : " + returnType).getBytes());
		// TODO: ADD this information to your representation of the current
		// method.
	}

	void addArguments(String desc) throws IOException {
		Type[] args = Type.getArgumentTypes(desc);
		String[] argNames = new String[args.length];
		
		for (int i = 0; i < args.length; i++) {
			argNames[i] = args[i].getClassName();
		}
		
		out.write(String.join(", ", argNames).getBytes());;
	}
}