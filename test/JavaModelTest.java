import static org.junit.Assert.*;

import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;
import edu.rosehulman.cjjb.javaModel.Relation;
import edu.rosehulman.cjjb.javaModel.AbstractJavaElement;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Class;
import edu.rosehulman.cjjb.javaModel.Field;
import edu.rosehulman.cjjb.javaModel.Interface;
import edu.rosehulman.cjjb.javaModel.modifier.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class JavaModelTest {
	@Test
	public void testGetChildParentIncludedRelations() {
		Set<String> set = new HashSet<String>();
		set.add("sampleClasses.Class1");
		set.add("sampleClasses.Class2");
		JavaModel mod = new JavaModel(set);
		Class clazz1 = new Class("sampleClasses.Class1");
		Class clazz2 = new Class("sampleClasses.Class2");
		clazz2.superClass = clazz1;
		mod.putStructure("sampleClasses.Class1", clazz1);
		mod.putStructure("sampleClasses.Class2", clazz2);
		
		List<Relation> correct = new LinkedList<Relation>();
		correct.add(new Relation(clazz2, clazz1));
		
		assertEquals(correct, mod.getChildParrentIncludedRelations());
	}
	
	@Test
	public void testGetIncludedInterfaceRelations() {
		Set<String> set = new HashSet<String>();
		set.add("sampleClasses.Class1");
		set.add("sampleClasses.Inter1");
		JavaModel mod = new JavaModel(set);
		AbstractJavaStructure clazz = new Class("sampleClasses.Class1", (IAccessModifier)new PublicModifier(), new LinkedList<IModifier>(),
				new LinkedList<AbstractJavaElement>(), new LinkedList<AbstractJavaStructure>(), (AbstractJavaStructure)new Class("Class2"));
		AbstractJavaStructure inter = new Interface("sampleClasses.Inter1");
		clazz.implement.add(inter);
		mod.putStructure("sampleClasses.Class1", clazz);
		
		List<Relation> correct = new LinkedList<Relation>();
		correct.add(new Relation(clazz, inter));
		
		assertEquals(correct, mod.getIncludedInterfaceRelations());
	}
	
	@Test
	public void testGetIncludedUsesRelations() {
		Set<String> set = new HashSet<String>();
		set.add("sampleClasses.Class1");
		set.add("sampleClasses.Class2");
		JavaModel mod = new JavaModel(set);
		AbstractJavaStructure clazz = new Class("sampleClasses.Class1", (IAccessModifier)new PublicModifier(), new LinkedList<IModifier>(),
				new LinkedList<AbstractJavaElement>(), new LinkedList<AbstractJavaStructure>(), (AbstractJavaStructure)new Class("Class2"));
		Class clazz2 = new Class("sampleClasses.Class2");
		Method meth = new Method(clazz, "getClass2", (IAccessModifier) new PublicModifier(), new LinkedList<IModifier>(),
				clazz2, new LinkedList<AbstractJavaStructure>(), false);
		clazz.addSubElement(meth);
		
		mod.putStructure("sampleClasses.Class1", clazz);
		mod.putStructure("sampleClasses.Class2", clazz2);
		
		List<Relation> correct = new LinkedList<Relation>();
		correct.add(new Relation(clazz, clazz2));
		
		assertEquals(correct, mod.getIncludedUsesRelations());
	}
	
	@Test
	public void testGetIncludedAssociationRelations() {
		Set<String> set = new HashSet<String>();
		set.add("sampleClasses.Class1");
		set.add("sampleClasses.Class2");
		JavaModel mod = new JavaModel(set);
		AbstractJavaStructure clazz2 = new Class("sampleClasses.Class2", (IAccessModifier)new PublicModifier(), new LinkedList<IModifier>(),
				new LinkedList<AbstractJavaElement>(), new LinkedList<AbstractJavaStructure>(), (AbstractJavaStructure)new Class("Class1"));
		Class clazz = new Class("sampleClasses.Class1");
		Field field = new Field(clazz2, "sampleClasses.Class1", (IAccessModifier) new PublicModifier(), new LinkedList<IModifier>(),
				clazz);
		clazz2.addSubElement(field);
		
		mod.putStructure("sampleClasses.Class1", clazz);
		mod.putStructure("sampleClasses.Class2", clazz2);
		
		List<Relation> correct = new LinkedList<Relation>();
		correct.add(new Relation(clazz2, clazz));
		
		assertEquals(correct, mod.getIncludedAssociationReltiations());
	}
	
}
