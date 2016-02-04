package edu.rosehulman.cjjb.javaModel.visitor;

import java.util.List;

import edu.rosehulman.cjjb.javaModel.AbstractJavaStructure;
import edu.rosehulman.cjjb.javaModel.JavaModel;
import edu.rosehulman.cjjb.javaModel.checks.IPattern;

public interface IStructureVisitor {

	List<IPattern> visit(JavaModel model, AbstractJavaStructure s);

}
