package AdapterTest;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.JavaModelClassVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.UMLDotVisitor;

public class AdapterTest {

	@Test
	public void testCheckForAdapters() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("problem.client.App");
		classes.add("problem.client.IteratorToEnumerationAdapter");
		classes.add("problem.lib.LinearTransformer");
		classes.add("java.util.Enumeration");
		classes.add("java.util.Iterator");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("problem.client.IteratorToEnumerationAdapter\\l\\<\\<adapter\\>\\>"));
		assertTrue(output.contains("java.util.Enumeration\\l\\<\\<target\\>\\>"));
		assertTrue(output.contains("java.util.Iterator\\l\\<\\<adaptee\\>\\>"));
		assertTrue(output.contains("\"problem.client.IteratorToEnumerationAdapter\" -> \"java.util.Iterator\" [label = \"\\<\\<adapts\\>\\>"));

		assertTrue(!output.contains("problem.lib.LinearTransformer\\l\\<\\<decorator\\>\\>"));
		assertTrue(!output.contains("problem.client.App\\l\\<\\<decorator\\>\\>"));
	}
	
	@Test
	public void testCheckForOtherAdapters() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("AdapterTest.ToAdapt");
		classes.add("AdapterTest.ToAdaptTo");
		classes.add("AdapterTest.ToAdaptToClass");
		classes.add("AdapterTest.Core");
		classes.add("AdapterTest.ToAdaptClass");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("AdapterTest.ToAdapt\\l\\<\\<adaptee\\>\\>"));
		assertTrue(output.contains("AdapterTest.ToAdaptTo\\l\\<\\<target\\>\\>"));
		assertTrue(output.contains("AdapterTest.Core\\l\\<\\<adapter\\>\\>"));
		assertTrue(output.contains("\"AdapterTest.Core\" -> \"AdapterTest.ToAdapt\" [label = \"\\<\\<adapts\\>\\>"));
	}
	
}

