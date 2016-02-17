package GeneralTests;
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
		String clazz = "GeneralTests.Class1";
		QualifiedMethod method = new QualifiedMethod("publicVoidMethod", "()V");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor vis = new JavaModelClassVisitor(clazz, method, 2);
		vis.buildSeqModelDefault();
		ISequenceVisitor seqVisitor = new SDSequenceVisitor(clazz, method, 2, out);
		vis.getModel().accept(seqVisitor);
		String result = new String(out.toByteArray());
		
		System.out.println(result);
		
		assertTrue(result.contains("GeneralTests\\.Class1:GeneralTests\\.Class1"));
		assertTrue(result.contains("GeneralTests\\.Class2:GeneralTests\\.Class2"));
		
		assertTrue(result.contains("GeneralTests\\.Class1:void=GeneralTests\\.Class2.protectedVoidMethod()"));
		assertTrue(result.contains("GeneralTests\\.Class2:GeneralTests.Class2=GeneralTests\\.Class1.getClass2()"));
	}
}
