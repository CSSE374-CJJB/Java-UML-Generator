package edu.rosehulman.cjjb.javaModel.visitor;

import edu.rosehulman.cjjb.javaModel.Field;
import edu.rosehulman.cjjb.javaModel.Interface;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.Method;

import java.io.IOException;

import edu.rosehulman.cjjb.javaModel.Class;

public interface IUMLVisitor {
	void visitStart() throws IOException;

	void visit(Class clazz) throws IOException;

	void visit(Interface clazz) throws IOException;

	void visit(Field clazz) throws IOException;

	void visitEndFields() throws IOException;

	void visit(Method clazz) throws IOException;

	void visitEndStructure() throws IOException;

	void visitRelations(JavaModel model) throws IOException;

	void visitEnd() throws IOException;
}
