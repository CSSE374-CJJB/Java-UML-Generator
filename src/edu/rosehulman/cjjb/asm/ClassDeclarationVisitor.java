package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;
import org.objectweb.asm.ClassVisitor;

import sun.security.util.Length;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private OutputStream out;
	private Relations relations;
	
	public ClassDeclarationVisitor(int api, OutputStream out, Relations relations) {
		super(api);
		this.out = out;
		
		this.relations = relations;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		/*System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		System.out.println("----------------------------");*/
		StringBuffer buf = new StringBuffer();
		
		String cleanName = getCleanName(name);
		
		relations.addElement(cleanName);
		
		addSuperName(cleanName, superName);
		addInterfaceName(cleanName, interfaces);
		
		buf.append("\"" + cleanName + "\"");
		buf.append(" [\n");
		buf.append("\tlabel = \"{");
		buf.append(cleanName + "|");
		try {
			out.write(buf.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	private void addInterfaceName(String thisName, String[] interfaces) {
		if(interfaces == null || interfaces.length == 0) {
			return;
		}
		for(String s : interfaces) {
			this.relations.addInterfaceRelation(thisName, getCleanName(s));
		}
	}

	private void addSuperName(String thisName, String superName) {
		if(superName == null)
			return;
		
		this.relations.addChildParrentRelation(thisName, getCleanName(superName));
	}

	public String getCleanName(String s) {
		return s.replaceAll("\\/", ".");
	}
}