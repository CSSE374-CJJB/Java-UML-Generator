package DecoratorTest;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.rosehulman.cjjb.JavaModelClassVisitor;
import edu.rosehulman.cjjb.javaModel.visitor.UMLDotVisitor;

public class DecoratorTest {

	@Test
	public void testCheckForDecorators() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("headfirst.decorator.io.InputTest");
		classes.add("headfirst.decorator.io.LowerCaseInputStream");
		classes.add("headfirst.decorator.starbuzz.Beverage");
		classes.add("headfirst.decorator.starbuzz.CondimentDecorator");
		classes.add("headfirst.decorator.starbuzz.DarkRoast");
		classes.add("headfirst.decorator.starbuzz.Decaf");
		classes.add("headfirst.decorator.starbuzz.Espresso");
		classes.add("headfirst.decorator.starbuzz.HouseBlend");
		classes.add("headfirst.decorator.starbuzz.Milk");
		classes.add("headfirst.decorator.starbuzz.Mocha");
		classes.add("headfirst.decorator.starbuzz.Soy");
		classes.add("headfirst.decorator.starbuzz.StarbuzzCoffee");
		classes.add("headfirst.decorator.starbuzz.Whip");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("headfirst.decorator.starbuzz.Beverage\\l\\<\\<component\\>\\>"));
		assertTrue(output.contains("headfirst.decorator.starbuzz.Milk\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("headfirst.decorator.starbuzz.Mocha\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("headfirst.decorator.starbuzz.Soy\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("headfirst.decorator.starbuzz.Whip\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("headfirst.decorator.starbuzz.CondimentDecorator\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("\"headfirst.decorator.starbuzz.CondimentDecorator\" -> \"headfirst.decorator.starbuzz.Beverage\" [label = \"\\<\\<decorates\\>\\>\"]"));

		assertTrue(!output.contains("headfirst.decorator.starbuzz.StarbuzzCoffee\\l\\<\\<decorator\\>\\>"));
		assertTrue(!output.contains("headfirst.decorator.io.InputTest\\l\\<\\<decorator\\>\\>"));
	}
	
	@Test
	public void testCheckForOtherDecorators() throws IOException {
		Set<String> classes = new HashSet<String>();
		classes.add("sampleClasses.Decorator2");
		classes.add("sampleClasses.InitialDecorator2");
		classes.add("sampleClasses.SecondLevelDecorator2");
		classes.add("sampleClasses.ThirdLevelDecorator2");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JavaModelClassVisitor visitor = new JavaModelClassVisitor(classes);
		visitor.buildUMLModelDefault();
		
		UMLDotVisitor umlVis = new UMLDotVisitor(out, visitor.getModel());
		visitor.getModel().accept(umlVis);
		String output = new String(out.toByteArray());
		
		System.out.println("\n\n");
		System.out.println(output);
		
		assertTrue(output.contains("sampleClasses.Decorator2\\l\\<\\<component\\>\\>"));
		assertTrue(output.contains("sampleClasses.InitialDecorator2\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("sampleClasses.SecondLevelDecorator2\\l\\<\\<decorator\\>\\>"));
		assertTrue(output.contains("sampleClasses.ThirdLevelDecorator2\\l\\<\\<decorator\\>\\>"));

		assertTrue(output.contains("\"sampleClasses.InitialDecorator2\" -> \"sampleClasses.Decorator2\" [label = \"\\<\\<decorates\\>\\>"));
		assertTrue(output.contains("\"sampleClasses.SecondLevelDecorator2\" -> \"sampleClasses.Decorator2\" [label = \"\\<\\<decorates\\>\\>"));
		assertTrue(output.contains("\"sampleClasses.ThirdLevelDecorator2\" -> \"sampleClasses.Decorator2\" [label = \"\\<\\<decorates\\>\\>"));
	}
	
}
