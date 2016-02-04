package edu.rosehulman.cjjb.javaModel.visitor;

import java.io.IOException;

import edu.rosehulman.cjjb.javaModel.JavaModel;

public interface ISequenceVisitor {
	void visit(JavaModel model) throws IOException;
}
