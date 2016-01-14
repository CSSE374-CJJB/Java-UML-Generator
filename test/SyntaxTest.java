import static org.junit.Assert.*;
import edu.rosehulman.cjjb.asm.Utils;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Class;
import edu.rosehulman.cjjb.javaModel.Interface;
import edu.rosehulman.cjjb.javaModel.modifier.*;
import org.objectweb.asm.*;

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

public class SyntaxTest {
	
	@Test
	public void testSyntax() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.Class1");
		classes.add("sampleClasses.Class2");
		classes.add("sampleClasses.Inter1");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUML();
		String result = new String(out.toByteArray());
		
		assertTrue(result.contains("Class1"));
		assertTrue(result.contains("Class2"));
		assertTrue(result.contains("Inter1"));
		assertTrue(result.contains("\"sampleClasses.Class1\" -> \"sampleClasses.Inter1\""));
		assertTrue(result.contains("\"sampleClasses.Class1\" -> \"sampleClasses.Class2\""));
		assertTrue(result.contains("\"sampleClasses.Class2\" -> \"sampleClasses.Class1\""));
		
		
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
		assertEquals(new PublicModifier(), Utils.getAccessModifier(Opcodes.ACC_PUBLIC));
		assertEquals(new PrivateModifier(), Utils.getAccessModifier(Opcodes.ACC_PRIVATE));
		assertEquals(new ProtectedPrivateModifier(), Utils.getAccessModifier(Opcodes.ACC_PROTECTED));
		assertEquals(new ProtectedModifier(), Utils.getAccessModifier(Opcodes.ACC_PROTECTED));
	}
	
	@Test
	public void testGetModifiers() {
		List<IModifier> list = new LinkedList<IModifier>();
		list.add(new StaticModifier());
		assertEquals(list, Utils.getModifiers(Opcodes.ACC_STATIC));
		list.remove(0);
		list.add(new SynchronizedModifier());
		assertEquals(list, Utils.getModifiers(Opcodes.ACC_SYNCHRONIZED));
		list.remove(0);
		list.add(new NativeModifier());
		assertEquals(list, Utils.getModifiers(Opcodes.ACC_NATIVE));
		list.remove(0);
		list.add(new FinalModifier());
		assertEquals(list, Utils.getModifiers(Opcodes.ACC_FINAL));
	}
	
	@Test
	public void testGetInstanceOrJavaStructure() {
		Set<String> set = new HashSet<String>();
		set.add("sampleClasses.Class1");
		JavaModel mod = new JavaModel(set);
		assertEquals(new Class("sampleClasses.Class1"), Utils.getInstanceOrJavaStructure(mod, "sampleClasses.Class1"));
		assertEquals(new Class("sampleClasses.Class1"), Utils.getInstanceOrJavaStructure(mod, "sampleClasses.Class1"));
	}
	
	@Test
	public void testGetInstanceOrJavaStructures() {
		Set<String> set = new HashSet<String>();
		set.add("sampleClasses.Class1");
		JavaModel mod = new JavaModel(set);
		String[] names = new String[2];
		names[0] = "sampleClasses.Class1";
		names[1] = "sampleClasses.Inter1";
		
		List<AbstractJavaStructure> list = new LinkedList<AbstractJavaStructure>();
		list.add(new Class("sampleClasses.Class1"));
		list.add(new Interface("sampleClasses.Inter1"));
		assertEquals(list, Utils.getInstanceOrJavaStructures(mod, names));
		assertEquals(list, Utils.getInstanceOrJavaStructures(mod, names));
	}
	
	@Test
	public void testGetCleanNames() {
		String[] names = new String[2];
		names[0] = "samplesClasses/Class1";
		names[1] = "samplesClasses/Inter1";
		
		String[] correct = new String[2];
		correct[0] = "samplesClasses.Class1";
		correct[1] = "samplesClasses.Inter1";
		
		assertArrayEquals(correct, Utils.getCleanNames(names));
		
	}
	
	@Test
	public void testGetGenericsPart() {
		String sig = "List<JavaModel>";
		String[] arr = new String[1];
		arr[0] = "JavaModel;";
		assertArrayEquals(arr, Utils.getGenericsPart(sig));
	}
	
	@Test
	public void testGetReturnType() {
		String desc = "public int test()";
		String desc2 = "public String test()";
		assertEquals("int", Utils.getReturnType(desc));
		assertEquals("String", Utils.getReturnType(desc2));
	}
	
	@Test
	public void testGetListOfArgs() {
		String desc = "public void test(int number, String word)";
		List<String> list = new LinkedList<String>();
		list.add("int");
		list.add("String");
		assertEquals(list, Utils.getListOfArgs(desc));
	}
	
}
