package SingletonTest;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.javaModel.checks.IPattern;
import edu.rosehulman.cjjb.javaModel.*;
import edu.rosehulman.cjjb.javaModel.JavaClass;
import edu.rosehulman.cjjb.javaModel.modifier.IModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PrivateModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PublicModifier;
import edu.rosehulman.cjjb.javaModel.modifier.StaticModifier;
import edu.rosehulman.cjjb.javaModel.visitor.IStructureVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.SingletonVisitor;;

public class SingletonTesting {
	
	@Test
	public void testCheckForStaticFieldInstance() {
		Set<String> classes = new HashSet<String>();
		classes.add("SingletonTest.Singleton");
		JavaModel model = new JavaModel(classes);
		JavaClass struct = new JavaClass("SingletonTest.Singleton");
		IStructureVisitor v = new SingletonVisitor();
		model.accept(v);
		List<IPattern> list = model.getPatterns();
		
		assertTrue(list.size() == 0);
		
		LinkedList<IModifier> modifiers = new LinkedList<IModifier>();
		modifiers.add(new StaticModifier());
		struct.addSubElement(new JavaField(struct, "instance", new PrivateModifier(), modifiers, struct));
		model.putStructure("SingletonTest.Singleton", struct);
		model.accept(v);
		list = model.getPatterns();
		assertTrue(list.size() == 1);
	}
	
	@Test
	public void testCheckForGetInstanceMethod() {
		Set<String> classes = new HashSet<String>();
		classes.add("SingletonTest.Singleton");
		JavaModel model = new JavaModel(classes);
		JavaClass struct = new JavaClass("SingletonTest.Singleton");
		
		LinkedList<IModifier> list = new LinkedList<IModifier>();
		list.add(new StaticModifier());
		struct.addSubElement(new JavaMethod(struct, "getInstance", new PublicModifier(), list, 
				struct, new LinkedList<AbstractJavaStructure>(), false));
		model.putStructure("SingletonTest.Singleton", struct);
		IStructureVisitor v = new SingletonVisitor();
		model.accept(v);
		
		List<IPattern> pList = model.getPatterns();
		assertTrue(pList.size() == 1);
	}

}
