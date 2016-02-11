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
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
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
		classes.add("sampleClasses.ToAdapt");
		classes.add("sampleClasses.ToAdaptTo");
		classes.add("sampleClasses.ToAdaptToClass");
		classes.add("sampleClasses.Core");
		classes.add("sampleClasses.ToAdaptClass");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("sampleClasses.ToAdapt\\l\\<\\<adaptee\\>\\>"));
		assertTrue(output.contains("sampleClasses.ToAdaptTo\\l\\<\\<target\\>\\>"));
		assertTrue(output.contains("sampleClasses.Core\\l\\<\\<adapter\\>\\>"));
		assertTrue(output.contains("\"sampleClasses.Core\" -> \"sampleClasses.ToAdapt\" [label = \"\\<\\<adapts\\>\\>"));
	}
	
}

