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
		assertTrue(output.contains("problem.sprites.RectangleTower\\l\\<\\<decorator\\>\\>\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.AbstractSprite\\l\\<\\<decorator\\>\\>\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.CompositeSprite\\l\\<\\<decorator\\>\\>\\l\\<\\<Composite\\>\\>"));
		assertTrue(output.contains("problem.sprites.CrystalBall\\l\\<\\<decorator\\>\\>\\l\\<\\<Leaf\\>\\>"));
		assertTrue(output.contains("problem.sprites.ISprite\\l\\<\\<component\\>\\>"));
	}
	
	@Test
	public void testCheckForOtherComposites() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.IComponent");
		classes.add("sampleClasses.TopComponent");
		classes.add("sampleClasses.SecondComponent");
		classes.add("sampleClasses.ThirdComponentLeaf");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes, out);
		visitor.buildUMLModel();
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		
	}
}
