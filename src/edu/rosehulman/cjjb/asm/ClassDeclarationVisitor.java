package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;
import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private OutputStream out;
	
	public ClassDeclarationVisitor(int api, OutputStream out) {
		super(api);
		this.out = out;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		/*System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		System.out.println("----------------------------");*/
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		buf.append(" [\n");
		buf.append("\tlabel = {");
		buf.append(name);
		buf.append("|");
		try {
			out.write(buf.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}
}