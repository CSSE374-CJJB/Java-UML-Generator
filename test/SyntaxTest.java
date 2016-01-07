import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import edu.rosehulman.cjjb.UMLClassVisitor;

public class SyntaxTest {
	
	@Test
	public void testSyntax() throws IOException {
		String[] classes = new String[] {"sampleClasses.Class1", "sampleClasses.Class2", "sampleClasses.Inter1"};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		UMLClassVisitor visitor = new UMLClassVisitor(classes, out);
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
	
}
