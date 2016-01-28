package edu.rosehulman.cjjb.javaModel.visitor;

import edu.rosehulman.cjjb.javaModel.JavaField;
import edu.rosehulman.cjjb.javaModel.JavaInterface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.JavaMethod;

import java.io.IOException;

import edu.rosehulman.cjjb.javaModel.JavaClass;

public interface IUMLVisitor {
	void visitStart() throws IOException;

	void visit(JavaClass clazz) throws IOException;

	void visit(JavaInterface clazz) throws IOException;

	void visit(JavaField clazz) throws IOException;

	void visitEndFields() throws IOException;

	void visit(JavaMethod clazz) throws IOException;

	void visitEndStructure() throws IOException;

	void visitRelations(JavaModel model) throws IOException;

	void visitEnd() throws IOException;

	void visitPatterns(JavaModel javaModel) throws IOException;
}
