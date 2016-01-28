import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.JavaModelClassVisitor;

public class AdapterTest {

	@Test
	public void testCheckForAdapters() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("problem.client.App");
		classes.add("problem.client.IteratorToEnumerationAdapter");
		classes.add("problem.lib.LinearTransformer");
		OutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		String output = out.toString();
		
		assertTrue(output.contains("problem.client.IteratorToEnumerationAdapter\\l\\<\\<adapter\\>\\>"));
		assertTrue(output.contains("java.util.Enumeration\\l\\<\\<adaptee\\>\\>"));
		assertTrue(output.contains("java.util.Iterator\\l\\<\\<target\\>\\>"));
		assertTrue(output.contains("problem.client.IteratorToEnumerationAdapter -> java.util.Enumeration [label = \\<\\<adapts\\>\\>]"));

		assertTrue(!output.contains("problem.lib.LinearTransformer\\l\\<\\<decorator\\>\\>"));
		assertTrue(!output.contains("problem.client.App\\l\\<\\<decorator\\>\\>"));
	}
	
}

