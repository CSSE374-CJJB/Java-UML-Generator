package edu.rosehulman.cjjb.asm;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Class;
import edu.rosehulman.cjjb.javaModel.Interface;
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
		
		if((access & Opcodes.ACC_STATIC) != 0) {
			toReturn.add(new StaticModifier());
		}
		if((access & Opcodes.ACC_SYNCHRONIZED) != 0) {
			toReturn.add(new SynchronizedModifier());
		}
		if((access & Opcodes.ACC_NATIVE) != 0) {
			toReturn.add(new NativeModifier());
		}
		if((access & Opcodes.ACC_ABSTRACT) != 0) {
			toReturn.add(new AbstractModifier());
		}
		if((access & Opcodes.ACC_FINAL) != 0) {
			toReturn.add(new FinalModifier());
		}
		
		return toReturn;
	}
	
	public static Class getInstanceOrDefaultClass(Map<String, AbstractJavaStructure> map, String name) {
		if(map.containsKey(name))
			return (Class) map.get(name);
		
		Class clazz = new Class(name, null, null, null, null, null);
		map.put(name, clazz);
		return clazz;
	}
	
	public static Interface getInstanceOrDefaultInterface(Map<String, AbstractJavaStructure> map, String name) {
		if(map.containsKey(name))
			return (Interface) map.get(name);
		
		Interface clazz = new Interface(name, null, null, null, null);
		map.put(name, clazz);
		return clazz;
	}
	
	public static List<Interface> getInstanceOrDefaultInterfaces(Map<String, AbstractJavaStructure> map, String[] names) {
		List<Interface> toReturn = new LinkedList<Interface>();
		
		for(String name: names) {
			toReturn.add(getInstanceOrDefaultInterface(map, name));
		}
		
		return toReturn;
	}
	
	public static String getCleanName(String name) {
		return name.replaceAll("\\/", ".");
	}
	
	public static String[] getCleanNames(String[] names) {
		String[] toReturn = new String[names.length];
		
		for(int i = 0; i < toReturn.length; i++) {
			toReturn[i] = getCleanName(names[i]);
		}
		
		return toReturn;
	}
}
