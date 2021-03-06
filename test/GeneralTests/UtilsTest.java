package GeneralTests;
import static org.junit.Assert.*;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.modifier.*;
import edu.rosehulman.cjjb.javaModel.visitor.IUMLVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.UMLDotVisitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

//import com.sun.deploy.uitoolkit.impl.fx.Utils;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

import edu.rosehulman.cjjb.JavaModelClassVisitor;

public class UtilsTest {
	
	@Test
	public void testSyntax() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("GeneralTests.Class1");
		classes.add("GeneralTests.Class2");
		classes.add("GeneralTests.Inter1");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		IUMLVisitor umlVisitor = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVisitor);
		
		String result = new String(out.toByteArray());
		
		assertTrue(result.contains("Class1"));
		assertTrue(result.contains("Class2"));
		assertTrue(result.contains("Inter1"));
		assertTrue(result.contains("\"GeneralTests.Class1\" -> \"GeneralTests.Inter1\""));
		assertTrue(result.contains("\"GeneralTests.Class1\" -> \"GeneralTests.Class2\""));
		assertTrue(result.contains("\"GeneralTests.Class2\" -> \"GeneralTests.Class1\""));
		
		assertEquals(countString("\\{", result), countString("\\}", result));
		assertEquals(countString("\\[", result), countString("\\]", result));
	}
	
	public int countString(String delimeter, String result) {
		int count = 0;
		while (result.contains(delimeter)) {
			result = result.replaceFirst(delimeter, "");
			count++;
		}
		return count;
	}
	
	@Test
	public void testGetAccessModifier() {
		IModifier mod = new PublicModifier();
		assertEquals(mod.getClass(), Utils.getAccessModifier(Opcodes.ACC_PUBLIC).getClass());
		mod = new PrivateModifier();
		assertEquals(mod.getClass(), Utils.getAccessModifier(Opcodes.ACC_PRIVATE).getClass());
		mod = new ProtectedModifier();
		assertEquals(mod.getClass(), Utils.getAccessModifier(Opcodes.ACC_PROTECTED).getClass());
	}
	
	@Test
	public void testGetModifiers() {
		List<IModifier> list = new LinkedList<IModifier>();
		IModifier mod = new StaticModifier();
		list.add(mod);
		mod = new SynchronizedModifier();
		list.add(mod);
		mod = new NativeModifier();
		list.add(mod);
		mod = new FinalModifier();
		list.add(mod);
		
		List<Integer> list2 = new LinkedList<Integer>();
		list2.add(Opcodes.ACC_STATIC);
		list2.add(Opcodes.ACC_SYNCHRONIZED);
		list2.add(Opcodes.ACC_NATIVE);
		list2.add(Opcodes.ACC_FINAL);
		
		for (int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i).getClass(), Utils.getModifiers(list2.get(i)).get(0).getClass());
		}
	}
	
	@Test
	public void testGetInstanceOrJavaStructure() {
		Set<String> set = new HashSet<String>();
		set.add("GeneralTests.Class1");
		JavaModel mod = new JavaModel(set);
		AbstractJavaStructure clazz = Utils.getInstanceOrJavaStructure(mod, "GeneralTests.Class1");
		assertEquals(clazz, Utils.getInstanceOrJavaStructure(mod, "GeneralTests.Class1"));
	}
	
	@Test
	public void testGetInstanceOrJavaStructures() {
		Set<String> set = new HashSet<String>();
		set.add("GeneralTests.Class1");
		JavaModel mod = new JavaModel(set);
		String[] names = new String[2];
		names[0] = "GeneralTests.Class1";
		names[1] = "GeneralTests.Inter1";
		
		List<AbstractJavaStructure> list = Utils.getInstanceOrJavaStructures(mod, names);
		
		assertEquals(list, Utils.getInstanceOrJavaStructures(mod, names));
	}
	
	@Test
	public void testGetCleanNames() {
		String[] names = new String[2];
		names[0] = "GeneralTests/Class1";
		names[1] = "GeneralTests/Inter1";
		
		String[] correct = new String[2];
		correct[0] = "GeneralTests.Class1";
		correct[1] = "GeneralTests.Inter1";
		
		assertArrayEquals(correct, Utils.getCleanNames(names));
		
	}
	
	/*@Test
	public void testGetGenericsPart() {
		String sig = "Ljava/util/List;<edu/rosehulman/cjjb/javaModel/JavaModel;>";
		String[] arr = new String[1];
		arr[0] = "JavaModel;";
		assertArrayEquals(arr, Utils.getGenericsPart(sig));
	}*/
	
	@Test
	public void testGetReturnType() {
		String desc = "()I";
		String desc2 = "()Ljava/lang/String;";
		assertEquals("int", Utils.getReturnType(desc));
		assertEquals("java.lang.String", Utils.getReturnType(desc2));
	}
	
	@Test
	public void testGetListOfArgs() {
		String desc = "(Ljava/lang/String;I)V";
		List<String> list = new LinkedList<String>();
		list.add("java.lang.String");
		list.add("int");
		assertEquals(list, Utils.getListOfArgs(desc));
	}
	
	@Test
	public void testShortName() {
		String toTest = "edu/rosehulman/cjjb";
		String correct = "cjjb";
		assertEquals(correct, Utils.shortName(toTest));
	}
}
