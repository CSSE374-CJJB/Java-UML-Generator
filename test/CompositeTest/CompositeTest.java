package CompositeTest;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.JavaModelClassVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.UMLDotVisitor;

public class CompositeTest {
	@Test
	public void testCheckForComposites() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("problem.sprites.CompositeSprite");
		classes.add("problem.sprites.CompositeSpriteIterator");
		classes.add("problem.sprites.CircleSprite");
		classes.add("problem.sprites.AbstractSprite");
		classes.add("problem.sprites.CrystalBall");
		classes.add("problem.sprites.ISprite");
		classes.add("problem.sprites.NullSpriteIterator");
		classes.add("problem.sprites.RectangleSprite");
		classes.add("problem.sprites.RectangleTower");
		classes.add("problem.sprites.SpriteFactory");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("problem.sprites.RectangleSprite\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.CircleSprite\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.RectangleTower\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.AbstractSprite\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.CompositeSprite\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.CrystalBall\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.ISprite\\l\\<\\<Component\\>\\>"));
	}
	
	@Test
	public void testCheckForOtherComposites() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("CompositeTest.IComponent");
		classes.add("CompositeTest.TopComponent");
		classes.add("CompositeTest.SecondComponent");
		classes.add("CompositeTest.ThirdComponent");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("CompositeTest.IComponent\\l\\<\\<Component\\>\\>"));
		assertTrue(output.contains("CompositeTest.TopComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("CompositeTest.SecondComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("CompositeTest.ThirdComponent\\l\\<\\<Leaf\\>\\>"));
	}
	
	@Test
	public void testCheckAgainForComposites() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("CompositeTest.IComponent2");
		classes.add("CompositeTest.TopComponent2");
		classes.add("CompositeTest.OtherTopComponent2");
		classes.add("CompositeTest.Leaf");
		classes.add("CompositeTest.Leaf2");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("CompositeTest.IComponent2\\l\\<\\<Component\\>\\>"));
		assertTrue(output.contains("CompositeTest.TopComponent2\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("CompositeTest.OtherTopComponent2\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("CompositeTest.Leaf\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("CompositeTest.Leaf2\\l\\<\\<Composite\\>\\>"));
	}
	
	@Test
	public void testNonComposite() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("CompositeTest.Decorator");
		classes.add("CompositeTest.InitialDecorator");
		classes.add("CompositeTest.SecondLevelDecorator");
		classes.add("CompositeTest.ThirdLevelDecorator");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(!output.contains("CompositeTest.Decorator\\l\\<\\<Component\\>\\>"));
		assertTrue(!output.contains("CompositeTest.InitialDecorator\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("CompositeTest.SecondLevelDecorator\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("CompositeTest.ThirdLevelDecorator\\l\\<\\<Leaf\\>\\>"));
		assertTrue(!output.contains("CompositeTest.Leaf2\\l\\<\\<Leaf\\>\\>"));
	}
	
	@Test
	public void testOtherNonComposite() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("CompositeTest.IBrokenComponent");
		classes.add("CompositeTest.BrokenTopComponent");
		classes.add("CompositeTest.BrokenSecondComponent");
		classes.add("CompositeTest.BrokenThirdComponent");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(!output.contains("CompositeTest.IBrokenComponent\\l\\<\\<Component\\>\\>"));
		assertTrue(!output.contains("CompositeTest.BrokenTopComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("CompositeTest.BrokenSecondComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("CompositeTest.BrokenThirdComponent\\l\\<\\<Leaf\\>\\>"));
		assertTrue(!output.contains("CompositeTest.Leaf2\\l\\<\\<Leaf\\>\\>"));
	}
}
