import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.javaModel.checks.IPatternCheck;
import edu.rosehulman.cjjb.javaModel.checks.SingletonCheck;
import edu.rosehulman.cjjb.javaModel.*;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PrivateModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PublicModifier;
import edu.rosehulman.cjjb.javaModel.modifier.StaticModifier;;

public class SingletonTesting {
	
	@Test
	public void testCheckForStaticFieldInstance() {
		IPatternCheck singleCheck = new SingletonCheck();
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.Singleton");
		JavaModel model = new JavaModel(classes);
		JavaClass struct = new JavaClass("sampleClasses.Singleton");
		assertTrue(!singleCheck.check(model, struct));
		
		LinkedList<IModifier> modifiers = new LinkedList<IModifier>();
		modifiers.add(new StaticModifier());
		struct.addSubElement(new JavaField(struct, "instance", new PrivateModifier(), modifiers, struct));
		model.putStructure("sampleClasses.Singleton", struct);
		
		assertTrue(singleCheck.check(model, struct));
	}
	
	@Test
	public void testCheckForGetInstanceMethod() {
		IPatternCheck singleCheck = new SingletonCheck();
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.Singleton");
		JavaModel model = new JavaModel(classes);
		JavaClass struct = new JavaClass("sampleClasses.Singleton");
		
		LinkedList<IModifier> list = new LinkedList<IModifier>();
		list.add(new StaticModifier());
		struct.addSubElement(new JavaMethod(struct, "getInstance", new PublicModifier(), list, 
				struct, new LinkedList<AbstractJavaStructure>(), false));
		model.putStructure("sampleClasses.Singleton", struct);
		
		assertTrue(singleCheck.check(model, struct));
	}

}
