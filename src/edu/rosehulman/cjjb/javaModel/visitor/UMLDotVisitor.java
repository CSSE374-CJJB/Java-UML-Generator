package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.Class;
import edu.rosehulman.cjjb.javaModel.Field;
import edu.rosehulman.cjjb.javaModel.Interface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;
import edu.rosehulman.cjjb.javaModel.Relation;
import edu.rosehulman.cjjb.javaModel.checks.IModelStructureBooleanCheck;
import edu.rosehulman.cjjb.javaModel.checks.SingletonCheck;
import edu.rosehulman.cjjb.javaModel.modifier.IAccessModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PrivateModifier;
import edu.rosehulman.cjjb.javaModel.modifier.ProtectedModifier;
import edu.rosehulman.cjjb.javaModel.modifier.ProtectedPrivateModifier;
import edu.rosehulman.cjjb.javaModel.modifier.PublicModifier;

public class UMLDotVisitor implements IUMLVisitor {

	private OutputStream out;
	private JavaModel model;

	public static final String BOILER_PLATE = "digraph G { fontname = \"Bitstream Vera Sans\" fontsize = 8 node [ fontname = \"Bitstream Vera Sans\" fontsize = 8 shape = \"record\" ] edge [ fontname = \"Bitstream Vera Sans\" fontsize = 8 ]\n";

	
	
	public UMLDotVisitor(OutputStream out, JavaModel model) {
		this.out = out;
	}

	@Override
	public void visitStart() throws IOException {
		out.write(BOILER_PLATE.getBytes());
	}

	@Override
	public void visit(Class clazz) throws IOException {
		out.write(String.format("\"%s\"", clazz.name).getBytes());
		
		IModelStructureBooleanCheck singleton = new SingletonCheck();
		String extraAfter = "";
		
		if(singleton.check(model, clazz))
			extraAfter += "\\l\\<\\<Singleton\\>\\>";
		
		out.write(String.format(" [ label = \"{%s%s|", clazz.name, extraAfter).getBytes());
	}

	@Override
	public void visit(Interface clazz) throws IOException {
		out.write(String.format("\"%s\"", clazz.name).getBytes());
		out.write(String.format(" [ label = \"{\\<\\<interface\\>\\>\\l%s|", clazz.name).getBytes());
	}

	@Override
	public void visit(Field clazz) throws IOException {
		out.write(String.format("%s%s : %s\\l", getAccessModifierString(clazz.access), clazz.name, clazz.type.name)
				.getBytes());
	}

	@Override
	public void visitEndFields() throws IOException {
		out.write("|".getBytes());
	}

	@Override
	public void visit(Method clazz) throws IOException {
		out.write(String.format("%s%s(%s) : %s\\l", getAccessModifierString(clazz.access), clazz.name.replace("<", "\\<").replace(">", "\\>"),
				getArgumentString(clazz.arguments), clazz.type.name).getBytes());
	}

	@Override
	public void visitEndStructure() throws IOException {
		out.write("}\" ]\n".getBytes());
	}

	@Override
	public void visitEnd() throws IOException {
		out.write("}".getBytes());
	}

	private String getAccessModifierString(IAccessModifier modifier) {
		if (modifier instanceof PrivateModifier) {
			return "- ";
		} else if (modifier instanceof ProtectedPrivateModifier) {
			return "";
		} else if (modifier instanceof ProtectedModifier) {
			return "# ";
		} else if (modifier instanceof PublicModifier) {
			return "+ ";
		}

		return "";
	}

	private String getArgumentString(List<AbstractJavaStructure> args) {
		List<String> names = new ArrayList<String>();
		for (AbstractJavaStructure struct : args)
			names.add(struct.name);

		return String.join(", ", names);
	}

	@Override
	public void visitRelations(JavaModel model) throws IOException {

		// Child Parent
		for (Relation relation : model.getChildParrentIncludedRelations()) {
			out.write(String.format("\"%s\"" + " -> \"%s\" [arrowhead=\"onormal\", style=\"filled\"]\n",
					relation.base.name, relation.other.name).getBytes());
		}

		// Implements
		for (Relation relation : model.getIncludedInterfaceRelations()) {
			out.write(String.format("\"%s\"" + " -> \"%s\" [arrowhead=\"onormal\", style=\"dashed\"]\n",
					relation.base.name, relation.other.name).getBytes());
		}

		// Uses
		for (Relation relation : model.getIncludedUsesRelations()) {
			out.write(String.format("\"%s\"" + " -> \"%s\" [arrowhead=\"vee\", style=\"dashed\"]\n", relation.base.name,
					relation.other.name).getBytes());
		}

		// Association
		for (Relation relation : model.getIncludedAssociationReltiations()) {
			out.write(String.format("\"%s\"" + " -> \"%s\" [arrowhead=\"vee\", style=\"filled\"]\n", relation.base.name,
					relation.other.name).getBytes());
		}
	}
}
