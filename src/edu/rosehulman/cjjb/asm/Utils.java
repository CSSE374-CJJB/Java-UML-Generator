package edu.rosehulman.cjjb.asm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.JavaInterface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.modifier.AbstractModifier;
import edu.rosehulman.cjjb.javaModel.modifier.FinalModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.NativeModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PrivateModifier;
import edu.rosehulman.cjjb.javaModel.modifier.ProtectedModifier;
import edu.rosehulman.cjjb.javaModel.modifier.ProtectedPrivateModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PublicModifier;
import edu.rosehulman.cjjb.javaModel.modifier.StaticModifier;
import edu.rosehulman.cjjb.javaModel.modifier.SynchronizedModifier;

public class Utils {

	public static IAccessModifier getAccessModifier(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			return new PublicModifier();
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			return new ProtectedModifier();
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			return new PrivateModifier();
		} else {
			return new ProtectedPrivateModifier();
		}
	}

	public static List<IModifier> getModifiers(int access) {
		List<IModifier> toReturn = new LinkedList<IModifier>();

		if ((access & Opcodes.ACC_STATIC) != 0) {
			toReturn.add(new StaticModifier());
		}
		if ((access & Opcodes.ACC_SYNCHRONIZED) != 0) {
			toReturn.add(new SynchronizedModifier());
		}
		if ((access & Opcodes.ACC_NATIVE) != 0) {
			toReturn.add(new NativeModifier());
		}
		if ((access & Opcodes.ACC_ABSTRACT) != 0) {
			toReturn.add(new AbstractModifier());
		}
		if ((access & Opcodes.ACC_FINAL) != 0) {
			toReturn.add(new FinalModifier());
		}

		return toReturn;
	}

	public static AbstractJavaStructure getInstanceOrJavaStructure(JavaModel model, String name) {
		if (model.containsStructure(name))
			return model.getStructure(name);

		AbstractJavaStructure clazz;
		if (isInterface(name)) {
			clazz = new JavaInterface(name);
		} else {
			clazz = new JavaClass(name);
		}
		model.putStructure(name, clazz);
		return clazz;

	}

	public static List<AbstractJavaStructure> getInstanceOrJavaStructures(JavaModel model, String[] names) {
		List<AbstractJavaStructure> toReturn = new LinkedList<AbstractJavaStructure>();

		for (String name : names) {
			toReturn.add(getInstanceOrJavaStructure(model, name));
		}

		return toReturn;
	}

	public static String getCleanName(String name) {
		return name.replaceAll("\\/", ".");
	}

	public static String[] getCleanNames(String[] names) {
		String[] toReturn = new String[names.length];

		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = getCleanName(names[i]);
		}

		return toReturn;
	}

	public static String[] getGenericsPart(String signiture) {
		String[] split = signiture.split("<");
		
		if(split.length <= 1) {
			return new String[0];
		}

		ArrayList<String> toReturn = new ArrayList<String>();
		
		for (String s : split[1].split(";")) {
			if (s.equals(">"))
				break;
			toReturn.add(Type.getType(s + ";").getClassName());
		}

		return toReturn.toArray(new String[toReturn.size()]);
	}

	public static String getReturnType(String desc) {
		return Type.getReturnType(desc).getClassName();
	}

	public static List<String> getListOfArgs(String desc) {
		List<String> toReturn = new LinkedList<String>();
		Type[] args = Type.getArgumentTypes(desc);

		for (Type t : args) {
			toReturn.add(Utils.getCleanName(t.getClassName()));

		}
		return toReturn;
	}

	public static boolean isInterface(String name) {
		try {
			return java.lang.Class.forName(name).isInterface();
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	public static String shortName(String name) {
		if(name.indexOf("/") != -1) {
			name = name.substring(name.lastIndexOf("/") + 1);
		}
		if(name.indexOf(".") != -1) {
			name = name.substring(name.lastIndexOf(".") + 1);
		}
		
		return name;
	}
	
	
	public static String getAsmName(String c) {
		String toReturn = Type.getType("L" + c.replace(".", "/") + ";").getClassName();
		
		if(toReturn.length() > 1) {
			toReturn = "L" + toReturn; 
		}
		
		return toReturn;
	}

	public static String getWithoutGenerics(String signature) {
		Type type = Type.getType(signature);
		String namePartial = type.getClassName();
		if(namePartial.contains("<")) {
			namePartial = namePartial.replaceAll("<{1}.*>{1}", "");
			namePartial = namePartial.replaceAll("<.*", "");
		}
		return Utils.getCleanName(namePartial);
	}
}
