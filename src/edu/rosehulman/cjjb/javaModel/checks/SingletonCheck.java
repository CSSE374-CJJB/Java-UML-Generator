package edu.rosehulman.cjjb.javaModel.checks;

import java.util.List;

import com.sun.glass.ui.CommonDialogs.Type;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;

import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Interface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;
import edu.rosehulman.cjjb.javaModel.modifier.AbstractModifier;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.StaticModifier;

public class SingletonCheck implements IModelStructureBooleanCheck {

	@Override
	public boolean check(JavaModel model, AbstractJavaStructure structure) {
		// Only classes can be singletons
		if(structure instanceof Interface)
			return false;
		
		if(checkForStaticInstance(structure))
			return true;
		
		if(checkForGetInstance(model, structure))
			return true;
		
		return false;
	}
	
	private boolean checkForStaticInstance(AbstractJavaStructure structure) {
		for(AbstractJavaElement element: structure.subElements) {
			if(element.name.equalsIgnoreCase("instance"))
				if(checkForModifier(element.modifiers, StaticModifier.class))
					return true;
		}
		return false;
	}
	
	private boolean checkForModifier(List<IModifier> modifiers, Class<?> c) {
		for(IModifier mod: modifiers)
			if(c.isInstance(mod))
				return true;
		
		return false;
	}

	private boolean checkForGetInstance(JavaModel model, AbstractJavaStructure structure) {
		Method method = structure.getMethodByQualifiedName(new QualifiedMethod("getInstance", "()" + Utils.getAsmName(structure.name)), model);
		
		if(method == null)
			method = structure.getMethodByQualifiedName(new QualifiedMethod("get" + Utils.shortName(structure.name), "()" + Utils.getAsmName(structure.name)), model);
		
		if(method == null)
			return false;
		
		if(method.arguments != null && method.arguments.size() != 0)
			return false;
		
		if(!checkForModifier(method.modifiers, StaticModifier.class))
			return false;
		
		if(method.type.equals(structure))
			return true;

		return false;			
	}
	
}
