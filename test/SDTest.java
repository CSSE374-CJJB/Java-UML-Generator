import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.JavaModelClassVisitor;
import edu.rosehulman.cjjb.asm.QualifiedMethod;
import edu.rosehulman.cjjb.javaModel.visitor.ISequenceVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.SDSequenceVisitor;

public class SDTest {
	@Test
	public void testSDDiagram() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("Class1");
		classes.add("Class2");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor vis = new JavaModelClassVisitor(classes, "sampleClasses.Class1", new QualifiedMethod("publicVoidMethod", "TO CHANGE"), 2);
		vis.buildSeqModel();
		ISequenceVisitor seqVisitor = new SDSequenceVisitor("sampleClasses.Class1", new QualifiedMethod("publicVoidMethod", "TO CHANGE"), 2, out);
		vis.getModel().accept(seqVisitor);
		String result = new String(out.toByteArray());
		
		System.out.println(result);
		
		assertTrue(result.contains("sampleClasses\\.Class1:sampleClasses\\.Class1"));
		assertTrue(result.contains("sampleClasses\\.Class2:sampleClasses\\.Class2"));
		
		assertTrue(result.contains("sampleClasses\\.Class1:void=sampleClasses\\.Class2.protectedVoidMethod()"));
		assertTrue(result.contains("sampleClasses\\.Class2:sampleClasses.Class2=sampleClasses\\.Class1.getClass2()"));
	}
}
