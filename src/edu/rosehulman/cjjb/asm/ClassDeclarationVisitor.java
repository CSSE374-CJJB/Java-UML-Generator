package edu.rosehulman.cjjb.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.objectweb.asm.ClassVisitor;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Class;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private OutputStream out;
	private HashMap<String, AbstractJavaStructure> map;
	
	public ClassDeclarationVisitor(int api, OutputStream out, HashMap<String, AbstractJavaStructure> map) {
		super(api);
		this.out = out;
		
		this.map = map;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		/*System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		System.out.println("----------------------------");*/
		StringBuffer buf = new StringBuffer();
		
		String cleanName = Utils.getCleanName(name);
		
		Class clazz = new Class(cleanName, Utils.getAccessModifier(access), Utils.getModifiers(access), null,
					Utils.getInstanceOrDefaultInterfaces(map, Utils.getCleanNames(interfaces)), map.get(Utils.getCleanName(superName)));
			map.put(cleanName, clazz);
		
		
		//addSuperName(cleanName, superName);
		//addInterfaceName(cleanName, interfaces);
		
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
	
	/*private void addInterfaceName(String thisName, String[] interfaces) {
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
	}*/

}