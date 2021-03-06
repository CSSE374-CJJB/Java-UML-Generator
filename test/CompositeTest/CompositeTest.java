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
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
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
		classes.add("sampleClasses.IComponent");
		classes.add("sampleClasses.TopComponent");
		classes.add("sampleClasses.SecondComponent");
		classes.add("sampleClasses.ThirdComponent");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("sampleClasses.IComponent\\l\\<\\<Component\\>\\>"));
		assertTrue(output.contains("sampleClasses.TopComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("sampleClasses.SecondComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("sampleClasses.ThirdComponent\\l\\<\\<Leaf\\>\\>"));
	}
	
	@Test
	public void testCheckAgainForComposites() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.IComponent2");
		classes.add("sampleClasses.TopComponent2");
		classes.add("sampleClasses.OtherTopComponent2");
		classes.add("sampleClasses.Leaf");
		classes.add("sampleClasses.Leaf2");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("sampleClasses.IComponent2\\l\\<\\<Component\\>\\>"));
		assertTrue(output.contains("sampleClasses.TopComponent2\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("sampleClasses.OtherTopComponent2\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("sampleClasses.Leaf\\l\\<\\<Leaf\\>\\>"));
		assertTrue(output.contains("sampleClasses.Leaf2\\l\\<\\<Leaf\\>\\>"));
	}
	
	@Test
	public void testNonComposite() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.Decorator");
		classes.add("sampleClasses.InitialDecorator");
		classes.add("sampleClasses.SecondLevelDecorator");
		classes.add("sampleClasses.ThirdLevelDecorator");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(!output.contains("sampleClasses.Decorator\\l\\<\\<Component\\>\\>"));
		assertTrue(!output.contains("sampleClasses.InitialDecorator\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("sampleClasses.SecondLevelDecorator\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("sampleClasses.ThirdLevelDecorator\\l\\<\\<Leaf\\>\\>"));
		assertTrue(!output.contains("sampleClasses.Leaf2\\l\\<\\<Leaf\\>\\>"));
	}
	
	@Test
	public void testOtherNonComposite() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.IBrokenComponent");
		classes.add("sampleClasses.BrokenTopComponent");
		classes.add("sampleClasses.BrokenSecondComponent");
		classes.add("sampleClasses.BrokenThirdComponent");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(!output.contains("sampleClasses.IBrokenComponent\\l\\<\\<Component\\>\\>"));
		assertTrue(!output.contains("sampleClasses.BrokenTopComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("sampleClasses.BrokenSecondComponent\\l\\<\\<Composite\\>\\>"));
		assertTrue(!output.contains("sampleClasses.BrokenThirdComponent\\l\\<\\<Leaf\\>\\>"));
		assertTrue(!output.contains("sampleClasses.Leaf2\\l\\<\\<Leaf\\>\\>"));
	}
}
